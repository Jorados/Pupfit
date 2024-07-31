<template>
  <div class="mb-5">
    <h3 class="mt-5" style="margin-top: 15px;">나의 강아지</h3>
    <div class="puppy-list">
      <div v-for="puppy in puppies" :key="puppy.id" class="puppy-item">

        <div class="puppy-details">
          <h5 style="color: green; font-weight: bold; cursor: pointer;" class="puppy-name">{{ puppy.puppyName }}({{ puppy.id ? puppy.puppyType : '알 수 없는 강아지' }})</h5>
          <p class="puppy-type">{{ puppy.id ? '강아지' : '알 수 없는 강아지' }}</p>
        </div>
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
    console.log(response);
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

.puppy-item {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  margin-left: 10px;
  margin-right: 10px;
  width: 280px;
  height: 150px;
  border-radius: 10px;
  border: 1px solid #d9d9d9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  cursor: pointer;
}

.puppy-details {
  display: flex;
  flex-direction: column;
  margin-top: auto; /* 추가된 속성 */
  margin-bottom: auto; /* 추가된 속성 */
}

.puppy-name {
  font-weight: bold;
}

.puppy-type {
  font-size: 16px;
  color: #4c4c4c;
}

</style>
