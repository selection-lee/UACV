<template>
  <tr>
    <td>{{ props.member.id }}</td>
    <td>{{ props.member.username }}</td>
    <td>{{ props.member.memberRole }}</td>
    <td>{{ props.member.createdDate }}</td>
    <td>{{ props.member.lastModifiedDate }}</td>
    <td>
      <v-btn type="button" >
        <router-link :to="{path: `/${props.member.id}`}">
          권한수정
        </router-link>
      </v-btn>
    </td>
  </tr>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const member = ref(null)

const props = defineProps({
  member : Object
})

</script>

<style scoped>

</style>