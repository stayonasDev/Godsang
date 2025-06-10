<template>
    <div>
      <h1>새 음식 추가</h1>
      <p>등록할 식사: <strong>{{ meal }}</strong> | 날짜: <strong>{{ date }}</strong></p>
      <form @submit.prevent="saveFood">
        <div>
          <label for="brand">브랜드:</label>
          <input id="brand" v-model="newFood.brand" required />
        </div>
        <div>
          <label for="name">음식 이름:</label>
          <input id="name" v-model="newFood.name" required />
        </div>
        <div>
          <label for="calories">칼로리:</label>
          <input id="calories" type="number" v-model.number="newFood.calories" required />
        </div>
        <div>
          <label for="protein">단백질 (g):</label>
          <input id="protein" type="number" v-model.number="newFood.protein" required />
        </div>
        <div>
          <label for="fat">지방 (g):</label>
          <input id="fat" type="number" v-model.number="newFood.fat" required />
        </div>
        <button type="submit">저장</button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  
  const route = useRoute()
  const router = useRouter()
  const { meal, date } = route.params
  
  // 신규 음식 정보를 위한 reactive 객체
  const newFood = reactive({
    brand: '',
    name: '',
    calories: 0,
    protein: 0,
    fat: 0
  })
  
  const saveFood = () => {
    // 실제 구현 시 신규 음식을 DB에 저장하는 API 호출을 진행합니다.
    // 저장 성공 후, 저장된 음식의 id (예: 999)를 받는다고 가정합니다.
    const generatedFoodId = 999
    console.log(`신규 음식 저장: ${JSON.stringify(newFood)}`)
    alert('신규 음식이 저장되었습니다.')
    // 음식 등록 후 그램 입력 페이지로 이동 (새 음식으로 섭취량 입력)
    router.push({ name: 'FoodGramInput', params: { meal, date, foodId: generatedFoodId } })
  }
  </script>
  
  <style scoped>
  form div {
    margin: 0.5rem 0;
  }
  </style>
  