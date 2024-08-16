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
    
    const KOREA_TIMEZONE = 'Asia/Seoul';

    const displayedLogs = computed(() => {
      return logs.value.slice(-2);
    });

    // UTC 시간을 한국 시간으로 변환
    const convertUTCToKoreaTime = (utcDateString) => {
      
      // UTC 문자열을 Date 객체로 변환 (명시적으로 UTC임을 표시)
      const utcDate = new Date(utcDateString + 'Z');
      
      const options = {
        timeZone: KOREA_TIMEZONE,
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: true
      };
      
      const koreaTimeString = utcDate.toLocaleString('ko-KR', options);
      
      return koreaTimeString;
    };

    const createLogMessage = (log) => {
      const convertedTime = convertUTCToKoreaTime(log.receivedAt);
      
      return {
        id: log.id,
        time: convertedTime,
        message: `총 소리 인식 (${log.soundType}로 추정)`,
        receivedAt: log.receivedAt // 원본 UTC 시간 저장
      };
    };

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
      const socket = new SockJS(`${replaceWsWithHttp(SOCKET_API_URL)}/sound`);
      stompClient.value = Stomp.over(socket);

      stompClient.value.connect({}, (frame) => {
        stompClient.value.subscribe('/orin/sound-logs', (message) => {
          if (message.body) {
            try {
              const newLog = JSON.parse(message.body);
              addLog(newLog);
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
      fetchInitialLogs().then(() => {
        connectWebSocket();
      });
    });

    onUnmounted(() => {
      if (stompClient.value && stompClient.value.connected) {
        stompClient.value.disconnect();
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