<template>
  <v-navigation-drawer app v-model="drawer" :mini-variant.sync="mini" :mini-variant-width="miniWidth"
    :width="drawerWidth" @mouseover="expandDrawer()" @mouseleave="collapseDrawer()">

    <v-list dense>
      <v-list-item>
        <v-list-item-icon>
          <v-icon>mdi-menu</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title class="title">Menu</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>

      <v-list-item to="/">
        <v-list-item-icon>
          <v-icon>mdi-home</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title>Home</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-icon>
          <v-icon>mdi-camera-account</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title>기록보기</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item to="/log_text">
        <v-list-item-icon>
          <v-icon>mdi-text-box</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item>로그</v-list-item>
        </v-list-item-content>
      </v-list-item>

      <v-list-item to="/log_cam">
        <v-list-item-icon>
          <v-icon>mdi-video-check</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item>영상</v-list-item>
        </v-list-item-content>
      </v-list-item>

      <v-list-item to="/imformation_edit">
        <v-list-item-icon>
          <v-icon>mdi-pencil</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title>정보수정</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item to="/user_management" v-if="role === 'ADMIN'">
        <v-list-item-icon>
          <v-icon>mdi-car-back</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title>사용자관리</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>

      <v-list-item :to="{ path: '/login' }" v-if="!(store.isLogin)">
        <v-list-item-icon>
          <v-icon>mdi-login</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title>로그인</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item @click="store.LogOut()" v-else>
        <v-list-item-icon>
          <v-icon>mdi-logout</v-icon>
        </v-list-item-icon>
        <v-list-item-content v-if="!mini">
          <v-list-item-title>로그아웃</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script setup>
import { useUserStore } from "@/stores/user"
import { onMounted, ref } from "vue"

const drawer = ref(true)
const mini = ref(true)

const miniWidth = ref(56)
const drawerWidth = ref(56)

const expandDrawer = () => {
  mini.value = false;
  drawerWidth.value = 150;
}

const collapseDrawer = () => {
  mini.value = true;
  drawerWidth.value = 56;
}

const store = useUserStore()
const role = ref(null)

onMounted(() => {
  role.value = store.memberRole
  console.log(store.isLogin)
})
</script>

<style lang="scss" scoped></style>