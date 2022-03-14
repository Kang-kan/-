import Vue from 'vue'
import VueRouter from 'vue-router'
import Blogs from '../views/Blogs.vue'
import Login from '../views/Login.vue'
import BlogDetail from '../views/BlogDetail.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogHome from "@/views/BlogHome";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: { name: 'Blogs' }
  },{
    path: '/home',
    name: 'BlogHome',
    component: BlogHome
  },{
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },{
    path: '/login',
    name: 'Login',
    component: Login
  },{
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
    meta:{
       requireAuth:true
    }
  },{
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta:{
      requireAuth:true
    }
  },{
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
