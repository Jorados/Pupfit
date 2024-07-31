<template>
  <div class="mb-5">
    <h3 class="mt-5" style="margin-top: 15px;">나의 강아지</h3>
    <div class="puppy-list">
      <div v-for="puppy in puppies" :key="puppy.id" class="puppy-card">
        <h4>{{ puppy.puppyName }}</h4>
        <img :src="puppy.imgUrl" alt="Puppy Image" class="puppy-image" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/api/axios'; // 수정된 경로로 Axios 임포트

const puppies = ref([]);
const fetchPuppies = async () => {
  try {
    const response = await axios.get('/api/userPuppy/read');
    puppies.value = response.data;
  } catch (error) {
    console.error('Failed to fetch puppies:', error);
  }
};

// 컴포넌트가 마운트될 때 데이터를 가져옴
onMounted(() => {
  fetchPuppies();
});
</script>

<style scoped>
.puppy-list {
  margin: 30px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.puppy-card {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 10px;
  margin: 10px;
  width: 200px;
  text-align: center;
}

.puppy-image {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

</style>
