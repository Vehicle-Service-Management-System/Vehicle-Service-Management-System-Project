const Database = require('better-sqlite3');
const path = require('path');

const DB_PATH = path.join(__dirname, '..', 'autoserve.db');

let db;

function getDb() {
  if (!db) {
    db = new Database(DB_PATH);
    db.pragma('journal_mode = WAL');
    db.pragma('foreign_keys = ON');
    initSchema();
  }
  return db;
}

function initSchema() {
  db.exec(`
    CREATE TABLE IF NOT EXISTS service_centres (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT NOT NULL,
      address TEXT NOT NULL,
      phone TEXT NOT NULL,
      created_at TEXT DEFAULT (datetime('now'))
    );

    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      centre_id INTEGER NOT NULL,
      name TEXT NOT NULL,
      role TEXT NOT NULL CHECK(role IN ('owner','manager','employee')),
      phone TEXT NOT NULL,
      address TEXT DEFAULT '',
      join_date TEXT DEFAULT (date('now')),
      password_hash TEXT NOT NULL,
      personal_notes TEXT DEFAULT '',
      FOREIGN KEY (centre_id) REFERENCES service_centres(id)
    );

    CREATE TABLE IF NOT EXISTS customers (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      centre_id INTEGER NOT NULL,
      name TEXT NOT NULL,
      phone TEXT NOT NULL,
      email TEXT DEFAULT '',
      address TEXT DEFAULT '',
      added_by INTEGER,
      created_at TEXT DEFAULT (datetime('now')),
      FOREIGN KEY (centre_id) REFERENCES service_centres(id),
      FOREIGN KEY (added_by) REFERENCES users(id)
    );

    CREATE TABLE IF NOT EXISTS vehicles (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      customer_id INTEGER NOT NULL,
      centre_id INTEGER NOT NULL,
      make TEXT NOT NULL,
      model TEXT NOT NULL,
      year INTEGER,
      reg_number TEXT NOT NULL,
      vehicle_type TEXT DEFAULT 'car' CHECK(vehicle_type IN ('car','bike','truck','suv','van','other')),
      added_by INTEGER,
      created_at TEXT DEFAULT (datetime('now')),
      FOREIGN KEY (customer_id) REFERENCES customers(id),
      FOREIGN KEY (centre_id) REFERENCES service_centres(id),
      FOREIGN KEY (added_by) REFERENCES users(id)
    );

    CREATE TABLE IF NOT EXISTS services (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      vehicle_id INTEGER NOT NULL,
      centre_id INTEGER NOT NULL,
      description TEXT NOT NULL,
      status TEXT DEFAULT 'pending' CHECK(status IN ('pending','in_progress','completed','cancelled')),
      cost REAL DEFAULT 0,
      added_by INTEGER,
      service_date TEXT DEFAULT (datetime('now')),
      notes TEXT DEFAULT '',
      FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
      FOREIGN KEY (centre_id) REFERENCES service_centres(id),
      FOREIGN KEY (added_by) REFERENCES users(id)
    );

    CREATE INDEX IF NOT EXISTS idx_users_centre ON users(centre_id);
    CREATE INDEX IF NOT EXISTS idx_customers_centre ON customers(centre_id);
    CREATE INDEX IF NOT EXISTS idx_vehicles_centre ON vehicles(centre_id);
    CREATE INDEX IF NOT EXISTS idx_vehicles_customer ON vehicles(customer_id);
    CREATE INDEX IF NOT EXISTS idx_services_centre ON services(centre_id);
    CREATE INDEX IF NOT EXISTS idx_services_vehicle ON services(vehicle_id);
    CREATE INDEX IF NOT EXISTS idx_services_added_by ON services(added_by);
  `);
}

module.exports = { getDb };
