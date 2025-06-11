<template>
  <div class="food-item flex items-center justify-between p-4 bg-gray-50 rounded-lg shadow-sm">
    <div class="flex items-center space-x-3">
      <input
        type="checkbox"
        class="hidden"
        :id="`checkbox-${item.id}`"
        v-model="item.exclude"
        @change="$emit('toggle-exclude', item.id, item.exclude)"
      >
      <label
        :for="`checkbox-${item.id}`"
        class="custom-checkbox"
        :class="{ 'checked': item.exclude }"
      ></label>

      <div>
        <h4 class="font-semibold text-lg">{{ item.name }}</h4>
        <div class="flex flex-wrap gap-2 mt-1">
          <span class="nutrition-pill bg-blue-100 text-blue-800">{{ item.quantity }}개</span>
          <span class="nutrition-pill bg-gray-100 text-gray-800">{{ item.calories * item.quantity }} kcal</span>
          <span class="nutrition-pill bg-green-100 text-green-800">{{ item.carbs * item.quantity }}g Carbs</span>
          <span class="nutrition-pill bg-yellow-100 text-yellow-800">{{ item.protein * item.quantity }}g Protein</span>
          <span class="nutrition-pill bg-red-100 text-red-800">{{ item.fat * item.quantity }}g Fat</span>
        </div>
        <div class="details-panel mt-2 text-sm text-gray-600" :class="{ 'show': item.showDetails }">
          <p v-if="item.notes">Notes: {{ item.notes }}</p>
          <p v-else class="text-gray-400">No notes for this item.</p>
        </div>
      </div>
    </div>
    <div class="flex space-x-2">
      <button
        @click="$emit('toggle-details', item.id)"
        class="text-gray-400 hover:text-blue-600"
        :class="{ 'rotate-180': item.showDetails }"
        style="transition: transform 0.2s ease;"
      >
        <i class="ri-arrow-down-s-line"></i>
      </button>
      <button
        @click="$emit('edit', item.id)"
        class="text-gray-400 hover:text-blue-600"
      >
        <i class="ri-edit-line"></i>
      </button>
      <button
        @click="$emit('delete', item.id)"
        class="text-gray-400 hover:text-red-600"
      >
        <i class="ri-delete-bin-line"></i>
      </button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

// props 정의
defineProps({
  item: {
    type: Object,
    required: true,
    // item 객체의 구조를 더 명확하게 정의할 수도 있습니다.
    // validator: (value) => { /* ... */ }
  }
});

// emits 정의
defineEmits(['edit', 'delete', 'toggle-details', 'toggle-exclude']);
</script>

<style scoped>
/* FoodItem 고유의 스타일은 현재 없습니다. */
/* details-panel의 transition과 custom-checkbox 스타일은 전역 CSS에 정의되어 있습니다. */
/* 버튼 회전 애니메이션을 위한 클래스는 Tailwind에 없으므로, 인라인 스타일로 추가했습니다. */
.rotate-180 {
  transform: rotate(180deg);
}
</style>