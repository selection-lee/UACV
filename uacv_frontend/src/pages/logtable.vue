<template>
  <div>
    <v-select v-model="selectedLogType" :items="logTypes" label="로그 구분"></v-select>

    <v-select v-if="selectedLogType === '수동 조작'" v-model="selectedCommandCriteriaKey" :items="commandCriteriaKeys"
      label="조작 명령 구분"></v-select>

    <v-data-table :headers="tableHeaders" :items="logs" :items-per-page="10"></v-data-table>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import axios from 'axios'

const logTypes = ref(['수동 조작', '센서 데이터', '총기 인식'])
const selectedLogType = ref(logTypes.value[0])

const commandCriteria = ref({
  '포신': 'cannon',
  '조향': 'steer',
  '사격': 'fire',
  '이동': 'move',
  '모두 보기': 'all'
})
const commandCriteriaKeys = computed(() => Object.keys(commandCriteria.value));
const selectedCommandCriteriaKey = ref(commandCriteriaKeys.value[0]);
const selectedCommandCriteriaValue = computed(() => commandCriteria.value[selectedCommandCriteriaKey.value]);

const logs = ref([])
const tableHeaders = ref([])

const fetchLogs = async () => {
  let url = ''
  const API_URL = import.meta.env.VITE_BACKEND_API_URL

  switch (selectedLogType.value) {
    case '총기 인식':
      url = `${API_URL}/sound-log`
      break
    case '수동 조작':
      url = `${API_URL}/device/log/command?query=${selectedCommandCriteriaValue.value}`
      break
    case '센서 데이터':
      url = `${API_URL}/device/log/sensor`
      break
    default:
      return
  }

  console.log(`Fetching ${selectedLogType.value} to ${url}`)

  axios({
    url: url,
    headers: {
      Authorization: `Bearer ${sessionStorage.getItem("token")}`
    }
  }).then((response) => {
    const { eventType, data } = response.data
    logs.value = data.queries

    if (eventType === 'command') {
      tableHeaders.value = [
        { title: '명령일시', value: 'sendDate', align: 'center', sortable: 'true' },
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
        { title: '수신일시', value: 'receivedDate', align: 'center', sortable: 'true' },
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
  }).catch((error) => {
    console.error('Error fetching logs:', error)
  })
}

watch(selectedLogType, () => {
  fetchLogs();
})

watch(selectedCommandCriteriaValue, () => {
  fetchLogs();
})

onMounted(() => {
  fetchLogs()
})
</script>

<style scoped></style>