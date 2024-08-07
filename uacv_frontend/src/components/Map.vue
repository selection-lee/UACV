<template>
  <div>
    <h5 class="text-h5 font-weight-bold">MAP</h5>
    <div class="map">
      <div class="iframe-wrapper">
        <div class="iframe-container" :style="{ transform: 'scale(' + zoomLevel + ')', transformOrigin: '200px 200px' }">
          <iframe
            class="map-iframe"
            src="http://192.168.100.163:3000/"
            frameborder="0"
          ></iframe>
        </div>
      </div>
      <div class="status">
        <p>배터리: 60%</p>
        <p>총: 준비 완료</p>
      </div>
      <div class="zoom-controls">
        <label for="zoomSlider">Zoom Level:</label>
        <input
          id="zoomSlider"
          type="range"
          min="1"
          max="2.5"
          step="0.1"
          v-model="zoomLevel"
        />
        <span>{{ zoomLevel }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Map",
  data() {
    return {
      zoomLevel: 1 // 초기 배율
    };
  }
};
</script>

<style scoped>
.map {
  text-align: center;
  position: relative;
}

.iframe-wrapper {
  position: relative;
  width: 100%;
  max-width: 600px;
  height: 400px;
  overflow: hidden;
}

.iframe-container {
  width: 100%;
  height: 100%;
  transform-origin: 200px 200px; /* 확대/축소 기준점 수정 */
}

.map-iframe {
  width: 100%;
  height: 100%;
  border: none;
}

.status {
  margin-top: 10px;
}

.zoom-controls {
  margin-top: 10px;
}

#zoomSlider {
  margin-left: 10px;
}
</style>
