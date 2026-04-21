const express = require('express');
const { getDb } = require('../db');
const { authenticate } = require('../middleware/auth');

const router = express.Router();

router.use(authenticate);

/**
 * GET /api/customers
 * List all customers for the current centre
 */
router.get('/', (req, res) => {
  try {
    const db = getDb();
    const customers = db.prepare(`
      SELECT c.*,
        u.name as added_by_name,
        (SELECT COUNT(*) FROM vehicles v WHERE v.customer_id = c.id) as vehicle_count,
        (SELECT MAX(s.service_date) FROM services s
         JOIN vehicles v ON s.vehicle_id = v.id
         WHERE v.customer_id = c.id) as last_service_date
      FROM customers c
      LEFT JOIN users u ON c.added_by = u.id
      WHERE c.centre_id = ?
      ORDER BY c.created_at DESC
    `).all(req.user.centreId);

    res.json({ customers });
  } catch (err) {
    console.error('Get customers error:', err);
    res.status(500).json({ error: 'Failed to fetch customers.' });
  }
});

/**
 * GET /api/customers/search?q=
 * Search customers by name or phone
 */
router.get('/search', (req, res) => {
  try {
    const { q } = req.query;
    if (!q || q.length < 2) {
      return res.json({ customers: [] });
    }

    const db = getDb();
    const customers = db.prepare(`
      SELECT c.*,
        (SELECT COUNT(*) FROM vehicles v WHERE v.customer_id = c.id) as vehicle_count
      FROM customers c
      WHERE c.centre_id = ? AND (c.name LIKE ? OR c.phone LIKE ?)
      ORDER BY c.name
      LIMIT 20
    `).all(req.user.centreId, `%${q}%`, `%${q}%`);

    res.json({ customers });
  } catch (err) {
    console.error('Search customers error:', err);
    res.status(500).json({ error: 'Failed to search customers.' });
  }
});

/**
 * GET /api/customers/:id
 * Get customer with vehicles and service history
 * Returns: { customer, vehicles: [ { ...vehicle, services: [...] } ] }
 */
router.get('/:id', (req, res) => {
  try {
    const db = getDb();
    const customer = db.prepare(
      'SELECT c.*, u.name as added_by_name FROM customers c LEFT JOIN users u ON c.added_by = u.id WHERE c.id = ? AND c.centre_id = ?'
    ).get(req.params.id, req.user.centreId);

    if (!customer) {
      return res.status(404).json({ error: 'Customer not found.' });
    }

    // Get vehicles
    const vehicles = db.prepare(`
      SELECT v.*, vu.name as added_by_name
      FROM vehicles v
      LEFT JOIN users vu ON v.added_by = vu.id
      WHERE v.customer_id = ? AND v.centre_id = ?
      ORDER BY v.created_at DESC
    `).all(customer.id, req.user.centreId);

    // Nest services under each vehicle
    for (const vehicle of vehicles) {
      vehicle.services = db.prepare(`
        SELECT s.*, su.name as added_by_name
        FROM services s
        LEFT JOIN users su ON s.added_by = su.id
        WHERE s.vehicle_id = ? AND s.centre_id = ?
        ORDER BY s.service_date DESC
      `).all(vehicle.id, req.user.centreId);
    }

    // Return customer with vehicles nested (each vehicle has its services array)
    customer.vehicles = vehicles;
    res.json(customer);
  } catch (err) {
    console.error('Get customer error:', err);
    res.status(500).json({ error: 'Failed to fetch customer details.' });
  }
});

/**
 * POST /api/customers
 * Add a new customer
 * Body: { name, phone, email, address }
 */
router.post('/', (req, res) => {
  try {
    const { name, phone, email, address } = req.body;

    if (!name || !phone) {
      return res.status(400).json({ error: 'Name and phone are required.' });
    }

    const db = getDb();
    const result = db.prepare(
      'INSERT INTO customers (centre_id, name, phone, email, address, added_by) VALUES (?, ?, ?, ?, ?, ?)'
    ).run(req.user.centreId, name, phone, email || '', address || '', req.user.userId);

    const customer = db.prepare('SELECT * FROM customers WHERE id = ?').get(result.lastInsertRowid);

    res.status(201).json({ message: 'Customer added.', customer });
  } catch (err) {
    console.error('Add customer error:', err);
    res.status(500).json({ error: 'Failed to add customer.' });
  }
});

module.exports = router;
