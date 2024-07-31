<template>
  <div class="container">
    <h3 class="title">강아지 목록</h3>
    <div class="card-container">
      <!-- puppies 배열을 반복하여 각 Puppy 정보를 카드로 표시합니다. -->
      <div v-for="puppy in puppies" :key="puppy.id" class="card">
        <img :src="puppy.imgUrl" alt="Puppy Image" class="card-img" />
        <div class="card-content">
          <h2 class="card-title">{{ puppy.puppyName }}</h2>
          <p class="card-type">Type: {{ puppy.puppyType }}</p>
          <p class="card-personality">Personality: {{ puppy.personality }}</p>
          <p class="card-walk-time">Walk Time: {{ puppy.walkLow }} - {{ puppy.walkHigh }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/api/axios'; // 수정된 경로로 Axios 임포트

// Puppy 데이터를 저장할 상태
const puppies = ref([]);

// 컴포넌트가 마운트되면 API 호출을 통해 데이터를 가져옵니다.
onMounted(async () => {
  try {
    const response = await axios.get('/api/puppy/all');
    puppies.value = response.data;
  } catch (error) {
    console.error('Error fetching puppy data:', error);
  }
});
</script>

<style scoped>
.container {
  padding: 20px;
}

.title {
  text-align: center;
  margin-bottom: 2rem;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.card {
  width: 300px; /* 4 cards per row with gap */
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.card-img {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.card-content {
  padding: 15px;
  flex-grow: 1;
}

.card-title {
  margin: 0;
  font-size: 1.5rem;
  font-weight: bold;
}

.card-type, .card-personality, .card-walk-time {
  margin: 0.5rem 0;
}
</style>
