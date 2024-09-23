import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import Signin from '@/views/Signin.vue'
import Signup from '@/views/Signup.vue'
import Analysis from '@/views/Analysis.vue'
import NewsPage from '@/views/NewsPage.vue'
import TestStartPage from '@/views/TestStartPage.vue'
import TestProcessPage from '@/views/TestProcessPage.vue'
import TestResultPage from '@/views/TestResultPage.vue'
import StockPage from '@/views/StockPage.vue'

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
    path: '/news',
    name: 'NewsPage',
    component: NewsPage
  },
  {
    path: '/test-start',
    name: 'TestStartPage',
    component: TestStartPage
  },
  {
    path: '/test-process',
    name: 'TestProcessPage',
    component: TestProcessPage
  },
  {
    path: '/test-result',
    name: 'TestResultPage',
    component: TestResultPage
  },
  {
    path: '/stock',
    name: 'StockPage',
    component: StockPage
  },
  // 필요한 다른 라우트들을 여기에 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router