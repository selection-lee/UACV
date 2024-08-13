<template>
  <v-app>
    <Navbar />

    <v-app-bar app>
      <v-toolbar-title>
        <br>
        <v-img src="@/assets/logo.png" height="100" contain></v-img>
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>

    <v-main>
      <v-container>
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">Administrator</h3>
          <v-btn icon :to="{ path: '/signup' }">
            <v-icon>mdi-new-box</v-icon>
          </v-btn>
        </div>

        <v-data-table v-if="members" :items="members" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>전체 회원 정보 조회</v-toolbar-title>
              <v-divider inset vertical class="mx-4"></v-divider>
            </v-toolbar>
          </template>

          <template v-slot:item.username="{ item }">
            <a @click="goDetail(item.id)">{{ item.username }}</a>
          </template>
        </v-data-table>

        <v-pagination v-model="page" :length="pages" class="mt-4">
        </v-pagination>

      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { userAdminStore } from '@/stores/admin';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';

const store_admin = userAdminStore()
const router = useRouter()
const members = ref(null)
const role = ref(null)

onMounted(async () => {
  members.value = await store_admin.memberList()
  role.value = sessionStorage.getItem("memberRole")
})

const goDetail = function (memberId) {
  router.push(`/${memberId}`)
}

</script>

<style scoped>

.v-toolbar-title span {
  font-size: 24px;
  font-weight: bold;
  color: #fff;
}

.v-toolbar {
  background-color: #004d40;
}

.v-pagination {
  justify-content: center;
}
</style>