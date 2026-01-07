import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Demo from '../views/demo.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/demo',
      name: 'home',
      component: Demo
    },
  ]
})

export default router
