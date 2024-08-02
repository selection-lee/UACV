<template>
    <div>
        <h1>권한 변경</h1>
        <v-form>
            <v-select label="Select" v-model.trim="userRole" :items="['CONTROL', 'MONITOR']"></v-select>
            <v-btn class="block" @click="updateRole">등록</v-btn>
        </v-form>
    </div>
</template>

<script setup>
  import { ref } from 'vue'
  import { useUserStore } from '@/stores/user'

  const store = useUserStore()
  const userRole = ref(null)
  
  const updateRole = function() {

    store.updateRole(userRole.value)
  }
</script>

<style scoped>

</style>