/* views/register.js — Clean Bay Centre Registration */

const RegisterView = {
  step: 1,
  centreData: {},

  render() {
    return `
    <section class="auth-view" id="register-view">
      <div class="auth-card wide-card" id="register-card">
        <div class="auth-header">
          <div class="auth-logo">
             <div class="auth-logo-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v14z"/>
                  <polyline points="17,21 17,13 7,13 7,21"/>
                </svg>
             </div>
          </div>
          <h2 class="auth-title">Register Your Centre</h2>
          <p class="auth-subtitle">Two-step setup to get your service centre online</p>
        </div>

        <!-- Stepper -->
        <div class="hud-stepper" id="reg-stepper" style="justify-content: center;">
          <div class="step-item active" data-step="1" id="step-indicator-1">
            <div class="step-dot">1</div>
            <span class="step-label">Centre Details</span>
          </div>
          <div class="step-line" style="flex: 0 1 100px;"></div>
          <div class="step-item" data-step="2" id="step-indicator-2">
            <div class="step-dot">2</div>
            <span class="step-label">Owner Account</span>
          </div>
        </div>

        <!-- Step 1 -->
        <form id="reg-step1" class="auth-form reg-step">
          <div class="form-row">
            <div class="form-group">
              <label class="hud-label" for="centre-name">Workshop / Centre Name</label>
              <input type="text" id="centre-name" class="hud-input" placeholder="e.g. Skyline Auto Works" required>
            </div>
            <div class="form-group">
              <label class="hud-label" for="centre-phone">Business Phone</label>
              <input type="tel" id="centre-phone" class="hud-input" placeholder="e.g. +1 800 555 0199" required>
            </div>
          </div>
          <div class="form-group">
            <label class="hud-label" for="centre-address">Business Address</label>
            <textarea id="centre-address" class="hud-input hud-textarea" placeholder="Full address of your service centre" rows="3" required></textarea>
          </div>

          <div id="reg-step1-error" class="form-error hidden"></div>

          <button type="submit" class="hud-btn hud-btn--primary w-full" id="btn-step1-next" style="margin-top: 12px;">
            Continue to Account Setup
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18">
              <polyline points="9,18 15,12 9,6"/>
            </svg>
          </button>
        </form>

        <!-- Step 2 -->
        <form id="reg-step2" class="auth-form reg-step hidden">
          <div class="form-row">
            <div class="form-group">
              <label class="hud-label" for="owner-name">Full Name</label>
              <input type="text" id="owner-name" class="hud-input" placeholder="Your full name" required>
            </div>
            <div class="form-group">
              <label class="hud-label" for="owner-phone">Mobile Phone</label>
              <input type="tel" id="owner-phone" class="hud-input" placeholder="e.g. +1 555 0198" required>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="hud-label" for="owner-password">Password</label>
              <input type="password" id="owner-password" class="hud-input" placeholder="Min. 8 characters" required minlength="6" autocomplete="new-password">
            </div>
            <div class="form-group">
              <label class="hud-label" for="owner-password2">Confirm Password</label>
              <input type="password" id="owner-password2" class="hud-input" placeholder="Re-enter password" required autocomplete="new-password">
            </div>
          </div>

          <div id="reg-step2-error" class="form-error hidden"></div>

          <div class="form-row" style="margin-top: 12px;">
            <button type="button" class="hud-btn hud-btn--ghost" id="btn-step2-back">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18">
                <polyline points="15,18 9,12 15,6"/>
              </svg>
              Back
            </button>
            <button type="submit" class="hud-btn hud-btn--primary" id="btn-step2-submit">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                <polyline points="22,4 12,14.01 9,11.01"/>
              </svg>
              <span id="reg-btn-text">Complete Registration</span>
            </button>
          </div>
        </form>

        <div class="form-divider" style="margin-top: 32px;">OR</div>

        <div class="auth-footer">
          Already registered? <a href="#/login" class="auth-link">Sign In to your account</a>
        </div>
      </div>
    </section>`;
  },

  init() {
    this.step = 1;
    this._bindStep1();
    this._bindStep2();
    this._animateIn();
  },

  _animateIn() {
    if (typeof anime === 'undefined') return;
    anime({ targets: '#register-card', opacity: [0, 1], translateY: [30, 0], duration: 600, easing: 'easeOutExpo' });
  },

  _goToStep2() {
    const s1 = document.getElementById('reg-step1');
    const s2 = document.getElementById('reg-step2');
    const ind1 = document.getElementById('step-indicator-1');
    const ind2 = document.getElementById('step-indicator-2');

    if (typeof anime !== 'undefined') {
      anime({
        targets: '#reg-step1',
        opacity: [1, 0], translateX: [0, -40],
        duration: 300, easing: 'easeInQuad',
        complete: () => {
          s1.classList.add('hidden');
          s2.classList.remove('hidden');
          anime({ targets: '#reg-step2', opacity: [0, 1], translateX: [40, 0], duration: 300, easing: 'easeOutQuad' });
        }
      });
    } else {
      s1.classList.add('hidden');
      s2.classList.remove('hidden');
    }

    ind1.classList.remove('active');
    ind1.classList.add('done');
    ind2.classList.add('active');
    this.step = 2;
  },

  _goToStep1() {
    const s1 = document.getElementById('reg-step1');
    const s2 = document.getElementById('reg-step2');
    const ind1 = document.getElementById('step-indicator-1');
    const ind2 = document.getElementById('step-indicator-2');

    if (typeof anime !== 'undefined') {
      anime({
        targets: '#reg-step2',
        opacity: [1, 0], translateX: [0, 40],
        duration: 300, easing: 'easeInQuad',
        complete: () => {
          s2.classList.add('hidden');
          s1.classList.remove('hidden');
          anime({ targets: '#reg-step1', opacity: [0, 1], translateX: [-40, 0], duration: 300, easing: 'easeOutQuad' });
        }
      });
    } else {
      s2.classList.add('hidden');
      s1.classList.remove('hidden');
    }

    ind2.classList.remove('active');
    ind1.classList.remove('done');
    ind1.classList.add('active');
    this.step = 1;
  },

  _bindStep1() {
    const form = document.getElementById('reg-step1');
    if (!form) return;
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      const name = document.getElementById('centre-name').value.trim();
      const phone = document.getElementById('centre-phone').value.trim();
      const address = document.getElementById('centre-address').value.trim();
      const err = document.getElementById('reg-step1-error');

      if (!name || !phone || !address) {
        err.textContent = 'All fields are required.';
        err.classList.remove('hidden');
        return;
      }
      err.classList.add('hidden');
      this.centreData = { name, phone, address };
      this._goToStep2();
    });
  },

  _bindStep2() {
    const back = document.getElementById('btn-step2-back');
    const form = document.getElementById('reg-step2');
    if (!form) return;

    back && back.addEventListener('click', () => this._goToStep1());

    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const ownerName = document.getElementById('owner-name').value.trim();
      const ownerPhone = document.getElementById('owner-phone').value.trim();
      const pw1 = document.getElementById('owner-password').value;
      const pw2 = document.getElementById('owner-password2').value;
      const err = document.getElementById('reg-step2-error');
      const btnText = document.getElementById('reg-btn-text');

      if (pw1 !== pw2) {
        err.textContent = 'Passwords do not match.';
        err.classList.remove('hidden');
        return;
      }
      err.classList.add('hidden');
      btnText.textContent = 'Creating Account...';

      try {
        const payload = {
          centre: this.centreData,
          owner: { name: ownerName, phone: ownerPhone, password: pw1 }
        };
        const res = await API.register(payload);
        Auth.setSession(res.token, res.user);
        Toast.show('Registration complete! Welcome, ' + res.user.name, 'success');
        window.location.hash = '#/dashboard';
      } catch (err2) {
        err.textContent = err2.message || 'Registration failed. Please try again.';
        err.classList.remove('hidden');
      } finally {
        btnText.textContent = 'Complete Registration';
      }
    });
  }
};
