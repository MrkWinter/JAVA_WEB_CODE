import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
//配置路由信息
const routes = [
  {
    path: '/emp',
    name: 'emp',
    component: () => import('../views/tlias/EmpView.vue')
  },
  {
    path: '/dept',
    name: 'dept',
    component: () => import('../views/tlias/DeptView.vue')
  },
  {
    path: '/',
    redirect: '/dept'
  }
]

const router = new VueRouter({
  routes
})

export default router
