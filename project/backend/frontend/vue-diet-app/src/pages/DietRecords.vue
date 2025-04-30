<template>
    <div>
      <h1>식단 기록 페이지</h1>
      <label for="record-date">날짜 선택:</label>
      <input id="record-date" type="date" v-model="selectedDate" />
  
      <div v-if="selectedDate">
        <h2>{{ formattedDate }}의 식단</h2>
        <div v-for="meal in meals" :key="meal" class="meal-section">
          <h3>{{ meal }}</h3>
          <ul>
            <!-- 여기서는 더미 데이터를 사용합니다. 실제 DB와 연동 시 API 호출 등으로 대체 -->
            <li v-for="item in getMealItems(meal)" :key="item.id">
              {{ item.foodName }} - {{ item.calories }} kcal
            </li>
            <li v-if="getMealItems(meal).length === 0">기록 없음</li>
          </ul>
          <!-- meal, selectedDate 정보를 파라미터로 FoodSearch 페이지로 전달 -->
          <router-link 
            :to="{ name: 'FoodSearch', params: { meal: meal, date: selectedDate } }" 
            class="add-btn">
            {{ meal }} 추가하기
          </router-link>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  
  // 선택 가능한 식사 종류
  const meals = ['아침', '점심', '저녁', '간식']
  const selectedDate = ref('')
  
  // 더미 식단 데이터 (실제 구현 시 API 또는 Vuex, Pinia 등에서 가져옴)
  const dietRecords = ref({
    // 예: "2024-04-01": { 아침: [{ id: 1, foodName: '오트밀', calories: 200 }], 점심: [], ... }
    "2024-04-01": {
      아침: [
        { id: 1, foodName: '오트밀', calories: 200 },
        { id: 2, foodName: '바나나', calories: 90 }
      ],
      점심: [],
      저녁: [],
      간식: []
    }
  })
  
  // 날짜 형식 변경 (yyyy-mm-dd → yyyy/mm/dd)
  const formattedDate = computed(() => {
    return selectedDate.value ? selectedDate.value.replace(/-/g, '/') : ''
  })
  
  // meal 별 기록 조회 함수
  const getMealItems = (meal) => {
    return (dietRecords.value[selectedDate.value] && dietRecords.value[selectedDate.value][meal]) || []
  }
  </script>
  
  <style scoped>
  .meal-section {
    margin-bottom: 2rem;
  }
  .add-btn {
    display: inline-block;
    margin-top: 0.5rem;
    padding: 0.3rem 0.6rem;
    background-color: #2196f3;
    color: white;
    text-decoration: none;
    border-radius: 4px;
  }
  </style>
  