<template>
  <v-app>
    <v-main v-if="store.isMembers">
      <router-view />
    </v-main>

    <Adminsignup v-else/>
  </v-app>
</template>

<script setup>
import Adminsignup from './pages/adminsignup.vue';
import { userAdminStore } from './stores/admin';

const store = userAdminStore()

//== 비동기화 ==//
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

onMounted(async () => {
  store.memberList()
  await sleep(100)
})

</script>

<style>
html,
body,
#app {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #12372A !important;
  /* 전체 배경 색상 */
}
</style>