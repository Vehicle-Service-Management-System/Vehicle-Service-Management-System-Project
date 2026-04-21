/* views/service-entry.js — 4-step Service Logging Flow */

const ServiceEntryView = {
  step: 1,
  ctx: {
    customer: null,
    vehicle: null,
  },

  render() {
    return `
    <div class="service-entry-root" id="service-entry-root">
      <header class="dashboard-header">
        <div>
          <h1 class="dashboard-title">NEW SERVICE ENTRY</h1>
          <p class="dashboard-sub">Log a vehicle service in 4 steps</p>
        </div>
      </header>

      <!-- Stepper -->
      <div class="hud-stepper hud-stepper--4" id="svc-stepper">
        ${['CUSTOMER', 'VEHICLE', 'SERVICE', 'CONFIRM'].map((label, i) => `
          <div class="step-item ${i === 0 ? 'active' : ''}" id="svc-step-ind-${i+1}" data-step="${i+1}">
            <div class="step-dot">${i+1}</div>
            <span class="step-label">${label}</span>
          </div>
          ${i < 3 ? '<div class="step-line"></div>' : ''}
        `).join('')}
      </div>

      <!-- Step Container -->
      <div class="svc-step-container" id="svc-step-container">
        <!-- Steps are injected here -->
      </div>
    </div>`;
  },

  init() {
    this.step = 1;
    this.ctx = { customer: null, vehicle: null };
    this._renderStep(1);
  },

  _renderStep(n) {
    const container = document.getElementById('svc-step-container');
    if (!container) return;

    const steps = [null, this._step1Html(), this._step2Html(), this._step3Html(), this._step4Html()];
    const incoming = document.createElement('div');
    incoming.className = 'svc-step';
    incoming.id = `svc-step-${n}`;
    incoming.innerHTML = steps[n];

    // Animate outgoing
    const outgoing = container.querySelector('.svc-step');
    const forward = n > this.step;

    if (outgoing && typeof anime !== 'undefined') {
      anime({ targets: outgoing, opacity: [1, 0], translateX: [0, forward ? -60 : 60],
        duration: 260, easing: 'easeInQuad', complete: () => outgoing.remove() });
      setTimeout(() => {
        container.appendChild(incoming);
        anime({ targets: incoming, opacity: [0, 1], translateX: [forward ? 60 : -60, 0],
          duration: 320, easing: 'easeOutExpo' });
      }, 150);
    } else {
      if (outgoing) outgoing.remove();
      container.appendChild(incoming);
    }

    this._updateStepper(n);
    this.step = n;

    setTimeout(() => this._bindStep(n), 200);
  },

  _updateStepper(n) {
    for (let i = 1; i <= 4; i++) {
      const ind = document.getElementById(`svc-step-ind-${i}`);
      if (!ind) continue;
      ind.classList.remove('active', 'done');
      if (i < n) ind.classList.add('done');
      else if (i === n) ind.classList.add('active');
    }
  },

  /* ──────────── STEP 1: Customer search / create ──────────── */
  _step1Html() {
    return `
    <div class="hud-card svc-card" id="card-step1">
      <h3 class="svc-card-title">IDENTIFY CUSTOMER</h3>

      <div class="form-group">
        <label class="hud-label" for="cust-search">SEARCH BY NAME OR PHONE</label>
        <div class="search-row">
          <input type="search" id="cust-search" class="hud-input" placeholder="Start typing…" autocomplete="off">
          <button class="hud-btn hud-btn--ghost" id="btn-cust-new">+ NEW</button>
        </div>
      </div>

      <div id="cust-search-results" class="search-results"></div>

      <!-- Create new customer (inline, hidden initially) -->
      <div id="new-cust-form-wrap" class="hidden">
        <div class="form-divider">NEW CUSTOMER</div>
        <form id="new-cust-form" class="auth-form">
          <div class="form-row">
            <div class="form-group">
              <label class="hud-label" for="new-cust-name">NAME *</label>
              <input type="text" id="new-cust-name" class="hud-input" required>
            </div>
            <div class="form-group">
              <label class="hud-label" for="new-cust-phone">PHONE *</label>
              <input type="tel" id="new-cust-phone" class="hud-input" required>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="hud-label" for="new-cust-email">EMAIL</label>
              <input type="email" id="new-cust-email" class="hud-input">
            </div>
            <div class="form-group">
              <label class="hud-label" for="new-cust-address">ADDRESS</label>
              <input type="text" id="new-cust-address" class="hud-input">
            </div>
          </div>
          <div id="new-cust-error" class="form-error hidden"></div>
          <button type="submit" class="hud-btn hud-btn--primary" id="btn-save-cust">SAVE &amp; CONTINUE</button>
        </form>
      </div>

      <div id="selected-customer-preview" class="selected-preview hidden"></div>
    </div>`;
  },

  _bindStep(n) {
    if (n === 1) this._bindStep1();
    if (n === 2) this._bindStep2();
    if (n === 3) this._bindStep3();
    if (n === 4) this._bindStep4();
  },

  _bindStep1() {
    const search = document.getElementById('cust-search');
    const newBtn = document.getElementById('btn-cust-new');
    const newWrap = document.getElementById('new-cust-form-wrap');

    if (this.ctx.customer) {
      this._showSelectedCustomer(this.ctx.customer);
    }

    let debounce;
    search && search.addEventListener('input', () => {
      clearTimeout(debounce);
      debounce = setTimeout(() => this._searchCustomers(search.value.trim()), 300);
    });

    newBtn && newBtn.addEventListener('click', () => {
      newWrap.classList.toggle('hidden');
      if (!newWrap.classList.contains('hidden') && typeof anime !== 'undefined') {
        anime({ targets: newWrap, opacity: [0, 1], translateY: [-10, 0], duration: 300 });
      }
    });

    const newForm = document.getElementById('new-cust-form');
    newForm && newForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const err = document.getElementById('new-cust-error');
      const btn = document.getElementById('btn-save-cust');
      btn.textContent = 'SAVING…';
      try {
        const customer = await API.addCustomer({
          name: document.getElementById('new-cust-name').value.trim(),
          phone: document.getElementById('new-cust-phone').value.trim(),
          email: document.getElementById('new-cust-email').value.trim(),
          address: document.getElementById('new-cust-address').value.trim(),
        });
        this.ctx.customer = customer;
        newWrap.classList.add('hidden');
        document.getElementById('cust-search-results').innerHTML = '';
        this._showSelectedCustomer(customer);
      } catch (ex) {
        err.textContent = ex.message;
        err.classList.remove('hidden');
      } finally {
        btn.textContent = 'SAVE & CONTINUE';
      }
    });
  },

  async _searchCustomers(q) {
    const results = document.getElementById('cust-search-results');
    if (!results) return;
    if (!q) { results.innerHTML = ''; return; }
    results.innerHTML = '<div class="table-loading small">SEARCHING…</div>';
    try {
      const customers = await API.searchCustomers(q);
      if (!customers.length) { results.innerHTML = '<div class="table-empty small">No customers found. Use "+ NEW" to add.</div>'; return; }
      results.innerHTML = `<ul class="search-list">${customers.map(c =>
        `<li class="search-item" data-id="${c.id}" data-name="${this._esc(c.name)}" data-phone="${this._esc(c.phone)}">
          <span class="si-name">${this._esc(c.name)}</span>
          <span class="si-phone">${this._esc(c.phone)}</span>
         </li>`
      ).join('')}</ul>`;
      results.querySelectorAll('.search-item').forEach(item => {
        item.addEventListener('click', () => {
          this.ctx.customer = { id: item.dataset.id, name: item.dataset.name, phone: item.dataset.phone };
          document.getElementById('cust-search').value = '';
          results.innerHTML = '';
          this._showSelectedCustomer(this.ctx.customer);
        });
      });
    } catch {
      results.innerHTML = '<div class="table-empty small">Search error.</div>';
    }
  },

  _showSelectedCustomer(customer) {
    const preview = document.getElementById('selected-customer-preview');
    if (!preview) return;
    preview.innerHTML = `
    <div class="preview-card">
      <div class="preview-icon cyan">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="20"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
      </div>
      <div class="preview-info">
        <b>${this._esc(customer.name)}</b>
        <span>${this._esc(customer.phone)}</span>
      </div>
      <button class="hud-btn hud-btn--ghost hud-btn--xs" id="btn-change-customer">CHANGE</button>
      <button class="hud-btn hud-btn--primary hud-btn--sm" id="btn-cust-next">NEXT: VEHICLE →</button>
    </div>`;
    preview.classList.remove('hidden');

    document.getElementById('btn-change-customer')?.addEventListener('click', () => {
      this.ctx.customer = null;
      preview.classList.add('hidden');
      preview.innerHTML = '';
    });

    document.getElementById('btn-cust-next')?.addEventListener('click', () => {
      if (!this.ctx.customer) { Toast.show('Please select a customer first', 'error'); return; }
      this._renderStep(2);
    });
  },

  /* ──────────── STEP 2: Vehicle select / create ──────────── */
  _step2Html() {
    return `
    <div class="hud-card svc-card" id="card-step2">
      <h3 class="svc-card-title">SELECT VEHICLE — <span class="text-cyan">${this._esc(this.ctx.customer?.name || '')}</span></h3>

      <div id="vehicle-list-wrap">
        <div class="table-loading">LOADING VEHICLES…</div>
      </div>

      <div class="form-divider">OR REGISTER NEW VEHICLE</div>
      <form id="new-veh-form" class="auth-form">
        <div class="form-row">
          <div class="form-group">
            <label class="hud-label" for="veh-make">MAKE *</label>
            <input type="text" id="veh-make" class="hud-input" placeholder="e.g. Maruti" required>
          </div>
          <div class="form-group">
            <label class="hud-label" for="veh-model">MODEL *</label>
            <input type="text" id="veh-model" class="hud-input" placeholder="e.g. Swift" required>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label class="hud-label" for="veh-year">YEAR</label>
            <input type="number" id="veh-year" class="hud-input" placeholder="2020" min="1970" max="2099">
          </div>
          <div class="form-group">
            <label class="hud-label" for="veh-reg">REG NUMBER *</label>
            <input type="text" id="veh-reg" class="hud-input" placeholder="KL 01 AB 1234" required>
          </div>
        </div>
        <div class="form-group">
          <label class="hud-label">VEHICLE TYPE</label>
          <div class="hud-toggle" id="veh-type-toggle">
            <button type="button" class="toggle-btn active" data-type="car">CAR</button>
            <button type="button" class="toggle-btn" data-type="bike">BIKE</button>
            <button type="button" class="toggle-btn" data-type="truck">TRUCK</button>
            <button type="button" class="toggle-btn" data-type="other">OTHER</button>
          </div>
        </div>
        <div id="new-veh-error" class="form-error hidden"></div>
        <div class="form-row">
          <button type="button" class="hud-btn hud-btn--ghost" id="btn-veh-back">← BACK</button>
          <button type="submit" class="hud-btn hud-btn--primary" id="btn-save-veh">SAVE &amp; CONTINUE</button>
        </div>
      </form>
    </div>`;
  },

  async _bindStep2() {
    // Load existing vehicles
    const listWrap = document.getElementById('vehicle-list-wrap');
    try {
      const vehicles = await API.getVehicles(this.ctx.customer.id);
      if (!vehicles.length) {
        listWrap.innerHTML = '<div class="table-empty small">No vehicles for this customer. Register one below.</div>';
      } else {
        listWrap.innerHTML = `<ul class="vehicle-pick-list">${vehicles.map(v =>
          `<li class="vehicle-pick-item${this.ctx.vehicle?.id == v.id ? ' selected' : ''}" data-id="${v.id}">
            <span class="hud-badge hud-badge--type">${(v.vehicle_type||'').toUpperCase()}</span>
            <b>${this._esc(v.make)} ${this._esc(v.model)}</b>
            <span class="reg-plate">${this._esc(v.reg_number)}</span>
            <button class="hud-btn hud-btn--primary hud-btn--sm pick-veh-btn" data-id="${v.id}"
              data-make="${this._esc(v.make)}" data-model="${this._esc(v.model)}"
              data-reg="${this._esc(v.reg_number)}" data-type="${this._esc(v.vehicle_type||'')}">
              SELECT →
            </button>
          </li>`
        ).join('')}</ul>`;

        listWrap.querySelectorAll('.pick-veh-btn').forEach(btn => {
          btn.addEventListener('click', () => {
            this.ctx.vehicle = { id: btn.dataset.id, make: btn.dataset.make, model: btn.dataset.model, reg_number: btn.dataset.reg, vehicle_type: btn.dataset.type };
            this._renderStep(3);
          });
        });
      }
    } catch (e) {
      listWrap.innerHTML = `<div class="table-empty small">Error loading vehicles: ${e.message}</div>`;
    }

    // Type toggle
    let selectedType = 'car';
    document.getElementById('veh-type-toggle')?.querySelectorAll('.toggle-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        document.getElementById('veh-type-toggle').querySelectorAll('.toggle-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        selectedType = btn.dataset.type;
      });
    });

    document.getElementById('btn-veh-back')?.addEventListener('click', () => this._renderStep(1));

    // New vehicle form
    document.getElementById('new-veh-form')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const err = document.getElementById('new-veh-error');
      const btn = document.getElementById('btn-save-veh');
      btn.textContent = 'SAVING…';
      try {
        const vehicle = await API.addVehicle({
          customer_id: this.ctx.customer.id,
          make: document.getElementById('veh-make').value.trim(),
          model: document.getElementById('veh-model').value.trim(),
          year: document.getElementById('veh-year').value || null,
          reg_number: document.getElementById('veh-reg').value.trim(),
          vehicle_type: selectedType,
        });
        this.ctx.vehicle = vehicle;
        this._renderStep(3);
      } catch (ex) {
        err.textContent = ex.message;
        err.classList.remove('hidden');
      } finally {
        btn.textContent = 'SAVE & CONTINUE';
      }
    });
  },

  /* ──────────── STEP 3: Service Details ──────────── */
  _step3Html() {
    const v = this.ctx.vehicle;
    return `
    <div class="hud-card svc-card" id="card-step3">
      <h3 class="svc-card-title">SERVICE DETAILS — <span class="text-cyan">${this._esc(v?.reg_number||'')} (${this._esc(v?.make||'')} ${this._esc(v?.model||'')})</span></h3>

      <form id="svc-details-form" class="auth-form">
        <div class="form-group">
          <label class="hud-label" for="svc-desc">DESCRIPTION *</label>
          <textarea id="svc-desc" class="hud-input hud-textarea" rows="3" placeholder="Describe the service performed…" required></textarea>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label class="hud-label">STATUS</label>
            <div class="hud-toggle" id="svc-status-toggle">
              <button type="button" class="toggle-btn active" data-status="pending">PENDING</button>
              <button type="button" class="toggle-btn" data-status="in_progress">IN PROGRESS</button>
              <button type="button" class="toggle-btn" data-status="completed">COMPLETED</button>
            </div>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label class="hud-label" for="svc-cost">COST (₹)</label>
            <input type="number" id="svc-cost" class="hud-input" placeholder="0.00" min="0" step="0.01">
          </div>
          <div class="form-group">
            <label class="hud-label" for="svc-date">SERVICE DATE</label>
            <input type="date" id="svc-date" class="hud-input" value="${new Date().toISOString().slice(0,10)}">
          </div>
        </div>
        <div class="form-group">
          <label class="hud-label" for="svc-notes">NOTES (INTERNAL)</label>
          <textarea id="svc-notes" class="hud-input hud-textarea" rows="2" placeholder="Parts used, remarks, etc."></textarea>
        </div>
        <div id="svc-error" class="form-error hidden"></div>
        <div class="form-row">
          <button type="button" class="hud-btn hud-btn--ghost" id="btn-svc-back">← BACK</button>
          <button type="submit" class="hud-btn hud-btn--primary" id="btn-svc-submit">REVIEW →</button>
        </div>
      </form>
    </div>`;
  },

  _bindStep3() {
    let selectedStatus = 'pending';
    document.getElementById('svc-status-toggle')?.querySelectorAll('.toggle-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        document.getElementById('svc-status-toggle').querySelectorAll('.toggle-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        selectedStatus = btn.dataset.status;
      });
    });

    document.getElementById('btn-svc-back')?.addEventListener('click', () => this._renderStep(2));

    document.getElementById('svc-details-form')?.addEventListener('submit', (e) => {
      e.preventDefault();
      this.ctx.service = {
        description: document.getElementById('svc-desc').value.trim(),
        status: selectedStatus,
        cost: document.getElementById('svc-cost').value || null,
        service_date: document.getElementById('svc-date').value,
        notes: document.getElementById('svc-notes').value.trim(),
      };
      this._renderStep(4);
    });
  },

  /* ──────────── STEP 4: Confirmation ──────────── */
  _step4Html() {
    const c = this.ctx.customer;
    const v = this.ctx.vehicle;
    const s = this.ctx.service;
    return `
    <div class="hud-card svc-card" id="card-step4">
      <h3 class="svc-card-title">CONFIRM &amp; LOG SERVICE</h3>

      <div class="confirm-grid">
        <div class="confirm-block">
          <div class="confirm-label">CUSTOMER</div>
          <div class="confirm-value">${this._esc(c?.name)} <span class="text-muted">${this._esc(c?.phone)}</span></div>
        </div>
        <div class="confirm-block">
          <div class="confirm-label">VEHICLE</div>
          <div class="confirm-value">
            <span class="hud-badge hud-badge--type">${(v?.vehicle_type||'').toUpperCase()}</span>
            ${this._esc(v?.make)} ${this._esc(v?.model)} · <span class="reg-plate">${this._esc(v?.reg_number)}</span>
          </div>
        </div>
        <div class="confirm-block full-width">
          <div class="confirm-label">DESCRIPTION</div>
          <div class="confirm-value">${this._esc(s?.description)}</div>
        </div>
        <div class="confirm-block">
          <div class="confirm-label">STATUS</div>
          <div class="confirm-value">${s?.status?.replace('_', ' ').toUpperCase()}</div>
        </div>
        <div class="confirm-block">
          <div class="confirm-label">COST</div>
          <div class="confirm-value">${s?.cost ? '₹' + Number(s.cost).toLocaleString('en-IN') : 'Not specified'}</div>
        </div>
        <div class="confirm-block">
          <div class="confirm-label">DATE</div>
          <div class="confirm-value">${s?.service_date || new Date().toISOString().slice(0,10)}</div>
        </div>
        ${s?.notes ? `<div class="confirm-block full-width">
          <div class="confirm-label">NOTES</div>
          <div class="confirm-value">${this._esc(s.notes)}</div>
        </div>` : ''}
      </div>

      <div id="svc-confirm-error" class="form-error hidden"></div>

      <div class="form-row" style="margin-top:2rem">
        <button class="hud-btn hud-btn--ghost" id="btn-confirm-back">← EDIT</button>
        <button class="hud-btn hud-btn--primary" id="btn-log-service">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="18">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22,4 12,14.01 9,11.01"/>
          </svg>
          <span id="log-btn-text">LOG SERVICE</span>
        </button>
      </div>
    </div>`;
  },

  _bindStep4() {
    document.getElementById('btn-confirm-back')?.addEventListener('click', () => this._renderStep(3));
    document.getElementById('btn-log-service')?.addEventListener('click', () => this._submitService());
  },

  async _submitService() {
    const btnText = document.getElementById('log-btn-text');
    const err = document.getElementById('svc-confirm-error');
    if (btnText) btnText.textContent = 'LOGGING…';

    try {
      await API.addService({
        vehicle_id: this.ctx.vehicle.id,
        description: this.ctx.service.description,
        status: this.ctx.service.status,
        cost: this.ctx.service.cost,
        service_date: this.ctx.service.service_date,
        notes: this.ctx.service.notes,
      });

      // Success animation
      const card = document.getElementById('card-step4');
      if (card && typeof anime !== 'undefined') {
        anime({ targets: card, scale: [1, 1.02, 1], duration: 400, easing: 'easeOutElastic(1, 0.5)' });
      }
      Toast.show('Service logged successfully!', 'success');

      // Reset and redirect
      setTimeout(() => {
        this.ctx = { customer: null, vehicle: null };
        window.location.hash = '#/dashboard';
      }, 1200);
    } catch (ex) {
      if (err) {
        err.textContent = ex.message;
        err.classList.remove('hidden');
      }
      Toast.show(ex.message, 'error');
      if (btnText) btnText.textContent = 'LOG SERVICE';
    }
  },

  _esc(str) {
    if (!str) return '';
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
  }
};
