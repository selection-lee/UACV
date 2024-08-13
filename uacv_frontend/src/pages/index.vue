<template>
  <v-app>
    <Navbar v-if="connect !== null"/>

    <Appbar v-if="connect !== null"/>

    <v-main v-if="connect !== null">
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="900">

          <v-row>
            <v-col cols="6">
              <div class="map-section">
                <Map />
              </div>
            </v-col>

            <v-col cols="3">
              <div class="cam-section">
                <!-- <Cam camSrc="@/assets/cam.jpg" :speed="23" :distance="1.3" /> -->
                 <CameraMonitor :speed="23" :distance="1.3"/>
              </div>
            </v-col>

            <v-col cols="3">
              <div class="cam-canon-section">
                <!-- <Cam_canon camSrc="@/assets/cam.jpg" :ammo="3" /> -->
                <CannonMonitor :ammo="3"/>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="6">
              <div class="log-section">
                <Log />
              </div>
            </v-col>

            <v-col cols="4">
              <div class="controls-section">
                <Controls />
              </div>
            </v-col>

            <v-col cols="2">
              <h5 class="text-h5 font-weight-bold">원격주행</h5>
              <div class="remote-control-section">
                <router-link :to="{ path: '/remote_control' }">
                  <v-icon>mdi-controller</v-icon>
                </router-link>
                <RemoteControl />
              </div>

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

import { onMounted, ref } from "vue"
import { useDeviceControlStore } from "@/stores/device_control"
import Loading from "./loading.vue"

const connect = ref(null)
const store = useDeviceControlStore()

onMounted(async() => {
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