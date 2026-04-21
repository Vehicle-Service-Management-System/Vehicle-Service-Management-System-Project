const express = require('express');
const bcrypt = require('bcryptjs');
const { getDb } = require('../db');
const { authenticate, requireRole } = require('../middleware/auth');

const router = express.Router();

// All routes require authentication
router.use(authenticate);

/**
 * GET /api/users
 * List all users for the current centre (owner/manager only)
 */
router.get('/', requireRole('owner', 'manager'), (req, res) => {
  try {
    const db = getDb();
    const users = db.prepare(`
      SELECT u.id, u.name, u.role, u.phone, u.address, u.join_date, u.personal_notes,
        (SELECT COUNT(*) FROM services s WHERE s.added_by = u.id) as services_count
      FROM users u
      WHERE u.centre_id = ?
      ORDER BY u.role, u.name
    `).all(req.user.centreId);

    res.json({ users });
  } catch (err) {
    console.error('Get users error:', err);
    res.status(500).json({ error: 'Failed to fetch users.' });
  }
});

/**
 * POST /api/users
 * Add new employee or manager (owner only)
 * Body: { name, role, phone, address, password, personalNotes }
 */
router.post('/', requireRole('owner'), (req, res) => {
  try {
    const { name, role, phone, address, password, personalNotes } = req.body;

    if (!name || !role || !phone || !password) {
      return res.status(400).json({ error: 'Name, role, phone, and password are required.' });
    }
    if (!['manager', 'employee'].includes(role)) {
      return res.status(400).json({ error: 'Role must be manager or employee.' });
    }
    if (password.length < 6) {
      return res.status(400).json({ error: 'Password must be at least 6 characters.' });
    }

    const db = getDb();

    // Check if user with same name and role already exists in this centre
    const existing = db.prepare(
      'SELECT id FROM users WHERE centre_id = ? AND name = ? AND role = ?'
    ).get(req.user.centreId, name, role);

    if (existing) {
      return res.status(409).json({ error: 'A user with this name and role already exists.' });
    }

    const passwordHash = bcrypt.hashSync(password, 10);
    const result = db.prepare(
      'INSERT INTO users (centre_id, name, role, phone, address, password_hash, personal_notes) VALUES (?, ?, ?, ?, ?, ?, ?)'
    ).run(req.user.centreId, name, role, phone, address || '', passwordHash, personalNotes || '');

    res.status(201).json({
      message: 'User created successfully.',
      user: { id: result.lastInsertRowid, name, role, phone }
    });
  } catch (err) {
    console.error('Create user error:', err);
    res.status(500).json({ error: 'Failed to create user.' });
  }
});

/**
 * PUT /api/users/:id
 * Update user details (owner only)
 */
router.put('/:id', requireRole('owner'), (req, res) => {
  try {
    const { name, phone, address, personalNotes, role } = req.body;
    const userId = req.params.id;
    const db = getDb();

    // Verify user belongs to this centre
    const user = db.prepare('SELECT * FROM users WHERE id = ? AND centre_id = ?').get(userId, req.user.centreId);
    if (!user) {
      return res.status(404).json({ error: 'User not found.' });
    }

    db.prepare(`
      UPDATE users SET
        name = COALESCE(?, name),
        phone = COALESCE(?, phone),
        address = COALESCE(?, address),
        personal_notes = COALESCE(?, personal_notes),
        role = COALESCE(?, role)
      WHERE id = ? AND centre_id = ?
    `).run(name || null, phone || null, address || null, personalNotes || null, role || null, userId, req.user.centreId);

    res.json({ message: 'User updated successfully.' });
  } catch (err) {
    console.error('Update user error:', err);
    res.status(500).json({ error: 'Failed to update user.' });
  }
});

/**
 * PUT /api/centre
 * Update centre settings (owner only)
 */
router.put('/centre/settings', requireRole('owner'), (req, res) => {
  try {
    const { name, address, phone } = req.body;
    const db = getDb();

    db.prepare(`
      UPDATE service_centres SET
        name = COALESCE(?, name),
        address = COALESCE(?, address),
        phone = COALESCE(?, phone)
      WHERE id = ?
    `).run(name || null, address || null, phone || null, req.user.centreId);

    res.json({ message: 'Centre updated successfully.' });
  } catch (err) {
    console.error('Update centre error:', err);
    res.status(500).json({ error: 'Failed to update centre.' });
  }
});

module.exports = router;
