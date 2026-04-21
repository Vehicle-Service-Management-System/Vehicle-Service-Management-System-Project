/* views/landing.js — Clean Bay Landing Page */

const LandingView = {
  render() {
    return `
    <div class="landing">

      <!-- TOP NAV BAR -->
      <header class="landing-topbar">
        <div class="landing-topbar-brand">
          <div class="brand-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="3"/>
              <path d="M19.07 4.93a10 10 0 0 0-14.14 0M4.93 19.07a10 10 0 0 0 14.14 0"/>
              <path d="M12 2v2M12 20v2M2 12h2M20 12h2"/>
            </svg>
          </div>
          <span class="brand-name">Auto<span>Serve</span></span>
        </div>
        <nav class="landing-topbar-nav">
          <a href="#/login" class="hud-btn ghost sm" id="btn-topbar-login">Sign In</a>
          <a href="#/register" class="hud-btn primary sm" id="btn-topbar-register">Get Started</a>
        </nav>
      </header>

      <!-- HERO SECTION -->
      <section class="landing-hero">
        <div class="landing-hero-left">

          <div class="hero-eyebrow">
            <span class="status-dot"></span>
            Manufacturer-Certified Workshop Software
          </div>

          <h1 class="hero-title">
            The Complete<br>
            <span class="text-brand">Vehicle Service</span><br>
            Management Platform
          </h1>

          <p class="hero-desc">
            AutoServe is purpose-built for modern dealership service centres.
            Manage every vehicle job from intake to delivery — track services,
            assign technicians, monitor costs, and generate reports, all from
            one trusted platform your entire team will love.
          </p>

          <div class="hero-cta">
            <a href="#/register" class="hud-btn primary lg" id="btn-register-centre">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18" height="18">
                <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v14z"/>
                <polyline points="17,21 17,13 7,13 7,21"/>
              </svg>
              Register Your Centre
            </a>
            <a href="#/login" class="hud-btn ghost lg" id="btn-login">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18" height="18">
                <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
                <polyline points="10,17 15,12 10,7"/>
                <line x1="15" y1="12" x2="3" y2="12"/>
              </svg>
              Sign In
            </a>
          </div>

          <div class="hero-trust">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
              <polyline points="22,4 12,14.01 9,11.01"/>
            </svg>
            No setup fees &nbsp;&bull;&nbsp; Role-based access &nbsp;&bull;&nbsp; Runs locally, your data stays yours
          </div>

        </div>

        <div class="landing-hero-right">
          <div class="hero-image-wrapper">
            <img
              src="https://images.pexels.com/photos/2244746/pexels-photo-2244746.jpeg?auto=compress&cs=tinysrgb&w=800"
              alt="Automotive service bay with technician working on a vehicle"
              loading="lazy"
            />
            <div class="hero-image-overlay">
              <div class="ov-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="18" height="18">
                  <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                </svg>
              </div>
              <div>
                <div class="ov-label">Centre Status</div>
                <div class="ov-value">● Active &amp; Secured</div>
              </div>
            </div>
          </div>

          <div class="hero-metric-chips">
            <div class="metric-chip">
              <div class="chip-val" data-target="2400" id="mc-services">0</div>
              <div class="chip-label">Services Logged</div>
            </div>
            <div class="metric-chip">
              <div class="chip-val" data-target="380" id="mc-vehicles">0</div>
              <div class="chip-label">Vehicles Tracked</div>
            </div>
            <div class="metric-chip">
              <div class="chip-val" data-target="99" id="mc-uptime">0</div>
              <div class="chip-label">Uptime %</div>
            </div>
          </div>
        </div>
      </section>

      <!-- FEATURES SECTION -->
      <section class="landing-features">
        <div class="landing-features-inner">
          <div class="section-title-block">
            <p class="section-label">Everything You Need</p>
            <h2 class="section-heading">Built for the Modern Service Bay</h2>
            <p class="section-sub">
              From the first customer call to handing back the keys,
              AutoServe keeps every step organised, visible, and measurable.
            </p>
          </div>

          <div class="features-grid">
            <div class="feature-card">
              <div class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="2" y="3" width="20" height="14" rx="2"/>
                  <line x1="8" y1="21" x2="16" y2="21"/>
                  <line x1="12" y1="17" x2="12" y2="21"/>
                </svg>
              </div>
              <h3>Live Dashboard</h3>
              <p>See every active job, pending approval, and revenue figure at a glance. Owner, manager and employee views — all in real time.</p>
            </div>

            <div class="feature-card">
              <div class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                  <polyline points="14 2 14 8 20 8"/>
                  <line x1="16" y1="13" x2="8" y2="13"/>
                  <line x1="16" y1="17" x2="8" y2="17"/>
                </svg>
              </div>
              <h3>Multi-Step Service Entry</h3>
              <p>Log new service jobs through a guided workflow — select customer, vehicle, service type, assigned technician, and cost estimate.</p>
            </div>

            <div class="feature-card">
              <div class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                  <circle cx="9" cy="7" r="4"/>
                  <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"/>
                </svg>
              </div>
              <h3>Role-Based Access</h3>
              <p>Owner, Manager and Employee roles each see exactly what they need. Secure JWT authentication with bcrypt-hashed passwords.</p>
            </div>

            <div class="feature-card">
              <div class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="1" y="3" width="15" height="13" rx="2"/>
                  <path d="M16 8h5l2 3v3h-7V8z"/>
                  <circle cx="5.5" cy="18.5" r="2.5"/>
                  <circle cx="18.5" cy="18.5" r="2.5"/>
                </svg>
              </div>
              <h3>Vehicle &amp; Customer Records</h3>
              <p>Maintain a full history for every customer and vehicle — registration, service history, contact details and more, instantly searchable.</p>
            </div>

            <div class="feature-card">
              <div class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <line x1="18" y1="20" x2="18" y2="10"/>
                  <line x1="12" y1="20" x2="12" y2="4"/>
                  <line x1="6" y1="20" x2="6" y2="14"/>
                </svg>
              </div>
              <h3>Revenue &amp; Reports</h3>
              <p>Filter service history by date, status or technician. Export summaries and track earnings across any time period.</p>
            </div>

            <div class="feature-card">
              <div class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                </svg>
              </div>
              <h3>Local-First &amp; Secure</h3>
              <p>Your data never leaves your premises. AutoServe runs on SQLite at your location — no cloud subscription, no data sharing.</p>
            </div>
          </div>
        </div>
      </section>

      <!-- HOW IT WORKS -->
      <section class="landing-how">
        <div class="landing-how-inner">
          <div class="section-title-block">
            <p class="section-label">Simple Workflow</p>
            <h2 class="section-heading">Up and Running in Minutes</h2>
            <p class="section-sub">
              AutoServe is designed for service centres of all sizes — from a single-bay workshop to a full dealership fleet.
            </p>
          </div>

          <div class="how-steps">
            <div class="how-step">
              <div class="how-step-number">1</div>
              <h4>Register Your Centre</h4>
              <p>Create your account, set up your team and assign roles in just a few clicks.</p>
            </div>
            <div class="how-step">
              <div class="how-step-number">2</div>
              <h4>Add Customers &amp; Vehicles</h4>
              <p>Build your customer database as vehicles come in — everything is saved automatically.</p>
            </div>
            <div class="how-step">
              <div class="how-step-number">3</div>
              <h4>Log Service Jobs</h4>
              <p>Use the guided multi-step entry to capture job details, assign technicians and set costs.</p>
            </div>
            <div class="how-step">
              <div class="how-step-number">4</div>
              <h4>Track &amp; Report</h4>
              <p>Monitor job status, update completion, and pull revenue reports whenever you need them.</p>
            </div>
          </div>
        </div>
      </section>

      <!-- CTA BANNER -->
      <section class="landing-cta-banner">
        <div class="cta-banner-inner">
          <div class="cta-banner-text">
            <h2>Ready to run a smarter service centre?</h2>
            <p>Join service centres trusting AutoServe to manage every job, every day.</p>
          </div>
          <div style="display:flex;gap:12px;flex-wrap:wrap;">
            <a href="#/register" class="hud-btn hud-btn--white lg" id="btn-cta-register">
              Register Free — No Card Needed
            </a>
            <a href="#/login" class="hud-btn ghost lg" style="background:rgba(255,255,255,0.1);border-color:rgba(255,255,255,0.3);color:#fff;" id="btn-cta-login">
              Sign In
            </a>
          </div>
        </div>
      </section>

      <!-- FOOTER -->
      <footer class="landing-footer">
        <span class="footer-brand">AutoServe</span>
        <span>Vehicle Service Management System &nbsp;&mdash;&nbsp; Built for certified workshops.</span>
        <span>&copy; ${new Date().getFullYear()} AutoServe. All rights reserved.</span>
      </footer>

    </div>`;
  },

  init() {
    this._animateStats();
    this._animateHero();
  },

  _animateHero() {
    if (typeof anime === 'undefined') return;
    anime.timeline({ easing: 'easeOutExpo' })
      .add({ targets: '.hero-eyebrow',     opacity: [0,1], translateY: [-16,0], duration: 500 })
      .add({ targets: '.hero-title',       opacity: [0,1], translateY: [24,0],  duration: 700 }, '-=300')
      .add({ targets: '.hero-desc',        opacity: [0,1], translateY: [16,0],  duration: 500 }, '-=400')
      .add({ targets: '.hero-cta',         opacity: [0,1], translateY: [16,0],  duration: 500 }, '-=300')
      .add({ targets: '.hero-trust',       opacity: [0,1], translateY: [12,0],  duration: 400 }, '-=200')
      .add({ targets: '.hero-image-wrapper', opacity: [0,1], translateX: [32,0], duration: 700 }, '-=700')
      .add({ targets: '.metric-chip',      opacity: [0,1], translateY: [16,0], delay: anime.stagger(80), duration: 400 }, '-=400')
      .add({ targets: '.feature-card',     opacity: [0,1], translateY: [20,0], delay: anime.stagger(60), duration: 400 }, '-=200')
      .add({ targets: '.how-step',         opacity: [0,1], translateY: [20,0], delay: anime.stagger(80), duration: 400 }, '-=200');
  },

  _animateStats() {
    document.querySelectorAll('[data-target]').forEach(el => {
      const target = parseInt(el.dataset.target, 10);
      let current = 0;
      const step = Math.ceil(target / 50);
      const timer = setInterval(() => {
        current = Math.min(current + step, target);
        el.textContent = current.toLocaleString();
        if (current >= target) clearInterval(timer);
      }, 22);
    });
  },

  destroy() {}
};
