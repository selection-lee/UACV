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
        <v-divider></v-divider>
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
        <br>
        <v-img src="@/assets/logo.png" height="100" contain></v-img>
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>

    <v-main>
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="900">
          <v-row align="center">
            <v-col cols="auto">
              <h4 class="text-h4 font-weight-bold">원격주행</h4>
            </v-col>
          </v-row>

          <!-- Components -->
          <v-row>
            <v-col cols="6">
              <div class="cam-section">
                <Cam camSrc="@/assets/cam.jpg" :speed="23" :distance="1.3" />
              </div>
            </v-col>

            <v-col cols="6">
              <div class="cam-canon-section">
                <Cam_canon camSrc="@/assets/cam.jpg" :ammo="3" />
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="6" class="text-center">
              <v-form>
                <div class="mt-4">
                  <h3>원격주행</h3>
                  <!-- Joystick -->
                  <!--<joy @move="handleJoystickMove" />-->
                  <!-- Button -->
                  <v-btn
                    @mousedown="startLogging('right')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn mr-2"
                    >Right</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('left')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn mr-2"
                    >Left</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('up')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn mr-2"
                    >UP</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('down')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn"
                    >DOWN</v-btn
                  >
                </div>
              </v-form>
            </v-col>

            <v-col cols="6" class="text-center">
              <v-form>
                <div class="mt-4">
                  <h5>포신방향</h5>
                  <!-- Button -->
                  <v-btn
                    @mousedown="startLogging('right_cannon')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn mr-2"
                    >Right</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('left_cannon')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn mr-2"
                    >Left</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('up_cannon')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn mr-2"
                    >UP</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('down_cannon')"
                    @mouseup="stopLogging"
                    @mouseleave="stopLogging"
                    class="direction-btn"
                    >DOWN</v-btn
                  >
                </div>
              </v-form>
            </v-col>
          </v-row>
        </v-responsive>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref } from "vue";
import Cam from "@/components/Cam.vue";
import Cam_canon from "@/components/Cam_canon.vue";

const joystickX = ref(0);
const joystickY = ref(0);
const angle = ref(0);
const distance = ref(0);

const counts = ref({
  right: 0,
  left: 0,
  up: 0,
  down: 0,
});

const calculateAngleAndDistance = (x, y) => {
  const distance = Math.sqrt(x * x + y * y);
  const angle = Math.atan2(y, x) * (180 / Math.PI);
  return { angle, distance };
};

const handleJoystickMove = (joystickData) => {
  joystickX.value = joystickData.x;
  joystickY.value = joystickData.y;

  const { angle: newAngle, distance: newDistance } = calculateAngleAndDistance(
    joystickX.value,
    joystickY.value
  );
  angle.value = newAngle;
  distance.value = newDistance;
  console.log(
    `Angle: ${angle.value.toFixed(
      2
    )} degrees, Distance: ${distance.value.toFixed(2)}`
  );
};

const logInterval = ref(null);

const startLogging = (direction) => {
  logInterval.value = setInterval(() => {
    console.log(direction);
  }, 100);
};

const stopLogging = () => {
  if (logInterval.value) {
    clearInterval(logInterval.value);
    logInterval.value = null;
  }
};
</script>

<script>
export default {
  name: "Cam_view",
  components: {
    Cam,
    Cam_canon,
  },
  data() {
    return {
      drawer: true,
      mini: true,
      miniWidth: 56,
      drawerWidth: 56,
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
.cam_view {
  display: grid;
  grid-template-areas:
    "cams-section cam-canon-section"
    "joy-div direction-btn";
  grid-gap: 20px;
}

.mr-2 {
  margin-right: 8px;
}

.text-center {
  text-align: center;
}

.v-main {
  background-color: #093028;
  color: #ffffef;
}

.v-toolbar {
  background-color: #004d40;
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

.joy-div {
  width: 200px;
  height: 200px;
  margin: 0 auto;
}

.direction-btn {
  margin: 4px;
}
</style>
