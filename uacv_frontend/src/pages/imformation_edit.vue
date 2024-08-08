<template>
  <v-app>
    <Navbar />

    <v-app-bar app>
      <v-toolbar-title>
        <br>
        <v-img src="@/assets/logo.png" height="100" contain></v-img>
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>

    <v-main>
      <v-container>
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">계정정보</h3>
        </div>

        <div class="text-center mt-5">
          <v-form>
            <v-text-field v-model.trim="username" label="Username" />
            <v-text-field v-model.trim="ID" label="ID" />
            <v-text-field v-model.trim="소속" label="소속" />
            <v-text-field v-model.trim="password1" label="Password" type="password" />
            <v-text-field v-model.trim="password2" label="Password 확인" type="password" />
            <v-btn class="block" @click="SignUp">등록</v-btn>
          </v-form>
        </div>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>

</script>

<style scoped>
.v-toolbar-title span {
  font-size: 24px;
  font-weight: bold;
  color: #fff;
}

.v-toolbar {
  background-color: #004d40;
}

.v-pagination {
  justify-content: center;
}
</style>