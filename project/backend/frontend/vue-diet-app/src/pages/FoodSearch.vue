<template>
    <div>
      <h1>음식 검색 페이지</h1>
      <p>선택된 식사: <strong>{{ meal }}</strong> | 날짜: <strong>{{ date }}</strong></p>
      
      <input v-model="query" placeholder="음식 검색 (예: 치킨)" />
      <button @click="searchFoods">검색</button>
      
      <ul>
        <li v-for="food in filteredFoods" :key="food.id" @click="selectFood(food)" class="food-item">
          {{ food.name }} - {{ food.calories }} kcal
        </li>
      </ul>
      
      <router-link :to="{ name: 'FoodAdd', params: { meal: meal, date: date } }" class="add-new">
        음식이 없어요? 추가하세요
      </router-link>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  
  // 라우트 파라미터 수신
  const route = useRoute()
  const router = useRouter()
  const meal = route.params.meal
  const date = route.params.date
  
  const query = ref('')
  // 더미 음식 데이터 (실제 구현 시 API 호출로 동기화)
  const foods = ref([
    { id: 101, name: '그릴드 치킨', calories: 250 },
    { id: 102, name: '치킨 샐러드', calories: 300 },
    { id: 103, name: '치킨 수프', calories: 180 }
  ])
  
  // 단순 검색 필터
  const filteredFoods = computed(() => {
    if (!query.value) return foods.value
    return foods.value.filter(food => food.name.includes(query.value))
  })
  
  // 음식 선택 시, 그램 입력 페이지로 이동 (foodId 전달)
  const selectFood = (food) => {
    router.push({ name: 'FoodGramInput', params: { meal, date, foodId: food.id } })
  }
  
  // 검색 버튼 (여기서는 이미 computed 속성으로 필터 처리하므로 특별한 로직 없이)
  const searchFoods = () => {
    // 실제 검색 요청을 한다면 이곳에서 API 호출 코드를 추가합니다.
    console.log('검색어:', query.value)
  }
  </script>
  
  <style scoped>
  .food-item {
    cursor: pointer;
    margin: 0.5rem 0;
    padding: 0.3rem;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  .add-new {
    display: inline-block;
    margin-top: 1rem;
    padding: 0.3rem 0.6rem;
    background-color: #4caf50;
    color: white;
    text-decoration: none;
    border-radius: 4px;
  }
  </style>
  