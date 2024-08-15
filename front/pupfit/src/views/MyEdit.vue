<template>
  <div class="user-edit-container">
    <h2 class="title">사용자 정보 수정</h2>
    <form @submit.prevent="updateUser">
      <div>
        <label for="name">이름:</label>
        <input id="name" v-model="state.nickname" type="text" required />
      </div>
      <div>
        <label for="password">새 비밀번호:</label>
        <input id="password" v-model="state.password" type="password" required />
      </div>
      <button type="submit">수정하기</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import axios from "@/api/axios.js";

const router = useRouter();
const store = useStore();

const state = ref({
  nickname : '',
  password : '',
})

const updateUser = () => {
  axios.patch('/api/user/update',{
    nickname: state.value.nickname,
    password: state.value.password
  })
      .then(response => {
          alert('사용자 정보가 성공적으로 수정되었습니다. 다시 로그인 해주세요.');
          store.dispatch('logout'); // Vuex에서 로그아웃 액션 호출
          router.push('/login'); // 로그인 페이지로 리디렉션
      })
      .catch(error => {
        console.error('정보 수정 에러 :', error);
      });
}

</script>


<style scoped>
.user-edit-container {
  width: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
  display: flex;
  flex-direction: column;
  padding: 1rem;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

form div {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 1rem;
}

form label {
  font-weight: bold;
  color: #4c4c4c;
  margin-bottom: 0.5rem;
}

form input {
  flex-basis: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f5f5f5;
  transition: background-color 0.2s;

}

form input:focus {
  background-color: #ffffff;
  outline: none;
  border-color: #d7a6a6;
}

button {
  margin: 10px;
  padding: 0.5rem 1rem;
  background-color: #d7a6a6;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.2s, box-shadow 0.2s;
}

button:hover {
  background-color: #d7a6a6;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.title{
  margin: 20px;
}
</style>