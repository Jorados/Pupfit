<template>
  <div class="user-container">

    <div class="user-card mb-5">
      <div class="user-card__header">
        <h3>사용자 정보</h3>
      </div>
      <div class="user-card__content">
        <p><strong>이름:</strong> {{ currentUser.nickname }}</p>
        <p><strong>성별:</strong> {{ currentUser.gender }}</p>
        <v-btn class="user-edit-button" @click="showPasswordModal" style="width: 100%">정보 수정</v-btn>
      </div>
    </div>

    <v-dialog v-model="state.passwordModal" max-width="290">
      <v-card>
        <v-card-title class="headline">비밀번호를 입력하세요.</v-card-title>
        <v-card-text>
          <v-text-field v-model="state.password" label="비밀번호" type="password"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#d7a6a6" text @click="state.passwordModal = false">취소</v-btn>
          <v-btn color="#d7a6a6" text @click="editUser">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


  </div>
</template>

<script setup>
import { onMounted,ref } from "vue";
import { useRouter } from 'vue-router';
import axios from "@/api/axios.js";

const router = useRouter();
const currentUser = ref([]);
const state = ref({
  passwordModal: false,
  password: '',
});

onMounted(() => {
  axios.get('/api/user/read')
      .then(response => {
        currentUser.value = response.data;
      })
      .catch(error => {
        console.error('정보 조회 에러 :', error);
      });
});

const showPasswordModal = () => {
  state.value.passwordModal = true;

};

const editUser = () => {
  if (!state.value.password) {
    alert('비밀번호를 입력해주세요.');
    return;
  }
  axios.post('/api/user/password',{
    password : state.value.password
  })
      .then(response =>{
        console.log("비밀번호 확인 성공", response);
        if(response.status == 200) {
          router.push({ name: 'MyEdit' });
        }
      })
      .catch(error  =>{
        console.log("비밀번호 확인 실패:", error);
        alert("비밀번호가 틀렸습니다. 다시 시도해주세요.");
      })
  state.value.passwordModal = false;
  state.value.password = '';
};

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
  margin-top: 0.7rem;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  background-color: #d7a6a6;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;
}
</style>