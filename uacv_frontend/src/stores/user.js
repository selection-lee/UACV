// Utilities
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

import axios from 'axios'

export const useUserStore = defineStore('counter', () => {

  const router = useRouter()

  const token = ref(sessionStorage.getItem("token"))

  //== 계정생성 ==//
  const signUp = function (payload) {
    const { username, password1, password2, memberRole, rnk, m_id } = payload
    axios({
      method: 'post',
      url: '/member/create',
      data: {
        username, password1, password2, memberRole, rnk, m_id
      }
    })
      .then((response) => {
        router.go(0)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  //== 로그인 ==//
  const LogIn = function (payload) {
    const { username, password } = payload
    axios({
      method: 'post',
      url: '/member/login',
      data: {
        username, password
      }

    })
      .then((response) => {
        console.log(response)
        sessionStorage.setItem("memberId", response.data.memberId)
        sessionStorage.setItem("memberRole", response.data.memberRole)
        sessionStorage.setItem("token", response.data.accessToken)

        router.push(
          {
            path: '/'
          }
        )
      })
      .catch((error) => {
        console.log(error)
      })
  }

  //== 로그아웃 ==//
  const LogOut = function () {
    sessionStorage.clear()
    alert("로그아웃 완료")
    router.go(0)
  }

const originalSetItem = sessionStorage.setItem;

sessionStorage.setItem = function(key, value) {
  originalSetItem.apply(this, arguments);
  if (key === 'token') {
    token.value = value;
  }
}

  //== 로그인 상태 확인 ==//
  const isLogin = computed(() => {
    if (token.value == null) {
      return false
    } else {
      return true
    }
  })

  //== 비밀번호 변경 ==//
  const updatePassword = function (payload) {
    const { newPassword } = payload

    axios({
      method: 'put',
      url: '/member/update/password',
      data: {
        newPassword
      },
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("token")}`
      }
    })
      .then((response) => {
        alert("비밀번호 변경 완료")
        router.go(0)
      })
      .catch((error) => {
        console.log(error)
      })

  }

  return {
    LogIn, LogOut, isLogin, updatePassword, signUp,
  }
})
