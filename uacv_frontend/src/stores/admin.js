import { defineStore } from "pinia"
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useUserStore } from "./user"

import axios from "axios"

export const userAdminStore = defineStore('admin', () => {

  const BASE_URL = 'http://localhost:8080/user'
  const store = useUserStore()
  const router = useRouter()

  //== token 저장 ==//
  const token = store.token
  
  //== 권한 변경 ==//
  const updateRole = function (memberId, payload) {

    const { username, memberRole } = payload

    axios({
      method: 'put',
      url: `${BASE_URL}/update/role`,
      data:{
        username, memberRole
      },
      headers:{
        Authorization: `Bearer ${token}`
      }
    })
    .then((response) => {
      // 권한 변경 성공 시 메인페이지로 이동
      // 삭제 예정
      router.go(0)
      alert("권한이 변경되었습니다.")
    })
    .catch((error) => {
      console.log(error)
    })
  }

  //== 회원 삭제 ==//
  const deleteMember = function(memberId) {
    axios({
      method: 'delete',
      url: `${BASE_URL}/delete/${memberId}`,
      headers:{
        Authorization: `Bearer ${token}`
      }
    })
    .then((response) => {
      console.log(response.data)
      
      router.push({
        path: '/memberList'
      })
    })
    .catch((error) => {
      console.log(error)
    })
  }
  //== memberList 저장 ==//
  const members = ref(null)

  //== 회원 리스트 출력 ==//
  const memberList = function() {
    axios({
      method: 'get',
      url: `${BASE_URL}/list`,
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then((response) => {
      members.value = response.data

    })
    .catch((error) => {
      console.log(error)
    })
  }

  //== 찾은 회원 정보 저장 ==//
  const memberInfo = ref(null)

  //== 해당 회원 정보 ==//
  const findMember = function(memberId) {
    axios({
      method: 'get',
      url: `${BASE_URL}/${memberId}`,
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then((response) => {
      memberInfo.value = response.data
    })
    .catch((error) => {
      console.log(error)
    })
  }

  

  return { memberList, findMember, updateRole, deleteMember,
    members, memberInfo}
})