import { createRouter, createWebHistory } from 'vue-router'
import DietRecords from '../pages/DietRecords.vue'
import FoodSearch from '../pages/FoodSearch.vue'
import FoodGramInput from '../pages/FoodGramInput.vue'
import FoodAdd from '../pages/FoodAdd.vue'

const routes = [
  {
    path: '/',
    name: 'DietRecords',
    component: DietRecords
  },
  {
    path: '/search/:meal/:date',
    name: 'FoodSearch',
    component: FoodSearch,
    props: true
  },
  {
    path: '/gram/:meal/:date/:foodId',
    name: 'FoodGramInput',
    component: FoodGramInput,
    props: true
  },
  {
    path: '/add/:meal/:date',
    name: 'FoodAdd',
    component: FoodAdd,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
