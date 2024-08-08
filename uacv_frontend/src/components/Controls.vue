<template>
  <h5 class="text-h5 font-weight-bold">자율주행</h5>
  <div class="controls">
    <button @click="emergencyStop">긴급정지</button>
    <br />
    <button @click="start">START</button>
    <!-- 조이스틱 제어 레이아웃 추가 -->
  </div>
</template>

<script>
export default {
  name: "Controls",
  methods: {
    emergencyStop() {
      console.log("긴급정지 버튼이 클릭되었습니다!");
      // 긴급 정지 기능 구현
    },
    start() {
      console.log("주행 버튼이 클릭되었습니다!");
      // 시작 기능 구현
    },
  },
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
}

button:hover {
  background-color: #750e21;
}

.status {
  margin-top: 10px;
}
</style>
