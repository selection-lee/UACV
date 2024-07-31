<template>
    <v-container>
      <div class="text-center">
        <div class="text-h3 font-weight-bold mb-n1">원격 주행</div>
      </div>
  
      <div class="text-center mt-5">
        <v-form>
          <div class="mt-4">
            <h5>원격주행</h5>
            <div id="joyDiv" style="width:200px;height:200px;margin-bottom:20px;"></div>
            <div>Joystick X: {{ joystickX }}</div>
            <div>Joystick Y: {{ joystickY }}</div>
            <v-btn 
              @mousedown="startLogging('right')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
              class="mr-2"
            >Right</v-btn>
            <v-btn 
              @mousedown="startLogging('left')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
              class="mr-2"
            >Left</v-btn>
            <v-btn 
              @mousedown="startLogging('front')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
              class="mr-2"
            >FRONT</v-btn>
            <v-btn 
              @mousedown="startLogging('back')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
            >BACK</v-btn>
          </div>
        </v-form>
  
        <v-form>
          <div class="mt-4">
            <h5>포신방향</h5>
            <v-btn 
              @mousedown="startLogging('right')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
              class="mr-2"
            >Right</v-btn>
            <v-btn 
              @mousedown="startLogging('left')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
              class="mr-2"
            >Left</v-btn>
            <v-btn 
              @mousedown="startLogging('up')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
              class="mr-2"
            >UP</v-btn>
            <v-btn 
              @mousedown="startLogging('down')" 
              @mouseup="stopLogging" 
              @mouseleave="stopLogging"
            >DOWN</v-btn>
          </div>
        </v-form>
      </div>
    </v-container>
  </template>
  
  <script setup>
  import { ref, onMounted, onUnmounted } from 'vue'
  
  const joystickX = ref(0)
  const joystickY = ref(0)
  let joy
  let joystickInterval
  
  const initializeJoystick = () => {
    // Initialize JoyStick
    joy = new window.JoyStick('joyDiv', {}, (stickData) => {
      joystickX.value = stickData.x
      joystickY.value = stickData.y
    })
  
    // Update joystick values at regular intervals
    joystickInterval = setInterval(() => {
      joystickX.value = joy.GetX()
      joystickY.value = joy.GetY()
    }, 50)
  }
  
  // Load joy.js and initialize the joystick
  const loadScript = (src, callback) => {
    const script = document.createElement('script')
    script.src = src
    script.onload = callback
    script.onerror = () => {
      console.error(`Failed to load script: ${src}`)
    }
    document.head.appendChild(script)
  }
  
  onMounted(() => {
    loadScript('/js/joy.js', initializeJoystick)
  })
  
  onUnmounted(() => {
    // Clean up interval when the component is unmounted
    if (joystickInterval) {
      clearInterval(joystickInterval)
    }
  })
  
  const logInterval = ref(null)
  
  const startLogging = (direction) => {
    logInterval.value = setInterval(() => {
      console.log(direction)
    }, 100)
  }
  
  const stopLogging = () => {
    clearInterval(logInterval.value)
    logInterval.value = null
  }
  </script>
  
  <style scoped>
  .mr-2 {
    margin-right: 8px;
  }
  </style>
  