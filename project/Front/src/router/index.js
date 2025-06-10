import { createRouter, createWebHistory } from 'vue-router'
import FoodView from '../views/FoodView.vue'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/SearchFood',
      name: 'SearchFood',
      component: FoodView,
    },
  ],
})

export default router
