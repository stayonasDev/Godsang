<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content p-6">
      <div class="flex justify-between items-center mb-4">
        <h3 class="text-2xl font-bold">{{ isEditing ? 'Edit Food Item' : 'Add Food Item' }}</h3>
        <button @click="$emit('close')" class="text-gray-500 hover:text-gray-700 text-2xl">
          <i :is="'i'" class="ri-close-line"></i>
        </button>
      </div>
      <form @submit.prevent="handleSubmit">
        <input type="hidden" v-model="formData.id" />

        <div class="mb-4">
          <label for="food-name" class="block text-gray-700 text-sm font-bold mb-2"
            >Food Name</label
          >
          <input
            type="text"
            id="food-name"
            v-model="formData.name"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="e.g., Apple"
            required
          />
        </div>
        <div class="mb-4">
          <label for="meal-type" class="block text-gray-700 text-sm font-bold mb-2"
            >Meal Type</label
          >
          <select
            id="meal-type"
            v-model="formData.meal"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          >
            <option value="breakfast">Breakfast</option>
            <option value="lunch">Lunch</option>
            <option value="dinner">Dinner</option>
            <option value="snack">Snacks</option>
          </select>
        </div>
        <div class="mb-4">
          <label for="quantity" class="block text-gray-700 text-sm font-bold mb-2">Quantity</label>
          <input
            type="number"
            id="quantity"
            v-model.number="formData.quantity"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            value="1"
            min="1"
            required
          />
        </div>
        <div class="mb-4">
          <label for="calories" class="block text-gray-700 text-sm font-bold mb-2"
            >Calories (kcal)</label
          >
          <input
            type="number"
            id="calories"
            v-model.number="formData.calories"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="e.g., 95"
            required
          />
        </div>
        <div class="mb-4">
          <label for="carbs" class="block text-gray-700 text-sm font-bold mb-2"
            >Carbohydrates (g)</label
          >
          <input
            type="number"
            id="carbs"
            v-model.number="formData.carbs"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="e.g., 25"
            required
          />
        </div>
        <div class="mb-4">
          <label for="protein" class="block text-gray-700 text-sm font-bold mb-2"
            >Protein (g)</label
          >
          <input
            type="number"
            id="protein"
            v-model.number="formData.protein"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="e.g., 0.5"
            required
          />
        </div>
        <div class="mb-4">
          <label for="fat" class="block text-gray-700 text-sm font-bold mb-2">Fat (g)</label>
          <input
            type="number"
            id="fat"
            v-model.number="formData.fat"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="e.g., 0.3"
            required
          />
        </div>
        <div class="mb-6">
          <label for="notes" class="block text-gray-700 text-sm font-bold mb-2">Notes</label>
          <textarea
            id="notes"
            v-model="formData.notes"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            rows="3"
            placeholder="Any additional notes..."
          ></textarea>
        </div>
        <div class="flex items-center justify-between">
          <button
            type="submit"
            class="bg-primary hover:bg-secondary text-white font-bold py-2 px-4 rounded-button focus:outline-none focus:shadow-outline"
          >
            Save Food
          </button>
          <div class="flex items-center space-x-2">
            <label for="exclude-summary-switch" class="text-gray-700 text-sm font-bold"
              >Exclude from Summary</label
            >
            <label class="custom-switch">
              <input type="checkbox" id="exclude-summary-switch" v-model="formData.exclude" />
              <span class="slider"></span>
            </label>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed, defineProps, defineEmits } from 'vue'

const props = defineProps({
  isVisible: {
    type: Boolean,
    default: false,
  },
  editingFood: {
    type: Object, // 수정할 음식 객체 (새로운 음식일 경우 null 또는 빈 객체)
    default: null,
  },
})

const emit = defineEmits(['close', 'save-food'])

// 폼 데이터를 반응형으로 관리
const formData = ref({
  id: null,
  name: '',
  meal: 'breakfast', // 기본값 설정
  quantity: 1,
  calories: 0,
  carbs: 0,
  protein: 0,
  fat: 0,
  notes: '',
  exclude: false,
  showDetails: false, // 모달에서는 이 값을 직접 수정할 필요는 없지만, 데이터 구조를 위해 포함
})

// 폼 초기화 함수를 watch 문보다 먼저 선언합니다.
const resetForm = () => {
  formData.value = {
    id: null,
    name: '',
    meal: 'breakfast',
    quantity: 1,
    calories: 0,
    carbs: 0,
    protein: 0,
    fat: 0,
    notes: '',
    exclude: false,
    showDetails: false,
  }
}

// 수정 모드인지 여부
const isEditing = computed(() => !!props.editingFood && props.editingFood.id !== null)

// editingFood prop이 변경될 때마다 formData를 업데이트
// 이 watch 함수가 resetForm을 사용하므로 resetForm이 먼저 선언되어야 합니다.
watch(
  () => props.editingFood,
  (newVal) => {
    if (newVal) {
      // 수정 모드: 기존 데이터로 폼 채우기
      formData.value = { ...newVal }
    } else {
      // 추가 모드: 폼 초기화 (ID만 null로)
      resetForm() // 이제 resetForm이 정의되어 있습니다.
    }
  },
  { immediate: true },
) // 컴포넌트 마운트 시 초기값 설정

// 폼 제출 핸들러
const handleSubmit = () => {
  // 유효성 검사 (간단하게 필수 필드만 확인)
  if (
    !formData.value.name ||
    formData.value.calories === null ||
    formData.value.quantity === null
  ) {
    alert('Please fill in all required fields (Food Name, Quantity, Calories).')
    return
  }
  emit('save-food', { ...formData.value }) // 폼 데이터를 부모에게 전달
  emit('close') // 저장 후 모달 닫기
  // resetForm(); // 폼 초기화는 부모에서 모달을 닫을 때 editingFood를 null로 전달하는 것으로 충분
}

// 모달 외부 클릭을 처리하는 CSS 오버레이를 위한 스타일
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* 다른 요소 위에 오도록 z-index 설정 */
}

.modal-content {
  background: white;
  padding: 1.5rem;
  border-radius: 0.5rem; /* rounded-lg */
  box-shadow:
    0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06); /* shadow-md */
  width: 90%;
  max-width: 500px;
  max-height: 90vh; /* 모달 높이가 화면을 넘지 않도록 */
  overflow-y: auto; /* 내용이 많아지면 스크롤 가능하도록 */
  position: relative;
  animation: fadeInScale 0.3s ease-out;
}

@keyframes fadeInScale {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

/* 닫기 버튼 */
.modal-content button.text-gray-500 {
  cursor: pointer;
  background: none;
  border: none;
  font-size: 1.5rem; /* text-2xl */
}

/* custom-switch 스타일 (전역 CSS에 있을 수 있지만, 여기에 직접 복사해서 사용해도 됨) */
.custom-switch {
  position: relative;
  display: inline-block;
  width: 40px;
  height: 20px;
}

.custom-switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: 0.4s;
  transition: 0.4s;
  border-radius: 20px;
}

.slider:before {
  position: absolute;
  content: '';
  height: 16px;
  width: 16px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  -webkit-transition: 0.4s;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #4f46e5; /* primary color */
}

input:focus + .slider {
  box-shadow: 0 0 1px #4f46e5;
}

input:checked + .slider:before {
  -webkit-transform: translateX(20px);
  -ms-transform: translateX(20px);
  transform: translateX(20px);
}
</style>
