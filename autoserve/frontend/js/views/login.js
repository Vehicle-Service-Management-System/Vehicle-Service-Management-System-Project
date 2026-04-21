/* views/login.js — Clean Bay Login View */

const LoginView = {
  selectedRole: 'owner',

  render() {
    return `
    <section class="auth-view" id="login-view">
      <div class="auth-card" id="login-card">
        <div class="auth-header">
          <div class="auth-logo">
             <div class="auth-logo-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="3"/>
                  <path d="M19.07 4.93a10 10 0 0 0-14.14 0M4.93 19.07a10 10 0 0 0 14.14 0"/>
                  <path d="M12 2v2M12 20v2M2 12h2M20 12h2"/>
                </svg>
             </div>
          </div>
          <h2 class="auth-title">Welcome Back</h2>
          <p class="auth-subtitle">Sign in to your AutoServe account</p>
        </div>

        <form id="login-form" class="auth-form" autocomplete="off">
          <div class="form-group">
            <label class="hud-label" for="login-name">Username</label>
            <input type="text" id="login-name" class="hud-input" placeholder="Enter your name" required autocomplete="off">
          </div>

          <div class="form-group">
            <label class="hud-label">Account Role</label>
            <div class="hud-toggle" id="role-toggle" role="group" aria-label="Select role">
              <button type="button" class="toggle-btn active" data-role="owner" id="role-owner">Owner</button>
              <button type="button" class="toggle-btn" data-role="manager" id="role-manager">Manager</button>
              <button type="button" class="toggle-btn" data-role="employee" id="role-employee">Employee</button>
            </div>
          </div>

          <div class="form-group">
            <label class="hud-label" for="login-password">Password</label>
            <div class="input-with-toggle">
              <input type="password" id="login-password" class="hud-input" placeholder="Enter your password" required autocomplete="current-password">
              <button type="button" class="input-eye-btn" id="login-pw-toggle" aria-label="Toggle password visibility">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="16">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                  <circle cx="12" cy="12" r="3"/>
                </svg>
              </button>
            </div>
          </div>

          <button type="submit" class="hud-btn hud-btn--primary w-full" id="login-submit-btn" style="margin-top: 8px;">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18" height="18">
              <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
              <polyline points="10,17 15,12 10,7"/>
              <line x1="15" y1="12" x2="3" y2="12"/>
            </svg>
            <span id="login-btn-text">Sign In</span>
          </button>

          <div id="login-error" class="form-error hidden"></div>
        </form>

        <div class="form-divider">OR</div>

        <div class="auth-footer">
          Don't have an account yet? <a href="#/register" class="auth-link" id="goto-register">Register your centre</a>
        </div>
      </div>
    </section>`;
  },

  init() {
    this._bindRoleToggle();
    this._bindForm();
    this._bindPasswordToggle();
    this._animateIn();
  },

  _animateIn() {
    if (typeof anime === 'undefined') return;
    anime({ targets: '#login-card', opacity: [0, 1], translateY: [30, 0], duration: 600, easing: 'easeOutExpo' });
  },

  _bindRoleToggle() {
    const toggle = document.getElementById('role-toggle');
    if (!toggle) return;
    toggle.querySelectorAll('.toggle-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        toggle.querySelectorAll('.toggle-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        this.selectedRole = btn.dataset.role;
      });
    });
  },

  _bindPasswordToggle() {
    const btn = document.getElementById('login-pw-toggle');
    const input = document.getElementById('login-password');
    if (!btn || !input) return;
    btn.addEventListener('click', () => {
      input.type = input.type === 'password' ? 'text' : 'password';
    });
  },

  _bindForm() {
    const form = document.getElementById('login-form');
    if (!form) return;
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const name = document.getElementById('login-name').value.trim();
      const password = document.getElementById('login-password').value;
      const errorEl = document.getElementById('login-error');
      const btnText = document.getElementById('login-btn-text');

      errorEl.classList.add('hidden');
      btnText.textContent = 'Signing in...';

      try {
        const res = await API.login({ name, role: this.selectedRole, password });
        Auth.setSession(res.token, res.user);
        Toast.show('Welcome back, ' + res.user.name, 'success');
        window.location.hash = '#/dashboard';
      } catch (err) {
        errorEl.textContent = err.message || 'Verification failed. Please check your credentials.';
        errorEl.classList.remove('hidden');
        if (typeof anime !== 'undefined') {
          anime({ targets: '#login-card', translateX: [-8, 8, -6, 6, -4, 4, 0], duration: 500, easing: 'linear' });
        }
      } finally {
        btnText.textContent = 'Sign In';
      }
    });
  }
};
