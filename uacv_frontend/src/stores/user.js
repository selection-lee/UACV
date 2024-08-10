// Utilities
import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

import axios from 'axios'

export const useUserStore = defineStore('counter', () => {

  const router = useRouter()

  //== token값 저장 ==//
  const token = ref(null)

  //== Role 저장 ==//
  const memberRole = ref(null)

  //== id 저장 ==//
  const memberId = ref(null)

  //== 계정생성 ==//
  const signUp = function (payload) {
    const { username, password1, password2, memberRole, rnk, m_id } = payload
    axios({
      method: 'post',
      url: `/member/create`,
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
      url: '/login', 
      data: {
        username, password
      }

    })
      .then((response) => {
        memberId.value = response.data.memberId
        memberRole.value = response.data.memberRole
        token.value = response.data.accessToken
        //  로그인 성공시 메인 페이지로 이동
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
    token.value = null
    memberId.value = null
    memberRole.value = null
    alert("로그아웃 완료")
    router.go(0)
  }

  //== 로그인 상태 확인 ==//
  const isLogin = computed(() => {
    if (token.value === null) {
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
      url: `/member/update/password`,
      data: {
        newPassword
      },
      headers: {
        Authorization: `Bearer ${token.value}`
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
     token, memberRole, memberId
  }
}, { persist: true })
