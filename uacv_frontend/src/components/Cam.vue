<template>
  <h5 class="text-h5 font-weight-bold">CAM1</h5>
    <div class="cam">
      <img src="@/assets/cam.jpg" alt="카메라 피드" />
      <div class="details">
        <p>{{ speed }} km/h</p>
        <p>{{ distance }} km</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Cam',
    props: {
      camSrc: String,
      speed: Number,
      distance: Number
    }
  }
  </script>
  
  <style scoped>
  .cam {
    text-align: center; 
  }

  .cam-image {
    width: 100%; 
    max-width: 600px;
    height: auto;
  }

  .status {
    margin-top: 10px; 
  }
  </style>
  