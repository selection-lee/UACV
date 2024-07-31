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
import { onMounted, ref, nextTick } from 'vue';
import { useUserStore } from '@/stores/user'
import memberListDetail from '@/components/memberListDetail.vue'

const store = useUserStore()

const members = ref(null)

onMounted(() => {
    store.memberList()
    members.value = store.members
})

</script>

<style scoped>

</style>