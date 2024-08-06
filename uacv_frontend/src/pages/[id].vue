<template>
    <div>
        <h1>회원 정보 페이지</h1>
        <h3 v-if="memberInfo">{{ memberInfo.id }}</h3>
        <h3 v-if="memberInfo">{{ memberInfo.username }}</h3>
        <!-- <h3 v-if="memberInfo">{{ memberInfo.memberRole }}</h3> -->
        <v-select label="Select" v-model.trim="memberRole" :items="['CONTROL', 'MONITOR']" ></v-select>
        <h3 v-if="memberInfo">{{ memberInfo.createdDate }}</h3>
        <h3 v-if="memberInfo">{{ memberInfo.lastModifiedDate }}</h3>
        <v-btn @click="updateRole">권한 변경</v-btn>
        <v-btn @click="deleteMember">회원 삭제</v-btn>
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
const memberRole = ref(null)

//== 권한 변경 ==//
const updateRole = function () {
  const payload = {
    username: memberInfo.value.username,
    memberRole: memberRole.value
  }

  store.updateRole(id, payload)

}

//== 회원 삭제 =//
const deleteMember = function () {
  store.deleteMember(id)
}

//== 비동기화 ==//
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

onMounted(async() => {
  store.findMember(id)
  await sleep(100)
  memberInfo.value = store.memberInfo
  memberRole.value = memberInfo.value.memberRole

})
</script>

<style scoped>

</style>