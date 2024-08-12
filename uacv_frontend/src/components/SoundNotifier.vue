<template>
  <div v-if="showNotification" class="sound-notification">
    <div class="alert alert-danger" role="alert">
        총 소리가 감지되었습니다!
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const showNotification = ref(false);

const handleSoundDetection = (event) => {
  // 소리 감지 처리 로직
  console.log("Sound detected:", event);
  showNotification.value = true;
  setTimeout(() => (showNotification.value = false), 10000); // 3초 후 알림 숨기기
};

onMounted(() => {
  if (typeof window !== "undefined" && "SpeechRecognition" in window) {
    const recognition = new (window.SpeechRecognition ||
      window.webkitSpeechRecognition)();
    recognition.onresult = handleSoundDetection;
    recognition.start();
  } else {
    console.warn("Speech Recognition API is not supported in this browser.");
  }
});
</script>

<style scoped>
.sound-notification {
  position: fixed;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 10px;
  border-radius: 5px;
}
</style>
