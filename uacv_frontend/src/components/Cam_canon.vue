<template>
  <h5 class="text-h5 font-weight-bold">CAM2</h5>
    <div class="cam-canon">
      <img src="@/assets/cam.jpg" alt="카메라 피드" />
      <div class="details">
        <p>{{ speed }} km/h</p>
        <p>{{ distance }} km</p>
        <p>{{ ammo }} / 10</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Cam_canon',
    props: {
      camSrc: String,
      speed: Number,
      distance: Number,
      ammo: Number
    }
  }
  </script>
  
  <style scoped>
  .cam-canon {
    /* 카메라 컴포넌트 스타일 */
  }
  .details {
    /* 세부사항 스타일 */
  }
  </style>
  