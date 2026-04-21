const express = require('express');
const { getDb } = require('../db');
const { authenticate, requireRole } = require('../middleware/auth');

const router = express.Router();

router.use(authenticate);

/**
 * POST /api/services
 * Log a new service
 * Body: { vehicle_id, description, status, cost, notes }
 */
router.post('/', (req, res) => {
  try {
    const { vehicle_id, description, status, cost, notes } = req.body;

    if (!vehicle_id || !description) {
      return res.status(400).json({ error: 'Vehicle and description are required.' });
    }

    const db = getDb();

    // Verify vehicle belongs to this centre
    const vehicle = db.prepare('SELECT id FROM vehicles WHERE id = ? AND centre_id = ?').get(vehicle_id, req.user.centreId);
    if (!vehicle) {
      return res.status(404).json({ error: 'Vehicle not found.' });
    }

    const result = db.prepare(
      'INSERT INTO services (vehicle_id, centre_id, description, status, cost, added_by, notes) VALUES (?, ?, ?, ?, ?, ?, ?)'
    ).run(vehicle_id, req.user.centreId, description, status || 'pending', cost || 0, req.user.userId, notes || '');

    const service = db.prepare(`
      SELECT s.*, v.make, v.model, v.reg_number, c.name as customer_name, u.name as added_by_name
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      JOIN customers c ON v.customer_id = c.id
      LEFT JOIN users u ON s.added_by = u.id
      WHERE s.id = ?
    `).get(result.lastInsertRowid);

    res.status(201).json({ message: 'Service logged successfully.', service });
  } catch (err) {
    console.error('Add service error:', err);
    res.status(500).json({ error: 'Failed to log service.' });
  }
});

/**
 * GET /api/services
 * List services with optional filters
 * Query: ?employee=&status=&vehicle_type=&from=&to=
 */
router.get('/', (req, res) => {
  try {
    const { employee, status, vehicle_type, from, to } = req.query;
    const db = getDb();

    let sql = `
      SELECT s.*, v.make, v.model, v.reg_number, v.vehicle_type,
        c.name as customer_name, c.phone as customer_phone,
        u.name as added_by_name
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      JOIN customers c ON v.customer_id = c.id
      LEFT JOIN users u ON s.added_by = u.id
      WHERE s.centre_id = ?
    `;
    const params = [req.user.centreId];

    if (employee) {
      sql += ' AND s.added_by = ?';
      params.push(employee);
    }
    if (status) {
      sql += ' AND s.status = ?';
      params.push(status);
    }
    if (vehicle_type) {
      sql += ' AND v.vehicle_type = ?';
      params.push(vehicle_type);
    }
    if (from) {
      sql += ' AND s.service_date >= ?';
      params.push(from);
    }
    if (to) {
      sql += ' AND s.service_date <= ?';
      params.push(to);
    }

    sql += ' ORDER BY s.service_date DESC';

    const services = db.prepare(sql).all(...params);
    res.json({ services });
  } catch (err) {
    console.error('Get services error:', err);
    res.status(500).json({ error: 'Failed to fetch services.' });
  }
});

/**
 * GET /api/services/my
 * Services added by current user
 */
router.get('/my', (req, res) => {
  try {
    const db = getDb();
    const services = db.prepare(`
      SELECT s.*, v.make, v.model, v.reg_number, v.vehicle_type,
        c.name as customer_name
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      JOIN customers c ON v.customer_id = c.id
      WHERE s.added_by = ? AND s.centre_id = ?
      ORDER BY s.service_date DESC
    `).all(req.user.userId, req.user.centreId);

    res.json({ services });
  } catch (err) {
    console.error('Get my services error:', err);
    res.status(500).json({ error: 'Failed to fetch your services.' });
  }
});

/**
 * GET /api/services/reports
 * Aggregated report data (owner/manager)
 */
router.get('/reports', requireRole('owner', 'manager'), (req, res) => {
  try {
    const db = getDb();
    const { from, to, employee, vehicle_type, status } = req.query;

    let whereClause = 's.centre_id = ?';
    const params = [req.user.centreId];

    if (from) { whereClause += ' AND s.service_date >= ?'; params.push(from); }
    if (to) { whereClause += ' AND s.service_date <= ?'; params.push(to); }
    if (employee) { whereClause += ' AND s.added_by = ?'; params.push(employee); }
    if (vehicle_type) { whereClause += ' AND v.vehicle_type = ?'; params.push(vehicle_type); }
    if (status) { whereClause += ' AND s.status = ?'; params.push(status); }

    // Summary stats
    const summary = db.prepare(`
      SELECT
        COUNT(*) as total_services,
        COALESCE(SUM(s.cost), 0) as total_revenue,
        COUNT(DISTINCT v.id) as unique_vehicles,
        COUNT(DISTINCT v.customer_id) as unique_customers,
        SUM(CASE WHEN s.status = 'in_progress' THEN 1 ELSE 0 END) as in_progress,
        SUM(CASE WHEN s.status = 'completed' THEN 1 ELSE 0 END) as completed,
        SUM(CASE WHEN s.status = 'pending' THEN 1 ELSE 0 END) as pending
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      WHERE ${whereClause}
    `).get(...params);

    // By status
    const byStatus = db.prepare(`
      SELECT s.status, COUNT(*) as count, COALESCE(SUM(s.cost), 0) as revenue
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      WHERE ${whereClause}
      GROUP BY s.status
    `).all(...params);

    // By employee
    const byEmployee = db.prepare(`
      SELECT u.name, COUNT(*) as count, COALESCE(SUM(s.cost), 0) as revenue
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      LEFT JOIN users u ON s.added_by = u.id
      WHERE ${whereClause}
      GROUP BY s.added_by
      ORDER BY count DESC
    `).all(...params);

    // By vehicle type
    const byVehicleType = db.prepare(`
      SELECT v.vehicle_type, COUNT(*) as count, COALESCE(SUM(s.cost), 0) as revenue
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      WHERE ${whereClause}
      GROUP BY v.vehicle_type
    `).all(...params);

    // Actual services list for report table
    const services = db.prepare(`
      SELECT s.*, v.make, v.model, v.reg_number, v.vehicle_type,
        c.name as customer_name, u.name as added_by_name
      FROM services s
      JOIN vehicles v ON s.vehicle_id = v.id
      JOIN customers c ON v.customer_id = c.id
      LEFT JOIN users u ON s.added_by = u.id
      WHERE ${whereClause}
      ORDER BY s.service_date DESC
    `).all(...params);

    res.json({ summary, byStatus, byEmployee, byVehicleType, services });
  } catch (err) {
    console.error('Get reports error:', err);
    res.status(500).json({ error: 'Failed to fetch reports.' });
  }
});

/**
 * PUT /api/services/:id
 * Update service status/details
 */
router.put('/:id', (req, res) => {
  try {
    const { status, cost, notes, description } = req.body;
    const db = getDb();

    const service = db.prepare('SELECT * FROM services WHERE id = ? AND centre_id = ?').get(req.params.id, req.user.centreId);
    if (!service) {
      return res.status(404).json({ error: 'Service not found.' });
    }

    db.prepare(`
      UPDATE services SET
        status = COALESCE(?, status),
        cost = COALESCE(?, cost),
        notes = COALESCE(?, notes),
        description = COALESCE(?, description)
      WHERE id = ? AND centre_id = ?
    `).run(status || null, cost || null, notes || null, description || null, req.params.id, req.user.centreId);

    res.json({ message: 'Service updated.' });
  } catch (err) {
    console.error('Update service error:', err);
    res.status(500).json({ error: 'Failed to update service.' });
  }
});

module.exports = router;
