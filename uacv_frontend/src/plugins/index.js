/**
 * plugins/index.js
 *
 * Automatically included in `./src/main.js`
 */

// Plugins
import pinia from '@/stores'
import vuetify from './vuetify'
import router from '@/router'

export function registerPlugins (app) {
  app
    .use(pinia)
    .use(vuetify)
    .use(router)
    
}
