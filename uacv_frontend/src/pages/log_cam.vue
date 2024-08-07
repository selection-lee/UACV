<template>
  <v-app>
    <v-navigation-drawer
      app
      v-model="drawer"
      :mini-variant.sync="mini"
      :mini-variant-width="miniWidth"
      :width="drawerWidth"
      @mouseover="expandDrawer"
      @mouseleave="collapseDrawer"
    >
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
          <v-list-item-content v-if="!mini">
            <v-list-item>로그</v-list-item>
          </v-list-item-content>
        </v-list-item>
        <v-list-item to="/log_cam">
          <v-list-item-content v-if="!mini">
            <v-list-item>영상</v-list-item>
          </v-list-item-content>
        </v-list-item>
        <v-list-item to="/imformation_edit">
          <v-list-item-content v-if="!mini">
            <v-list-item-icon>
              <v-icon>mdi-pencil</v-icon>
            </v-list-item-icon>
            <v-list-item-title>정보수정</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item to="/user_management">
          <v-list-item-content v-if="!mini">
            <v-list-item-icon>
              <v-icon>mdi-car-back</v-icon>
            </v-list-item-icon>
            <v-list-item-title>사용자관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item :to="{path: '/login'}" v-if="!store.isLogin">
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
        <!-- Title and Buttons -->
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">기록보기</h3>
          <v-btn icon :to="{ path: '/log_text' }">
            <v-icon>mdi-text</v-icon>
          </v-btn>
          <v-btn icon :to="{ path: '/log_cam' }">
            <v-icon>mdi-video</v-icon>
          </v-btn>
        </div>

        <!-- Sound Records -->
        <v-card class="mb-4 pa-4">
          <h4 class="text-h5 font-weight-bold">소리기록</h4>
          <v-row>
            <v-col
              v-for="(item, index) in soundRecords"
              :key="index"
              cols="12"
              sm="6"
              md="3"
            >
              <v-card class="mx-auto" max-width="344">
                <v-img :src="item.image" height="200px"></v-img>
                <v-card-title>{{ item.date }}</v-card-title>
              </v-card>
            </v-col>
          </v-row>
          <div class="text-right">
            <v-btn text>더보기</v-btn>
          </div>
        </v-card>

        <!-- Firing Records -->
        <v-card class="pa-4">
          <h4 class="text-h5 font-weight-bold">발사기록</h4>
          <v-row>
            <v-col
              v-for="(item, index) in firingRecords"
              :key="index"
              cols="12"
              sm="6"
              md="3"
            >
              <v-card class="mx-auto" max-width="344">
                <v-img :src="item.image" height="200px"></v-img>
                <v-card-title>{{ item.date }}</v-card-title>
              </v-card>
            </v-col>
          </v-row>
          <div class="text-right">
            <v-btn text>더보기</v-btn>
          </div>
        </v-card>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { useUserStore } from '@/stores/user';

const store = useUserStore()

export default {
  data() {
    return {
      store,
      drawer: true,
      mini: true,
      miniWidth: 56,
      drawerWidth: 56,
      page: 1,
      pages: 5, // Set the total number of pages here
      soundRecords: [
        { image: "@/assets/cam.jpg", date: "2024.07.26 11:08:25" },
        { image: "@/assets/cam.jpg", date: "2024.07.25 16:14:20" },
        { image: "@/assets/cam.jpg", date: "2024.07.25 16:12:59" },
        { image: "@/assets/cam.jpg", date: "2024.07.25 13:25:59" },
      ],
      firingRecords: [
        { image: "@/assets/cam.jpg", date: "2024.07.26 13:01:54" },
        { image: "@/assets/cam.jpg", date: "2024.07.25 18:27:02" },
        { image: "@/assets/cam.jpg", date: "2024.07.25 17:12:38" },
        { image: "@/assets/cam.jpg", date: "2024.07.25 12:48:08" },
      ],
    };
  },
  methods: {
    expandDrawer() {
      this.mini = false;
      this.drawerWidth = 150;
    },
    collapseDrawer() {
      this.mini = true;
      this.drawerWidth = 56;
    },
    logout() {
      this.$router.push("/login");
    },
  },
};
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

.v-card {
  background-color: #093028;
  color: #fff;
}
</style>
