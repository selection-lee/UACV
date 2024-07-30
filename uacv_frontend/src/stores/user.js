// Utilities
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import axios from 'axios'

export const useUserStore = defineStore('counter', () => {

  const BASE_URL = 'http://localhost:8080/user'
  const router = useRouter()
  const token = ref(null)

  //회원가입
  const signUp = function (payload) {
    const { username, password1, password2, memberRole } = payload

    axios({
      method: 'post',
      url: `${BASE_URL}/signup`,
      data: {
        username, password1, password2, memberRole
      }
    })
      .then((response) => {
        console.log(response.data)
        router.push(
          {
            path: '/login'
          }
        )
      })
      .catch((error) => {
        console.log(error)
      })
  }

  //로그인
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
        token.value = response.data.accessToken
        console.log(token.value)
      })
      .catch((error) => {
        console.log(error)
      })

  }

  return { signUp, LogIn, token }
})
