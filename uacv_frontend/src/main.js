/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'

// Components
import App from './App.vue'
import axios from 'axios'

// Composables
import { createApp } from 'vue'

import './styles/global.css' // 글로벌 스타일 파일 import

axios.defaults.baseURL = import.meta.env.VITE_BACKEND_API_URL

const app = createApp(App)

registerPlugins(app)

app.mount('#app')
