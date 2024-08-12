<template>
  <v-container>
    <v-app-bar app>
      <v-toolbar-title>
        <br>
        <v-img src="@/assets/logo.png" height="100" contain />
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer />
    </v-app-bar>

    <div class="text-center">
      <div class="text-h3 font-weight-bold mb-n1">사용자 등록</div>
    </div>

    <div class="text-center mt-5">
      <v-form>
        <v-text-field v-model.trim="username" label="Username" />
        <v-text-field v-model.trim="password1" label="Password" type="password" />
        <v-text-field v-model.trim="password2" label="Password 확인" type="password" />
        <v-select label="Select" v-model.trim="userRole" :items="['CONTROL', 'MONITOR']"></v-select>
        <v-text-field v-model.trim="rnk" label="소속계급" />
        <v-text-field v-model.trim="m_id" label="군번" />
        <v-btn class="block" @click="SignUp">등록</v-btn>
      </v-form>
    </div>

  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { userAdminStore } from '@/stores/admin';

const store = userAdminStore()

const username = ref(null)
const password1 = ref(null)
const password2 = ref(null)
const userRole = ref(null)
const rnk = ref(null)
const m_id = ref(null)

const SignUp = function () {
  const payload = {
    username: username.value,
    password1: password1.value,
    password2: password2.value,
    memberRole: userRole.value,
    rnk: rnk.value,
    m_id: m_id.value
  }

  store.signUp(payload)
}

</script>

<style scoped>

</style>
