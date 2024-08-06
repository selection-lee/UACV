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
        <v-img src="@/assets/logo.png" height="100" contain />
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer />
    </v-app-bar>
    
    <v-main>
      <v-container>
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">기록보기</h3>
          <v-btn icon :to="{ path: '/log_text' }">
            <v-icon>mdi-text</v-icon>
          </v-btn>
          <v-btn icon :to="{ path: '/log_cam' }">
            <v-icon>mdi-video</v-icon>
          </v-btn>
        </div>
        
        <v-data-table
          :headers="headers"
          :items="filteredItems"
          class="elevation-1"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>기록보기</v-toolbar-title>
              <v-divider inset vertical class="mx-4" />
              
              <v-menu
                v-model="menu"
                max-height="400"
                :close-on-content-click="false"
                transition="scale-transition"
                class="custom-menu"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon v-bind="attrs" v-on="on" @click="toggleMenu">
                    <v-icon>mdi-filter</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                    v-for="option in filterOptions"
                    :key="option"
                    @click="applyFilter(option)"
                  >
                    <v-list-item-content>{{ option }}</v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-toolbar>
          </template>
        </v-data-table>
        
        <v-pagination
          v-model="page"
          :length="pages"
          class="mt-4"
        />
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
      drawerWidth: 56,
      page: 1,
      pages: 5,
      menu: false,
      filterOption: null,
      headers: [
        { text: "시간", value: "time" },
        { text: "구분", value: "type" },
        { text: "내용", value: "content" },
      ],
      filterOptions: ["소리인식", "발사기록", "센서인식"],
      items: [
      {
          time: "2024.07.26 11:08:25",
          type: "소리인식",
          content: "총 소리 인식 (AKM으로 추정됨)",
        },
        {
          time: "2024.07.25 18:07:41",
          type: "발사기록",
          content: "포 1발 발사",
        },
        {
          time: "2024.07.25 18:08:56",
          type: "소리인식",
          content: "총 소리 인식 (AKM으로 추정됨)",
        },
        {
          time: "2024.07.25 17:59:16",
          type: "소리인식",
          content: "총 소리 인식 (ACE으로 추정됨)",
        },
        {
          time: "2024.07.25 13:08:25",
          type: "소리인식",
          content: "총 소리 인식 (M762으로 추정됨)",
        },
        {
          time: "2024.07.25 11:54:52",
          type: "센서인식",
          content: "UACV 적발 추정",
        },
        {
          time: "2024.07.25 11:25:48",
          type: "발사기록",
          content: "포 1발 발사",
        },
        {
          time: "2024.07.25 11:24:43",
          type: "소리인식",
          content: "총 소리 인식 (AKM으로 추정됨)",
        },
        {
          time: "2024.07.25 11:08:07",
          type: "소리인식",
          content: "총 소리 인식 (M762으로 추정됨)",
        },
      ],
    };
  },

  computed: {
    filteredItems() {
      if (this.filterOption) {
        return this.items.filter(item => item.type === this.filterOption);
      }
      return this.items;
    },
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
    applyFilter(option) {
      this.filterOption = option;
      this.menu = false;
    },
    toggleMenu() {
      this.menu = !this.menu;
    },
  },
  
  components: {
    NavigationItem: {
      props: {
        icon: String,
        title: String,
        to: String,
        mini: Boolean,
      },
      template: `
        <v-list-item :to="to" @click="$emit('click')">
          <v-list-item-icon v-if="icon">
            <v-icon>{{ icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content v-if="title && !mini">
            <v-list-item-title>{{ title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      `,
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

.v-menu {
  position: absolute;
  top: 30%;
  left: 85%;
}
</style>
