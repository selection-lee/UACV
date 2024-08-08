<template>
    <v-container>
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
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const userStore = useUserStore()
const router = useRouter()

const username = ref(null)
const password1 = ref(null)
const password2 = ref(null)
const userRole = "ADMIN"
const rnk = ref(null)
const m_id = ref(null)

//== 계정생성 ==//
const SignUp = function () {
    const payload = {
      username: username.value,
      password1: password1.value,
      password2: password2.value,
      memberRole: userRole,
      rnk: rnk.value,
      m_id: m_id.value
    }
    userStore.signUp(payload)
  }
</script>

<style scoped>

</style>