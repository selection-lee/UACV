<template>
  <v-app>
    <v-main v-if="store.isMembers">
      <router-view />
    </v-main>

    <v-container v-else>
    <div class="text-center">
      <div class="text-h3 font-weight-bold mb-n1">관리자 등록</div>
    </div>

    <div class="text-center mt-5">
      <v-form>
        <v-text-field v-model.trim="username" label="Username"/>
        <v-text-field v-model.trim="password1" label="Password" type="password" />
        <v-text-field v-model.trim="password2" label="Password 확인" type="password" />
        <v-text-field v-model.trim="rnk" label="소속계급"/>
        <v-text-field v-model.trim="m_id" label="군번"/>
        <v-btn class="block" @click="SignUp">등록</v-btn>
      </v-form>
    </div>
  </v-container>
  </v-app>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { userAdminStore } from './stores/admin';
import { useUserStore } from './stores/user';
import { useRouter } from 'vue-router';

const userStore = useUserStore()
const store = userAdminStore()
const router = useRouter()

const username = ref(null)
const password1 = ref(null)
const password2 = ref(null)
const userRole = "ADMIN"
const rnk = ref(null)
const m_id = ref(null)

//== 계정생성 ==//
const SignUp = async function () {
    const payload = {
      username: username.value,
      password1: password1.value,
      password2: password2.value,
      memberRole: userRole,
      rnk: rnk.value,
      m_id: m_id.value
    }
    store.signUp(payload)
  }

//== 비동기화 ==//
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

onMounted(async() => {
  store.memberList()
  await sleep(100)
})

</script>

<style>
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #12372A !important; /* 전체 배경 색상 */
}

</style>