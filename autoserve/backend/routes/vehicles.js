const express = require('express');
const { getDb } = require('../db');
const { authenticate } = require('../middleware/auth');

const router = express.Router();

router.use(authenticate);

/**
 * GET /api/vehicles?customer_id=
 * List vehicles for a customer
 */
router.get('/', (req, res) => {
  try {
    const { customer_id } = req.query;
    const db = getDb();

    let vehicles;
    if (customer_id) {
      vehicles = db.prepare(`
        SELECT v.*, u.name as added_by_name
        FROM vehicles v
        LEFT JOIN users u ON v.added_by = u.id
        WHERE v.customer_id = ? AND v.centre_id = ?
        ORDER BY v.created_at DESC
      `).all(customer_id, req.user.centreId);
    } else {
      vehicles = db.prepare(`
        SELECT v.*, c.name as customer_name, u.name as added_by_name
        FROM vehicles v
        JOIN customers c ON v.customer_id = c.id
        LEFT JOIN users u ON v.added_by = u.id
        WHERE v.centre_id = ?
        ORDER BY v.created_at DESC
      `).all(req.user.centreId);
    }

    res.json({ vehicles });
  } catch (err) {
    console.error('Get vehicles error:', err);
    res.status(500).json({ error: 'Failed to fetch vehicles.' });
  }
});

/**
 * GET /api/vehicles/:id
 * Get vehicle details with service history
 */
router.get('/:id', (req, res) => {
  try {
    const db = getDb();
    const vehicle = db.prepare(`
      SELECT v.*, c.name as customer_name, c.phone as customer_phone
      FROM vehicles v
      JOIN customers c ON v.customer_id = c.id
      WHERE v.id = ? AND v.centre_id = ?
    `).get(req.params.id, req.user.centreId);

    if (!vehicle) {
      return res.status(404).json({ error: 'Vehicle not found.' });
    }

    const services = db.prepare(`
      SELECT s.*, u.name as added_by_name
      FROM services s
      LEFT JOIN users u ON s.added_by = u.id
      WHERE s.vehicle_id = ? AND s.centre_id = ?
      ORDER BY s.service_date DESC
    `).all(vehicle.id, req.user.centreId);

    res.json({ vehicle, services });
  } catch (err) {
    console.error('Get vehicle error:', err);
    res.status(500).json({ error: 'Failed to fetch vehicle.' });
  }
});

/**
 * POST /api/vehicles
 * Add a new vehicle
 * Body: { customer_id, make, model, year, reg_number, vehicle_type }
 */
router.post('/', (req, res) => {
  try {
    const { customer_id, make, model, year, reg_number, vehicle_type } = req.body;

    if (!customer_id || !make || !model || !reg_number) {
      return res.status(400).json({ error: 'Customer, make, model, and registration number are required.' });
    }

    const db = getDb();

    // Verify customer belongs to this centre
    const customer = db.prepare('SELECT id FROM customers WHERE id = ? AND centre_id = ?').get(customer_id, req.user.centreId);
    if (!customer) {
      return res.status(404).json({ error: 'Customer not found.' });
    }

    const result = db.prepare(
      'INSERT INTO vehicles (customer_id, centre_id, make, model, year, reg_number, vehicle_type, added_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)'
    ).run(customer_id, req.user.centreId, make, model, year || null, reg_number, vehicle_type || 'car', req.user.userId);

    const vehicle = db.prepare('SELECT * FROM vehicles WHERE id = ?').get(result.lastInsertRowid);

    res.status(201).json({ message: 'Vehicle added.', vehicle });
  } catch (err) {
    console.error('Add vehicle error:', err);
    res.status(500).json({ error: 'Failed to add vehicle.' });
  }
});

module.exports = router;
