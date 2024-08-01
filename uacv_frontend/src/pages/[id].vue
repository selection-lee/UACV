<template>
    <div>
        <h1>회원 정보 페이지</h1>
        <h3>{{ id }}</h3>
        <h3>{{ username }}</h3>
        <h3>{{ memberRole }}</h3>
        <v-select label="Select" v-model.trim="newMemberRole" :items="['CONTROL', 'MONITOR']"></v-select>
        <h3>{{ createdDate }}</h3>
        <h3>{{ lastModifiedDate }}</h3>

        <v-btn @click="updateRole">권한 변경</v-btn>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router';
import { userAdminStore } from '@/stores/admin'

const memberInfo = ref(null)
const route = useRoute()
const id = route.params.id
const store = userAdminStore()

const username = ref(null)
const memberRole = ref(null)
const createdDate = ref(null)
const lastModifiedDate = ref(null)

// const check = ref(true)
const newMemberRole = ref(null)

const updateRole = function () {
  const payload = {
    username: username.value,
    memberRole: newMemberRole.value
  }

  store.updateRole(payload)

  alert("권한이 변경되었습니다.")
}

onMounted(() => {
  memberInfo.value = store.findMember(id)[0]
  username.value = memberInfo.value.username
  memberRole.value = memberInfo.value.memberRole
  // if (memberRole === "ADMIN") {
  //   check.value = false
  // }
  createdDate.value = memberInfo.value.createdDate
  lastModifiedDate.value = memberInfo.value.lastModifiedDate
})
</script>

<style scoped>

</style>