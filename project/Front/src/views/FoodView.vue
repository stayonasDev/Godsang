<template>
  <div class="min-h-screen">
    <main class="container mx-auto px-4 py-6 max-w-6xl">
      <div class="flex justify-center mb-6">
        <div class="inline-flex bg-gray-100 rounded-full p-1">
          <button
            class="bg-primary text-white px-4 py-2 rounded-full whitespace-nowrap !rounded-button"
          >
            Recent
          </button>
          <button class="text-gray-600 px-4 py-2 rounded-full whitespace-nowrap !rounded-button">
            Favorites
          </button>
          <button class="text-gray-600 px-4 py-2 rounded-full whitespace-nowrap !rounded-button">
            All Foods
          </button>
          <button class="text-gray-600 px-4 py-2 rounded-full whitespace-nowrap !rounded-button">
            Breakfast
          </button>
          <button class="text-gray-600 px-4 py-2 rounded-full whitespace-nowrap !rounded-button">
            Lunch
          </button>
          <button class="text-gray-600 px-4 py-2 rounded-full whitespace-nowrap !rounded-button">
            Dinner
          </button>
          <button class="text-gray-600 px-4 py-2 rounded-full whitespace-nowrap !rounded-button">
            Snacks
          </button>
        </div>
      </div>
      <div class="relative mb-6">
        <div class="flex items-center bg-white rounded-lg shadow-sm">
          <div class="w-10 h-10 flex items-center justify-center text-gray-500">
            <i class="ri-search-line text-lg"></i>
          </div>
          <input
            type="text"
            placeholder="Search foods, meals or nutrients..."
            class="w-full h-10 text-sm bg-transparent border-none focus:ring-0 text-gray-700"
            v-model="searchTerm"
            @keyup.enter="handleSearchClick"
          /><!-- Enter 키 입력 시 검색 실행 -->
          <!-- searchTerm 상태와 입력 필드 값 연결 -->
          <button
            class="px-4 h-10 bg-primary text-white rounded-r-lg !rounded-button whitespace-nowrap"
            @click="handleSearchClick"
            :disabled="isLoading"
          >
            {{ isLoading ? '검색 중........' : '검색' }}
          </button>

          <!-- 로딩 상태  표시 -->
          <p v-if="isLoading">검색 결과를 불러오는 중입니다...</p>
          <!-- 에러 상태 표시-->
          <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
        </div>
      </div>

      <!-- 검색 결과 -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="bg-white p-6 rounded-lg shadow-sm">
          <div v-if="searchResults.length > 0">
            <h2 class="text-xl font-semibold mb-4" v-if="searchResults.length > 0">
              {{ foodTitle }}
              <!-- TODO 고정 값으로 만들기-->
            </h2>
            <div class="space-y-4" v-for="item in searchResults" :key="item.id">
              <div class="flex items-center p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition">
                <img
                  src="https://readdy.ai/api/search-image?query=grilled%20chicken%20breast%20on%20a%20white%20plate%2C%20professional%20food%20photography%2C%20high%20quality%2C%20detailed%20texture&width=80&height=80&seq=1&orientation=squarish"
                  alt="Grilled Chicken Breast"
                  class="w-16 h-16 rounded-lg object-cover object-top"
                />
                <div class="ml-4 flex-1">
                  <h3 class="font-medium">{{ item.name }}</h3>
                  <p class="text-sm text-gray-600">
                    {{ item.kal }} kal · Carb {{ item.carb }}g · Protein {{ item.protein }}g · Fat
                    {{ item.pat }}g
                  </p>
                </div>
                <button
                  class="w-8 h-8 flex items-center justify-center bg-gray-200 hover:bg-primary hover:text-white rounded-full transition"
                  @click="viewDetails(item)"
                >
                  <i class="ri-add-line"></i>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-white p-6 rounded-lg shadow-sm">
          <div v-if="selectedFood">
            <h2 class="text-xl font-semibold mb-2">Nutritional Information</h2>
            <h3 class="text-lg font-medium mb-1">{{ calculatedNutrition.name }}</h3>
            <!-- <p class="text-sm text-gray-500 mb-6">Per 100g serving</p> -->
            <div class="grid grid-cols-4 gap-4 mb-8">
              <div class="text-center">
                <p class="text-2xl font-bold text-primary">{{ calculatedNutrition.kal }}</p>
                <p class="text-sm text-gray-500">calories</p>
              </div>
              <div class="text-center">
                <p class="text-2xl font-bold text-primary">{{ calculatedNutrition.carb }}</p>
                <p class="text-sm text-gray-500">carbs</p>
              </div>
              <div class="text-center">
                <p class="text-2xl font-bold text-primary">{{ calculatedNutrition.protein }}</p>
                <p class="text-sm text-gray-500">protein</p>
              </div>
              <div class="text-center">
                <p class="text-2xl font-bold text-primary">{{ calculatedNutrition.pat }}</p>
                <p class="text-sm text-gray-500">fat</p>
              </div>
            </div>
            <div class="mb-6">
              <label class="block text-sm font-medium text-gray-700 mb-2">Serving Size</label>
              <div class="flex items-center">
                <button
                  class="w-8 h-8 flex items-center justify-center bg-gray-200 hover:bg-gray-300 rounded-l-lg !rounded-button"
                  @click="decrementServing"
                >
                  <i class="ri-subtract-line"></i>
                </button>
                <input
                  type="number"
                  v-model.number="currentServingSize"
                  class="w-16 h-8 text-center border-none bg-gray-100"
                  min="1"
                />
                <button
                  class="w-8 h-8 flex items-center justify-center bg-gray-200 hover:bg-gray-300 rounded-r-lg !rounded-button"
                  @click="incrementServing"
                >
                  <i class="ri-add-line"></i>
                </button>
                <div class="relative ml-2">
                  <select
                    class="appearance-none bg-gray-100 border-none rounded-lg py-2 pl-3 pr-8 text-gray-700"
                  >
                    <option>g</option>
                    <!-- <option>oz</option>
                  <option>serving</option> -->
                  </select>
                  <div
                    class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none"
                  >
                    <i class="ri-arrow-down-s-line"></i>
                  </div>
                </div>
              </div>
            </div>
            <div class="space-y-3 mb-6">
              <div class="flex justify-between items-center">
                <span class="text-gray-700">Saturated Fat</span>
                <span class="font-medium">{{ calculatedNutrition.saturatedFat }}g</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-700">TransFat</span>
                <span class="font-medium">{{ calculatedNutrition.transFat }}mg</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-700">polyFat</span>
                <span class="font-medium">{{ calculatedNutrition.polyFat }}mg</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-700">unsaturatedFat</span>
                <span class="font-medium">{{ calculatedNutrition.unsaturatedFat }}mg</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-700">Cholesterol</span>
                <span class="font-medium">{{ calculatedNutrition.cholest }}mg</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-700">Sodium</span>
                <span class="font-medium">{{ calculatedNutrition.sodium }}mg</span>
              </div>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Add to Meal</label>
              <div class="relative">
                <select
                  class="w-full appearance-none bg-white border border-gray-300 rounded-lg py-2 px-3 text-gray-700 !rounded-button"
                  v-model="selectedMealType"
                >
                  <option>DINNER</option>
                  <option>BREAKFAST</option>
                  <option>LUNCH</option>
                </select>
                <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none">
                  <i class="ri-arrow-down-s-line"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
    <footer
      class="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 py-3 px-6 flex justify-between items-center"
    >
      <div class="flex items-center gap-4">
        <button
          class="w-8 h-8 flex items-center justify-center bg-gray-100 rounded-full"
          @click="prevPage"
        >
          <i class="ri-arrow-left-s-line"></i>
        </button>
        <span class="bg-primary bg-opacity-10 text-primary px-3 py-1 rounded-full text-sm">
          {{ currentPage }} / {{ totalPages }}
        </span>
        <button
          class="w-8 h-8 flex items-center justify-center bg-gray-100 rounded-full"
          @click="nextPage"
        >
          <i class="ri-arrow-right-s-line"></i>
        </button>
      </div>
      <div class="flex items-center gap-4">
        <button class="w-8 h-8 flex items-center justify-center bg-gray-100 rounded-full">
          <i class="ri-fullscreen-line"></i>
        </button>
        <button
          class="flex items-center gap-1 bg-primary text-white px-4 py-2 rounded-lg !rounded-button"
          @click="addToMeal"
          :disabled="!selectedFood || currentServingSize <= 0 || isLoading"
        >
          <i class="ri-download-line"></i>
          <span>Export</span>
        </button>
        <button
          class="flex items-center gap-1 bg-white border border-gray-300 px-4 py-2 rounded-lg !rounded-button"
        >
          <i class="ri-share-line"></i>
          <span>Share</span>
        </button>
        <button class="w-8 h-8 flex items-center justify-center bg-gray-100 rounded-full">
          <i class="ri-sun-line"></i>
        </button>
        <button class="w-8 h-8 flex items-center justify-center bg-gray-100 rounded-full">
          <i class="ri-layout-grid-line"></i>
        </button>
        <button
          class="flex items-center gap-1 bg-white border border-gray-300 px-4 py-2 rounded-lg !rounded-button"
        >
          <i class="ri-code-line"></i>
          <span>Code</span>
        </button>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue' // 상태 관리와 데이터 변화 감지를 위해 ref, watch import
import axios from 'axios'

const foodTitle = ref('')
const searchTerm = ref('')
const searchResults = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(0)
const isLoading = ref(false)
const errorMessage = ref(' ')
//선택된 음식 항목을 저장할 상태
const selectedFood = ref(null)
//현재 상세 정보를 표시할 음식의 서빙 사이즈
const currentServingSize = ref(0)
//식사 타입
const selectedMealType = ref('DINNER')

// 계산된 영양 성분 값을 저장할 상태
const calculatedNutrition = ref({
  kal: 0,
  carb: 0,
  protein: 0,
  pat: 0,
  saturatedFat: 0,
  transFat: 0,
  polyFat: 0,
  unsaturatedFat: 0,
  sodium: 0,
  cholest: 0,
  sugar: 0,
})

//배포 환경에서는 Nginx/ALB의 URL + API 경로 사용
const API_BASE_URL = 'http://localhost:8080/godsang/food'

//검색결과 비동기적으로 가져옴
const fetchSearchResults = async () => {
  if (!searchTerm.value) {
    searchResults.value = []
    totalPages.value = 0
    errorMessage.value = ' '
    isLoading.value = false
    selectedFood.value = null
    currentServingSize.value = 0
    foodTitle.value = ''
    return
  }

  isLoading.value = true
  errorMessage.value = ' '
  selectedFood.value = null
  currentServingSize.value = 0
  console.log(
    `검색 요청: "${searchTerm.value}", 페이지: ${currentPage.value}, 크기: ${pageSize.value}`,
  )

  try {
    const response = await axios.get(API_BASE_URL + '/foodList', {
      params: {
        foodName: searchTerm.value,
        page: currentPage.value,
        size: pageSize.value,
      },
    })

    console.log('API 응답 : ', response.data)

    searchResults.value = response.data.content || []
    totalPages.value = response.data.totalPages || 0
    foodTitle.value = searchTerm.value
  } catch (error) {
    console.error('API 호출 중 에러 발생: ', error)
    errorMessage.value = '검색 결과를 가져오는 데 실패했습니다.'
    searchResults.value = []
    totalPages.value = 0
    executedSearchTerm.value = ''
  } finally {
    isLoading.value = false
  }
}

// 이벤트 핸들러 함수
const handleSearchClick = () => {
  if (currentPage.value === 1) {
  } else {
    currentPage.value = 1
  }
  fetchSearchResults()
}

//이전 페이지
const prevPage = () => {
  // ★ 현재 페이지가 1보다 클 때만 이동 가능 (1이 최소 페이지)
  if (currentPage.value > 1) {
    currentPage.value-- // 페이지 번호 감소
    fetchSearchResults()
  }
}

const nextPage = () => {
  // ★ 현재 페이지가 전체 페이지 수보다 작을 때만 이동 가능
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    fetchSearchResults()
  }
}

const viewDetails = (foodItem) => {
  console.log('상세 정보 클릭:', foodItem)
  selectedFood.value = foodItem
  //상세 정보 열릴 때 서빙 사이즈를 해당 음식의 기본 amount로 초기화
  currentServingSize.value = foodItem.amount || 100 // amount가 없으면 기본 100g
}

//서빙 사이즈 조절
const decrementServing = () => {
  if (currentServingSize.value > 1) {
    currentServingSize.value--
  }
}

const incrementServing = () => {
  currentServingSize.value++
}

//식사 추가 버튼 클릭 시
const addToMeal = async () => {
  if (!selectedFood.value || currentServingSize.value <= 0) {
    console.warn('식사 추가 오류')
    return
  }

  const mealEntryData = {
    memberId: 2, //로그인 기능 아직 완성되지 않아 2번으로 고정
    date: new Date().toISOString().split('T')[0], // 현재 날짜 YYYY-MM-DD 형식
    mealType: selectedMealType.value,
    items: [
      {
        foodId: selectedFood.value.id,
        gram: currentServingSize.value,
      },
    ],
  }

  console.log('식사 추가 데이터 전송: ', mealEntryData)

  try {
    const response = await axios.post(API_BASE_URL + '/intakes', mealEntryData)

    console.log('식사 추가 API 응답: ', response.data)

    alert('식사 추가 성공')
    // close 기능 추가 ?
  } catch (error) {
    console.error('식사 추가 API 호출 중 에러 발생', error)
    if (error.response) {
      console.error('응답 에러 상태: ', error.response.status)
      console.error('응답 에러 데이터: ', error.response.data)
    } else if (error.request) {
      console.error('요청 에러: ', error.request)
    } else {
      console.error('기타 에러: ', error.message)
    }
    alert('식사 추가에 실패했습니다. 다시 시도해주세요')
  }
}

const updateCalculatedNutrition = () => {
  console.log('updateCalculatedNutrition 함수 실행')

  if (!selectedFood.value || currentServingSize.value <= 0) {
    calculatedNutrition.value = {
      kal: 0,
      carb: 0,
      protein: 0,
      pat: 0,
      saturatedFat: 0,
      transFat: 0,
      polyFat: 0,
      unsaturatedFat: 0,
      sodium: 0,
      cholest: 0,
      sugar: 0,
    }
    return
  }

  const baseAmount = selectedFood.value.amount || 100
  const scaleFactor = currentServingSize.value / baseAmount

  calculatedNutrition.value = {
    kal: (selectedFood.value.kal * scaleFactor).toFixed(1),
    carb: (selectedFood.value.carb * scaleFactor).toFixed(1),
    protein: (selectedFood.value.protein * scaleFactor).toFixed(1),
    pat: (selectedFood.value.pat * scaleFactor).toFixed(1),
    saturatedFat: (selectedFood.value.saturatedFat * scaleFactor).toFixed(1),
    transFat: (selectedFood.value.transFat * scaleFactor).toFixed(1),
    polyFat: (selectedFood.value.polyFat * scaleFactor).toFixed(1),
    unsaturatedFat: (selectedFood.value.unsaturatedFat * scaleFactor).toFixed(1),
    sodium: (selectedFood.value.sodium * scaleFactor).toFixed(1),
    cholest: (selectedFood.value.cholest * scaleFactor).toFixed(1),
    sugar: (selectedFood.value.sugar * scaleFactor).toFixed(1),
  }
}

// ★ currentServingSize 또는 selectedFood가 변경될 때 영양 성분 계산 함수 호출
// computed 대신 이 watch가 계산 로직을 트리거합니다.
watch(
  [selectedFood, currentServingSize],
  () => {
    console.log('Watch: selectedFood 또는 currentServingSize 변경 감지! -> 계산 함수 호출')
    updateCalculatedNutrition() // 계산 함수 호출
  },
  { immediate: true },
) // 컴포넌트 마운트 시 초기값으로 한번 실행되도록 설정

//selectedFood와 currentServingSize가 변경될 때마다 자동으로 다시 계산
// const scaledNutrition = computed(() => {
//   console.log('실행 되는 중')
//   if (!selectedFood.value || currentServingSize.value <= 0) {
//     return {
//       kal: 0,
//       carb: 0,
//       protein: 0,
//       pat: 0,
//       saturatedFat: 0,
//       transFat: 0,
//       polyFat: 0,
//       unsaturatedFat: 0,
//       sodium: 0,
//       cholest: 0,
//       sugar: 0,
//     }
//   }

//   const baseAmount = selectedFood.value.amount || 100
//   const scaleFactor = currentServingSize.value / baseAmount // 현재 서빙 사이즈와 기준량의 비율

//   // toFixed(n)을 사용하여 소수점 n자리까지 반올림
//   return {
//     kal: (selectedFood.value.kal * scaleFactor).toFixed(1),
//     carb: (selectedFood.value.carb * scaleFactor).toFixed(1),
//     protein: (selectedFood.value.protein * scaleFactor).toFixed(1),
//     pat: (selectedFood.value.pat * scaleFactor).toFixed(1),
//     saturatedFat: (selectedFood.value.saturatedFat * scaleFactor).toFixed(1),
//     transFat: (selectedFood.value.transFat * scaleFactor).toFixed(1),
//     polyFat: (selectedFood.value.polyFat * scaleFactor).toFixed(1),
//     unsaturatedFat: (selectedFood.value.unsaturatedFat * scaleFactor).toFixed(1),
//     sodium: (selectedFood.value.sodium * scaleFactor).toFixed(1),
//     cholest: (selectedFood.value.cholest * scaleFactor).toFixed(1),
//     sugar: (selectedFood.value.sugar * scaleFactor).toFixed(1),
//   }
// })
// ★ currentServingSize 상태 변화를 감지하는 watch 추가
watch(currentServingSize, (newValue, oldValue) => {
  console.log('Watch: currentServingSize 변경 감지!', {
    oldValue: oldValue,
    newValue: newValue,
    type: typeof newValue, // 값의 타입 확인
  })
})

// --- 감시자(Watcher) ---
// 검색어(searchTerm)가 변경될 때마다 페이지 번호를 초기화하고 바로 검색 실행 (선택 사항)
// 만약 검색 버튼 클릭 시에만 검색하려면 이 watch 블록을 제거합니다.
// watch(searchTerm, (newValue, oldValue) => {
//   if (newValue !== oldValue) {
//     currentPage.value = 0; // 검색어 변경 시 페이지 초기화
//     // debounce/throttle 적용하여 너무 잦은 검색 방지 가능
//     fetchSearchResults(); // 변경된 검색어로 검색 실행
//   }
// });

// // --- 감시자(Watcher) ---
// // currentPage 값이 변경될 때마다 자동으로 검색 실행 (페이지네이션 클릭 시)
// // 검색 버튼 클릭 시 페이지를 1로 초기화할 때도 이 watch가 트리거됩니다.
// // handleSearchClick에서 page를 1로 변경했을 때 fetchSearchResults를 직접 호출하는 대신 watch에 맡깁니다.
// watch(currentPage, (newPage, oldPage) => {
//     // 초기 로딩 시 (oldPage가 정의되지 않았을 때) 또는 페이지 변경 시
//     if (newPage !== oldPage || (oldPage === undefined && newPage === 1 && searchTerm.value)) {
//         console.log(`페이지 변경 감지: ${oldPage} -> ${newPage}. 검색 실행.`);
//         // 검색어가 있을 때만 페이지 변경에 따른 검색 실행
//         if (searchTerm.value) {
//              fetchSearchResults();
//         }
//     }
// }, { immediate: true }); // 컴포넌트 마운트 시 초기값(1)으로 한번 실행되도록 설정 (선택 사항)

// // 검색어(searchTerm)가 변경될 때마다 페이지 번호를 초기화 (검색어 입력 중)
// // 실제 검색은 검색 버튼 클릭 시 또는 필요하다면 여기에 debounce/throttle을 추가하여 자동 검색
// watch(searchTerm, (newValue, oldValue) => {
//     if (newValue !== oldValue) {
//         // 검색어 변경 시 페이지를 1로 초기화
//         currentPage.value = 1;
//         // 만약 검색어 입력 시 자동으로 검색하고 싶다면 여기에 debounce/throttle 적용 후 fetchSearchResults 호출
//     }
// });

// selectedFood가 변경될 때 currentServingSize 초기화
watch(selectedFood, (newItem) => {
  if (newItem) {
    // 선택된 항목의 amount 값으로 서빙 사이즈 초기화, amount가 없으면 100g으로 설정
    currentServingSize.value = newItem.amount || 100
    selectedMealType.value = 'DINNER' // 새로운 음식 선택 시 식사 타입 기본값으로 초기화
  }
})
</script>

<style>
:where([class^='ri-'])::before {
  content: '\f3c2';
}
body {
  background-color: #f9fafb;
  color: black;
}
input:focus {
  outline: none;
}
input[type='number']::-webkit-inner-spin-button,
input[type='number']::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
.error-message {
  color: red;
  margin-top: 10px;
}
</style>
