<template>
  <div id="app">
    <div class="welcome-message" v-if="isAuthenticated">
      <p class="welcome-text">안녕하세요, <span class="user-name"></span>{{ nickname }} 님!</p>
      <p class="description">오늘도 즐거운 강아지 산책하세요.</p>
      <button @click="logout">로그아웃</button>
    </div>
    <div class="brand" v-if="isAuthenticated">
      PUPFIT
    </div>

    <NavBar v-if="isAuthenticated"></NavBar>
    <div class="router-view-wrapper">
      <router-view></router-view>
    </div>

    <footer class="footer" v-if="isAuthenticated">
      <p>&copy; 2023 Leafy. All rights reserved.</p>
    </footer>
  </div>

</template>

<script setup>
import { computed, onMounted , ref } from 'vue';
import { useStore } from 'vuex';
import NavBar from "@/components/NavBar.vue";
import { useRouter } from 'vue-router';
import axios from '@/api/axios';

const store = useStore();
const router = useRouter();
const isAuthenticated = computed(() =>{
  return store.getters.isAuthenticated;
});

const logout = () => {
  store.dispatch('logout'); // Vuex에서 로그아웃 액션 호출
  router.push('/login'); // 로그인 페이지로 리디렉션
};


const nickname = ref('');
onMounted(async () => {
  if (isAuthenticated.value) {
    try {
      const response = await axios.get('/api/user/read');
      nickname.value = response.data.nickname; // response의 nickname 필드 사용
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  }
});
</script>


<style>
html,
body {
  height: 100%;
  margin: 0;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.router-view-wrapper {
  flex-grow: 1;
  display: flex;
  margin: auto;
}

.welcome-text {
  font-size: 0.9rem;
  font-weight: bold;
}

.container {
  max-width: 1200px;
  margin: 40px auto;
}

.brand {
  font-size: 2.5em;
  font-weight: bold;
  color: #595849FF;
  padding-top: 30px;
  padding-bottom: 20px;
  text-align: center;
}

.welcome-message {
  margin-bottom: 1rem;
  position: absolute;
  top: 0px;
  right: 20px;
  text-align: right;
}

.footer {
  background-color: #595849FF;
  color: white;
  padding: 1rem;
  font-size: 0.9rem;
  text-align: center;
  bottom: 0;
  width: 100%;
}
</style>
