<template>
    <div>
        <h1>비밀번호 변경</h1>
        <v-text-field v-model.trim="currentPassword" label="현재 비밀번호" type="password" />
        <v-text-field v-model.trim="newPassword1" label="새로운 비밀번호" type="password" />
        <v-text-field v-model.trim="newPassword2" label="비밀번호 확인" type="password" />

        <v-btn class="block" @click="updatePassword">등록</v-btn>
    </div>
</template>

<script setup>
  import { ref } from 'vue'
  import { useUserStore } from '@/stores/user'

  const store = useUserStore()
  const currentPassword = ref(null)
  const newPassword1 = ref(null)
  const newPassword2 = ref(null)

  const updatePassword = function () {
    console.log(currentPassword, newPassword1, newPassword2)
    // 비밀번호가 같다면
    if (currentPassword.value === newPassword1.value){
      alert("지금 사용중인 비밀번호 입니다.")
    } else if (newPassword1.value !== newPassword2.value) {
      alert("비밀번호가 다릅니다.")
    } else if (newPassword1.value === newPassword2.value){
      const payload = {
        currentPassword: currentPassword.value,
        newPassword: newPassword1.value
      }
      store.updatePassword(payload)
    }
    currentPassword.value = null
    newPassword1.value = null
    newPassword2.value = null
  }
</script>

<style scoped>

</style>