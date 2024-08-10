import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import { useUserStore } from "./user"; // 사용자 스토어 가져오기

export const useDeviceControlStore = defineStore('deviceControl', () => {
  const userStore = useUserStore() // 사용자 스토어 가져오기

  // 상태 정의
  const fire = ref(null);
  const move = ref(null);
  const cannon_x = ref(90);
  const cannon_y = ref(140);
  const steer = ref(90);

  const sendFireCommand = async () => {
    try {
      const response = await axios.post(
        `/device/fire`,
        { fire: 'on' },
        { headers: { Authorization: `Bearer ${userStore.token}` } }
      );
      fire.value = "on";
      console.log(response.data);
    } catch (error) {
      console.error("Error sending fire command:", error);
    }
  };

  const sendCannonCommand = async (cannon_x_val, cannon_y_val) => {
    try {
      const response = await axios.post(
        `/device/cannon`,
        { cannon_x, cannon_y },
        { headers: { Authorization: `Bearer ${userStore.token}` } }
      );
      cannon_x.value = cannon_x_val;
      cannon_y.value = cannon_y_val;
      console.log(response.data);
    } catch (error) {
      console.error("Error sending cannon command:", error);
    }
  };

  const sendSteerCommand = async (steer_val) => {
    try {
      const response = await axios.post(
        `/device/steer`,
        { steer },
        { headers: { Authorization: `Bearer ${userStore.token}` } }
      );
      steer.value = steer_val;
      console.log(response.data);
    } catch (error) {
      console.error("Error sending steer command:", error);
    }
  };

  const sendMoveCommand = async (move_val) => {
    try {
      const response = await axios.post(
        `/device/move`,
        { move },
        { headers: { Authorization: `Bearer ${userStore.token}` } }
      );
      move.value = move_val;
      console.log(response.data);
    } catch (error) {
      console.error("Error sending move command:", error);
    }
  };
  
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
  };
});
