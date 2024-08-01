<template>
    <h5 class="text-h5 font-weight-bold">MAP</h5>
    <div class="map">
      <img src="@/assets/SlamMapping.jpg" alt="Map" />
      <div class="status">
        <p>배터리: 60%</p>
        <p>총: 준비 완료</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Map'
  }
  </script>
  
  <style scoped>
  .map {
    /* 맵 컴포넌트 스타일 */
  }
  .status {
    /* 상태 스타일 */
  }
  </style>
  