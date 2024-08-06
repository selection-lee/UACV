<template>
  <v-container>
    <div class="text-center">
      <div class="text-h3 font-weight-bold mb-n1">Login</div>
    </div>

    <div class="text-center mt-5">
      <v-form>
        <v-text-field v-model.trim="username" label="Username" />
        <!-- <label for="username">username</label>
        <input type="text" id="username" v-model.trim="username"> -->
        <!-- <label for="password">password</label>
        <input type="text" id="password" v-model.trim="password"> -->
        <v-text-field
          v-model.trim="password"
          label="Password"
          type="password"
        />
        <v-btn class="block" @click="Login">Login</v-btn>
      </v-form>
    </div>
  </v-container>
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

<style scoped></style>
