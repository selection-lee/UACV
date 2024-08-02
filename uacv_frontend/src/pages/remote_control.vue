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
          <v-img src="@/assets/logo.png" height="100" contain></v-img>
          <span class="ml-3">UACV</span>
        </v-toolbar-title>
        <v-spacer></v-spacer>
    </v-app-bar>

  <v-container>
    <div class="text-center">
      <div class="text-h3 font-weight-bold mb-n1">원격 주행</div>
    </div>

    <div class="text-center mt-5">
      <v-form>
        <div class="mt-4">
          <h5>원격주행</h5>
          <div id="joyDiv" style="width:200px;height:200px;margin-bottom:20px;"></div>
          <div>Joystick X: {{ joystickX }}</div>
          <div>Joystick Y: {{ joystickY }}</div>
          <v-btn 
            @mousedown="startLogging('right')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
            class="mr-2"
          >Right</v-btn>
          <v-btn 
            @mousedown="startLogging('left')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
            class="mr-2"
          >Left</v-btn>
          <v-btn 
            @mousedown="startLogging('front')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
            class="mr-2"
          >FRONT</v-btn>
          <v-btn 
            @mousedown="startLogging('back')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
          >BACK</v-btn>
        </div>
      </v-form>

      <v-form>
        <div class="mt-4">
          <h5>포신방향</h5>
          <v-btn 
            @mousedown="startLogging('right_cannon')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
            class="mr-2"
          >Right</v-btn>
          <v-btn 
            @mousedown="startLogging('left_cannon')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
            class="mr-2"
          >Left</v-btn>
          <v-btn 
            @mousedown="startLogging('up_cannon')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
            class="mr-2"
          >UP</v-btn>
          <v-btn 
            @mousedown="startLogging('down_cannon')" 
            @mouseup="stopLogging" 
            @mouseleave="stopLogging"
          >DOWN</v-btn>
        </div>
      </v-form>
    </div>
  </v-container>
  </v-app>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const joystickX = ref(0)
const joystickY = ref(0)
let joy
let joystickInterval

const initializeJoystick = () => {
  joy = new window.JoyStick('joyDiv', {}, (stickData) => {
    joystickX.value = stickData.x
    joystickY.value = stickData.y
  })

  joystickInterval = setInterval(() => {
    joystickX.value = joy.GetX()
    joystickY.value = joy.GetY()
  }, 50)
}

const loadScript = (src, callback) => {
  const script = document.createElement('script')
  script.src = src
  script.onload = callback
  script.onerror = () => {
    console.error(`Failed to load script: ${src}`)
  }
  document.head.appendChild(script)
}

onMounted(() => {
  loadScript('/js/joy.js', initializeJoystick)
})

onUnmounted(() => {
  if (joystickInterval) {
    clearInterval(joystickInterval)
  }
})

const logInterval = ref(null)

const startLogging = (direction) => {
  logInterval.value = setInterval(() => {
    console.log(direction)
  }, 100)
}

const stopLogging = () => {
  clearInterval(logInterval.value)
  logInterval.value = null
}

</script>

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
.mr-2 {
  margin-right: 8px;
}
.dashboard {
  display: grid;
  grid-template-areas:
    'map-section map-section'
    'cams-section cam-canon-section'
    'log-section controls-section';
  grid-gap: 20px;
}
.v-toolbar {
    background-color: #004d40;
}
</style>
