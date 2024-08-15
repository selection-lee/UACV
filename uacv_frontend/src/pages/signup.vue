<template>
  

  <v-app class="custom-app">
    <Navbar />
  <Appbar />

    <v-container >
      <div class="login-container">
        <div class="logo-container">
          <div class="logo-text">계정 등록</div>
        </div>
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
  </V-APP>
</template>

<script setup>
import { ref } from "vue";
import { userAdminStore } from "@/stores/admin";
import Appbar from "@/components/appbar.vue";
import Navbar from "@/components/navbar.vue";

const store = userAdminStore();

const username = ref(null);
const password1 = ref(null);
const password2 = ref(null);
const userRole = ref(null);
const rnk = ref(null);
const m_id = ref(null);

const SignUp = function () {
  const payload = {
    username: username.value,
    password1: password1.value,
    password2: password2.value,
    memberRole: userRole.value,
    rnk: rnk.value,
    m_id: m_id.value,
  };

  store.signUp(payload);
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
}

.block {
  background-color: #a3b18a;
  color: #ffffff;
  font-weight: bold;
  margin: 10px;
}
</style>
