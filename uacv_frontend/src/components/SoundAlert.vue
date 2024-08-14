<template>
  <v-container>
    <v-alert v-if="showAlert" type="error" dismissible @input="hideAlert">
      {{ alertMessage }}
    </v-alert>
  </v-container>
</template>


<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

window.global = window;

export default {
  data() {
    return {
      showAlert: false, // 알림 표시 여부
      alertMessage: "", // 표시할 알림메시지
      stompClient: null,
      alertTimer: null
    };
  },
  methods: {
    displayAlert(message) {  // 일반 함수로 변경
      this.alertMessage = message;
      this.showAlert = true;
      // console.log("Alert status: ", this.showAlert);
      // console.log("Alert message: ", this.alertMessage);

      if (this.alertTimer) {
        clearTimeout(this.alertTimer);
      }

      this.alertTimer = setTimeout(() => {
        this.hideAlert();
      }, 3000);
    },
    hideAlert() {
      this.showAlert = false;
      this.alertMessage = "";

      if (this.alertTimer) {
        clearTimeout(this.alertTimer);
        this.alertTimer = null;
      }
    },

    replaceWsWithHttp(url) {
      return url.replace(/^ws(s)?:\/\//, function (match, secure) {
        return secure ? 'https://' : 'http://';
      });
    },

    connectWebSocket() {
      const SOCKET_API_URL = import.meta.env.VITE_SOCKET_URL
      const socket = new SockJS(`${this.replaceWsWithHttp(SOCKET_API_URL)}/sound`);
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, (frame) => {
        // console.log("WebSocketConnected: " + frame);
        this.stompClient.subscribe("/orin/sensor", (message) => {
          // console.log("Received WebSocket message:", message);
          try {
            const soundData = JSON.parse(message.body);
            let alertMessage = `총기소리 인식: ${soundData.soundType}`;
            this.displayAlert(alertMessage);
          } catch (error) {
            this.displayAlert(message.body);
          }
        });

      }, (error) => {
        console.error("STOMP error:", error);
      });
    }
  },
  mounted() {
    this.connectWebSocket();
  },
  beforeDestroy() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    if (this.alertTimer) {
      clearTimeout(this.alertTimer);
    }
  }
}
</script>