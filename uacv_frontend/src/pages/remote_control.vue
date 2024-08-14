<template>
  <v-app>
    <Navbar />

    <Appbar />

    <v-main>
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="900">
          <div style="display: flex; flex-direction: row; justify-content: center; align-items: center;">
            <!-- 카메라 1 -->
            <div style="background-color: #2c4d41; border-radius: 10%; margin-left: 10px; padding: 20px; height: 432px;
             display: flex; flex-direction: column; justify-content: center; align-items: center;">
              <!-- 카메라 화면 -->
              <div>
                <CameraMonitor />
              </div>
              <!-- 조종 -->
              <div>
                <!-- 슬라이더 -->
                <div>
                  <div class="angle-controls">
                    <label for="angleSlider">angle : </label>
                    <input id="angle" type="range" min="45" max="135" step="5" v-model="steerAngle"
                      @input="updateSteerAngle" />
                    <span>{{ steerAngle }}</span>
                  </div>
                </div>
                <!-- 버튼 -->
                <div>
                  <div>
                    <v-btn @mousedown="startLogging('forward')" @click="moveForward"
                      class="direction-btn mr-2">FORWARD</v-btn>
                    <v-btn @mousedown="startLogging('backward')" @click="moveBackward"
                      class="direction-btn">BACKWARD</v-btn>
                  </div>
                  <div style="display: flex; flex-direction: row; justify-content: center; align-items: center;">
                    <v-btn @mousedown="startLogging('stop')" @click="stopVehicle" class="direction-btn">STOP</v-btn>

                  </div>
                </div>
              </div>
            </div>
            <!-- 카메라 2 -->
            <div style="background-color: #2c4d41; border-radius: 10%; margin-left: 50px; padding: 20px; height: 432px;
             display: flex; flex-direction: column; justify-content: center; align-items: center;">
              <!-- 카메라 화면 -->
              <div>
                <CannonMonitor />
              </div>
              <!-- 조종 -->
              <div style="margin-top: 10px;">
                <div style="display: flex; flex-direction: row; justify-content: center; align-items: center">
                  <v-btn @mousedown="startLogging('up_cannon')" @click="cannonUp" class="direction-btn mr-2">
                    UP
                  </v-btn>
                </div>
                <div>
                  <v-btn @mousedown="startLogging('left_cannon')" @click="cannonLeft"
                  class="direction-btn mr-2">Left</v-btn>
                
                  <v-btn @mousedown="startLogging('fire_cannon')" @click="sendFire" class="direction-btn">FIRE</v-btn>
                
                  <v-btn @mousedown="startLogging('right_cannon')" @click="cannonRight"
                  class="direction-btn mr-2">Right</v-btn>
                </div>

                <div style="display: flex; flex-direction: row; justify-content: center; align-items: center">
                <v-btn @mousedown="startLogging('down_cannon')" @click="cannonDown" class="direction-btn">DOWN</v-btn>
              </div>
              </div>
            </div>
          </div>
        </v-responsive>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import CameraMonitor from "@/components/CameraMonitor.vue";
import CannonMonitor from "@/components/CannonMonitor.vue";
import Navbar from "@/components/navbar.vue";

import { ref, onMounted, watch } from "vue";
import { useDeviceControlStore } from "@/stores/device_control";
import Appbar from "@/components/appbar.vue";

const store = useDeviceControlStore();

// 상태 값
const steerAngle = ref(store.steerAngle || 90);
const moveState = ref(store.moveState || null);
const cannonAngle = ref(store.cannonAngle || 90);
const cannonElevation = ref(store.cannonElevation || 140);

onMounted(() => {
  store.sendSteerCommand(steerAngle.value);
  store.sendMoveCommand(moveState.value, steerAngle.value);
  store.sendCannonCommand(cannonAngle.value, cannonElevation.value);
});

watch(steerAngle, (newValue) => {
  store.steerAngle = newValue;
  store.sendSteerCommand(newValue);
});

watch(moveState, (newValue) => {
  store.sendMoveCommand(newValue, steerAngle.value);
});

watch(cannonAngle, (newValue) => {
  store.cannonAngle = newValue;
  store.sendCannonCommand(newValue, cannonElevation.value);
});

watch(cannonElevation, (newValue) => {
  store.cannonElevation = newValue;
  store.sendCannonCommand(cannonAngle.value, newValue);
});

const moveForward = () => {
  moveState.value = "forward";
  store.sendMoveCommand("forward", steerAngle.value);
};

const moveBackward = () => {
  moveState.value = "backward";
  store.sendMoveCommand("backward", steerAngle.value);
};

const stopVehicle = () => {
  moveState.value = "stop";
  store.sendMoveCommand("stop", steerAngle.value);
};

const cannonRight = () => {
  if (cannonAngle.value > 10) cannonAngle.value -= 10;
};

const cannonLeft = () => {
  if (cannonAngle.value < 170) cannonAngle.value += 10;
};

const cannonUp = () => {
  if (cannonElevation.value > 70) cannonElevation.value -= 5;
};

const cannonDown = () => {
  if (cannonElevation.value < 140) cannonElevation.value += 5;
};

const sendFire = () => {
  store.sendFireCommand();
};

const updateSteerAngle = () => {
  store.steerAngle = steerAngle.value;
  store.sendSteerCommand(steerAngle.value);
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
  -webkit-appearance: none;
  appearance: none;
  background: transparent;
}

input[type="range"]::-webkit-slider-runnable-track {
  width: 100%;
  height: 8px;
  background: #ddd;
  border-radius: 5px;
}

input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 16px;
  height: 16px;
  background: #007bff;
  border-radius: 50%;
  cursor: pointer;
  margin-top: -4px;
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
