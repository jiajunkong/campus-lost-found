import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // ===== 前台 =====
  {
    path: '/',
    component: () => import('@/views/front/FrontLayout.vue'),
    children: [
      { path: '', redirect: 'home' },
      { path: 'home', component: () => import('@/views/front/Home.vue') },
      { path: 'detail/:id', component: () => import('@/views/front/Detail.vue') },
      { path: 'publish', component: () => import('@/views/front/Publish.vue'), meta: { auth: true } },
      { path: 'profile', component: () => import('@/views/front/Profile.vue'), meta: { auth: true } },
      { path: 'my-publish', component: () => import('@/views/front/MyPublish.vue'), meta: { auth: true } },
      { path: 'notice', component: () => import('@/views/front/NoticeList.vue') }
    ]
  },
  { path: '/login', component: () => import('@/views/front/Login.vue') },
  { path: '/register', component: () => import('@/views/front/Register.vue') },

  // ===== 后台 =====
  { path: '/admin/login', component: () => import('@/views/admin/AdminLogin.vue') },
  {
    path: '/admin',
    component: () => import('@/views/admin/AdminLayout.vue'),
    meta: { admin: true },
    children: [
      { path: '', redirect: 'dashboard' },
      { path: 'dashboard', component: () => import('@/views/admin/Dashboard.vue') },
      { path: 'users', component: () => import('@/views/admin/UserManage.vue') },
      { path: 'goods', component: () => import('@/views/admin/GoodsManage.vue') },
      { path: 'category', component: () => import('@/views/admin/CategoryManage.vue') },
      { path: 'notice', component: () => import('@/views/admin/NoticeManage.vue') },
      { path: 'password', component: () => import('@/views/admin/AdminPassword.vue') }
    ]
  }
]

const router = new VueRouter({
  mode: 'hash',
  routes
})

// 简单路由守卫
router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  if (to.meta.admin) {
    if (!user || user.role !== 'admin') return next('/admin/login')
  }
  if (to.meta.auth) {
    if (!user) return next('/login')
  }
  next()
})

export default router
