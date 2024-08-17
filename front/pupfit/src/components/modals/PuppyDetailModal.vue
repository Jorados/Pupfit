<template>
  <v-dialog v-bind="dialogProps" max-width="600">
    <v-card>
      <v-card-title style="text-align: center">
        <h3 style="margin-top: 20px;">{{ puppy.puppyName }}</h3>
      </v-card-title>

      <v-card-text>
        <v-row>
          <v-col cols="12">
            <v-img :src="puppy.imgUrl" alt="puppy image" width="100%" aspect-ratio="1.5"></v-img>
          </v-col>
          <v-col cols="12">
            <p><strong>견종 :</strong> {{ puppy.puppyType }}</p>
            <br>
            <p><strong>특징 :</strong> {{ puppy.personality }}</p>
            <br>
            <p><strong>산책 가능 시간 :</strong> {{ puppy.walkLow }} ~ {{ puppy.walkHigh }}</p>
          </v-col>
        </v-row>
      </v-card-text>

      <v-row class="button-group" align="center" justify="center">
        <v-col :cols="deleteButton ? 4 : 10">
          <v-btn block color="#999999" @click="closeModal">닫기</v-btn>
        </v-col>
        <v-col v-if="deleteButton" cols="4">
          <v-btn block color="#DA8F8FFF" @click="deletePuppy">삭제</v-btn>
        </v-col>
        <v-col v-if="deleteButton" cols="4">
          <v-btn block color="#d7a6a6" style="color: white;" @click="addUserPuppy">관리 강아지 추가</v-btn>
        </v-col>
      </v-row>
    </v-card>

    <v-dialog v-model="nicknameDialogOpen" max-width="400px">
      <v-card>
        <v-card-title class="text-center">
          <h3>강아지 이름 입력</h3>
        </v-card-title>
        <v-card-text>
          <v-text-field v-model="userPuppyName" label="이름" />
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#d7a6a6" text @click="cancelUserPuppy">취소</v-btn>
          <v-btn color="#d7a6a6" text @click="submitUserPuppy">확인</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-dialog>
</template>

<script setup>
import { ref, watch, computed } from 'vue';
import axios from '@/api/axios'; // 수정된 경로로 Axios 임포트
import { useRouter } from 'vue-router';
const router = useRouter();


const props = defineProps({
  isOpen: Boolean,
  puppyId: Number,
  deleteButton: {
    type: Boolean,
    defaultValue: false,
  }
});

const dialogProps = computed(() => ({
  modelValue: props.isOpen,
}));

const emit = defineEmits(['update:isOpen','removed-puppy']);
const closeModal = () => {
  emit('update:isOpen', false);
};

const puppy = ref([]);
const userPuppyName = ref("");
const nicknameDialogOpen = ref(false);

// puppyId가 변경될 때마다 fetchPuppyDetails를 호출
watch(() => props.puppyId, (newPuppyId) => {
  if (newPuppyId) {
    fetchPuppyDetails(newPuppyId);
  }
});

const fetchPuppyDetails = (puppyId) => {
  axios.get(`/api/puppy/${puppyId}`)
      .then(response => {
        puppy.value = response.data;
      })
      .catch(error => {
        console.error('puppy 상세조회 실패 :', error);
      });
};

const deletePuppy = () => {
  axios.delete(`/api/puppy/${props.puppyId}`)
      .then(response => {
        alert(response.data);
        emit('removed-puppy');
        emit('update:isOpen', false);
      })
      .catch(error => {
        console.error('puppy 삭제 실패 :',error);
      })
}

const addUserPuppy = () => {
  nicknameDialogOpen.value = true;
};

const submitUserPuppy = () => {
  axios.post(`/api/userPuppy/create`,{
    puppyPersonalName: userPuppyName.value,
    puppyId: props.puppyId
  })
      .then(response => {
        alert("관리 강아지가 성공적으로 생성되었습니다.");
        emit('removed-puppy');
        emit('update:isOpen', false);
        router.push({ name: 'HomeView' });
      })
      .catch(error=>{
        console.log('userPuppy 추가 실패 :',error);
      })

  userPuppyName.value="";
}

const cancelUserPuppy = () => {
  nicknameDialogOpen.value = false;
  userPuppyName.value = "";
}

</script>

<style scoped>
.button-group{
  margin: 1rem;
}
</style>
