<template>
  <v-app>
    <Navbar />

    <!-- <Appbar/> -->

    <v-app-bar app>
      <v-toolbar-title>
        <br>
        <v-img src="@/assets/logo.png" height="100" contain></v-img>
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>

    <v-main>
      <v-container style="display: flex; flex-direction: column; align-items: center;">
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">정보 수정</h3>
        </div>

        <div style="width: 600px;">
          <div class="div-flex border-b-thin">
            <p style="width: 200px;">username</p>
            <p v-if="info">{{ info.username }}</p>
          </div>

          <div class="div-flex border-b-thin">
            <p style="width: 200px;">memberRole</p>
            <p v-if="info">{{ info.memberRole }}</p>
          </div>

          <v-form>
            <div class="div-flex border-b-thin">
              <label for="password1" style="width: 200px;">password</label>
              <v-text-field v-model.trim="password1" label="Password" type="password" />
            </div>

            <div class="div-flex border-b-thin">
              <label for="password2" style="width: 200px;">password 확인</label>
              <v-text-field v-model.trim="password2" label="Password 확인" type="password" />
            </div>
          </v-form>
        </div>
            <v-btn @click="changePassword">비밀번호 변경</v-btn>
          

      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { userAdminStore } from '@/stores/admin';
import { useUserStore } from '@/stores/user';
import Appbar from '@/components/appbar.vue';

const adminStore = userAdminStore()
const userStore = useUserStore()
const info = ref(null)

const password1 = ref(null)
const password2 = ref(null)

//== 비밀번호 변경 ==//
const changePassword = function () {
  if (password1.value === password2.value) {
    const payload = {
      newPassword : password1.value
    }

    userStore.updatePassword(payload)

  } else {
    alert("비밀번호가 다릅니다")
  }
}

onMounted(async () => {
  info.value = await adminStore.findMember(sessionStorage.getItem("memberId"))
})

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

.div-flex {
  display: flex;
  margin: 20px;
}

</style>