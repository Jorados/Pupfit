<template>
  <div class="login-container">
    <h1>PUPFIT</h1>
    <form @submit.prevent="login">
      <div class="form-group">
        <input v-model="username" type="text" placeholder="아이디" />
      </div>
      <div class="form-group">
        <input v-model="password" type="password" placeholder="비밀번호" />
      </div>

      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script setup>
import axios from '@/api/axios';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { ref } from 'vue';

const router = useRouter();
const store = useStore();

const username = ref('');
const password = ref('');

// async -> 이거를 사용하면 해당 함수를 Promise 객체를 반환하는 비동기 함수가 된다.
const login = () => {
  axios.post('/login', {
    username: username.value,
    password: password.value
  })
      .then(response => {
        const token = response.headers['authorization'];
        store.dispatch('login', token);
        router.push('/');
      })
      .catch(error => {
        console.error(error);
        alert('로그인 실패');
      });
};

</script>

<style scoped>
.login-container {
  max-width: 300px;
  margin: 0 auto;
  height: 90vh;
  display: flex;
  justify-content: center;
  align-content: stretch;
  flex-direction: column;
}

h1 {
  font-weight: 900;
  font-size: 3rem;
  margin: 2rem;
  margin-top: 1rem;
  color: #d7a6a6;
}
form {
  display: flex;
  flex-direction: column;
}
.form-group {
  margin-bottom: 1rem;
  text-align: left;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.3rem;
}
input[type="text"],
input[type="password"] {
  padding: 0.6rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
  box-sizing: border-box;
}
button[type="submit"] {
  background-color: #d7a6a6;
  border: none;
  color: white;
  padding: 0.7rem;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 1.2rem;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
  box-sizing: border-box;
}
button[type="submit"]:hover {
  background-color: #d7a6a6;
}
</style>
