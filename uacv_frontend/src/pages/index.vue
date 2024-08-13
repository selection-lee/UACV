<template>
  <v-app>
    <Navbar v-if="connect !== null" />

    <Appbar v-if="connect !== null" />

    <v-main v-if="connect !== null">
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="1000px">
          <v-row>
            <v-col cols="6"
              style="display: flex; flex-direction: column; justify-content: space-around; align-items: center; padding: 10px;">

              <!-- Map Section -->
              <v-row
                style="background-color: #2c4d41; border-radius: 15px; padding: 10px; flex: 1;">
                <div class="map-section" style="margin: 10px;">
                  <Map />
                  <!-- <SoundAlert /> -->
                </div>
              </v-row>

              <!-- Log Section -->
              <v-row
                style="background-color: #2c4d41; border-radius: 15px; padding: 10px; margin-top: 20px; flex: 1;">
                <div class="log-section" style="width: 350px; margin: 10px;">
                  <Log />
                </div>
              </v-row>

            </v-col>

            <v-col cols="6" style="display: flex; flex-direction: column; justify-content: space-around; align-items: center; padding: 10px;">
              <v-row style="background-color: #2c4d41; border-radius: 15px; padding: 10px; flex: 1;">
                <CameraMonitor/>
              </v-row>

            </v-col>

          </v-row>
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
import Controls from "@/components/Controls.vue"
import Navbar from "@/components/navbar.vue"
import Appbar from "@/components/appbar.vue"
import SoundAlert from "@/components/SoundAlert.vue"

import { onMounted, ref } from "vue"
import { useDeviceControlStore } from "@/stores/device_control"
import Loading from "./loading.vue"

const connect = ref(null)
const store = useDeviceControlStore()

onMounted(async () => {
  connect.value = await store.connect()
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