/* app.js — SPA Router, Nav, Toast */

/* ──────────── Toast Notification System ──────────── */
const Toast = {
  show(message, type = 'info', duration = 3500) {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `hud-toast hud-toast--${type}`;
    toast.innerHTML = `
      <span class="toast-icon">${{ success: '✓', error: '✕', info: '◉', warning: '⚠' }[type] || '◉'}</span>
      <span class="toast-msg">${message}</span>
    `;
    container.appendChild(toast);

    if (typeof anime !== 'undefined') {
      anime({ targets: toast, opacity: [0, 1], translateX: [40, 0], duration: 300, easing: 'easeOutExpo' });
    } else {
      toast.style.opacity = '1';
    }

    setTimeout(() => {
      if (typeof anime !== 'undefined') {
        anime({
          targets: toast, opacity: [1, 0], translateX: [0, 40], duration: 300,
          complete: () => toast.remove()
        });
      } else {
        toast.remove();
      }
    }, duration);
  }
};

/* ──────────── Scan Line Transition ──────────── */
const ScanTransition = {
  run(callback) {
    const el = document.getElementById('scan-line');
    if (!el || typeof anime === 'undefined') {
      callback();
      return;
    }

    el.style.display = 'block';
    anime({
      targets: el,
      translateY: ['-100%', '0%'],
      opacity: [0, 0.85],
      duration: 250,
      easing: 'easeInQuad',
      complete: () => {
        callback();
        anime({
          targets: el,
          translateY: ['0%', '100%'],
          opacity: [0.85, 0],
          duration: 300,
          easing: 'easeOutQuad',
          complete: () => { el.style.display = 'none'; }
        });
      }
    });
  }
};

/* ──────────── Navigation ──────────── */
const Nav = {
  update(route) {
    const isLoggedIn = Auth.isLoggedIn();
    const nav = document.getElementById('main-nav');
    const layout = document.getElementById('layout');

    if (isLoggedIn && route !== 'landing' && route !== 'login' && route !== 'register') {
      nav.classList.remove('hidden');
      layout.classList.add('with-nav');
    } else {
      nav.classList.add('hidden');
      layout.classList.remove('with-nav');
    }

    // Update active link
    document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
    const active = document.querySelector(`.nav-link[data-route="${route}"]`);
    if (active) active.classList.add('active');

    // Update user info
    if (isLoggedIn) {
      const user = Auth.getCurrentUser();
      if (user) {
        const nameEl = document.getElementById('nav-user-name');
        const roleEl = document.getElementById('nav-user-role');
        const avatarEl = document.getElementById('nav-user-avatar');
        if (nameEl) nameEl.textContent = user.name;
        if (roleEl) roleEl.textContent = user.role.toUpperCase();
        if (avatarEl) avatarEl.textContent = user.name.charAt(0).toUpperCase();
      }
    }
  },

  init() {
    document.getElementById('nav-logout-btn')?.addEventListener('click', () => {
      Auth.logout();
      Toast.show('Logged out successfully', 'info');
    });
  }
};

/* ──────────── Views Registry ──────────── */
const Views = {
  landing: LandingView,
  login: LoginView,
  register: RegisterView,
  dashboard: DashboardView,
  'service-entry': ServiceEntryView,
};

let currentView = null;

/* ──────────── Router ──────────── */
function getRoute() {
  const hash = window.location.hash || '#/';
  const path = hash.replace('#/', '').split('?')[0] || 'landing';
  return path === '' ? 'landing' : path;
}

function navigate(route) {
  const app = document.getElementById('app');
  if (!app) return;

  // Auth guards
  const publicRoutes = ['landing', 'login', 'register'];
  if (!Auth.isLoggedIn() && !publicRoutes.includes(route)) {
    window.location.hash = '#/login';
    return;
  }
  if (Auth.isLoggedIn() && (route === 'login' || route === 'register')) {
    window.location.hash = '#/dashboard';
    return;
  }

  const ViewClass = Views[route];
  if (!ViewClass) {
    app.innerHTML = `<div class="error-view"><h2>404 — MODULE NOT FOUND</h2><a href="#/" class="hud-btn hud-btn--ghost">HOME</a></div>`;
    return;
  }

  // Destroy previous view if it has a destroy hook
  if (currentView && currentView.destroy) currentView.destroy();

  ScanTransition.run(() => {
    app.innerHTML = ViewClass.render();
    Nav.update(route);
    currentView = ViewClass;

    // Initialize view
    if (ViewClass.init) {
      Promise.resolve(ViewClass.init()).catch(e => {
        console.error('[AutoServe] View init error:', e);
        Toast.show('Error loading view: ' + e.message, 'error');
      });
    }

    // Scroll to top
    window.scrollTo(0, 0);
  });
}

/* ──────────── Bootstrap ──────────── */
window.addEventListener('hashchange', () => navigate(getRoute()));

document.addEventListener('DOMContentLoaded', () => {
  Nav.init();
  navigate(getRoute());
});
