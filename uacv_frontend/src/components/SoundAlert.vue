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
      showAlert: false,
      alertMessage: "",
      stompClient: null,
      alertTimer: null
    };
  },
  methods: {
    displayAlert(message) {
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

    connectWebSocket() {
      const socket = new SockJS("http://localhost:8080/socket/sound");
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, (frame) => {

        this.stompClient.subscribe("/orin/sensor", (message) => {
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