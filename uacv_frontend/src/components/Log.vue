<template>
    <h5 class="text-h5 font-weight-bold">LOG</h5>
    <div class="log">
      <ul>
        <li v-for="log in logs" :key="log.id">{{ log.time }} - {{ log.message }}</li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Log',
    data() {
      return {
        logs: [
          { id: 1, time: '11:08:25', message: '총소리 인식 (AKM으로 추정됨)' },
          { id: 2, time: '10:58:48', message: '포 1발 발사' }
        ]
      }
    }
  }
  </script>
  
  <style scoped>
  .log {
    /* 로그 컴포넌트 스타일 */
  }
  ul {
    /* 리스트 스타일 */
  }
  li {
    /* 리스트 아이템 스타일 */
  }
  </style>
  