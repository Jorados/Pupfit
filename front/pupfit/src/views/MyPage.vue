<template>
  <div class="user-container">
    <div class="user-card">
      <div class="user-card__header">
        <h3>사용자 정보</h3>
      </div>
      <div class="user-card__content">
        <p><strong>이름:</strong> {{ currentUser.name }}</p>
        <p><strong>이메일:</strong> {{ currentUser.nickname }}</p>
        <p><strong>성별:</strong> {{ currentUser.gender }}</p>

        <v-btn class="user-edit-button" @click="showPasswordModal" style="width: 100%">정보 수정</v-btn>

      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted,ref } from "vue";
import axios from "@/api/axios.js";

const currentUser = ref([]);
onMounted(async () => {
  try {
    const response = await axios.get('/api/user/read');
    currentUser.value = response.data;
  } catch (error) {
    console.error('Error fetching puppy data:', error);
  }
});



</script>



<style scoped>
.user-container{
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.user-card {
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 16px;
  width: 300px;
}

.user-card__header {
  background-color: #f5f5f5;
  border-radius: 4px 4px 0 0;
  padding: 8px;
}
.user-card__header h3 {
  margin: 0;
}

.user-card__content {
  padding-top: 16px;
}

.user-card__content p {
  margin: 0 0 8px;
}

.user-card__content p:last-child {
  margin-bottom: 0;
}
.user-edit-button {
  margin-top: 2rem;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  background-color: #1c1904;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;
}
</style>