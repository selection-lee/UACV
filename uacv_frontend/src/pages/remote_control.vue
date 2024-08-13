<template>
  <v-app>
    <Navbar />

    <v-app-bar app>
      <v-toolbar-title>
        <br />
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
                  <!--주행 슬라이더, 버튼 -->
                  <div class="angle-controls">
                    <label for="angleSlider">angle : </label>
                    <input
                      id="angle"
                      type="range"
                      min="45"
                      max="135"
                      step="5"
                      v-model="steerAngle"
                      @input="updateSteerAngle"
                    />
                    <span>{{ steerAngle }}</span>
                  </div>

                  <v-btn
                    @mousedown="startLogging('forward')"
                    @click="moveForward"
                    class="direction-btn mr-2"
                    >FORWARD</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('backward')"
                    @click="moveBackward"
                    class="direction-btn"
                    >BACKWARD</v-btn
                  >
                  <br />
                  <v-btn
                    @mousedown="startLogging('stop')"
                    @click="stopVehicle"
                    class="direction-btn"
                    >STOP</v-btn
                  >
                </div>
              </v-form>
            </v-col>

            <v-col cols="6" class="text-center">
              <v-form>
                <div class="mt-4">
                  <h5>포신방향</h5>
                  <!--포신 버튼 -->
                  <v-btn
                    @mousedown="startLogging('right_cannon')"
                    @click="cannonRight"
                    class="direction-btn mr-2"
                    >Left</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('left_cannon')"
                    @click="cannonLeft"
                    class="direction-btn mr-2"
                    >Right</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('up_cannon')"
                    @click="cannonUp"
                    class="direction-btn mr-2"
                    >UP</v-btn
                  >
                  <v-btn
                    @mousedown="startLogging('down_cannon')"
                    @click="cannonDown"
                    class="direction-btn"
                    >DOWN</v-btn
                  >
                  <br />
                  <v-btn
                    @mousedown="startLogging('fire_cannon')"
                    @click="sendFire"
                    class="direction-btn"
                    >FIRE</v-btn
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
import { ref, onMounted  } from "vue";
import Cam from "@/components/Cam.vue";
import Cam_canon from "@/components/Cam_canon.vue";
import Navbar from "@/components/navbar.vue";
import { useDeviceControlStore } from "@/stores/device_control";

const store = useDeviceControlStore();

// 상태 값
const steerAngle = ref(90);
const moveState = ref(null);
const cannonAngle = ref(90);
const cannonElevation = ref(140);

const computedSteerAngle = computed({
  get: () => steerAngle.value,
  set: (value) => {
    steerAngle.value = value;
    store.sendSteerCommand(value);
    store.steerAngle = value; // Update the store
  },
});

const computedMoveState = computed({
  get: () => moveState.value,
  set: (value) => {
    moveState.value = value;
    store.sendMoveCommand(value);
    store.moveState = value; // Update the store
  },
});

const computedCannonAngle = computed({
  get: () => cannonAngle.value,
  set: (value) => {
    cannonAngle.value = value;
    store.sendCannonCommand(value, cannonElevation.value);
    store.cannonAngle = value; // Update the store
  },
});

const computedCannonElevation = computed({
  get: () => cannonElevation.value,
  set: (value) => {
    cannonElevation.value = value;
    store.sendCannonCommand(cannonAngle.value, value);
    store.cannonElevation = value; // Update the store
  },
});

onMounted(() => {
  store.sendSteerCommand(computedSteerAngle.value);
  store.sendMoveCommand(computedMoveState.value);
  store.sendCannonCommand(computedCannonAngle.value, computedCannonElevation.value);
});

const updateSteerAngle = () => {
  computedSteerAngle.value = steerAngle.value;
};

const moveForward = () => {
  computedMoveState.value = "forward";
};

const moveBackward = () => {
  computedMoveState.value = "backward";
};

const stopVehicle = () => {
  computedMoveState.value = "stop";
};

const cannonLeft = () => {
  if (computedCannonAngle.value > 10) computedCannonAngle.value -= 5;
};

const cannonRight = () => {
  if (computedCannonAngle.value < 170) computedCannonAngle.value += 5;
};

const cannonUp = () => {
  if (computedCannonElevation.value > 70) computedCannonElevation.value -= 5;
};

const cannonDown = () => {
  if (computedCannonElevation.value < 140) computedCannonElevation.value += 5;
};

const sendFire = () => {
  store.sendFireCommand();
};

const startLogging = (message) => {
  console.log(message);
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
.angle-controls {
  margin-top: 10px;
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
.direction-btn {
  margin: 4px;
}
input[type="range"] {
  -webkit-appearance: none; /* 크롬, 사파리, 오페라 */
  appearance: none;
  background: transparent; /* 기본 게이지 배경을 투명하게 설정 */
}

input[type="range"]::-webkit-slider-runnable-track {
  width: 100%;
  height: 8px; /* 슬라이더 트랙의 높이를 원하는 크기로 설정 */
  background: #ddd; /* 슬라이더 트랙의 색상 */
  border-radius: 5px;
}

input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 16px;
  height: 16px;
  background: #007bff; /* 슬라이더 핸들의 색상 */
  border-radius: 50%;
  cursor: pointer;
  margin-top: -4px; /* 슬라이더 트랙의 중앙에 맞추기 위해 조정 */
}

input[type="range"]::-moz-range-track {
  width: 100%;
  height: 8px;
  background: #ddd;
  border-radius: 5px;
}

input[type="range"]::-moz-range-thumb {
  width: 16px;
  height: 16px;
  background: #007bff;
  border-radius: 50%;
  cursor: pointer;
}

/* IE 및 Edge의 경우 */
input[type="range"]::-ms-track {
  width: 100%;
  height: 8px;
  background: transparent;
  border-color: transparent;
  color: transparent;
}

input[type="range"]::-ms-fill-lower {
  background: #ddd;
  border-radius: 5px;
}

input[type="range"]::-ms-fill-upper {
  background: #ddd;
  border-radius: 5px;
}

input[type="range"]::-ms-thumb {
  width: 16px;
  height: 16px;
  background: #007bff;
  border-radius: 50%;
  cursor: pointer;
}
</style>
