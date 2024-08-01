import { defineStore } from "pinia";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "./user";

import axios from "axios";

export const userAdminStore = defineStore('admin', () => {

  const BASE_URL = 'http://localhost:8080/user'
  const store = useUserStore()
  const token = store.token
  const members = store.members
  const router = useRouter()

  //== 해당 회원 정보 ==//
  const findMember = function(memberId) {
    return members.filter((member) => member.id == memberId)
  }

  //== 권한 변경 ==//
  const updateRole = function (payload) {

    const { username, memberRole } = payload

    axios({
      method: 'put',
      url: `${BASE_URL}/updateRole`,
      data:{
        username, memberRole
      },
      headers:{
        Authorization: `Bearer ${token}`
      }
    })
    .then((response) => {
      console.log(response)
      // 권한 변경 성공 시 메인페이지로 이동
      // 삭제 예정
      router.push(
        {
          path: '/memberList'
        }
      )
    })
    .catch((error) => {
      console.log(error)
    })
  }

  return { findMember, updateRole }
})