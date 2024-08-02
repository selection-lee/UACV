/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { registerPlugins } from '@/plugins'


// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue'
import { createPinia } from 'pinia'

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)

registerPlugins(app)
app.mount('#app')
