<template>
    <div>
        <h1>memberList Page</h1>
        <v-table>
          <thead>
            <tr>
              <th>ID</th>
              <th>username</th>
              <th>memberRole</th>
              <th>created</th>
              <th>lastModified</th>
              <th>회원 관리</th>
            </tr>
          </thead>

          <tbody>
            <memberListDetail
            v-for="member in members"
            :key="member.id"
            :member="member"
            />
          </tbody>
        </v-table>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/user'
import memberListDetail from '@/components/memberListDetail.vue'

const store = useUserStore()

const members = ref(null)

// 비동기화
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

onMounted(async () => {
    store.memberList()
    // 기다려!
    await sleep(100)
    members.value = store.members
})

</script>

<style scoped>

</style>