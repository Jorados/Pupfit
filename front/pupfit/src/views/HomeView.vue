<template>
  <div>
    <h3 style="margin-top: 50px;">관리 강아지</h3>
    <div class="puppy-list">
      <div v-for="puppy in puppies" :key="puppy.id" class="puppy-item" @click="openPuppyDetailModal(puppy.id)">
        <!--v-icon color="red" large>mdi-emoticon-angry-outline</v-icon-->
        <v-icon style="margin-top:10px" color="blue" large>mdi-emoticon-happy-outline</v-icon>
        <div class="puppy-details">
          <h5>기분 좋음</h5>
          <h5 style="color: green; margin-top: 5px; font-weight: bold; cursor: pointer;" class="puppy-name">{{ puppy.puppyPersonalName }}({{ puppy.id ? puppy.puppyName : '알 수 없는 강아지' }})</h5>
        </div>
      </div>
    </div>

    <h3 style="margin-top: 15px;">최근 산책 일기</h3>
    <div class="note-container">
      <v-row>
        <v-col v-for="walkedNote in walkedNotes" :key="walkedNote.id" cols="12" md="6" lg="3">
          <v-card class="note-card" variant="outlined">
            <div class="note-header">
              <v-icon v-if="walkedNote.walked" color="primary darken-4" large>mdi-dog-service</v-icon>
            </div>
            <div class="note-body">
              <v-card-text class="note-title">
                <h5 style="color: green; font-weight: bold; cursor: pointer;">
                  {{ walkedNote.puppyPersonalName }} ({{ walkedNote.puppyName }})
                </h5>
                <br>{{ walkedNote.walkedContent }}
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



const fetchPuppies = async () => {
  try {
    const response = await axios.get('/api/userPuppy/read');
    puppies.value = response.data;
    console.log("강아지 데이터", puppies);
  } catch (error) {
    console.error('Failed to fetch puppies:', error);
  }
};

const fetchWalkedNotes = async()=>{
  try {
    const response = await axios.get('/api/walkedNote/read');
    walkedNotes.value = formatWalkedNotes(response.data);
    console.log(response);
  } catch (error) {
    console.error('Failed to fetch walkedNote:', error);
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

// 컴포넌트가 마운트될 때 데이터를 가져옴
onMounted(() => {
  fetchPuppies();
  fetchWalkedNotes();
});

const state = ref({
  selectedPuppyId: null,
  showPuppyDetailModal: false,
});

const openPuppyDetailModal = (puppyId) => {
  state.value.selectedPuppyId = puppyId;
  state.value.showPuppyDetailModal = true;
};

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
}


</style>
