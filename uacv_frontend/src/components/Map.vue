<template>
  <h5 class="text-h5 font-weight-bold">MAP</h5>
  <div class="map">
    <img class="map-image" src="@/assets/SlamMapping.jpg" alt="Map" />
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
  text-align: center; 
}

.map-image {
  width: 100%; 
  max-width: 600px;
  height: auto;
}

.status {
  margin-top: 10px; 
}
</style>
