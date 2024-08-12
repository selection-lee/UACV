<template>
  <h5 class="text-h5 font-weight-bold">CAM2</h5>
  <div class="cam-canon">
    <img src="@/assets/cam.jpg" alt="카메라 피드" />
    <div class="details">
      <p>잔탄 {{ ammo }} / 10</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cam_canon",
  props: {
    ammo: Number,
  },
};
</script>

<style scoped>
.cam-canon {
  text-align: center;
}

.cam-canon-image {
  width: 100%;
  max-width: 600px;
  height: auto;
}

.status {
  margin-top: 10px;
}

*{
  font-family: 'Noto Sans KR', sans-serif;
  color: #FFFFEF;
}
</style>
