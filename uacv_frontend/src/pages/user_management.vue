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
        <v-list-item to="/home">
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
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">Administrator</h3>
          <v-btn icon :to="{ path: '/signup' }">
            <v-icon>mdi-new-box</v-icon>
          </v-btn>
        </div>

        <v-data-table v-if="members" :headers="headers" :items="members" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>기록보기</v-toolbar-title>
              <v-divider inset vertical class="mx-4"></v-divider>
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
        </v-data-table>

        <v-pagination
          v-model="page"
          :length="pages"
          class="mt-4"
        ></v-pagination>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { ref, onMounted } from 'vue';
import { userAdminStore } from '@/stores/admin';

const store = userAdminStore()
const members = ref(null)

// 비동기화
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

onMounted(async () => {
    store.memberList()
    // 기다려!
    await sleep(100)
    members.value = store.members
    console.log(members.value)
})


export default {
  data() {
    return {
      store,
      members,
      drawer: true,
      mini: true,
      miniWidth: 56,
      drawerWidth: 56,
      page: 1,
      pages: 5,
      headers: [
        { text: "PK", value: "pk" },
        { text: "ID", value: "id" },
        { text: "권한", value: "authority" },
        { text: "소속계급", value: "class" },
        { text: "군번", value: "m_id" },
        { text: "비고", value: "icon" },
      ],
      // items: members.value,
      // [
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // },
        // {
        //   pk: "14572",
        //   id: "gildong@army.ko",
        //   authority: "관리자",
        //   class: "대령",
        //   m_id: "07-5789146",
        //   icon: "",
        // }
      // ],
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
      this.$router.push("/");
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

.v-pagination {
  justify-content: center;
}
</style>