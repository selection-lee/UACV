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
        <v-list-item @click="logout">
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
  
  <script>
  export default {
    data() {
      return {
        drawer: true,
        mini: true,
        miniWidth: 56,
        drawerWidth: 56
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
      this.$router.push('/login'); 
      },
    }
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
  
  .v-pagination {
    justify-content: center;
  }
  </style>
  