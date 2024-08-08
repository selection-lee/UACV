// Utilities
import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

import axios from 'axios'

export const useUserStore = defineStore('counter', () => {

  // const BASE_URL = '/api/member'
  const BASE_URL = 'http://localhost:8080/api/member'
  const router = useRouter()
  //== token값 저장 ==//
  const token = ref(null)

  //== Role 저장 ==//
  const memberRole = ref(null)


  const check = ref(null)

  //== 중복 아이디 검사 ==//
  const checkUsername = function(username) {
    axios({
      method: 'get',
      url: `${BASE_URL}/check`,
      params: {
        "username": username
      }
    })
    .then((response) => {
      check.value = response.data
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
      url: `${BASE_URL}/login`,
      data: {
        username, password
      }
    })
      .then((response) => {
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

  const LogOut = function () {
    token.value = null
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
    const { currentPassword, newPassword } = payload

    axios({
      method: 'put',
      url: `${BASE_URL}/update/password`,
      data:{
        currentPassword, newPassword
      },
      headers:{
        Authorization: `Bearer ${token.value}`
      }
    })
    .then((response) => {
      console.log(response)
      //비밀번호 변경 성공시 메인 페이지로 이동
      // 삭제 예정
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

  return { LogIn, LogOut, isLogin, updatePassword, checkUsername,
    check, token, memberRole }
}, { persist: true })
