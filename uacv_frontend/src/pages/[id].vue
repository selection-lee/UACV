<template>
  <v-app>
  <Navbar />
  <Appbar />
  <v-main>
  <div style="display: flex; flex-direction: column; align-items: center;">
    <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">회원 정보 페이지</h3>
        </div>

    <div style="width: 50%; background-color: #2c4d41; border-radius: 10px;">
      <div class="div-flex">
        <h3 style="width: 200px;">id</h3>
        <p v-if="memberInfo">{{ memberInfo.id }}</p>
      </div>

      <div class="div-flex">
        <h3 style="width: 200px;">username</h3>
        <p v-if="memberInfo">{{ memberInfo.username }}</p>
      </div>

      <div class="div-flex">
        <h3 style="width: 200px;">memberRole</h3>
        <v-select label="Select" v-model.trim="memberRole" :items="['CONTROL', 'MONITOR']"></v-select>
      </div>

      <div class="div-flex">
        <h3 style="width: 200px;">created</h3>
        <p v-if="memberInfo">{{ memberInfo.createdDate }}</p>
      </div>

      <div class="div-flex">
        <h3 style="width: 200px;">lastModified</h3>
        <p v-if="memberInfo">{{ memberInfo.lastModifiedDate }}</p>
      </div>

      <div class="div-flex" style="flex-direction: row; justify-content: center;">

        <v-btn @click="updateRole" variant="elevated" color="blue" style="margin: 20px;"> 권한 변경 </v-btn>
        <v-btn @click="deleteMember" variant="elevated" color="red" style="margin: 20px"> 회원 삭제 </v-btn>
      </div>
    </div>
  </div>

</v-main>
</v-app>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router';
import { userAdminStore } from '@/stores/admin'
import Appbar from '@/components/appbar.vue';

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
  store.updateRole(payload)
}

//== 회원 삭제 =//
const deleteMember = function () {
  store.deleteMember(id)
}

onMounted(async () => {
  memberInfo.value = await store.findMember(id)
  memberRole.value = memberInfo.value.memberRole
})

</script>

<style scoped>
.v-main {
  background-color: #093028;
  color: #ffffee;
}

.div-flex {
  display: flex;
  margin: 20px;
}
</style>