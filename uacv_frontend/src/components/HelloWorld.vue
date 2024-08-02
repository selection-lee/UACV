/** dashboard **/

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
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="900">
          <v-img class="mb-4" height="150" src="@/assets/logo.png" />

          <div class="text-center">
            <div class="text-body-2 font-weight-light mb-n1">Welcome to</div>
            <h1 class="text-h2 font-weight-bold">UACV</h1>
          </div>

      <div class="text-center">
        <router-link :to="{path: '/login'}">로그인</router-link>
        <span> | </span>
        <router-link :to="{path: '/adminsignup'}">관리자 등록</router-link>
        <span> | </span>
        <router-link :to="{path: '/signup'}">회원 등록</router-link>
        <span> | </span>
        <router-link :to="{path: '/updatePassword'}">비밀번호 변경</router-link>
        <span> | </span>
        <router-link :to="{path: '/updaterole'}">권한 변경</router-link>
        <span> | </span>
        <router-link :to="{path: '/memberlist'}">회원 리스트</router-link>
      </div>
      <div class="py-4" />

          <div class="text-center">
            <router-link :to="{ path: '/login' }">로그인</router-link>
            <span> | </span>
            <router-link :to="{ path: '/signup' }">관리자 등록</router-link>
          </div>
          <div class="py-4" />

          <!-- Custom Dashboard Components -->
          <v-row>
            <v-col cols="6">
              <div class="map-section">
                <Map />
              </div>
            </v-col>

            <v-col cols="3">
              <div class="cam-section">
                <Cam camSrc="@/assets/cam.jpg" :speed="23" :distance="1.3" />
              </div>
            </v-col>

            <v-col cols="3">
              <div class="cam-canon-section">
                <Cam_canon camSrc="@/assets/cam.jpg" :ammo="3" />
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="4">
              <div class="log-section">
                <Log />
              </div>
            </v-col>

            <v-col cols="4">
              <div class="controls-section">
                <Controls />
              </div>
            </v-col>

            <v-col cols="4">
              <h5 class="text-h5 font-weight-bold">원격주행</h5>
              <div class="remote-control-section">
                <router-link :to="{ path: '/remote_control' }">원격 주행</router-link>
                <RemoteControl />
              </div>
            </v-col>
          </v-row>
        </v-responsive>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Map from '@/components/Map.vue'
import Cam from '@/components/Cam.vue'
import Cam_canon from '@/components/Cam_canon.vue'
import Log from '@/components/Log.vue'
import Controls from '@/components/Controls.vue'

export default {
  name: 'Dashboard',
  components: {
    Map,
    Cam,
    Cam_canon,
    Log,
    Controls
  },
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
    }
  }
};
</script>

<style scoped>
.dashboard {
  display: grid;
  grid-template-areas:
    'map-section map-section'
    'cams-section cam-canon-section'
    'log-section controls-section';
  grid-gap: 20px;
}

.map-section {
  grid-area: map-section;
}

.cams-section {
  grid-area: cams-section;
  display: flex;
  justify-content: space-around;
}
.cams-canon-section {
  grid-area: cams-canon-section;
  display: flex;
  justify-content: space-around;
}

.log-section {
  grid-area: log-section;
}

.controls-section {
  grid-area: controls-section;
}
</style>
