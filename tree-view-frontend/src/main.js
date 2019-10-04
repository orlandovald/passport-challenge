import Vue from 'vue'
import VueNativeSock from 'vue-native-websocket'
import {EagleModal} from 'vue-eagle-modal'

import App from './App.vue'
import store from './store'

Vue.config.productionTip = false

Vue.use(EagleModal);
Vue.use(VueNativeSock, 'ws://localhost:8086/ws/tree', { format: 'json' })

new Vue({
  store,
  render: h => h(App)
}).$mount('#app')
