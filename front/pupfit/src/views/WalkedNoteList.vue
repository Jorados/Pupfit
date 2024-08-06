<template>
  <div>
    <v-btn v-if="!state.dialog" class="add-note-btn" @click="openDialog">
      강아지 기록 추가
    </v-btn>

    <transition name="slide-fade">
      <v-card v-if="state.dialog" class="note-card">
        <div class="add-log-body">
          <v-card-text>
            <UserPuppy @updated-userPuppy-id="updateUserPuppyId" />
            <v-text-field v-model="state.newLog.note" label="Note" required></v-text-field>
            <v-text-field v-model="state.walkedDate" label="walkedDate" required placeholder="ex) 1999-09-22 01:30:30"></v-text-field>
            <v-switch
                v-model="state.newLog.walked"
                hide-details
                label="산책 여부"
                color="#a6ced7"
            ></v-switch>

          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue" @click="closeDialog">취소</v-btn>
            <v-btn color="primary" @click="addNote">작성</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </transition>

    <div class="note-container" v-if="walkedNotes.length > 0">
      <!-- 산책 기록을 순서대로 나열 -->
      <div v-for="note in walkedNotes" :key="note.id" class="walked-note-card">
        <div class="note-header">
          <v-icon v-if="note.walked" color="blue darken-4" large>mdi-check-bold</v-icon>
          <v-icon v-if="!note.walked" color="red darken-4"  large>mdi-close-thick</v-icon>
          <v-icon color="red darken-4" @click="deleteNote(note.id)" large>mdi-delete</v-icon>
        </div>
        <div class="note-body">
          <v-card-text>
            <p>{{ note.walkedContent }}</p>
            <h5>{{ note.puppyPersonalName }} ({{ note.puppyName }})</h5>
          </v-card-text>
        </div>
        <div class="note-footer">
          <v-card-text>{{ formatDate(note.walkedDate) }}</v-card-text>
        </div>
      </div>
    </div>
    <div v-else>
      <p>산책 기록이 없습니다.</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import axios from '@/api/axios';
import {formatDate} from "@/store/store.js"; // 수정된 경로로 Axios 임포트
import UserPuppy from "@/components/UserPuppy.vue";

const walkedNotes = ref([]);

onMounted(async () => {
  fetchWalkedNotes();
});

const fetchWalkedNotes = async () => {
  try {
    const response = await axios.get('/api/walkedNote/read');
    console.log(response);
    walkedNotes.value = formatWalkedNotes(response.data);
  } catch (error) {
    console.error('Failed to fetch walked notes:', error);
  }
};

const formatWalkedNotes = (data) => {
  let notesArray = [];
  // data가 Map 형태라면, 이를 배열로 변환
  for (const notes of Object.values(data)) {
    notesArray = notesArray.concat(notes);
  }
  return notesArray;
};

const state = reactive({
  newLog: {
    note: "",
    walked: false,
  },
  dialog: false,
  showPuppyDetailModal: false,
  selectedPuppyId: null,
  walkedDate: null,
})

const openDialog = () => {
  state.dialog = true;
};

const closeDialog = () => {
  state.dialog = false;
};

const updateUserPuppyId = (id) => {
  state.selectedPuppyId = id;
};

const formatDateToISO = (date) => {
  if (!date) return null; // 날짜가 없을 경우 null 반환
  return new Date(date).toISOString(); // ISO 8601 형식으로 변환
};

const addNote = async () => {
  try {
    // 서버에 POST 요청 보내기
    await axios.post('/api/walkedNote/create', {
      userPuppyId: state.selectedPuppyId,
      walkedContent: state.newLog.note,
      walked: state.newLog.walked,
      walkedDate: formatDateToISO(state.walkedDate), // LocalDateTime 형식으로 전송
    });
    // 요청이 성공하면 기록을 새로 불러오고 다이얼로그 닫기
    await fetchWalkedNotes();
    closeDialog();
    state.newLog.note = '';
    state.walkedDate = null;
    state.newLog.walked = false;
  } catch (error) {
    console.error('Failed to add note:', error);
  }
};

const deleteNote = async (walkedNoteId) => {
  try {
    console.log(walkedNoteId);
    await axios.delete(`/api/walkedNote/delete/${walkedNoteId}`);
    await fetchWalkedNotes();
  } catch (error) {
    console.error('Failed to delete note:', error);
  }
};

</script>



<style scoped>
.walked-note-card {
  width: 700px;
  margin: 15px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0,0,0, .1);
}

h1 {
  margin-bottom: 20px;
}

p {
  margin: 5px 0;
  font-size: 20px;
}

.note-header {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  gap: 7px;
}

.note-body {
  justify-content: center;
  width: 100%;
}

.note-footer {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  text-align: right;
}

.note-container {
  margin-bottom: 50px;
  box-sizing: inherit;
}

.add-note-btn {
  width: 730px;
  max-width: 90%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 1;
  background-color: #d7a6a6;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  margin-top: 40px;
  margin-bottom: 10px;
}

.note-card{
  margin-top: 10px;
  margin-left: 14px;
  width:700px;
}


.slide-fade-enter-active {
  transition: all 0.7s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.01s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-20px);
  opacity: 0;
}
</style>
