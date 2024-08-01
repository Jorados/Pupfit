import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import store from './store/store'; // store 임포트

// 부트스트랩
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
    components,
    directives,
})

const app = createApp(App)
app.use(vuetify)
app.use(router)
app.use(store)
app.mount('#app')
