/* views/dashboard.js — Role-aware Dashboard */

const DashboardView = {
  user: null,
  data: {},

  render() {
    this.user = Auth.getCurrentUser();
    if (!this.user) return '<p>Unauthorized</p>';

    const roleLabel = { owner: 'OWNER', manager: 'MANAGER', employee: 'EMPLOYEE' }[this.user.role] || this.user.role.toUpperCase();

    return `
    <div class="dashboard-root" id="dashboard-root">
      <header class="dashboard-header">
        <div>
          <h1 class="dashboard-title">COMMAND CENTRE</h1>
          <p class="dashboard-sub">${this.user.centre_name || 'Service Centre'} · <span class="hud-badge hud-badge--${this.user.role}">${roleLabel}</span></p>
        </div>
        <div class="dashboard-header-actions">
          <a href="#/service-entry" class="hud-btn hud-btn--primary" id="btn-new-service-dash">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="16"><path d="M12 5v14M5 12h14"/></svg>
            New Service
          </a>
        </div>
      </header>

      <!-- Stat Cards -->
      <div class="stat-cards" id="stat-cards">
        <div class="hud-card stat-card" id="card-total-services">
          <div class="stat-card-icon cyan">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="22"><path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/></svg>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value" id="stat-total">—</span>
            <span class="stat-card-label">TOTAL SERVICES</span>
          </div>
        </div>
        <div class="hud-card stat-card" id="card-pending">
          <div class="stat-card-icon amber">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="22"><circle cx="12" cy="12" r="10"/><polyline points="12,6 12,12 16,14"/></svg>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value" id="stat-pending">—</span>
            <span class="stat-card-label">IN PROGRESS</span>
          </div>
        </div>
        <div class="hud-card stat-card" id="card-completed">
          <div class="stat-card-icon green">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="22"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22,4 12,14.01 9,11.01"/></svg>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value" id="stat-completed">—</span>
            <span class="stat-card-label">COMPLETED</span>
          </div>
        </div>
        ${Auth.canManage() ? `
        <div class="hud-card stat-card" id="card-revenue">
          <div class="stat-card-icon purple">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="22"><line x1="12" y1="1" x2="12" y2="23"/><path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value" id="stat-revenue">—</span>
            <span class="stat-card-label">TOTAL REVENUE ₹</span>
          </div>
        </div>` : ''}
      </div>

      <!-- My Services Table -->
      <div class="hud-card dashboard-section" id="section-my-services">
        <div class="section-header">
          <h2 class="section-title">MY RECENT SERVICES</h2>
          <span class="section-count" id="my-services-count">…</span>
        </div>
        <div id="my-services-table-wrap">
          <div class="table-loading">LOADING DATA…</div>
        </div>
      </div>

      ${Auth.canManage() ? this._renderManagerSection() : ''}
      ${Auth.isOwner() ? this._renderOwnerSections() : ''}
    </div>`;
  },

  _renderManagerSection() {
    return `
    <div class="hud-card dashboard-section" id="section-all-customers">
      <div class="section-header">
        <h2 class="section-title">ALL CUSTOMERS</h2>
        <div class="section-header-actions">
          <input type="search" id="customer-search-input" class="hud-input hud-input--sm" placeholder="Search name / phone…">
          <span class="section-count" id="all-customers-count">…</span>
        </div>
      </div>
      <div id="all-customers-wrap">
        <div class="table-loading">LOADING DATA…</div>
      </div>
    </div>`;
  },

  _renderOwnerSections() {
    return `
    <!-- Employees Panel -->
    <div class="hud-card dashboard-section" id="section-employees">
      <div class="section-header">
        <h2 class="section-title">TEAM</h2>
        <button class="hud-btn hud-btn--ghost hud-btn--sm" id="btn-add-employee">+ ADD MEMBER</button>
      </div>
      <div id="employees-wrap">
        <div class="table-loading">LOADING DATA…</div>
      </div>
    </div>

    <!-- Service Reports -->
    <div class="hud-card dashboard-section" id="section-reports">
      <div class="section-header">
        <h2 class="section-title">SERVICE REPORTS</h2>
      </div>
      <div class="report-filters" id="report-filters">
        <div class="filter-group">
          <label class="hud-label">FROM</label>
          <input type="date" id="filter-date-from" class="hud-input hud-input--sm">
        </div>
        <div class="filter-group">
          <label class="hud-label">TO</label>
          <input type="date" id="filter-date-to" class="hud-input hud-input--sm">
        </div>
        <div class="filter-group">
          <label class="hud-label">STATUS</label>
          <select id="filter-status" class="hud-input hud-input--sm">
            <option value="">ALL</option>
            <option value="pending">PENDING</option>
            <option value="in_progress">IN PROGRESS</option>
            <option value="completed">COMPLETED</option>
          </select>
        </div>
        <div class="filter-group">
          <label class="hud-label">TYPE</label>
          <select id="filter-vehicle-type" class="hud-input hud-input--sm">
            <option value="">ALL</option>
            <option value="car">CAR</option>
            <option value="bike">BIKE</option>
            <option value="truck">TRUCK</option>
            <option value="other">OTHER</option>
          </select>
        </div>
        <button class="hud-btn hud-btn--ghost hud-btn--sm" id="btn-apply-filters">APPLY</button>
        <button class="hud-btn hud-btn--ghost hud-btn--sm" id="btn-reset-filters">RESET</button>
      </div>
      <div id="reports-table-wrap">
        <div class="table-loading">SELECT FILTERS AND APPLY</div>
      </div>
      <div class="report-totals" id="report-totals" style="display:none">
        <span>TOTAL SERVICES: <b id="report-total-count">0</b></span>
        <span>TOTAL REVENUE: <b id="report-total-revenue">₹0</b></span>
      </div>
    </div>

    <!-- Centre Settings -->
    <div class="hud-card dashboard-section" id="section-settings">
      <div class="section-header">
        <h2 class="section-title">CENTRE SETTINGS</h2>
      </div>
      <form id="centre-settings-form" class="auth-form" style="max-width:520px">
        <div class="form-group">
          <label class="hud-label" for="settings-centre-name">CENTRE NAME</label>
          <input type="text" id="settings-centre-name" class="hud-input" value="">
        </div>
        <div class="form-group">
          <label class="hud-label" for="settings-centre-phone">PHONE</label>
          <input type="tel" id="settings-centre-phone" class="hud-input" value="">
        </div>
        <div class="form-group">
          <label class="hud-label" for="settings-centre-address">ADDRESS</label>
          <textarea id="settings-centre-address" class="hud-input hud-textarea" rows="3"></textarea>
        </div>
        <button type="submit" class="hud-btn hud-btn--primary" id="btn-save-settings">SAVE SETTINGS</button>
      </form>
    </div>`;
  },

  async init() {
    this.user = Auth.getCurrentUser();
    await this._loadAll();
    this._animateCards();
    if (Auth.canManage()) this._bindCustomerSearch();
    if (Auth.isOwner()) {
      this._bindReportFilters();
      this._bindAddEmployee();
      this._bindSettingsForm();
      this._prefillSettings();
    }
  },

  async _loadAll() {
    await Promise.allSettled([
      this._loadMyServices(),
      Auth.canManage() ? this._loadAllCustomers() : Promise.resolve(),
      Auth.isOwner() ? this._loadEmployees() : Promise.resolve(),
    ]);
    await this._loadStats();
  },

  async _loadStats() {
    try {
      if (Auth.isOwner()) {
        const rep = await API.getReports({});
        document.getElementById('stat-total').textContent = rep.summary?.total_services ?? '—';
        document.getElementById('stat-pending').textContent = rep.summary?.in_progress ?? '—';
        document.getElementById('stat-completed').textContent = rep.summary?.completed ?? '—';
        const rev = rep.summary?.total_revenue ?? 0;
        document.getElementById('stat-revenue').textContent = '₹' + Number(rev).toLocaleString('en-IN');
      } else {
        const svcs = await API.getMyServices();
        const total = svcs.length;
        const pending = svcs.filter(s => s.status === 'in_progress').length;
        const completed = svcs.filter(s => s.status === 'completed').length;
        document.getElementById('stat-total').textContent = total;
        document.getElementById('stat-pending').textContent = pending;
        document.getElementById('stat-completed').textContent = completed;
      }
    } catch {}
  },

  async _loadMyServices() {
    const wrap = document.getElementById('my-services-table-wrap');
    const countEl = document.getElementById('my-services-count');
    try {
      const services = await API.getMyServices();
      if (countEl) countEl.textContent = services.length + ' records';
      wrap.innerHTML = this._renderServicesTable(services);
      this._animateRows('#my-services-table-wrap');
    } catch (e) {
      wrap.innerHTML = `<div class="table-empty">Error loading services: ${e.message}</div>`;
    }
  },

  async _loadAllCustomers(q = '') {
    const wrap = document.getElementById('all-customers-wrap');
    const countEl = document.getElementById('all-customers-count');
    try {
      const customers = q ? await API.searchCustomers(q) : await API.getCustomers();
      if (countEl) countEl.textContent = customers.length + ' customers';
      wrap.innerHTML = this._renderCustomersTable(customers);
      this._bindCustomerExpand();
      this._animateRows('#all-customers-wrap');
    } catch (e) {
      wrap.innerHTML = `<div class="table-empty">Error loading customers: ${e.message}</div>`;
    }
  },

  async _loadEmployees() {
    const wrap = document.getElementById('employees-wrap');
    try {
      const users = await API.getUsers();
      wrap.innerHTML = this._renderUsersTable(users);
      this._animateRows('#employees-wrap');
    } catch (e) {
      wrap.innerHTML = `<div class="table-empty">Error loading team: ${e.message}</div>`;
    }
  },

  _renderServicesTable(services) {
    if (!services.length) return `<div class="table-empty">No services yet. <a href="#/service-entry" class="auth-link">Log your first service →</a></div>`;
    return `
    <table class="hud-table">
      <thead>
        <tr>
          <th>DATE</th><th>CUSTOMER</th><th>VEHICLE</th><th>TYPE</th><th>DESCRIPTION</th><th>STATUS</th><th>COST ₹</th>
        </tr>
      </thead>
      <tbody>
        ${services.map(s => `
        <tr class="table-row-reveal">
          <td>${this._fmtDate(s.service_date)}</td>
          <td>${this._esc(s.customer_name || '—')}</td>
          <td>${this._esc(s.reg_number || '—')}</td>
          <td><span class="hud-badge hud-badge--type">${(s.vehicle_type || 'N/A').toUpperCase()}</span></td>
          <td class="desc-cell">${this._esc(s.description)}</td>
          <td>${this._statusBadge(s.status)}</td>
          <td class="cost-cell">${s.cost ? '₹' + Number(s.cost).toLocaleString('en-IN') : '—'}</td>
        </tr>`).join('')}
      </tbody>
    </table>`;
  },

  _renderCustomersTable(customers) {
    if (!customers.length) return `<div class="table-empty">No customers found.</div>`;
    return `
    <table class="hud-table">
      <thead><tr><th>NAME</th><th>PHONE</th><th>EMAIL</th><th>ADDED</th><th></th></tr></thead>
      <tbody>
        ${customers.map(c => `
        <tr class="table-row-reveal">
          <td>${this._esc(c.name)}</td>
          <td>${this._esc(c.phone)}</td>
          <td>${this._esc(c.email || '—')}</td>
          <td>${this._fmtDate(c.created_at)}</td>
          <td><button class="hud-btn hud-btn--ghost hud-btn--xs expand-customer-btn" data-id="${c.id}">HISTORY ▾</button></td>
        </tr>
        <tr class="customer-expand-row hidden" id="expand-${c.id}">
          <td colspan="5">
            <div class="expand-inner" id="expand-inner-${c.id}">
              <div class="table-loading">LOADING…</div>
            </div>
          </td>
        </tr>`).join('')}
      </tbody>
    </table>`;
  },

  _renderUsersTable(users) {
    if (!users.length) return `<div class="table-empty">No team members yet.</div>`;
    return `
    <table class="hud-table">
      <thead><tr><th>NAME</th><th>ROLE</th><th>PHONE</th><th>JOINED</th></tr></thead>
      <tbody>
        ${users.map(u => `
        <tr class="table-row-reveal">
          <td>${this._esc(u.name)}</td>
          <td><span class="hud-badge hud-badge--${u.role}">${u.role.toUpperCase()}</span></td>
          <td>${this._esc(u.phone || '—')}</td>
          <td>${this._fmtDate(u.join_date)}</td>
        </tr>`).join('')}
      </tbody>
    </table>`;
  },

  _bindCustomerExpand() {
    document.querySelectorAll('.expand-customer-btn').forEach(btn => {
      btn.addEventListener('click', async () => {
        const id = btn.dataset.id;
        const row = document.getElementById(`expand-${id}`);
        const inner = document.getElementById(`expand-inner-${id}`);
        if (!row || !inner) return;

        if (!row.classList.contains('hidden')) {
          row.classList.add('hidden');
          btn.textContent = 'HISTORY ▾';
          return;
        }
        row.classList.remove('hidden');
        btn.textContent = 'HISTORY ▴';

        try {
          const customer = await API.getCustomer(id);
          inner.innerHTML = this._renderCustomerDetail(customer);
        } catch (e) {
          inner.innerHTML = `<div class="table-empty">Error: ${e.message}</div>`;
        }
      });
    });
  },

  _renderCustomerDetail(customer) {
    const vehs = customer.vehicles || [];
    if (!vehs.length) return `<div class="table-empty small">No vehicles registered for this customer.</div>`;
    return vehs.map(v => `
      <div class="vehicle-group">
        <div class="vehicle-group-header">
          <span class="hud-badge hud-badge--type">${(v.vehicle_type || 'N/A').toUpperCase()}</span>
          <b>${this._esc(v.make)} ${this._esc(v.model)}</b>
          <span class="reg-plate">${this._esc(v.reg_number)}</span>
          <span class="text-muted">${v.year || ''}</span>
        </div>
        ${v.services && v.services.length ? `
        <table class="hud-table hud-table--nested">
          <thead><tr><th>DATE</th><th>DESCRIPTION</th><th>STATUS</th><th>COST ₹</th><th>BY</th></tr></thead>
          <tbody>
            ${v.services.map(s => `<tr>
              <td>${this._fmtDate(s.service_date)}</td>
              <td>${this._esc(s.description)}</td>
              <td>${this._statusBadge(s.status)}</td>
              <td>${s.cost ? '₹' + Number(s.cost).toLocaleString('en-IN') : '—'}</td>
              <td>${this._esc(s.added_by_name || '—')}</td>
            </tr>`).join('')}
          </tbody>
        </table>` : `<div class="table-empty small">No services logged for this vehicle.</div>`}
      </div>
    `).join('');
  },

  _bindCustomerSearch() {
    const input = document.getElementById('customer-search-input');
    if (!input) return;
    let debounce;
    input.addEventListener('input', () => {
      clearTimeout(debounce);
      debounce = setTimeout(() => this._loadAllCustomers(input.value.trim()), 350);
    });
  },

  _bindReportFilters() {
    const apply = document.getElementById('btn-apply-filters');
    const reset = document.getElementById('btn-reset-filters');
    if (apply) apply.addEventListener('click', () => this._loadReports());
    if (reset) reset.addEventListener('click', () => {
      ['filter-date-from', 'filter-date-to', 'filter-status', 'filter-vehicle-type'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.value = '';
      });
      document.getElementById('reports-table-wrap').innerHTML = '<div class="table-loading">SELECT FILTERS AND APPLY</div>';
      const totals = document.getElementById('report-totals');
      if (totals) totals.style.display = 'none';
    });
  },

  async _loadReports() {
    const params = {};
    const from = document.getElementById('filter-date-from')?.value;
    const to = document.getElementById('filter-date-to')?.value;
    const status = document.getElementById('filter-status')?.value;
    const vtype = document.getElementById('filter-vehicle-type')?.value;
    if (from) params.from = from;
    if (to) params.to = to;
    if (status) params.status = status;
    if (vtype) params.vehicle_type = vtype;

    const wrap = document.getElementById('reports-table-wrap');
    wrap.innerHTML = '<div class="table-loading">FETCHING REPORT…</div>';

    try {
      const rep = await API.getReports(params);
      const services = rep.services || [];
      const summary = rep.summary || {};

      wrap.innerHTML = this._renderServicesTable(services);
      this._animateRows('#reports-table-wrap');

      const totals = document.getElementById('report-totals');
      if (totals) {
        totals.style.display = 'flex';
        document.getElementById('report-total-count').textContent = summary.total_services || services.length;
        document.getElementById('report-total-revenue').textContent = '₹' + Number(summary.total_revenue || 0).toLocaleString('en-IN');
      }
    } catch (e) {
      wrap.innerHTML = `<div class="table-empty">Error: ${e.message}</div>`;
    }
  },

  _bindAddEmployee() {
    const btn = document.getElementById('btn-add-employee');
    if (!btn) return;
    btn.addEventListener('click', () => this._showAddEmployeeModal());
  },

  _showAddEmployeeModal() {
    const existing = document.getElementById('add-employee-modal');
    if (existing) existing.remove();

    const modal = document.createElement('div');
    modal.className = 'hud-modal-overlay';
    modal.id = 'add-employee-modal';
    modal.innerHTML = `
    <div class="hud-modal">
      <div class="hud-modal-header">
        <h3>ADD TEAM MEMBER</h3>
        <button class="modal-close-btn" id="close-emp-modal">✕</button>
      </div>
      <form id="add-employee-form" class="auth-form">
        <div class="form-group">
          <label class="hud-label" for="emp-name">NAME</label>
          <input type="text" id="emp-name" class="hud-input" required>
        </div>
        <div class="form-group">
          <label class="hud-label">ROLE</label>
          <div class="hud-toggle" id="emp-role-toggle">
            <button type="button" class="toggle-btn active" data-role="employee">EMPLOYEE</button>
            <button type="button" class="toggle-btn" data-role="manager">MANAGER</button>
          </div>
        </div>
        <div class="form-group">
          <label class="hud-label" for="emp-phone">PHONE</label>
          <input type="tel" id="emp-phone" class="hud-input">
        </div>
        <div class="form-group">
          <label class="hud-label" for="emp-password">PASSKEY</label>
          <input type="password" id="emp-password" class="hud-input" required minlength="6">
        </div>
        <div id="emp-form-error" class="form-error hidden"></div>
        <button type="submit" class="hud-btn hud-btn--primary w-full" id="btn-emp-submit">ADD MEMBER</button>
      </form>
    </div>`;

    document.body.appendChild(modal);
    if (typeof anime !== 'undefined') {
      anime({ targets: '.hud-modal', scale: [0.9, 1], opacity: [0, 1], duration: 300, easing: 'easeOutExpo' });
    }

    let selectedRole = 'employee';
    modal.querySelectorAll('.toggle-btn').forEach(b => {
      b.addEventListener('click', () => {
        modal.querySelectorAll('.toggle-btn').forEach(x => x.classList.remove('active'));
        b.classList.add('active');
        selectedRole = b.dataset.role;
      });
    });

    document.getElementById('close-emp-modal').addEventListener('click', () => modal.remove());
    modal.addEventListener('click', (e) => { if (e.target === modal) modal.remove(); });

    document.getElementById('add-employee-form').addEventListener('submit', async (e) => {
      e.preventDefault();
      const btn = document.getElementById('btn-emp-submit');
      const err = document.getElementById('emp-form-error');
      btn.textContent = 'ADDING…';
      try {
        await API.addUser({
          name: document.getElementById('emp-name').value.trim(),
          role: selectedRole,
          phone: document.getElementById('emp-phone').value.trim(),
          password: document.getElementById('emp-password').value,
        });
        Toast.show('Team member added successfully', 'success');
        modal.remove();
        this._loadEmployees();
      } catch (ex) {
        err.textContent = ex.message;
        err.classList.remove('hidden');
      } finally {
        btn.textContent = 'ADD MEMBER';
      }
    });
  },

  _prefillSettings() {
    try {
      const user = Auth.getCurrentUser();
      if (!user) return;
      const nameEl = document.getElementById('settings-centre-name');
      const phoneEl = document.getElementById('settings-centre-phone');
      const addrEl = document.getElementById('settings-centre-address');
      if (nameEl) nameEl.value = user.centre_name || '';
      if (phoneEl) phoneEl.value = user.centre_phone || '';
      if (addrEl) addrEl.value = user.centre_address || '';
    } catch {}
  },

  _bindSettingsForm() {
    const form = document.getElementById('centre-settings-form');
    if (!form) return;
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const btn = document.getElementById('btn-save-settings');
      btn.textContent = 'SAVING…';
      try {
        await API.updateCentre({
          name: document.getElementById('settings-centre-name').value.trim(),
          phone: document.getElementById('settings-centre-phone').value.trim(),
          address: document.getElementById('settings-centre-address').value.trim(),
        });
        Toast.show('Centre settings saved', 'success');
      } catch (e) {
        Toast.show(e.message, 'error');
      } finally {
        btn.textContent = 'SAVE SETTINGS';
      }
    });
  },

  _animateCards() {
    if (typeof anime === 'undefined') return;
    anime({
      targets: '.stat-card',
      opacity: [0, 1], scale: [0.93, 1], translateY: [20, 0],
      delay: anime.stagger(100), duration: 500, easing: 'easeOutExpo'
    });
    anime({
      targets: '.dashboard-section',
      opacity: [0, 1], translateY: [30, 0],
      delay: anime.stagger(120, { start: 300 }), duration: 600, easing: 'easeOutExpo'
    });
  },

  _animateRows(selector) {
    if (typeof anime === 'undefined') return;
    anime({
      targets: `${selector} .table-row-reveal`,
      opacity: [0, 1], translateY: [10, 0],
      delay: anime.stagger(40), duration: 350, easing: 'easeOutQuad'
    });
  },

  _statusBadge(status) {
    const map = {
      pending: 'hud-badge--pending',
      in_progress: 'hud-badge--progress',
      completed: 'hud-badge--done',
    };
    const label = { pending: 'PENDING', in_progress: 'IN PROGRESS', completed: 'DONE' };
    const cls = map[status] || '';
    return `<span class="hud-badge ${cls}">${label[status] || (status || 'N/A').toUpperCase()}</span>`;
  },

  _fmtDate(dt) {
    if (!dt) return '—';
    try {
      return new Date(dt).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' });
    } catch { return dt; }
  },

  _esc(str) {
    if (!str) return '';
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
  }
};
