<template>
  <div>
    <h1>강아지 목록</h1>
    <ul>
      <!-- puppies 배열을 반복하여 각 Puppy 정보를 리스트 항목으로 표시합니다. -->
      <li v-for="puppy in puppies" :key="puppy.id">
        <h2>{{ puppy.puppyName }}</h2>
        <img :src="puppy.imgUrl" alt="Puppy Image" width="100" />
        <p>Type: {{ puppy.puppyType }}</p>
        <p>Walk Time: {{ puppy.walkLow }} - {{ puppy.walkHigh }}</p>
        <p>Personality: {{ puppy.personality }}</p>
      </li>
    </ul>
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
/* PuppyList 페이지에 대한 스타일 */
h1 {
  font-size: 2rem;
  text-align: center;
  margin-bottom: 1rem;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}

img {
  border-radius: 8px;
  margin-right: 1rem;
}

p {
  margin: 0.5rem 0;
}
</style>
