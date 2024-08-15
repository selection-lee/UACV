<!-- log_text.vue -->
<template>
  <v-app>
    <Navbar />
    <Appbar />
    <v-main>
      <v-container>
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">LOG</h3>
        </div>

        <v-select v-model="selectedLogType" :items="logTypes" label="로그 구분"></v-select>

        <v-select v-if="selectedLogType === '수동 조작'" v-model="selectedCommandCriteriaKey" :items="commandCriteriaKeys"
          label="조작 명령 구분"></v-select>

        <v-data-table :headers="tableHeaders" :items="filteredItems" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title style="color: #FFFFEF;">로그 기록</v-toolbar-title>
              <v-divider inset vertical class="mx-4" />
            </v-toolbar>
          </template>
        </v-data-table>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { ref, computed, watch, onMounted } from 'vue';
import axios from 'axios';
import Appbar from '@/components/appbar.vue';
import Navbar from '@/components/navbar.vue';

export default {
  components: {
    Appbar,
    Navbar,
  },
  setup() {
    const logTypes = ref(['수동 조작', '센서 데이터', '총기 인식']);
    const selectedLogType = ref('총기 인식');

    const commandCriteria = ref({
      '포신': 'cannon',
      '조향': 'steer',
      '사격': 'fire',
      '이동': 'move',
      '모두 보기': 'all'
    });
    const commandCriteriaKeys = computed(() => Object.keys(commandCriteria.value));
    const selectedCommandCriteriaKey = ref(commandCriteriaKeys.value[0]);
    const selectedCommandCriteriaValue = computed(() => commandCriteria.value[selectedCommandCriteriaKey.value]);

    const logs = ref([]);
    const tableHeaders = ref([]);

    const fetchLogs = async () => {
      let url = '';
      const API_URL = import.meta.env.VITE_BACKEND_API_URL;

      switch (selectedLogType.value) {
        case '총기 인식':
          url = `${API_URL}/sound-logs`;
          break;
        case '수동 조작':
          url = `${API_URL}/device/log/command?query=${selectedCommandCriteriaValue.value}`;
          break;
        case '센서 데이터':
          url = `${API_URL}/device/log/sensor`;
          break;
        default:
          return;
      }

      console.log(`Fetching ${selectedLogType.value} from ${url}`);

      try {
        const response = await axios.get(url, {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem("token")}`
          }
        });

        if (selectedLogType.value === '총기 인식') {
          logs.value = response.data.map(log => ({
            time: new Date(log.receivedAt).toLocaleString('ko-KR'),
            type: '총기 인식',
            content: `${log.soundType}`,
            receivedAt: new Date(log.receivedAt),
          })).sort((a, b) => b.receivedAt - a.receivedAt);

          tableHeaders.value = [
            { title: "인식 시간", value: "time", align: "center" },
            { title: "구분", value: "type", align: "center" },
            { title: "분석 결과", value: "content", align: "center" },
          ];
        } else {
          const { eventType, data } = response.data;
          logs.value = data.queries;

          if (eventType === 'command') {
            tableHeaders.value = [
              { title: '명령일시', value: 'sendDate', align: 'center', sortable: true },
              { title: '명령', value: 'command', align: 'center' },
              { title: '사용자', value: 'commander', align: 'center' },
              { title: '발사', value: 'controlData.fire', align: 'center' },
              { title: '이동', value: 'controlData.move', align: 'center' },
              {
                title: '포신 각도', align: 'center',
                children: [
                  { title: 'X', value: 'controlData.cannon_x', align: 'center' },
                  { title: 'Y', value: 'controlData.cannon_y', align: 'center' }
                ]
              },
              { title: '조향 각도', value: 'controlData.steer', align: 'center' }
            ];
          } else if (eventType === 'sensor') {
            tableHeaders.value = [
              { title: '수신일시', value: 'receivedDate', align: 'center', sortable: true },
              { title: '각도', value: 'heading', align: 'center' },
              { title: '방향', value: 'direction', align: 'center' },
              {
                title: '가속도계', align: 'center',
                children: [
                  { title: 'X', value: 'accelerometer.X', align: 'center' },
                  { title: 'Y', value: 'accelerometer.Y', align: 'center' },
                  { title: 'Z', value: 'accelerometer.Z', align: 'center' }
                ]
              },
              {
                title: '자이로스코프', align: 'center',
                children: [
                  { title: 'X', value: 'gyroscope.X', align: 'center' },
                  { title: 'Y', value: 'gyroscope.Y', align: 'center' },
                  { title: 'Z', value: 'gyroscope.Z', align: 'center' }
                ]
              }
            ];
          }
        }
      } catch (error) {
        console.error('Error fetching logs:', error);
      }
    };

    const filteredItems = computed(() => logs.value);

    watch(selectedLogType, () => {
      fetchLogs();
    });

    watch(selectedCommandCriteriaValue, () => {
      if (selectedLogType.value === '수동 조작') {
        fetchLogs();
      }
    });

    onMounted(() => {
      fetchLogs();
    });

    return {
      logTypes,
      selectedLogType,
      commandCriteriaKeys,
      selectedCommandCriteriaKey,
      tableHeaders,
      filteredItems,
    };
  },
};
</script>

<style scoped>
.v-toolbar-title span {
  font-size: 24px;
  font-weight: bold;
  color: #FFFFEF;
}

.v-toolbar {
  background-color: #004d40;
  text-align: center;
}

.v-pagination {
  justify-content: center;
}

.v-select {
    color: #093028;
    /* background-color: #ffffef; */
}

/* 전체 앱에 대한 스타일 */
:deep(.v-application) {
  position: relative;
  z-index: 1;
}

/* 데이터 테이블에 대한 스타일 */
:deep(.v-data-table) {
  position: relative;
  z-index: 2;
}
</style>