/* auth.js — Token & user session management */

const Auth = {
  TOKEN_KEY: 'autoserve_token',
  USER_KEY: 'autoserve_user',

  setSession(token, user) {
    localStorage.setItem(this.TOKEN_KEY, token);
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  },

  clear() {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  },

  isLoggedIn() {
    return !!localStorage.getItem(this.TOKEN_KEY);
  },

  getToken() {
    return localStorage.getItem(this.TOKEN_KEY);
  },

  getCurrentUser() {
    try {
      return JSON.parse(localStorage.getItem(this.USER_KEY)) || null;
    } catch {
      return null;
    }
  },

  getUserRole() {
    const u = this.getCurrentUser();
    return u ? u.role : null;
  },

  isOwner() { return this.getUserRole() === 'owner'; },
  isManager() { return this.getUserRole() === 'manager'; },
  isEmployee() { return this.getUserRole() === 'employee'; },
  canManage() { return this.isOwner() || this.isManager(); },

  logout() {
    this.clear();
    window.location.hash = '#/login';
  }
};
