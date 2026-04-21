const express = require('express');
const path = require('path');
const cors = require('cors');
const { getDb } = require('./db');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());

// Serve static frontend files
app.use(express.static(path.join(__dirname, '..', 'frontend')));

// Initialize database on startup
getDb();
console.log('[AutoServe] Database initialized.');

// API Routes
app.use('/api', require('./routes/auth'));
app.use('/api/users', require('./routes/users'));
app.use('/api/customers', require('./routes/customers'));
app.use('/api/vehicles', require('./routes/vehicles'));
app.use('/api/services', require('./routes/services'));

// SPA fallback — all non-API routes serve index.html
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, '..', 'frontend', 'index.html'));
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error('[AutoServe] Unhandled error:', err);
  res.status(500).json({ error: 'Internal server error.' });
});

app.listen(PORT, () => {
  console.log(`\n  ╔══════════════════════════════════════════╗`);
  console.log(`  ║          AUTOSERVE HUD ENGINE            ║`);
  console.log(`  ║     Vehicle Service Management System    ║`);
  console.log(`  ╠══════════════════════════════════════════╣`);
  console.log(`  ║  Status:  ONLINE                        ║`);
  console.log(`  ║  Port:    ${String(PORT).padEnd(30)}║`);
  console.log(`  ║  URL:     http://localhost:${String(PORT).padEnd(14)}║`);
  console.log(`  ╚══════════════════════════════════════════╝\n`);
});
