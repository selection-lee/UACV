<!-- // src/components/AlertBanner.vue -->
<template>
  <v-container>
    <v-alert
      v-model="alert"
      type="success"
      dismissible
    >
      {{ alertMessage }}
    </v-alert>
  </v-container>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

// ReferenceError: global is not defined
// Add this line to provide a global object
window.global = window;

export default {
  data() {
    return {
      alert: false, // 알림 표시 여부
      alertMessage: '' // 표시할 알림메시지
    };
  },
  methods: {
    showAlert(message) { // 알림 표시하기
      this.alertMessage = message;
      this.alert = true;
    }
  }, 
  mounted() { // 컴포넌트 마운트될 떄 웹소켓 연결 설정
    const socket = new SockJS('http://localhost:8081/sockjs'); 
    // const socket = new SockJS('http://localhost:8081', null, { withCredentials: true }); // withCredentials 설정
    // const socket = new SockJS('http://localhost:8081');
    // const socket = new SockJS('http://i11c202.p.ssafy.io/ws');
    // const socket = new SockJS(env.SOUND_SOCKET_URL);
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, frame => {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/signal', message => {
        const signalData = JSON.parse(message.body);
        this.showAlert(signalData.message);
      });
    });
  }
};
</script>
