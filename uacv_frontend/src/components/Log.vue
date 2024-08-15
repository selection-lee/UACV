<!-- Log.vue -->
<template>
  <h5 class="text-h5 font-weight-bold">LOG</h5>
  <div>
    <ul>
      <li v-for="log in displayedLogs.slice().reverse()" :key="log.id">
        {{ log.time }} - {{ log.message }}
      </li>
    </ul>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, computed } from 'vue';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default {
  name: "Log",
  setup() {
    const logs = ref([]);
    const stompClient = ref(null);

    const processedLogIds = ref(new Set());

    const displayedLogs = computed(() => {
      return logs.value.slice(-2);
    });

    const createLogMessage = (log) => ({
      id: log.id,
      time: new Date(log.receivedAt).toLocaleString('ko-KR'),
      message: `총 소리 인식 (${log.soundType}로 추정)`,
    });

    const addLog = (newLog) => {
      if (!processedLogIds.value.has(newLog.id)) {
        const formattedLog = createLogMessage(newLog);
        logs.value.push(formattedLog);
        if (logs.value.length > 100) {
          logs.value.shift();
        }
        processedLogIds.value.add(newLog.id);
      }
    };

    const fetchInitialLogs = async () => {
      try {
        const response = await fetch(`${import.meta.env.VITE_BACKEND_API_URL}/sound-logs`);
        const data = await response.json();

        logs.value = data.map(createLogMessage);
        console.log('Initial logs loaded:', logs.value);
      } catch (error) {
        console.error('Error fetching initial logs:', error);
      }
    };

    const replaceWsWithHttp = (url) => {
      return url.replace(/^ws(s)?:\/\//, (match, secure) => {
        return secure ? 'https://' : 'http://';
      });
    };

    const connectWebSocket = () => {
      const SOCKET_API_URL = import.meta.env.VITE_SOCKET_URL;
      console.log('Connecting to WebSocket at:', `${replaceWsWithHttp(SOCKET_API_URL)}/sound`);
      const socket = new SockJS(`${replaceWsWithHttp(SOCKET_API_URL)}/sound`);
      stompClient.value = Stomp.over(socket);

      stompClient.value.connect({}, (frame) => {
        console.log('Connected to WebSocket:', frame);
        stompClient.value.subscribe('/orin/sound-logs', (message) => {
          // console.log('Received message on /orin/sound-logs:', message.body);
          if (message.body) {
            try {
              const newLog = JSON.parse(message.body);
              addLog(newLog);
              // console.log('New log added:', newLog);
            } catch (error) {
              console.error('Error parsing WebSocket message:', error);
            }
          }
        });
      }, (error) => {
        console.error('WebSocket connection error:', error);
      });
    };

    onMounted(() => {
      console.log("Log component mounted");
      fetchInitialLogs().then(() => {
        connectWebSocket();
      });
    });

    onUnmounted(() => {
      if (stompClient.value && stompClient.value.connected) {
        stompClient.value.disconnect();
        console.log('WebSocket disconnected');
      }
    });

    return {
      displayedLogs,
    };
  },
};
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 5px;
}
</style>