<template>
  <div class="container" style="max-width: 1300px">
    <h3 class="title">강아지 목록</h3>
    <div class="card-container">
      <!-- puppies 배열을 반복하여 각 Puppy 정보를 카드로 표시합니다. -->
      <div v-for="puppy in puppies" :key="puppy.id" class="card" @click="openPuppyDetailModal(puppy.id)">
        <img :src="puppy.imgUrl" alt="Puppy Image" class="card-img" />
        <div class="card-content">
          <p class="card-type"> {{ puppy.puppyName }}</p>
          <p class="card-personality"> {{ truncateText(puppy.personality, 100) }}</p>
        </div>
      </div>
    </div>
    <v-btn class="add-puppy-btn" @click="openPuppyCreateModal()">강아지 추가하기</v-btn>
  </div>

  <PuppyDetailModal :isOpen="state.showPuppyDetailModal" :puppyId="state.selectedPuppyId"  @update:isOpen="state.showPuppyDetailModal = $event" />
  <PuppyCreateModal :isOpen2="state.showPuppyCreateModal" @update:isOpen2="state.showPuppyCreateModal = $event"/>
</template>



<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/api/axios';
import PuppyDetailModal from "@/components/modals/PuppyDetailModal.vue"; // 수정된 경로로 Axios 임포트
import PuppyCreateModal from "@/components/modals/PuppyCreateModal.vue";

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

const state = ref({
  selectedPuppyId: null,
  showPuppyDetailModal: false,
  showPuppyCreateModal: false,
});

const openPuppyDetailModal = (puppyId) => {
  state.value.selectedPuppyId = puppyId;
  state.value.showPuppyDetailModal = true;
};

const openPuppyCreateModal = () => {
  state.value.showPuppyCreateModal = true;
}

function truncateText(text, length) {
  if (text.length > length) {
    return text.substring(0, length) + '...';
  }
  return text;
}

</script>

<style scoped>
.container {
  padding: 20px;
}

.title {
  text-align: center;
  margin-bottom: 2rem;
  margin-top: -20px;
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
  height: 200px;
  object-fit: cover;
}

.add-puppy-btn {
  width: 730px;
  max-width: 90%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 1;
  background-color: #a6ced7;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  margin-top: 40px;
  margin-bottom: 10px;
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
