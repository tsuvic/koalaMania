import { createRouter, createWebHistory } from 'vue-router'
import Home from '/src/views/home.vue'
import Param from '/src/views/param.vue'
import Auth from '/src/views/auth.vue'
import Inbox from '/src/views/inbox.vue'
import NotFound from '/src/views/errors/404.vue'
import Users from '/src/views/users.vue'


const routes = [
  {
    path: '/',      
    name: 'home',
    component: Home 
  },
  {
    path: '/param/:param1/:param2',
    name: 'param',
    component: Param
  },
  {
    path: '/auth',
    name: 'auth',
    component: Auth
  },
  {
    path: '/inbox',
    name: 'inbox',
    component: Inbox
  },
  {
    path: '/:catchAll(.*)',
    name: 'not-found',
    component: NotFound
  },
  {
    path: '/users',
    name: 'users',
    component: Users
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router;
