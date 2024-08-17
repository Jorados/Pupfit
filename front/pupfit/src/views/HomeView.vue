<template>
  <div>
    <h3 style="margin-top: 50px;">관리하는 강아지</h3>
    <div class="puppy-list">
      <div v-for="puppy in puppies" :key="puppy.id" class="puppy-item" @click="openPuppyDetailModal(puppy.puppyId)">
      <div class="icon">
        <v-icon v-if="!puppyStatuses[puppy.id]" color="red" large>mdi-emoticon-angry-outline</v-icon>
        <v-icon v-else color="blue" large>mdi-emoticon-happy-outline</v-icon>
      </div>
        <div class="puppy-details">
          <h5 style="color: #ce9191; margin-top: 5px; font-weight: bold; cursor: pointer;" class="puppy-name">{{ puppy.puppyPersonalName }}({{ puppy.id ? puppy.puppyName : '알 수 없는 강아지' }})</h5>
        </div>
      </div>
    </div>

    <h3 style="margin-top: 15px;">최근 산책 일기</h3>
    <div class="note-container">
      <v-row class="note-row">
        <!-- walkedNotes가 비어 있으면 "데이터가 없습니다"를 표시 -->
        <v-col v-if="!walkedNotes || walkedNotes.length === 0">
          <v-card class="note-card" variant="outlined">
            <div class="note-body">
              <v-card-text class="note-title">
                <h5 class="note-null">
                  데이터가 없습니다
                </h5>
              </v-card-text>
            </div>
          </v-card>
        </v-col>

        <!-- walkedNotes가 존재할 때 각 walkedNote를 표시 -->
        <v-col v-else v-for="walkedNote in walkedNotes" :key="walkedNote.id">
          <v-card :to="{name: 'walkedNoteList'}" href="walkedNote" class="note-card" variant="outlined">
            <div class="note-header">
              <v-icon v-if="walkedNote.walked" color="primary darken-4" large>mdi-dog-service</v-icon>
              <v-icon v-if="!walkedNote.walked" color="red darken-4" large>mdi-dog-service</v-icon>
            </div>
            <div class="note-body">
              <v-card-text class="note-title">
                <h5 style="color: #ce9191; font-weight: bold; cursor: pointer;">
                  {{ walkedNote.puppyPersonalName }} ({{ walkedNote.puppyName }})
                </h5>
                <br>{{ truncateText(walkedNote.walkedContent, 10) }}
              </v-card-text>
            </div>
            <div class="note-footer">
              <v-card-text>{{ formatDate(walkedNote.walkedDate) }}</v-card-text>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </div>

    <PuppyDetailModal :isOpen="state.showPuppyDetailModal" @update:isOpen="state.showPuppyDetailModal = $event" :puppyId="state.selectedPuppyId" />

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/api/axios';
import PuppyDetailModal from '@/components/modals/PuppyDetailModal.vue';
import { formatDate } from '@/store/store.js';

const puppies = ref([]);
const walkedNotes = ref([]);
const puppyStatuses = ref({});

const fetchPuppies = () => {
  axios.get('/api/userPuppy/read')
      .then(response => {
        puppies.value = response.data;

        const requests = puppies.value.map(puppy => {
          return axios.get(`/api/walkedNote/read/userPuppy/${puppy.id}`)
              .then(walkedNotesResponse => {
                const walkedNotesData = walkedNotesResponse.data;

                if (walkedNotesData.walkedDate != null) {
                  const latestDate = walkedNotesData.walkedDate;
                  puppyStatuses.value[puppy.id] = latestDate ? isWithinLastWeek(latestDate) : false;
                } else {
                  puppyStatuses.value[puppy.id] = false; // 기본적으로 false로 설정
                }
              })
              .catch(error => {
                console.error(`userPuppy ${puppy.id} 최신 산책정보 조회 에러 :`, error);
              });
        });
        // Promise.all을 이용해서 모든 API 요청을 병렬로 수행
        // 모든 walkedNotes 요청이 완료될 때까지 기다림
        return Promise.all(requests);
      })
      .catch(error => {
        console.error('puppies 에러 :', error);
      });
};

const fetchWalkedNotes = () => {
  const params = { limit: 4 }; // 객체로 설정
  axios.get('/api/walkedNote/read', {params})
      .then(response => {
        walkedNotes.value = formatWalkedNotes(response.data);
      })
      .catch(error => {
        console.error('walkedNote 에러 :', error);
      });
};

const formatWalkedNotes = (data) => {
  let notesArray = [];
  // data가 Map 형태라면, 이를 배열로 변환
  for (const notes of Object.values(data)) {
    notesArray = notesArray.concat(notes);
  }
  return notesArray;
};

const state = ref({
  selectedPuppyId: null,
  showPuppyDetailModal: false,
});

const openPuppyDetailModal = (puppyId) => {
  state.value.selectedPuppyId = puppyId;
  state.value.showPuppyDetailModal = true;
};


// 날짜가 7일 이내인지 확인하는 함수
const isWithinLastWeek = (date) => {
  const currentDate = new Date();
  const walkDate = new Date(date);
  const oneWeekAgo = new Date(currentDate.setDate(currentDate.getDate() - 7));
  return walkDate >= oneWeekAgo;
};

const truncateText = (text,length) =>{
  return text.length > length ? text.substring(0,length) + ".." : text;
}

onMounted(() => {
  fetchPuppies();
  fetchWalkedNotes();
});

</script>

<style scoped>
.puppy-list {
  margin: 30px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.note-row{
  display: inline-flex;
}

.note-null{
  color: #ce9191;
  font-weight: bold;
  margin-top: 50px;
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
  margin-top: auto;
  margin-bottom: auto;
}

.puppy-name {
  font-weight: bold;
}

.puppy-type {
  font-size: 16px;
  color: #4c4c4c;
}

.note-container {
  margin: 30px;
}

.note-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  border-radius: 10px;
  border-color: #d9d9d9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  height: 220px;
  width: 300px;

}

.icon{
  margin-top: 20px;
}
</style>
