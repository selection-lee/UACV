<template>
  <v-app class="custom-app">
    <v-container>
      <div class="logo-container">
        <img src="@/assets/logo.png" alt="Logo" class="logo" />
        <div class="logo-text">관리자등록</div>
      </div>

      <div class="text-center mt-5">
        <v-form>
          <v-text-field v-model.trim="username" label="Username" />
          <v-text-field
            v-model.trim="password1"
            label="Password"
            type="password"
          />
          <v-text-field
            v-model.trim="password2"
            label="Password 확인"
            type="password"
          />
          <v-text-field v-model.trim="rnk" label="소속계급" />
          <v-text-field v-model.trim="m_id" label="군번" />
          <v-form class="form-container">
            <v-btn class="block" @click="SignUp">등록</v-btn>
          </v-form>
        </v-form>
      </div>
    </v-container>
  </v-app>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

const username = ref(null);
const password1 = ref(null);
const password2 = ref(null);
const userRole = "ADMIN";
const rnk = ref(null);
const m_id = ref(null);

//== 계정생성 ==//
const SignUp = function () {
  const payload = {
    username: username.value,
    password1: password1.value,
    password2: password2.value,
    memberRole: userRole,
    rnk: rnk.value,
    m_id: m_id.value,
  };
  userStore.signUp(payload);
};
</script>

<style scoped>
.custom-app {
  background-color: #093028;
  color: #ffffef;
  height: 100vh;
  width: 100vw;
}

.v-container {
  padding: 0.1px;
  border-radius: 8px;
  width: 800px;
  margin: 100px auto;
  text-align: center;
}

.logo-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.logo {
  width: 80px;
  height: auto;
}

.logo-text {
  font-family: "Arial Black", sans-serif;
  font-size: 40px;
  font-weight: bold;
  color: #d5e0d7;
  margin-top: 10px;
}

.form-container {
  display: flex;
  align-items: center;
  flex-direction: column;
  color: #d5e0d7;
}

.block {
  background-color: #a3b18a;
  color: #ffffff;
  font-weight: bold;
  margin-top: 20px;
}
</style>
