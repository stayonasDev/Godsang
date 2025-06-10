<template>
  <div class="bg-gray-50 font-sans">
    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Calendar Section -->
      <div class="bg-white rounded-lg shadow p-6 mb-8">
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-2xl font-semibold text-gray-900">Calendar Schedule</h2>
          <div class="flex space-x-4">
            <button @click="previousMonth" class="rounded-md p-2 text-gray-600 hover:bg-gray-100">
              <i class="fas fa-chevron-left"></i>
            </button>
            <span class="flex items-center px-4 py-2 text-lg font-medium">
              {{ currentMonthYear }}
            </span>
            <button @click="nextMonth" class="rounded-md p-2 text-gray-600 hover:bg-gray-100">
              <i class="fas fa-chevron-right"></i>
            </button>
          </div>
        </div>

        <div class="grid grid-cols-7 gap-px bg-gray-200 text-sm">
          <div
            v-for="day in weekDays"
            :key="day"
            class="bg-gray-50 p-2 text-center font-medium"
            :class="[day === 'Sun' || day === 'Sat' ? 'text-gray-500' : 'text-gray-900']"
          >
            {{ day }}
          </div>
        </div>

        <div class="grid grid-cols-7 gap-px bg-gray-200">
          <div
            v-for="date in calendarDates"
            :key="`${date.month}-${date.day}`"
            @click="selectDate(date)"
            class="bg-white p-4 min-h-[100px] cursor-pointer hover:bg-gray-50 relative"
            :class="[date.isCurrentMonth ? 'text-gray-900' : 'text-gray-400']"
          >
            {{ date.day }}
            <div v-if="getEventsForDate(date).length > 0" class="absolute bottom-2 left-2 right-2">
              <div
                v-for="event in getEventsForDate(date)"
                :key="event.id"
                class="text-xs rounded px-2 py-1 mb-1"
                :class="getEventColorClass(event.type)"
              >
                {{ event.type === 'meal' ? `Meal: ${event.time}` : event.title }}
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <!-- Today's Menu -->
        <div class="bg-white rounded-lg shadow p-6">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-medium text-gray-900">Today's Menu</h3>
            <button
              @click="showFoodModal = true"
              class="rounded-md custom-bg px-3 py-1 text-white text-sm hover:bg-blue-600"
            >
              <i class="fas fa-plus mr-1"></i>Add Food
            </button>
          </div>
          <div class="space-y-4">
            <div
              v-for="meal in todaysMeals"
              :key="meal.id"
              class="flex items-center p-3 bg-gray-50 rounded-lg"
            >
              <div class="w-4 h-4 rounded-full mr-4" :class="getMealColorClass(meal.type)"></div>
              <div class="flex-1">
                <div class="flex items-center justify-between">
                  <h4 class="text-sm font-medium text-gray-900">{{ meal.name }}</h4>
                  <span class="text-sm text-gray-500">{{ meal.time }}</span>
                </div>
                <p class="text-sm text-gray-500">{{ meal.description }}</p>
                <p class="text-xs text-gray-400">{{ meal.calories }} cal</p>
              </div>
              <div class="ml-4">
                <input
                  type="checkbox"
                  v-model="meal.completed"
                  class="h-4 w-4 text-blue-600 border-gray-300 focus:ring-blue-500"
                />
              </div>
            </div>
            <div v-if="todaysMeals.length === 0" class="text-center text-gray-500 py-8">
              No meals planned for today
            </div>
          </div>
        </div>

        <!-- Nutrition Progress -->
        <div class="bg-white rounded-lg shadow p-6">
          <h3 class="text-lg font-medium text-gray-900 mb-4">Nutrition Progress</h3>
          <div class="space-y-6">
            <div class="bg-gray-50 p-4 rounded-lg">
              <div class="flex justify-between items-center mb-4">
                <h4 class="text-sm font-medium text-gray-900">Daily Progress</h4>
                <select
                  v-model="progressView"
                  class="text-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
                >
                  <option value="daily">Daily</option>
                  <option value="weekly">Weekly</option>
                  <option value="monthly">Monthly</option>
                </select>
              </div>
              <div class="space-y-4">
                <div
                  v-for="nutrient in nutritionProgress"
                  :key="nutrient.name"
                  class="relative pt-1"
                >
                  <div class="flex justify-between items-center mb-2">
                    <span class="text-xs font-medium text-gray-700">
                      {{ nutrient.name }} ({{ nutrient.current }}{{ nutrient.unit }}/{{
                        nutrient.target
                      }}{{ nutrient.unit }})
                    </span>
                    <span class="text-xs font-medium text-gray-700"
                      >{{ nutrient.percentage }}%</span
                    >
                  </div>
                  <div
                    class="overflow-hidden h-2 text-xs flex rounded"
                    :class="`bg-${nutrient.color}-100`"
                  >
                    <div
                      :style="`width: ${Math.min(nutrient.percentage, 100)}%`"
                      class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center"
                      :class="`bg-${nutrient.color}-500`"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
            <div class="mt-4">
              <button
                @click="showNutritionModal = true"
                class="w-full rounded-md custom-bg p-2 text-white shadow-sm hover:bg-blue-600"
              >
                Register Diet Details
              </button>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Event Modal -->
    <div v-if="showEventModal" class="modal-overlay" @click="closeEventModal">
      <div class="modal-content bg-white rounded-lg shadow-xl p-6 w-96" @click.stop>
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-medium text-gray-900">Add New Event</h3>
          <button @click="closeEventModal" class="text-gray-400 hover:text-gray-600">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="saveEvent">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Event Type</label>
            <select
              v-model="newEvent.type"
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            >
              <option value="routine">Routine</option>
              <option value="meal">Meal</option>
              <option value="exercise">Exercise</option>
              <option value="appointment">Appointment</option>
            </select>
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Title</label>
            <input
              v-model="newEvent.title"
              type="text"
              required
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Date</label>
            <input
              v-model="newEvent.date"
              type="date"
              required
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Time</label>
            <input
              v-model="newEvent.time"
              type="time"
              required
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
            <textarea
              v-model="newEvent.description"
              rows="3"
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            ></textarea>
          </div>
          <div class="flex justify-end space-x-3">
            <button
              type="button"
              @click="closeEventModal"
              class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-md hover:bg-gray-200"
            >
              Cancel
            </button>
            <button
              type="submit"
              class="px-4 py-2 text-sm font-medium text-white custom-bg rounded-md hover:bg-blue-600"
            >
              Save Event
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Food Modal -->
    <div v-if="showFoodModal" class="modal-overlay" @click="closeFoodModal">
      <div class="modal-content bg-white rounded-lg shadow-xl p-6 w-96" @click.stop>
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-medium text-gray-900">Add Food Item</h3>
          <button @click="closeFoodModal" class="text-gray-400 hover:text-gray-600">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="saveFood">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Food Name</label>
            <input
              v-model="newFood.name"
              type="text"
              required
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Meal Type</label>
            <select
              v-model="newFood.type"
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            >
              <option value="breakfast">Breakfast</option>
              <option value="lunch">Lunch</option>
              <option value="dinner">Dinner</option>
              <option value="snack">Snack</option>
            </select>
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Time</label>
            <input
              v-model="newFood.time"
              type="time"
              required
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Calories</label>
            <input
              v-model="newFood.calories"
              type="number"
              required
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
            <textarea
              v-model="newFood.description"
              rows="2"
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            ></textarea>
          </div>
          <div class="flex justify-end space-x-3">
            <button
              type="button"
              @click="closeFoodModal"
              class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-md hover:bg-gray-200"
            >
              Cancel
            </button>
            <button
              type="submit"
              class="px-4 py-2 text-sm font-medium text-white custom-bg rounded-md hover:bg-blue-600"
            >
              Add Food
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Nutrition Modal -->
    <div v-if="showNutritionModal" class="modal-overlay" @click="closeNutritionModal">
      <div class="modal-content bg-white rounded-lg shadow-xl p-6 w-96" @click.stop>
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-medium text-gray-900">Update Nutrition</h3>
          <button @click="closeNutritionModal" class="text-gray-400 hover:text-gray-600">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="saveNutrition">
          <div v-for="nutrient in nutritionProgress" :key="nutrient.name" class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">
              {{ nutrient.name }} {{ nutrient.unit }}
            </label>
            <input
              v-model.number="nutrient.current"
              type="number"
              step="0.1"
              class="w-full border-gray-300 focus:border-blue-500 focus:ring-blue-500 rounded-md"
            />
          </div>
          <div class="flex justify-end space-x-3">
            <button
              type="button"
              @click="closeNutritionModal"
              class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-md hover:bg-gray-200"
            >
              Cancel
            </button>
            <button
              type="submit"
              class="px-4 py-2 text-sm font-medium text-white custom-bg rounded-md hover:bg-blue-600"
            >
              Update
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'

const activeTab = ref('calaendar')
const currentDate = ref(new Date())
const weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
const showEventModal = ref(false)
const showFoodModal = ref(false)
const showNutritionModal = ref(false)
const progressView = ref('daily')

const events = ref([
  {
    id: 1,
    type: 'meal',
    title: 'Breakfast',
    date: '2024-06-04',
    time: '06:00',
    description: 'Healthy breakfast',
  },
])

const meals = ref([
  {
    id: 1,
    name: 'Bruschetta',
    type: 'breakfast',
    time: '06:00',
    description: 'Toasted bread with tomatoes and herbs',
    calories: 280,
    date: new Date().toISOString().split('T')[0],
    completed: false,
  },
])

const nutritionProgress = ref([
  { name: 'Water', current: 2.5, target: 3, unit: 'L', color: 'blue' },
  { name: 'Protein', current: 45, target: 65, unit: 'g', color: 'green' },
  { name: 'Carbs', current: 120, target: 150, unit: 'g', color: 'yellow' },
  { name: 'Fat', current: 45, target: 65, unit: 'g', color: 'red' },
])

const newEvent = ref({
  type: 'routine',
  title: '',
  date: '',
  time: '',
  description: '',
})

const newFood = ref({
  name: '',
  type: 'breakfast',
  time: '',
  calories: 0,
  description: '',
})

const currentMonthYear = computed(() => {
  return currentDate.value.toLocaleDateString('en-US', {
    month: 'long',
    year: 'numeric',
  })
})

const calendarDates = computed(() => {
  // currentDate ref의 값에 접근할 때 .value를 사용합니다.
  const year = currentDate.value.getFullYear()
  const month = currentDate.value.getMonth()
  const firstDay = new Date(year, month, 1)
  const lastDay = new Date(year, month + 1, 0)
  const startDate = new Date(firstDay)
  startDate.setDate(startDate.getDate() - firstDay.getDay())

  const dates = []
  const current = new Date(startDate) // 변수 이름 충돌 방지를 위해 다른 이름 사용

  for (let i = 0; i < 42; i++) {
    dates.push({
      day: current.getDate(),
      month: current.getMonth(),
      year: current.getFullYear(),
      isCurrentMonth: current.getMonth() === month,
      fullDate: new Date(current),
    })
    current.setDate(current.getDate() + 1)
  }

  return dates
})

const todaysMeals = computed(() => {
  const today = new Date().toISOString().split('T')[0]
  return meals.value.filter((meal) => meal.date === today)
})

const previousMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, 1)
}

const nextMonth = () => {
  // currentDate ref의 값을 변경할 때 .value를 사용합니다.
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 1)
}

const selectDate = (date) => {
  // newEvent 및 showEventModal ref의 값을 변경할 때 .value를 사용합니다.
  newEvent.value.date = `${date.year}-${String(date.month + 1).padStart(2, '0')}-${String(date.day).padStart(2, '0')}`
  showEventModal.value = true
}

const getEventsForDate = (date) => {
  const dateStr = `${date.year}-${String(date.month + 1).padStart(2, '0')}-${String(date.day).padStart(2, '0')}`
  return events.value.filter((event) => event.date === dateStr)
}

const getEventColorClass = (type) => {
  const colors = {
    meal: 'bg-blue-100 text-blue-800',
    routine: 'bg-green-100 text-green-800',
    exercise: 'bg-red-100 text-red-800',
    appointment: 'bg-purple-100 text-purple-800',
  }
  // 타입에 해당하는 클래스를 반환하거나, 없을 경우 기본값 반환
  return colors[type] || 'bg-gray-100 text-gray-800'
}

const getMealColorClass = (type) => {
  const colors = {
    breakfast: 'bg-blue-500',
    lunch: 'bg-green-500',
    dinner: 'bg-orange-500',
    snack: 'bg-purple-500',
  }
  // 타입에 해당하는 클래스를 반환하거나, 없을 경우 기본값 반환
  return colors[type] || 'bg-gray-500'
}

const closeEventModal = () => {
  showEventModal.value = false
  resetNewEvent()
}

const closeFoodModal = () => {
  showFoodModal.value = false
  resetNewFood()
}

const closeNutritionModal = () => {
  showNutritionModal.value = false
}

const resetNewEvent = () => {
  // newEvent ref의 값을 변경할 때 .value를 사용합니다.
  newEvent.value = {
    type: 'routine',
    title: '',
    date: '',
    time: '',
    description: '',
  }
}

const resetNewFood = () => {
  // newFood ref의 값을 변경할 때 .value를 사용합니다.
  newFood.value = {
    name: '',
    type: 'breakfast',
    time: '',
    calories: 0,
    description: '',
  }
}

const saveEvent = () => {
  const newEventData = {
    // 변수 이름 충돌 방지를 위해 newEventData 사용
    id: Date.now(),
    // newEvent ref의 값에 접근할 때 .value를 사용합니다.
    ...newEvent.value,
  }
  // events ref의 값(배열)에 접근할 때 .value를 사용하고 push 합니다.
  events.value.push(newEventData)
  // closeEventModal 함수 호출
  closeEventModal()
}

const saveFood = () => {
  const newMealData = {
    // 변수 이름 충돌 방지를 위해 newMealData 사용
    id: Date.now(),
    // newFood ref의 값에 접근할 때 .value를 사용합니다.
    name: newFood.value.name,
    type: newFood.value.type,
    time: newFood.value.time,
    description: newFood.value.description,
    calories: newFood.value.calories,
    date: new Date().toISOString().split('T')[0],
    completed: false,
  }
  // meals ref의 값(배열)에 접근할 때 .value를 사용하고 push 합니다.
  meals.value.push(newMealData)
  // closeFoodModal 함수 호출
  closeFoodModal()
}

const saveNutrition = () => {
  // nutritionProgress ref의 값(배열)에 접근할 때 .value를 사용합니다.
  nutritionProgress.value.forEach((nutrient) => {
    // nutrient 객체 자체는 반응형이므로 .value 없이 속성 접근
    nutrient.percentage = Math.round((nutrient.current / nutrient.target) * 100)
  })
  // closeNutritionModal 함수 호출
  closeNutritionModal()
}

// 감시자 선언
// 첫 번째 인자는 감시할 소스(ref, reactive, computed 등), 두 번째 인자는 콜백 함수
// 세 번째 인자는 옵션 객체 ({ deep: true, immediate: true } 등)
watch(
  // 감시할 소스: 값 (배열) 전체
  nutritionProgress,
  // 콜백 함수: newVal은 nutritionProgress.value의 새로운 값
  (newVal) => {
    newVal.forEach((nutrient) => {
      nutrient.percentage = Math.round((nutrient.current / nutrient.target) * 100)
    })
  },
  // 옵션: deep: true는 객체/배열 내부의 변화까지 감지
  { deep: true },
)

// --- mounted 훅 변환 --- 마운트 후 실행될 로직을 등록
onMounted(() => {
  console.log('Component mounted!')

  nutritionProgress.value.forEach((nutrient) => {
    // nutrient 객체 자체는 반응형이므로 .value 없이 속성 접근
    nutrient.percentage = Math.round((nutrient.current / nutrient.target) * 100)
  })
  newEvent.value.date = new Date().toISOString().split('T')[0]
})
</script>

<style>
.custom-bg {
  background-color: #3b82f6;
}
.custom-text {
  color: #3b82f6;
}
.custom-border {
  border-color: #3b82f6;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
}
.modal-content {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1001;
}
</style>
