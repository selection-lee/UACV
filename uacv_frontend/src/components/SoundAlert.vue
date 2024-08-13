<template>
  <v-container>
    <!-- <v-alert v-if="alert" type="error" dismissible @input="alert = false">
      {{ alertMessage }}
    </v-alert> -->
    <!-- CHANGE: Updated v-if condition to use showAlert instead of alert for clarity -->
    <v-alert v-if="showAlert" type="error" dismissible @input="hideAlert">
      {{ alertMessage }}
    </v-alert>
  </v-container>
</template>


<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

// ReferenceError: global is not defined
// Add this line to provide a global object
window.global = window;

export default {
  data() {
    return {
      // alert: false, // 알림 표시 여부
      // alertMessage: "", // 표시할 알림메시지
      // stompClient: null  // CHANGE: Added to store the STOMP client instance
      // CHANGE: Renamed 'alert' to 'showAlert' for clarity
      showAlert: false,
      alertMessage: "",
      stompClient: null,
      // NEW: Added alertTimer to manage the auto-hide functionality
      alertTimer: null
    };
  },
  methods: {
    // CHANGE: Renamed to 'displayAlert' to avoid naming conflict with data property
    displayAlert(message) {  // 일반 함수로 변경
      this.alertMessage = message;
      this.showAlert = true;
      console.log("Alert status: ", this.showAlert);
      console.log("Alert message: ", this.alertMessage);

      // NEW: Clear any existing timer to prevent multiple timers
      if (this.alertTimer) {
        clearTimeout(this.alertTimer);
      }

      // NEW: Set a new timer to hide the alert after 3 seconds
      this.alertTimer = setTimeout(() => {
        this.hideAlert();
      }, 3000);
    },
    // NEW: Added method to hide the alert and clear the timer
    hideAlert() {
      this.showAlert = false;
      this.alertMessage = "";
      if (this.alertTimer) {
        clearTimeout(this.alertTimer);
        this.alertTimer = null;
      }
    },

  // CHANGE: New method to handle WebSocket connection
  connectWebSocket() {
      // The "/socket/sound" endpoint should match your Spring WebSocket configuration
      // const socket = new SockJS("://localhost:8081/socket/sound");
      const socket = new SockJS("http://localhost:8080/socket/sound");
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, (frame) => {
        console.log("WebSocketConnected: " + frame);
        // CHANGE: Subscribe to "/topic/sound" to match the server's convertAndSend destination
        // CHANGE: Subscribe to /orin/sensor
        this.stompClient.subscribe("/orin/sensor", (message) => {
          console.log("Received WebSocket message:", message);
          console.log(typeof message.body)
          console.log(message.body)
          try {
            const soundData = JSON.parse(message.body);
            console.log("Parsed message: ", soundData);
            let alertMessage = `총기소리 인식: ${soundData.soundType}`; // (ID: ${soundData.id});
            this.displayAlert(alertMessage);
          } catch (error) {
            console.error("Error parsing message:", error);
            console.log("Raw message body:", message.body);
            this.displayAlert(message.body);
          }
        });
        console.log("Subscribed to /orin/sensor");
      }, (error) => {
        console.error("STOMP error:", error);
      });
    }
  },
  // CHANGE: Connect to WebSocket when component is mounted
  mounted() {
    console.log("Component mounted, connecting to WebSocket...");
    this.connectWebSocket();
  },
  // CHANGE: Disconnect WebSocket when component is destroyed
  beforeDestroy() {
    console.log("Component being destroyed, disconnecting WebSocket...");
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      console.log("WebSocket disconnected");
    }
    // NEW: Added cleanup for the alert timer when component is destroyed
    if (this.alertTimer) {
      clearTimeout(this.alertTimer);
    }
  }
}
  // mounted() {
  //   // 컴포넌트 마운트될 떄 웹소켓 연결 설정
  //   this.showAlert('테스트 알림입니다!');
  //   const socket = new SockJS("http://localhost:8081/sockjs");
  //   const stompClient = Stomp.over(socket);

  //   stompClient.connect({}, (frame) => {
  //     console.log("Connected: " + frame);
  //     // 웹소켓 연결이 완료된 후에 구독을 설정
  //     stompClient.subscribe("/topic/signal", (message) => {
  //       try {
  //         const signalData = JSON.parse(message.body);
  //         console.log("Received message: ", signalData);

  //         // showAlert 메서드 호출
  //         this.showAlert(signalData.message);
  //       } catch (error) {
  //         console.error("Error parsing message:", error);
  //       }
  //     });
  //   }, error => {
  //     console.error("STOMP error:", error);
  //   });
  // }

</script>