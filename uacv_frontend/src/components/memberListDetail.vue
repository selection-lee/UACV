<template>

    <tr>
        <td>{{ prop.member.id }}</td>
        <td>{{ prop.member.username }}</td>
        <td>{{ prop.member.memberRole }}</td>
        <td>{{ prop.member.createdDate }}</td>
        <td>{{ prop.member.lastModifiedDate }}</td>
        <td>
            <v-btn type="button">권한수정</v-btn>
        </td>
    </tr>

</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const member = ref(null)

const prop = defineProps({
    member : Object
})
</script>

<style scoped>

</style>