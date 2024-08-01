<template>
  <v-dialog v-bind="dialogProps" max-width="600">
    <v-card>
      <v-card-title style="text-align: center">
        <h3 style="margin-top: 20px;">{{ puppy.puppyName }}</h3>
      </v-card-title>

      <v-card-text>
        <v-row>
          <v-col cols="12">
            <img :src="puppy.imgUrl" alt="puppy image" style="width: 100%;">
          </v-col>
          <v-col cols="12">
            <p><strong>견종 :</strong> {{ puppy.puppyType }}</p>
            <br>
            <p><strong>성격 :</strong> {{ puppy.personality }}</p>
            <br>
            <p><strong>산책 시간 :</strong> {{ puppy.walkLow }} ~ {{ puppy.walkHigh }}</p>
          </v-col>
        </v-row>
      </v-card-text>

      <v-card-actions>
        <v-btn color="primary" @click="closeModal">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, watch, computed } from 'vue';
import axios from '@/api/axios'; // 수정된 경로로 Axios 임포트

const props = defineProps({
  isOpen: Boolean,
  puppyId: Number
});

const emit = defineEmits(['update:isOpen']);

const puppy = ref([]);

// 모달 속성 객체 계산
const dialogProps = computed(() => ({
  modelValue: props.isOpen,
  'onUpdate:modelValue': (val) => emit('update:isOpen', val),
}));

const fetchPuppyDetails = async (puppyId) => {
  try {
    const response = await axios.get(`/api/puppy/${puppyId}`);
    puppy.value = response.data;
  } catch (error) {
    console.error('Failed to fetch puppy details:', error);
  }
};

// puppyId가 변경될 때마다 fetchPuppyDetails를 호출
watch(() => props.puppyId, (newPuppyId) => {
  if (newPuppyId) {
    fetchPuppyDetails(newPuppyId);
  }
});

// 모달 닫기 함수
const closeModal = () => {
  emit('update:isOpen', false);
};
</script>
