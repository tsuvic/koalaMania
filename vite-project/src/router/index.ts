import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import TopPage from '/src/views/toppage.vue'
import Param from '/src/views/param.vue'
import Auth from '/src/views/auth.vue'
import Inbox from '/src/views/inbox.vue'
import NotFound from '/src/views/errors/404.vue'
import Users from '/src/views/users.vue'
import Description from '/src/views/description.vue'

const routes = [
  {
    path: '/',      
    name: 'toppage',
    component: TopPage 
  },
  {
    path: '/description',      
    name: 'description',
    component: Description 
  },
  {
    path: '/animalRegister',      
    name: 'animalRegister',
    component: TopPage,
    beforeEnter() {
      window.location.href = "/insert"
    },
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
  {
    path: '/oauth/twitter/auth',
    name: 'twitter-auth',
    component: Auth,
    beforeEnter() {
      window.location.href = "/oauth/twitter/auth"
    },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
})

export default router;
