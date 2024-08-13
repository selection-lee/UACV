<template>
  <div>
    <div class="row">
      <div class="columnLateral">
        <div id="joy1Div" ref="joy1Div" class="joystick"></div>
        <div>Posizione X: <input v-model="joy1PosX" type="text" readonly /></div>
        <div>Posizione Y: <input v-model="joy1PosY" type="text" readonly /></div>
        <div>Direzione: <input v-model="joy1Dir" type="text" readonly /></div>
      </div>
    </div>
  </div>
</template>

<script>
import JoyStick from './joy.js';

export default {
  data() {
    return {
      joy1: null,
      joy1PosX: '',
      joy1PosY: '',
      joy1Dir: ''
    };
  },
  mounted() {
    this.joy1 = new JoyStick(this.$refs.joy1Div, {}, this.handleJoystickMove);
  },
  methods: {
    handleJoystickMove(stickData) {
      this.joy1PosX = stickData.x;
      this.joy1PosY = stickData.y;
      this.joy1Dir = stickData.cardinalDirection;

      const angle = Math.atan2(this.joy1PosY, this.joy1PosX) * (180 / Math.PI);
      const distance = Math.sqrt(this.joy1PosX * this.joy1PosX + this.joy1PosY * this.joy1PosY);

      this.$emit('move', { x: this.joy1PosX, y: this.joy1PosY, angle, distance });
    }
  }
};
</script>

<style scoped>
.joystick {
  width: 200px;
  height: 200px;
  border: 2px solid black;
  border-radius: 50%;
  position: relative;
  background-color: #f0f0f0;
}
.controls {
  margin-top: 20px;
}
.row {
  display: flex;
}
.columnLateral, .columnCetral {
  margin: 10px;
}
</style>