<template>
  <div class="bg-white p-6 rounded-lg shadow-md mb-6">
    <div class="flex justify-between items-center mb-4">
      <h3 class="text-xl font-bold">{{ title }}</h3>
      <span class="text-gray-500 text-sm">{{ totalMealCalories }} kcal</span>
    </div>
    <div class="space-y-4">
      <p v-if="mealItems.length === 0" class="text-gray-500 text-center py-4">
        No food items logged for {{ title.toLowerCase() }}.
      </p>
      <div v-else>
        <FoodItem
          v-for="item in mealItems"
          :key="item.id"
          :item="item"
          @edit="handleEditFood"
          @delete="handleDeleteFood"
          @toggle-details="handleToggleDetails"
          @toggle-exclude="handleToggleExclude"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import FoodItem from './FoodItem.vue'; // FoodItem 컴포넌트 임포트

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  mealItems: {
    type: Array,
    default: () => []
  },
  totalMealCalories: {
    type: Number,
    default: 0
  }
});

// App.vue로 이벤트를 다시 내보내기 위해 emit 정의
const emit = defineEmits(['edit-food', 'delete-food', 'update-food-item']);

// FoodItem에서 발생한 edit 이벤트를 App.vue로 다시 내보냄
const handleEditFood = (id) => {
  emit('edit-food', id);
};

// FoodItem에서 발생한 delete 이벤트를 App.vue로 다시 내보냄
const handleDeleteFood = (id) => {
  emit('delete-food', id);
};

// FoodItem에서 발생한 toggle-details 이벤트를 처리
const handleToggleDetails = (id) => {
  const itemToToggle = props.mealItems.find(item => item.id === id);
  if (itemToToggle) {
    // 다른 패널을 닫고 선택된 패널만 토글합니다.
    props.mealItems.forEach(item => {
      if (item.id !== id) {
        item.showDetails = false;
      }
    });
    itemToToggle.showDetails = !itemToToggle.showDetails;
  }
};

// FoodItem에서 발생한 toggle-exclude 이벤트를 처리
const handleToggleExclude = (id, newExcludeStatus) => {
  // App.vue로 업데이트 요청을 보냄
  emit('update-food-item', id, { exclude: newExcludeStatus });
};
</script>

<style scoped>
/* MealSection 고유의 스타일은 현재 없습니다. */
</style>