import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Signin from '@/views/Signin.vue';
import Signup from '@/views/Signup.vue';
import Analysis from '@/views/Analysis.vue';
import Deposit from '@/views/Deposit.vue';
import Fund from '@/views/Fund.vue'; // Fund.vue 추가
import ISA from '@/views/ISA.vue'; // ISA.vue 추가

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin,
    meta: { hideHeaderFooter: true },
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
    meta: { hideHeaderFooter: true },
  },
  {
    path: '/analysis',
    name: 'analysis',
    component: Analysis,
  },
  {
    path: '/deposit', // Deposit 페이지 경로 추가
    name: 'Deposit',
    component: Deposit,
  },
  {
    path: '/fund', // Fund 페이지 경로 추가
    name: 'Fund',
    component: Fund,
  },
  {
    path: '/isa', // ISA 페이지 경로 추가
    name: 'ISA',
    component: ISA,
  },

  // 필요한 다른 라우트들을 여기에 추가할 수 있습니다.
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
