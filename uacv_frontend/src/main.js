/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'

// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue'

import './styles/global.css' // 글로벌 스타일 파일 import

const app = createApp(App)

registerPlugins(app)

app.mount('#app')
