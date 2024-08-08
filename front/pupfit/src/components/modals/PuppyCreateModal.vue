<template>
  <v-dialog v-bind="dialogProps" max-width="600">
    <v-card>
      <v-card-title class="text-center">
        <h3 style="margin-top: 20px;">강아지 추가</h3>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form" @submit.prevent="createPuppy">
            <v-row>
              <v-col cols="6">
                <v-text-field
                    v-model="state.puppyName"
                    label="강아지 (품종)"
                    required
                ></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="state.puppyType"
                    label="강아지 (견종)"
                    required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-textarea
                    v-model="state.personality"
                    label="강아지 특징"
                    required
                ></v-textarea>
              </v-col>
              <v-col cols="12">
                <v-text-field
                    v-model="state.imageUrl"
                    label="이미지 URL"
                    required
                ></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="state.walkLow"
                    label="산책 최소 시간"
                    required
                ></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="state.walkHigh"
                    label="산책 최대 시간"
                    required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>
        </v-container>
      </v-card-text>
      <v-row align="center" justify="center">
        <v-col cols="5">
          <v-btn block size="large" color="#999999" @click="close">취소</v-btn>
        </v-col>
        <v-col cols="5">
          <v-btn block size="large" color="#d7a6a6" @click="createPuppy">추가</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-dialog>
</template>

<script setup>
import {ref, computed, reactive} from 'vue';
import axios from '@/api/axios'; // 수정된 경로로 Axios 임포트

const props = defineProps({
  isOpen2: Boolean
});
const dialogProps = computed(() => ({
  modelValue: props.isOpen2,
}));

const emit = defineEmits(['update:isOpen2']);
const close = () => {
  emit('update:isOpen2', false);
};

const state = reactive({
  puppyName: "",
  puppyType: "",
  personality: "",
  imageUrl: "",
  walkLow: "",
  walkHigh: "",
});

// 폼 제출 함수
const createPuppy = async () => {
  try{
    await axios.post('/api/puppy/create',{
      puppyName : state.puppyName,
      puppyType : state.puppyType,
      personality: state.personality,
      imgUrl : state.imageUrl,
      walkLow : state.walkLow,
      walkHigh : state.walkHigh,
    })
    state.puppyName = '';
    state.puppyType = '';
    state.personality = '';
    state.imageUrl = '';
    state.walkLow = '';
    state.walkHigh = '';
    close();
  } catch(error){
    console.error('Failed to add puppy:', error);
  }
};
</script>


<style scoped>

</style>
