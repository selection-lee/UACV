<template>
  <v-responsive class="align-center fill-height mx-auto" max-width="900">

    <v-row>
      <v-col cols="6">
        <div class="map-section">
          <Map />
          <SoundAlert />
        </div>
      </v-col>

      <v-col cols="3">
        <div class="cam-section">
          <CameraMonitor />
        </div>
      </v-col>

      <v-col cols="3">
        <div class="cam-canon-section">
          <CannonMonitor />
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
</template>

<script setup>

</script>

<style lang="scss" scoped></style>