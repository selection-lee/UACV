<template>
  <h5 class="text-h5 font-weight-bold">자율주행</h5>
  <div class="controls">
    <div style="display: flex; gap: 10px">
      <button @click="emergencyStop">긴급정지</button>
      <button @click="start">START</button>
    </div>
  </div>
</template>

<script setup>
import { useDeviceControlStore } from "@/stores/device_control";
import { ref } from "vue";

// Pinia 스토어 가져오기
const store = useDeviceControlStore();

// 긴급 정지 및 시작 기능 구현
const emergencyStop = async () => {
  console.log("긴급정지 버튼이 클릭되었습니다!");
  // 긴급 정지 명령 전송
  await store.sendMoveCommand("stop");
};

const start = async () => {
  console.log("주행 버튼이 클릭되었습니다!");
  // 시작 명령 전송
  await store.sendMoveCommand("forward");
};
</script>

<style scoped>
.controls {
  text-align: center;
}

button {
  background-color: #750e21;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  float: left;
}

button:hover {
  background-color: #550d1d;
}

.status {
  margin-top: 10px;
}

*{
  font-family: 'Noto Sans KR', sans-serif;
  color: #ffffef;
}
</style>
