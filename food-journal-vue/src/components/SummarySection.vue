<template>
  <section class="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-md mb-8">
    <h2 class="text-2xl font-bold mb-4">Today's Summary</h2>
    <div class="grid grid-cols-2 md:grid-cols-4 gap-4 text-center">
      <div class="bg-blue-50 p-4 rounded-lg">
        <p class="text-gray-500 text-sm">Calories</p>
        <p class="text-xl font-bold text-blue-700">{{ calories }}</p>
      </div>
      <div class="bg-green-50 p-4 rounded-lg">
        <p class="text-gray-500 text-sm">Carbs (g)</p>
        <p class="text-xl font-bold text-green-700">{{ carbs }}</p>
      </div>
      <div class="bg-yellow-50 p-4 rounded-lg">
        <p class="text-gray-500 text-sm">Protein (g)</p>
        <p class="text-xl font-bold text-yellow-700">{{ protein }}</p>
      </div>
      <div class="bg-red-50 p-4 rounded-lg">
        <p class="text-gray-500 text-sm">Fat (g)</p>
        <p class="text-xl font-bold text-red-700">{{ fat }}</p>
      </div>
    </div>
  </section>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  calories: {
    type: Number,
    default: 0
  },
  carbs: {
    type: Number,
    default: 0
  },
  protein: {
    type: Number,
    default: 0
  },
  fat: {
    type: Number,
    default: 0
  }
});
</script>

<style scoped>
/* 이 컴포넌트에만 고유한 스타일이 필요하다면 여기에 추가합니다. */
/* 현재는 Tailwind 클래스와 전역 CSS로 충분합니다. */
</style>