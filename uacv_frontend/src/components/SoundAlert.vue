<template>
  <v-container>
    <v-alert v-if="alert" type="error" dismissible @input="alert = false">
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
      alert: false, // 알림 표시 여부
      alertMessage: "", // 표시할 알림메시지
    };
  },
  methods: {
    showAlert(message) {  // 일반 함수로 변경
      this.alertMessage = message;
      this.alert = true;
      console.log("Alert status: ", this.alert);
      console.log("Alert message: ", this.alertMessage);
    },
  },
  mounted() {
    // 컴포넌트 마운트될 떄 웹소켓 연결 설정
    this.showAlert('테스트 알림입니다!');
    const socket = new SockJS("http://localhost:8081/sockjs");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log("Connected: " + frame);
      // 웹소켓 연결이 완료된 후에 구독을 설정
      stompClient.subscribe("/topic/signal", (message) => {
        try {
          const signalData = JSON.parse(message.body);
          console.log("Received message: ", signalData);

          // showAlert 메서드 호출
          this.showAlert(signalData.message);
        } catch (error) {
          console.error("Error parsing message:", error);
        }
      });
    }, error => {
      console.error("STOMP error:", error);
    });
  }
}
</script>
