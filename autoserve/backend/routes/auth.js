const express = require('express');
const bcrypt = require('bcryptjs');
const { getDb } = require('../db');
const { authenticate, generateToken } = require('../middleware/auth');

const router = express.Router();

/**
 * POST /api/register
 * Create a new service centre + owner account
 * Body: { centre: { name, address, phone }, owner: { name, phone, password } }
 * Also accepts flat: { centreName, centreAddress, centrePhone, ownerName, ownerPhone, password }
 */
router.post('/register', (req, res) => {
  try {
    let centreName, centreAddress, centrePhone, ownerName, ownerPhone, password;

    // Accept both nested and flat payload formats
    if (req.body.centre && req.body.owner) {
      centreName = req.body.centre.name;
      centreAddress = req.body.centre.address;
      centrePhone = req.body.centre.phone;
      ownerName = req.body.owner.name;
      ownerPhone = req.body.owner.phone;
      password = req.body.owner.password;
    } else {
      ({ centreName, centreAddress, centrePhone, ownerName, ownerPhone, password } = req.body);
    }

    // Validation
    if (!centreName || !centreAddress || !centrePhone || !ownerName || !ownerPhone || !password) {
      return res.status(400).json({ error: 'All fields are required.' });
    }
    if (password.length < 6) {
      return res.status(400).json({ error: 'Password must be at least 6 characters.' });
    }

    const db = getDb();

    // Create service centre
    const centreResult = db.prepare(
      'INSERT INTO service_centres (name, address, phone) VALUES (?, ?, ?)'
    ).run(centreName, centreAddress, centrePhone);

    const centreId = centreResult.lastInsertRowid;

    // Hash password and create owner
    const passwordHash = bcrypt.hashSync(password, 10);
    const userResult = db.prepare(
      'INSERT INTO users (centre_id, name, role, phone, password_hash) VALUES (?, ?, ?, ?, ?)'
    ).run(centreId, ownerName, 'owner', ownerPhone, passwordHash);

    const user = {
      id: userResult.lastInsertRowid,
      centre_id: centreId,
      name: ownerName,
      role: 'owner'
    };

    const token = generateToken(user);

    res.status(201).json({
      message: 'Service centre registered successfully.',
      token,
      user: {
        id: user.id,
        name: user.name,
        role: user.role,
        centreId: centreId,
        centre_name: centreName,
        centre_phone: centrePhone,
        centre_address: centreAddress
      }
    });
  } catch (err) {
    console.error('Register error:', err);
    res.status(500).json({ error: 'Registration failed. Please try again.' });
  }
});

/**
 * POST /api/login
 * Authenticate user by name + role + password
 * Body: { name, role, password }
 */
router.post('/login', (req, res) => {
  try {
    const { name, role, password } = req.body;

    if (!name || !role || !password) {
      return res.status(400).json({ error: 'Name, role, and password are required.' });
    }

    const db = getDb();
    const user = db.prepare(
      'SELECT * FROM users WHERE name = ? AND role = ?'
    ).get(name, role);

    if (!user) {
      return res.status(401).json({ error: 'Invalid credentials. User not found with that name and role.' });
    }

    const validPassword = bcrypt.compareSync(password, user.password_hash);
    if (!validPassword) {
      return res.status(401).json({ error: 'Invalid credentials. Wrong password.' });
    }

    // Get centre info for the response
    const centre = db.prepare('SELECT * FROM service_centres WHERE id = ?').get(user.centre_id);

    const token = generateToken(user);

    res.json({
      message: 'Login successful.',
      token,
      user: {
        id: user.id,
        name: user.name,
        role: user.role,
        centreId: user.centre_id,
        phone: user.phone,
        joinDate: user.join_date,
        centre_name: centre ? centre.name : '',
        centre_phone: centre ? centre.phone : '',
        centre_address: centre ? centre.address : ''
      }
    });
  } catch (err) {
    console.error('Login error:', err);
    res.status(500).json({ error: 'Login failed. Please try again.' });
  }
});

/**
 * GET /api/me
 * Get current user profile from token
 */
router.get('/me', authenticate, (req, res) => {
  try {
    const db = getDb();
    const user = db.prepare(
      'SELECT u.id, u.name, u.role, u.phone, u.address, u.join_date, u.personal_notes, u.centre_id, s.name as centre_name FROM users u JOIN service_centres s ON u.centre_id = s.id WHERE u.id = ?'
    ).get(req.user.userId);

    if (!user) {
      return res.status(404).json({ error: 'User not found.' });
    }

    res.json({ user });
  } catch (err) {
    console.error('Get profile error:', err);
    res.status(500).json({ error: 'Failed to fetch profile.' });
  }
});

module.exports = router;
