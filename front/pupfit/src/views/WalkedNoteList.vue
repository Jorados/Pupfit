<template>
  <div class="mb-5">
    <h3 class="mt-5 mb-4">강아지 산책 기록</h3>
    <div v-if="walkedNotes.length > 0">
      <!-- 산책 기록을 순서대로 나열 -->
      <div v-for="note in walkedNotes" :key="note.id" class="walked-note-card">
        <p><strong>산책 시간:</strong> {{ note.walkedDate }}</p>
        <p><strong>내용:</strong> {{ note.walkedContent }}</p>
        <p><strong>완료 여부:</strong> {{ note.walked ? '완료' : '미완료' }}</p>
      </div>
    </div>
    <div v-else>
      <p>산책 기록이 없습니다.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/api/axios'; // 수정된 경로로 Axios 임포트

const walkedNotes = ref([]); // 산책 기록을 저장할 반응형 변수

onMounted( async () => {
  try {
    const response = await axios.get('/api/walkedNote/read');
    console.log(response);
    walkedNotes.value = formatWalkedNotes(response.data);
  } catch (error) {
    console.error('Failed to fetch walked notes:', error);
  }
});

const formatWalkedNotes = (data) => {
  let notesArray = [];
  // data가 Map 형태라면, 이를 배열로 변환
  for (const notes of Object.values(data)) {
    notesArray = notesArray.concat(notes);
  }
  return notesArray;
};

</script>

<style scoped>
.walked-note-card {
  width: 600px;
  margin: 15px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

h1 {
  margin-bottom: 20px;
}

p {
  margin: 5px 0;
}
</style>
