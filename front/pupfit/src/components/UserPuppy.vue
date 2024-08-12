<template>
  <div class="user-puppy">
    <h3>나의 강아지</h3>
    <v-select
        :items="formattedUserPuppies"
        item-title="formattedTitle"
        item-value="id"
        v-model="selectedUserPuppyId"
        label="강아지를 선택하세요"
        @input="onSelectChange"
    >
    </v-select>
  </div>
</template>

<script setup>
import { ref, onMounted , watch, computed } from 'vue';
import axios from '@/api/axios.js';

const userPuppies = ref([]);
const selectedUserPuppyId = ref(null);


const emit = defineEmits(['updated-userPuppy-id']);
const onSelectChange = (value) =>{
  emit('updated-userPuppy-id', value);
}

onMounted(() => {
  axios.get('/api/userPuppy/read')
      .then(response => {
        userPuppies.value = response.data;
      })
      .catch(error => {
        console.error('관리 강아지 조회 실패 :', error);
      });
});

const formattedUserPuppies = computed(() => {
  return userPuppies.value.map(puppy => ({
    ...puppy,
    formattedTitle: `${puppy.puppyPersonalName} (${puppy.puppyName})`
  }));
});

watch(
    () => selectedUserPuppyId.value,
    (newSelectedUserPuppyId, oldSelectedUserPuppyId) => {
      if (newSelectedUserPuppyId !== oldSelectedUserPuppyId) {
        onSelectChange(selectedUserPuppyId.value);
      }
    }
);

</script>

<style scoped>
.user-puppy {
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 1rem;
}

h3 {
  margin-top: 0;
  margin-bottom: 1rem;
}
</style>
