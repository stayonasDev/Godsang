<template>
    <div>
      <h1>음식 섭취량 입력</h1>
      <p>선택한 음식 ID: <strong>{{ foodId }}</strong></p>
      <!-- 실제 앱에서는 foodId를 통해 음식 상세 정보를 API로 가져옵니다. -->
      <form @submit.prevent="saveGram">
        <label for="gram">섭취량 (그램):</label>
        <input id="gram" type="number" v-model.number="grams" required />
        <button type="submit">저장하기</button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  
  const route = useRoute()
  const router = useRouter()
  const { meal, date, foodId } = route.params
  
  const grams = ref(0)
  
  const saveGram = () => {
    // 여기에서 DB에 기록하는 로직 또는 API 요청 호출
    console.log(`저장: 날짜 ${date}, 식사 ${meal}, 음식ID ${foodId}, 섭취량 ${grams.value}g`)
    alert('섭취량이 저장되었습니다.')
    // 저장 후 식단 기록 페이지로 돌아가거나 다른 페이지로 이동 가능합니다.
    router.push({ name: 'DietRecords' })
  }
  </script>
  
  <style scoped>
  form {
    margin-top: 1rem;
  }
  </style>
  