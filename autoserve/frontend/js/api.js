/* api.js — Centralized fetch wrapper for AutoServe */

const API_BASE = '/api';

async function fetchAPI(endpoint, options = {}) {
  const token = localStorage.getItem('autoserve_token');
  const headers = { 'Content-Type': 'application/json', ...options.headers };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const response = await fetch(`${API_BASE}${endpoint}`, { ...options, headers });

  if (response.status === 401) {
    localStorage.removeItem('autoserve_token');
    localStorage.removeItem('autoserve_user');
    window.location.hash = '#/login';
    throw new Error('Unauthorized');
  }

  const data = await response.json();
  if (!response.ok) throw new Error(data.error || data.message || 'API Error');
  return data;
}

/* ──────────── Auth ──────────── */
/* Backend mounts auth.js on /api so endpoints are /api/login, /api/register, /api/me */
const API = {
  login: (payload) => fetchAPI('/login', { method: 'POST', body: JSON.stringify(payload) }),
  register: (payload) => fetchAPI('/register', { method: 'POST', body: JSON.stringify(payload) }),
  me: () => fetchAPI('/me').then(d => d.user),

  /* ──────────── Customers ──────────── */
  /* Backend wraps arrays: { customers: [...] } */
  getCustomers: () => fetchAPI('/customers').then(d => d.customers),
  searchCustomers: (q) => fetchAPI(`/customers/search?q=${encodeURIComponent(q)}`).then(d => d.customers),
  getCustomer: (id) => fetchAPI(`/customers/${id}`),
  addCustomer: (payload) => fetchAPI('/customers', { method: 'POST', body: JSON.stringify(payload) }).then(d => d.customer),

  /* ──────────── Vehicles ──────────── */
  getVehicles: (customerId) => fetchAPI(`/vehicles?customer_id=${customerId}`).then(d => d.vehicles),
  getVehicle: (id) => fetchAPI(`/vehicles/${id}`),
  addVehicle: (payload) => fetchAPI('/vehicles', { method: 'POST', body: JSON.stringify(payload) }).then(d => d.vehicle),

  /* ──────────── Services ──────────── */
  getServices: (params = {}) => {
    const qs = new URLSearchParams(params).toString();
    return fetchAPI(`/services${qs ? '?' + qs : ''}`).then(d => d.services);
  },
  getMyServices: () => fetchAPI('/services/my').then(d => d.services),
  getReports: (params = {}) => {
    const qs = new URLSearchParams(params).toString();
    return fetchAPI(`/services/reports${qs ? '?' + qs : ''}`);
  },
  addService: (payload) => fetchAPI('/services', { method: 'POST', body: JSON.stringify(payload) }),
  updateService: (id, payload) => fetchAPI(`/services/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),

  /* ──────────── Users ──────────── */
  getUsers: () => fetchAPI('/users').then(d => d.users),
  addUser: (payload) => fetchAPI('/users', { method: 'POST', body: JSON.stringify(payload) }),
  updateUser: (id, payload) => fetchAPI(`/users/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  updateCentre: (payload) => fetchAPI('/users/centre/settings', { method: 'PUT', body: JSON.stringify(payload) }),
};
