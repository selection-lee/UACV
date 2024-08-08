<template>
  <v-main>
  <v-container class="background">
    <div class="login-container">
      <div class="logo-container">
        <img src="@/assets/logo.png" alt="Logo" class="logo">
        <div class="logo-text">UACV</div>
      </div>

      <v-form class="form-container">
        <v-text-field v-model.trim="username" label="ID" outlined dense />
        <v-text-field v-model.trim="password" label="Password" type="password" outlined dense />
        <v-btn class="login-button" @click="Login">Login</v-btn>
      </v-form>
    </div>
  </v-container>
</v-main>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user";

const store = useUserStore();

const username = ref(null);
const password = ref(null);

const Login = function () {
  const payload = {
    username: username.value,
    password: password.value,
  };
  store.LogIn(payload);
};
</script>

<style scoped>
.v-main {
  background-color: #093028;
  height: 100vh;
  width: 100vw;
}

.login-container {
  padding: 20px;
  border-radius: 8px;
  width: 300px;
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
  font-family: 'Arial Black', sans-serif;
  font-size: 40px;
  color: #d5e0d7;
  margin-top: 10px;
}

.form-container {
  display: flex;
  flex-direction: column;
  color: #d5e0d7;
}

.login-button {
  background-color: #a3b18a;
  color: #ffffff;
  font-weight: bold;
  margin-top: 20px;
}
</style>
