<template>
    <v-container>
      <div class="text-center">
        <div class="text-h3 font-weight-bold mb-n1">사용자 등록</div>
      </div>
  
      <div class="text-center mt-5">
        <v-form>
          <v-text-field v-model.trim="username" label="Username" />
          <v-text-field v-model.trim="password1" label="Password" type="password" />
          <v-text-field v-model.trim="password2" label="Password 확인" type="password" />
          <v-select label="Select" v-model.trim="userRole" :items="['CONTROL', 'MONITOR']"></v-select>
          <v-btn class="block" @click="SignUp">등록</v-btn>
        </v-form>
      </div>
  
    </v-container>
  </template>
  
  <script setup>
    import { ref } from 'vue'
    import { useUserStore } from '@/stores/user'
  
    const store = useUserStore()
  
    const username = ref(null)
    const password1 = ref(null)
    const password2 = ref(null)
    const userRole = ref(null)
  
    const SignUp = function () {
      const payload = {
        username: username.value,
        password1: password1.value,
        password2: password2.value,
        memberRole: userRole.value
      }
      store.signUp(payload)
    }
    
  </script>
  
  <style scoped></style>
