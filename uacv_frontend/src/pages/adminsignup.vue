<template>
  <v-container>
    <div class="text-center">
      <div class="text-h3 font-weight-bold mb-n1">관리자 등록</div>
    </div>

    <div class="text-center mt-5">
      <v-form>
        <v-text-field v-model.trim="username" label="Username" required />
        <p v-if="check === 1" style="color: red;">중복된 아이디 입니다.</p>
        <v-text-field v-model.trim="password1" label="Password" type="password" />
        <v-text-field v-model.trim="password2" label="Password 확인" type="password" />
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
  const userRole = "ADMIN"
  const check = ref(0)

  // 비동기화
  function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms))
  }

  const SignUp = async function () {
    const payload = {
      username: username.value,
      password1: password1.value,
      password2: password2.value,
      memberRole: userRole
    }

    store.checkUsername(username.value)
    await sleep(100)
    check.value = store.check
    if (check.value === "사용가능") {
      store.signUp(payload)
    } else {
      check.value = 1
    }
  }
</script>

<style scoped>

</style>