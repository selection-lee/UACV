/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router/auto'
import { setupLayouts } from 'virtual:generated-layouts'
import { routes } from 'vue-router/auto-routes'
import Login from '../pages/login.vue'
import HelloWorld from '../components/HelloWorld.vue'

// 기본 경로('/')의 컴포넌트를 login.vue로 설정
const modifiedRoutes = routes.map(route => {
  if (route.path === '/') {
    return { ...route, component: Login }
  }
  return route
})
// 기존의 기본 경로('/')를 '/home' 경로로 변경
const helloRoute = {
  path: '/home',
  name: 'HelloWorld',
  component: HelloWorld
}
// 새로운 경로를 routes 배열에 추가
modifiedRoutes.push(helloRoute)

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: setupLayouts(modifiedRoutes),
})

// Workaround for https://github.com/vitejs/vite/issues/11804
router.onError((err, to) => {
  if (err?.message?.includes?.('Failed to fetch dynamically imported module')) {
    if (!localStorage.getItem('vuetify:dynamic-reload')) {
      console.log('Reloading page to fix dynamic import error')
      localStorage.setItem('vuetify:dynamic-reload', 'true')
      location.assign(to.fullPath)
    } else {
      console.error('Dynamic import error, reloading page did not fix it', err)
    }
  } else {
    console.error(err)
  }
})

router.isReady().then(() => {
  localStorage.removeItem('vuetify:dynamic-reload')
})

export default router
