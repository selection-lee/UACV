import { defineStore } from "pinia"
import { ref } from "vue"
import axios from "axios"
import { useUserStore } from "./user"

export const useDeviceControlStore = defineStore('deviceControl', () => {
  const userStore = useUserStore()

  const fire = ref('off')
  const move = ref(null)
  const cannon_x = ref(90)
  const cannon_y = ref(140)
  const steer = ref(90)

  const sendFireCommand = function () {
    axios({
      method: 'post',
      url: '/device/control/fire',
      data: { fire: 'on' },
      headers: { "Authorization": `Bearer ${userStore.token}` }
    }).then((response) => {
      console.log(`Sent fire command with status code ${response.status}`)
    }).catch((error) => {
      console.error("Error sending fire command: ", error)
    })
  }
  
  const sendCannonCommand = function (cannon_x, cannon_y) {
    axios({
      method: 'post',
      url: '/device/control/cannon',
      data: { cannon_x, cannon_y },
      headers: { "Authorization": `Bearer ${userStore.token}` }
    }).then((response) => {
      console.log(`Sent cannon command with status code ${response.status}`)
    }).catch((error) => {
      console.error("Error sending cannon command :", error)
    })
  }
  
  const sendSteerCommand = function (steer) {
    axios({
      method: 'post',
      url: '/device/control/steer',
      data: { steer },
      headers: { "Authorization": `Bearer ${userStore.token}` }
    }).then((response) => {
      console.log(`Sent steer command with status code ${response.status}`)
    }).catch((error) => {
      console.error("Error sending steer command: ", error)
    })
  }
  
  const sendMoveCommand = function (move) {
    axios({
      method: 'post',
      url: '/device/control/move',
      data: { move },
      headers: { "Authorization": `Bearer ${userStore.token}` }
    }).then((response) => {
      console.log(`Sent steer command with status code ${response.status}`)
    }).catch((error) => {
      console.error("Error sending move command :", error)
    })
  }
  
  return {
    fire,
    move,
    cannon_x,
    cannon_y,
    steer,
    sendFireCommand,
    sendCannonCommand,
    sendSteerCommand,
    sendMoveCommand,
  }
})
