import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"

import axios from 'axios'

import 'mavon-editor/dist/css/index.css'
import mavonEditor from 'mavon-editor'


Vue.use(Element)
Vue.use(mavonEditor)

Vue.prototype.$axios = axios //
Vue.config.productionTip = false

//请求体会经过这里
import './axios'
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
