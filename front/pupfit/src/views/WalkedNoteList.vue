<template>
  <div>
    <h1>강아지 산책 기록</h1>
    <div v-if="walkedNotes.length > 0">
      <!-- 산책 기록을 순서대로 나열 -->
      <div v-for="note in walkedNotes" :key="note.id" class="walked-note-card">
        <p><strong>산책 시간:</strong> {{ formatTime(note.walkedDate) }}</p>
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

const fetchWalkedNotes = async () => {
  try {
    const response = await axios.get('/api/walkedNote/read');
    walkedNotes.value = formatWalkedNotes(response.data); // 응답 데이터로 walkedNotes 배열 업데이트
  } catch (error) {
    console.error('Failed to fetch walked notes:', error);
  }
};

// 데이터를 가져오는 함수 호출
onMounted(() => {
  fetchWalkedNotes();
});

// 산책 기록을 포맷하여 배열로 변환
const formatWalkedNotes = (data) => {
  let notesArray = [];

  // data가 Map 형태라면, 이를 배열로 변환
  for (const notes of Object.values(data)) {
    notesArray = notesArray.concat(notes);
  }

  return notesArray;
};

// 시간을 형식화하는 함수
const formatTime = (time) => {
  const date = new Date(time);
  return date.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', second: '2-digit' });
};
</script>

<style scoped>
.walked-note-card {
  margin: 10px 0;
  padding: 10px;
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
