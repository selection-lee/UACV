<template>
  <v-app>
    <Navbar v-if="connect !== null" />

    <Appbar v-if="connect !== null" />

    <v-main v-if="connect !== null">
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="1200px">
          <div style="display: flex; flex-direction: row; justify-content: center; align-items: center">
            <!-- Map -->
            <div style="justify-content: center; align-items: center;">
              <div
                style="background-color: #2c4d41; justify-content: center; align-items: center; border-radius: 10%; margin-left: 10px; padding: 10px;">
                <div class="map-section" style="padding: 5px;">
                  <Map />
                </div>
                <CoordinateSender />
              </div>
              <SoundAlert />
            </div>

            <!-- camera -->
            <div>
              <div style="display: flex; flex-direction: row; justify-content: center; align-items: center;">
                <!-- camera1 -->
                <div cols="6"
                  style="width: 330px; background-color: #2c4d41; border-radius: 10%; margin-left: 10px; margin-bottom: 10px; flex: 1; padding: 10px;">
                  <CameraMonitor />
                </div>

                <!-- camera2 -->
                <div cols="6"
                  style="width: 330px; background-color: #2c4d41; border-radius: 10%; margin: 0px 10px 10px; flex: 1; padding: 10px;">
                  <CannonMonitor />
                </div>
              </div>

              <!-- log -->
              <div
                style="background-color: #2c4d41; border-radius: 10px; margin: 15px; padding: 10px; padding-left: 20px;">
                <div class="log-section">
                  <Log />
                </div>
              </div>
            </div>
          </div>
        </v-responsive>
      </v-container>
    </v-main>

    <Loading v-else />
  </v-app>
</template>


<script setup>
import Map from "@/components/Map.vue"
import CameraMonitor from "@/components/CameraMonitor.vue"
import CannonMonitor from "@/components/CannonMonitor.vue"
import Log from "@/components/Log.vue"
import Navbar from "@/components/navbar.vue"
import Appbar from "@/components/appbar.vue"
import SoundAlert from "@/components/SoundAlert.vue"
import CoordinateSender from "@/components/CoordinateSender.vue"


import { onUnmounted, onMounted, ref } from "vue"
import { useDeviceControlStore } from "@/stores/device_control"
import Loading from "./loading.vue"

const connect = ref(null)
const store = useDeviceControlStore()
const logUpdateTrigger = ref(0)
const socket = ref(null)

onMounted(async () => {
  connect.value = await store.connect()

  socket.value = new WebSocket(`${import.meta.env.VITE_SOUND_SOCKET_URL}`);
  socket.value.onmessage = () => {
    logUpdateTrigger.value++
  }
})

onUnmounted(() => {
  if (socket.value) {
    socket.value.close()
  }
})
</script>

<style scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
  color: #ffffef;
}

.dashboard {
  display: grid;
  grid-template-areas:
    "map-section map-section"
    "cams-section cam-canon-section"
    "log-section controls-section";
  grid-gap: 20px;
}

.v-main {
  background-color: #093028;
  color: #FFFFEF;
}

.v-toolbar {
  background-color: #004d40;
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

.remote-control-section {
  grid-area: controls-section;
  height: 100%;
}

.remote-control-section .v-icon {
  cursor: pointer;
  font-size: 100px;
  color: #FFFFEF;
}
</style>