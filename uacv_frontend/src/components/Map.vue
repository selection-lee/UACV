<template>
  <div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
    <h2>MAP</h2>
    <div class="map">
      <div class="canvas-wrapper">
        <div class="canvas-container" :style="{ transform: 'scale(' + zoomLevel + ')' }">
          <canvas id="mapCanvas"></canvas>
        </div>
      </div>
      <div class="zoom-controls">
        <label for="zoomSlider">Zoom Level:</label>
        <input @mouseleave="resetZoom" id="zoomSlider" type="range" min="0" max="2" step="0.1" v-model="zoomLevel" />
        <span>{{ zoomLevel }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import ROSLIB from 'roslib'

const zoomLevel = ref(1)
const SOCKET_API_URL = import.meta.env.VITE_MAPPING_SOCKET_URL

const resetZoom = function () {
  zoomLevel.value = 1
}

onMounted(() => {
  const ros = new ROSLIB.Ros({
    url: `${SOCKET_API_URL}/mapping`
  });

  ros.on('connection', function () {
  });

  ros.on('error', function (error) {
    console.log('Error connecting to websocket server:', error);
  });

  const mapListener = new ROSLIB.Topic({
    ros: ros,
    name: '/map',
    messageType: 'nav_msgs/OccupancyGrid'
  });

  const scanListener = new ROSLIB.Topic({
    ros: ros,
    name: '/scan',
    messageType: 'sensor_msgs/LaserScan'
  });

  const canvas = document.getElementById('mapCanvas');
  const ctx = canvas.getContext('2d');

  var mapData = null;
  var scanData = null;
  var mapScale = 3; // 지도 확대 배율
  var scanScale = 60; // 스캔 확대 배율
  var offsetY = -30; // 지도 Y 축으로 이동할 픽셀 수
  var offsetX = 0; // 스캔 X 축으로 이동할 픽셀 수
  var scanOffsetY = 0; // 스캔 Y 축으로 이동할 픽셀 수

  canvas.width = 350;
  canvas.height = 350;

  mapListener.subscribe(function (message) {
    mapData = message
    drawMap()
  })

  scanListener.subscribe(function (message) {
    scanData = message
    drawMap()
  })

  async function drawMap() {
    if (!mapData) return;

    var width = mapData.info.width;
    var height = mapData.info.height;
    var resolution = mapData.info.resolution;
    var originX = mapData.info.origin.position.x;
    var originY = mapData.info.origin.position.y;

    var imageData = ctx.createImageData(width * mapScale, height * mapScale);

    for (let y = 0; y < height; y++) {
      for (let x = 0; x < width; x++) {
        let value = mapData.data[y * width + x];
        let color = (value === 100) ? 0 : (value === -1 ? 128 : 255);
        for (let dy = 0; dy < mapScale; dy++) {
          for (let dx = 0; dx < mapScale; dx++) {
            let index = ((width - 1 - x) * mapScale + dx + (y * mapScale + dy) * width * mapScale) * 4;
            imageData.data[index] = color;
            imageData.data[index + 1] = color;
            imageData.data[index + 2] = color;
            imageData.data[index + 3] = 255; // Alpha
          }
        }
      }
    }

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // 지도 회전 변환
    const imageBitmap = await createImageBitmap(imageData);
    ctx.save();
    ctx.translate(canvas.width / 2, canvas.height / 2);
    ctx.rotate(Math.PI / 2); // 90도 회전
    ctx.translate(-imageData.height / 2, -imageData.width / 2);
    ctx.drawImage(imageBitmap, 0, 0);
    ctx.restore();

    drawScan(); // 맵을 그린 후 스캔 데이터 그리기
  }

  function drawScan() {
    if (!scanData) return;

    var width = canvas.width;
    var height = canvas.height;
    var angle_min = scanData.angle_min;
    var angle_increment = scanData.angle_increment;
    var range_max = scanData.range_max;

    ctx.save(); // 현재 상태 저장
    ctx.globalCompositeOperation = 'source-over'; // 기존 맵 데이터 위에 그리기

    ctx.fillStyle = 'red';

    scanData.ranges.forEach((range, index) => {
      if (range < range_max) {
        const angle = angle_min + index * angle_increment;
        const x = (range * Math.cos(angle)) * scanScale;
        const y = (range * Math.sin(angle)) * scanScale;

        // 90도 회전
        const rotatedX = -y;
        const rotatedY = x;

        // 상하 반전 추가
        const flippedX = rotatedX;
        const flippedY = -rotatedY;

        ctx.beginPath();
        ctx.arc((canvas.width / 2) + flippedX + offsetX, (canvas.height / 2) + flippedY + scanOffsetY, 1, 0, 2 * Math.PI);
        ctx.fill();
      }
    });

    ctx.restore(); // 저장된 상태 복원
  }
})
</script>

<style scoped>
.map {
  text-align: center;
  position: relative;
}

.canvas-wrapper {
  position: relative;
  width: 100%;
  max-width: 600px;
  height: 350px;
  overflow: hidden;
}

.canvas-container {
  width: 100%;
  height: 100%;
  transform-origin: 200px 200px;
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

* {
  font-family: 'Noto Sans KR', sans-serif;
  color: #ffffef;
}
</style>
