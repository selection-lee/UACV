<template>
    <v-navigation-drawer expand-on-hover rail style="background-color: #ffffee;">
        <v-list>
            <v-list-item prepend-icon="mdi-tank" title="UACV"></v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list density="compact" nav>
            <v-list-item prepend-icon="mdi-home" title="Home" :to="{ path: '/' }"></v-list-item>
            <v-list-item prepend-icon="mdi-text-box" title="Log" :to="{ path: '/log_text' }"></v-list-item>
            <v-list-item prepend-icon="mdi-controller" title="원격주행" :to="{ path: '/remote_control' }"></v-list-item>
            <v-list-item prepend-icon="mdi-account" title="계정 정보" :to="{ path: '/information_edit' }"></v-list-item>
            <v-list-item prepend-icon="mdi-security" title="사용자 관리" :to="{ path: '/user_management' }"
                v-if="role === 'ADMIN'">
            </v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list density="compact" nav>
            <v-list-item prepend-icon="mdi-login" title="Login" :to="{ path: '/login' }"
                v-if="!(store.isLogin)"></v-list-item>
            <v-list-item prepend-icon="mdi-logout" title="Logout" @click="store.LogOut()" v-else></v-list-item>
        </v-list>

    </v-navigation-drawer>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';

const store = useUserStore()
const role = ref(null)

onMounted(() => {
    role.value = sessionStorage.getItem("memberRole")
})
</script>

<style scoped></style>