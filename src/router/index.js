import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import Signin from '@/views/Signin.vue'
import Signup from '@/views/Signup.vue'
import Analysis from '@/views/Analysis.vue'
import Dictionary from "@/views/menu/dictionary/Dictionary.vue";
import Savings from "@/views/menu/savings/Savings.vue";
import Gold from "@/views/menu/gold/Gold.vue";
import SavingsDetail from "@/views/menu/savings/SavingsDetail.vue";
import GoldInvest from "@/views/menu/gold/GoldInvest.vue";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin,
    meta: { hideHeaderFooter: true }
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
    meta: { hideHeaderFooter: true }
  },
  {
    path: '/analysis',
    name: 'analysis',
    component: Analysis
  },
  {
    path: '/dictionary',
    name: 'dictionary',
    component: Dictionary
  },
  {
    path: '/savings',
    name: 'savings',
    component: Savings
  },
  {
    path: '/savingsDetail',
    name: 'savingsDetail',
    component: SavingsDetail
  },
  {
    path: '/gold',
    name: 'gold',
    component: Gold
  },
  {
    path: '/goldInvest',
    name: 'goldInvest',
    component: GoldInvest
  },

  // 필요한 다른 라우트들을 여기에 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router