<template>
  <v-app-bar color="#093028">
    <v-toolbar-title style="display: flex; justify-content: center; align-items: center;">
      <div style="display: flex; align-items: center;">
        <!-- 이미지 -->
        <div style="height: 80px; width: 100px; margin-right: 10px;">
          <img src="@/assets/logo.png" alt="Logo" style="height: 100%; width: 100%; object-fit: contain;">
        </div>
        <!-- 텍스트 -->
        <div>
          <h1 style="color: #FFFFEF; margin: 0;">UACV</h1>
        </div>
      </div>
    </v-toolbar-title>
  </v-app-bar>
</template>


<script setup>

</script>

<style lang="scss" scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
  color: #FFFFEF;
}
</style>