<template>
  <div class="min-h-screen bg-gray-50 p-4 font-inter text-gray-800">
    <AppHeader />
    <SummarySection
      :calories="totalCalories"
      :carbs="totalCarbs"
      :protein="totalProtein"
      :fat="totalFat"
    />

    <main class="max-w-4xl mx-auto mb-20">
      <div id="meal-sections">
        <MealSection
          title="Breakfast"
          :meal-items="breakfastItems"
          :total-meal-calories="totalBreakfastCalories"
          @edit-food="handleEditFood"
          @delete-food="handleDeleteFood"
          @update-food-item="handleUpdateFoodItem"
        />

        <MealSection
          title="Lunch"
          :meal-items="lunchItems"
          :total-meal-calories="totalLunchCalories"
          @edit-food="handleEditFood"
          @delete-food="handleDeleteFood"
          @update-food-item="handleUpdateFoodItem"
        />

        <MealSection
          title="Dinner"
          :meal-items="dinnerItems"
          :total-meal-calories="totalDinnerCalories"
          @edit-food="handleEditFood"
          @delete-food="handleDeleteFood"
          @update-food-item="handleUpdateFoodItem"
        />

        <MealSection
          title="Snacks"
          :meal-items="snackItems"
          :total-meal-calories="totalSnacksCalories"
          @edit-food="handleEditFood"
          @delete-food="handleDeleteFood"
          @update-food-item="handleUpdateFoodItem"
        />
      </div>
    </main>

    <AddFoodButton @click="handleAddFood" />

    <AddEditFoodModal
      :isVisible="isModalVisible"
      :editingFood="currentEditingFood"
      @close="isModalVisible = false"
      @save-food="handleSaveFood"
    />

    <ConfirmDialog
      :isVisible="isConfirmDialogVisible"
      title="Confirm Deletion"
      message="Are you sure you want to delete this food item?"
      confirm-text="Delete"
      cancel-text="Cancel"
      @confirm="confirmDelete"
      @cancel="isConfirmDialogVisible = false"
    />

    <ToastNotification
      :isVisible="isToastVisible"
      :message="toastMessage"
      :type="toastType"
    />
  </div>
</template>

<script setup>
import { ref, computed, nextTick } from 'vue';
import AppHeader from './components/AppHeader.vue';
import SummarySection from './components/SummarySection.vue';
import MealSection from './components/MealSection.vue';
import AddFoodButton from './components/AddFoodButton.vue';
import AddEditFoodModal from './components/AddEditFoodModal.vue';
import ConfirmDialog from './components/ConfirmDialog.vue';
import ToastNotification from './components/ToastNotification.vue'; // 새로 만든 ToastNotification 컴포넌트 임포트

// 샘플 음식 데이터
const foodItems = ref([
  { id: 1, name: '사과', meal: 'breakfast', quantity: 1, calories: 95, carbs: 25, protein: 0.5, fat: 0.3, notes: '간식으로 먹음', exclude: false, showDetails: false },
  { id: 2, name: '치킨 샐러드', meal: 'lunch', quantity: 1, calories: 300, carbs: 10, protein: 30, fat: 15, notes: '', exclude: false, showDetails: false },
  { id: 3, name: '스테이크', meal: 'dinner', quantity: 1, calories: 500, carbs: 0, protein: 50, fat: 30, notes: '레어', exclude: false, showDetails: false },
  { id: 4, name: '요거트', meal: 'snack', quantity: 1, calories: 150, carbs: 20, protein: 10, fat: 5, notes: '딸기맛', exclude: false, showDetails: false },
  { id: 5, name: '바나나', meal: 'breakfast', quantity: 1, calories: 105, carbs: 27, protein: 1.3, fat: 0.3, notes: '', exclude: false, showDetails: false },
  { id: 6, name: '초콜릿', meal: 'snack', quantity: 1, calories: 200, carbs: 30, protein: 2, fat: 10, notes: '다크 초콜릿', exclude: true, showDetails: false },
]);

// 식사 유형별로 음식 아이템 필터링
const breakfastItems = computed(() => foodItems.value.filter(item => item.meal === 'breakfast'));
const lunchItems = computed(() => foodItems.value.filter(item => item.meal === 'lunch'));
const dinnerItems = computed(() => foodItems.value.filter(item => item.meal === 'dinner'));
const snackItems = computed(() => foodItems.value.filter(item => item.meal === 'snack'));

// 각 식사 유형의 총 칼로리 계산
const calculateMealCalories = (mealItems) => {
  return mealItems
    .filter(item => !item.exclude)
    .reduce((sum, item) => sum + item.calories * item.quantity, 0);
};

// 총 영양 성분 계산
const totalCalories = computed(() =>
  foodItems.value
    .filter(item => !item.exclude)
    .reduce((sum, item) => sum + item.calories * item.quantity, 0)
);
const totalCarbs = computed(() =>
  foodItems.value
    .filter(item => !item.exclude)
    .reduce((sum, item) => sum + item.carbs * item.quantity, 0)
);
const totalProtein = computed(() =>
  foodItems.value
    .filter(item => !item.exclude)
    .reduce((sum, item) => sum + item.protein * item.quantity, 0)
);
const totalFat = computed(() =>
  foodItems.value
    .filter(item => !item.exclude)
    .reduce((sum, item) => sum + item.fat * item.quantity, 0)
);

const totalBreakfastCalories = computed(() => calculateMealCalories(breakfastItems.value));
const totalLunchCalories = computed(() => calculateMealCalories(lunchItems.value));
const totalDinnerCalories = computed(() => calculateMealCalories(dinnerItems.value));
const totalSnacksCalories = computed(() => calculateMealCalories(snackItems.value));


// 모달/다이얼로그/토스트 표시 상태 관리
const isModalVisible = ref(false); // 음식 추가/수정 모달
const currentEditingFood = ref(null); // 수정할 음식 객체 (null이면 추가 모드)
const isConfirmDialogVisible = ref(false); // 삭제 확인 다이얼로그
const foodToDeleteId = ref(null); // 삭제할 음식의 ID

const toastMessage = ref('');
const toastType = ref('success'); // 'success', 'error', 'warning'
const isToastVisible = ref(false);
let toastTimeout = null;

// 음식 추가 버튼 클릭 핸들러
const handleAddFood = () => {
  currentEditingFood.value = null; // 새로운 음식 추가이므로 현재 편집 중인 음식 없음
  isModalVisible.value = true;
};

// 음식 아이템 수정 버튼 클릭 핸들러 (MealSection -> FoodItem에서 이벤트 발생)
const handleEditFood = (id) => {
  const foodToEdit = foodItems.value.find(item => item.id === id);
  if (foodToEdit) {
    currentEditingFood.value = { ...foodToEdit }; // 반응형 객체로 전달 (원본 직접 수정 방지)
    isModalVisible.value = true;
  }
};

// 음식 아이템 삭제 버튼 클릭 핸들러 (MealSection -> FoodItem에서 이벤트 발생)
const handleDeleteFood = (id) => {
  foodToDeleteId.value = id; // 삭제할 음식 ID 저장
  isConfirmDialogVisible.value = true; // 확인 다이얼로그 표시
};

// 확인 다이얼로그에서 'Delete' 클릭 시 실제 삭제 로직
const confirmDelete = () => {
  if (foodToDeleteId.value !== null) {
    foodItems.value = foodItems.value.filter(item => item.id !== foodToDeleteId.value);
    showToast('Food item deleted successfully!', 'success');
    foodToDeleteId.value = null; // ID 초기화
  }
  isConfirmDialogVisible.value = false; // 다이얼로그 닫기
};

// AddEditFoodModal에서 'save-food' 이벤트 발생 시 처리
const handleSaveFood = (foodData) => {
  if (foodData.id) {
    // 수정 모드
    const index = foodItems.value.findIndex(item => item.id === foodData.id);
    if (index !== -1) {
      Object.assign(foodItems.value[index], foodData); // 기존 객체 업데이트
      showToast('Food item updated successfully!', 'success');
    }
  } else {
    // 추가 모드
    const newId = Math.max(0, ...foodItems.value.map(item => item.id)) + 1; // 가장 큰 ID + 1
    foodItems.value.push({ ...foodData, id: newId, showDetails: false });
    showToast('Food item added successfully!', 'success');
  }
};

// FoodItem에서 exclude 상태 업데이트 처리
const handleUpdateFoodItem = (id, updates) => {
  const index = foodItems.value.findIndex(item => item.id === id);
  if (index !== -1) {
    Object.assign(foodItems.value[index], updates);
    // exclude 상태 변경 시 요약도 바로 업데이트 되므로 별도 토스트는 필요 없음
  }
};

// 토스트 메시지 표시 함수
const showToast = (message, type = 'success') => {
  clearTimeout(toastTimeout); // 이전 토스트가 있다면 타이머 초기화

  toastMessage.value = message;
  toastType.value = type;
  isToastVisible.value = true;

  toastTimeout = setTimeout(() => {
    isToastVisible.value = false;
  }, 3000);
};

</script>

<style>
/* App.vue의 전역 스타일 또는 Main.css에 있던 스타일을 여기에 유지 */
/* .modal, .confirm-dialog, .toast 관련 스타일은 이제 컴포넌트 내부에서 관리됩니다. */

/* Food Journal.html에 있던 전역 CSS는 App.vue의 <style> 블록에 두거나, */
/* index.css(또는 main.css) 파일에 옮겨놓는 것이 좋습니다. */
/* 여기서는 모달, confirm, 토스트 관련 스타일은 해당 컴포넌트 내부로 옮겼습니다. */

/* Example of original global styles that might be needed in App.vue or main.css */
.font-pacifico {
  font-family: 'Pacifico', cursive;
}
.font-inter {
  font-family: 'Inter', sans-serif;
}
.text-primary {
  color: #4f46e5;
}
.bg-primary {
  background-color: #4f46e5;
}
.hover\:bg-secondary:hover {
  background-color: #6366f1;
}
.rounded-button {
  border-radius: 8px;
}

/* custom-checkbox styles from original HTML CSS */
.custom-checkbox {
  width: 24px;
  height: 24px;
  border: 2px solid #ccc;
  border-radius: 4px;
  display: inline-block;
  vertical-align: middle;
  cursor: pointer;
  position: relative;
  transition: all 0.2s ease-in-out;
}

.custom-checkbox::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 12px;
  height: 12px;
  background-color: white;
  border-radius: 2px;
  transform: translate(-50%, -50%) scale(0);
  opacity: 0;
  transition: all 0.2s ease-in-out;
}

.custom-checkbox.checked {
  background-color: #4f46e5; /* primary */
  border-color: #4f46e5; /* primary */
}

.custom-checkbox.checked::after {
  transform: translate(-50%, -50%) scale(1);
  opacity: 1;
}

/* details-panel transition from original HTML CSS */
.details-panel {
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s ease-out, opacity 0.3s ease-out;
    opacity: 0;
}

.details-panel.show {
    max-height: 100px; /* Adjust based on content */
    opacity: 1;
}
</style>