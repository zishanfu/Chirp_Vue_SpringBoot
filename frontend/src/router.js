import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

// ===================== Pages Components ======================
import Navbar from './components/navbar'
import MainPanel from './components/main-panel'
import Signup from './components/Signup'
import GoogleSso from './components/Google-sso'

// ==================== Router registration ====================
export default new Router({
  mode: 'history', //hash
  routes: [
    { path: '/', component: MainPanel },
    { path: '/signup', component: Signup},
    { path: '/token', component: GoogleSso}
  ]
});
