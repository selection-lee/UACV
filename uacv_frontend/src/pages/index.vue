<template>
  <v-app>
    <Navbar />

    <!--
    <v-app-bar app>
      <v-toolbar-title>
        <br>
        <v-img src="@/assets/logo.png" height="100" contain></v-img>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>
    -->

    <v-main>
      <v-container class="fill-height">
        <v-responsive class="align-center fill-height mx-auto" max-width="900">
          <v-row align="center">
            <v-col cols="auto">
              <h4 class="text-h4 font-weight-bold">UACV</h4>
            </v-col>
          </v-row>
          <div class="py-4" />

          <!--Dashboard Components -->
          <v-row>
            <v-col cols="6">
              <div class="map-section">
                <Map />
              </div>
            </v-col>

            <v-col cols="3">
              <div class="cam-section">
                <!-- <Cam camSrc="@/assets/cam.jpg" :speed="23" :distance="1.3" /> -->
                 <CameraMonitor/>
              </div>
            </v-col>

            <v-col cols="3">
              <div class="cam-canon-section">
                <!-- <Cam_canon camSrc="@/assets/cam.jpg" :ammo="3" /> -->
                <CannonMonitor/>
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
  </v-app>
</template>

<script setup>
import Map from "@/components/Map.vue"
import CameraMonitor from "@/components/CameraMonitor.vue"
import CannonMonitor from "@/components/CannonMonitor.vue"
import Cam from "@/components/Cam.vue"
import Cam_canon from "@/components/Cam_canon.vue"
import Log from "@/components/Log.vue"
import Controls from "@/components/Controls.vue"
import Navbar from "@/components/navbar.vue"
</script>

<style scoped>
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
  color: #ffffef;
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
  color: #ffffef;
}
</style>