<template>
    <div>
        <h1>비밀번호 변경</h1>

        <v-text-field v-model.trim="currentPassword" label="Password" type="password" />
        <v-text-field v-model.trim="newPassword" label="Password 확인" type="password" />
        <v-btn class="block" @click="updatePassword">등록</v-btn>
    </div>
</template>

<script setup>
  import { ref } from 'vue'
  import { useUserStore } from '@/stores/user'

  const store = useUserStore()
  const currentPassword = ref(null)
  const newPassword = ref(null)

  const updatePassword = function () {

    const payload = {
      currentPassword: currentPassword.value,
      newPassword: newPassword.value
    }
    store.updatePassword(payload)
  }
</script>

<style scoped>

</style>