import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import Home from '@/views/Home.vue';
import Signin from '@/views/Signin.vue';
import Signup from '@/views/Signup.vue';
import NewsPage from '@/views/NewsPage.vue';
import TestStartPage from '@/views/menu/analysis/TestStartPage.vue';
import TestProcessPage from '@/views/menu/analysis/TestProcessPage.vue';
import TestResultPage from '@/views/menu/analysis/TestResultPage.vue';
import ShowType from '@/views/menu/analysis/ShowType.vue';
import Dictionary from '@/views/menu/dictionary/Dictionary.vue';
import Savings from '@/views/menu/savings/Savings.vue';
import SavingsDetail from '@/views/menu/savings/SavingsDetail.vue';
import StockMain from '@/views/StockMain.vue';
import StockDetail from '@/views/StockDetail.vue';
import StockCategory from '@/views/StockCategory.vue';
import StockChart from '@/views/StockChart.vue';
import Gold from '@/views/menu/gold/Gold.vue';
import GoldInvestmentPage from '@/views/menu/gold/GoldInvestmentPage.vue';
import Calculator from '@/views/modal/Calculator.vue';
import Deposit from '@/views/menu/desposit/Deposit.vue';
import Fund from '@/views/menu/fund/Fund.vue';
import ISA from '@/views/ISA.vue';
import FundDetail from '@/views/menu/fund/FundDetail.vue';
import MyPage from '@/views/myPage/MyPage.vue';
import MyPageContent from '@/views/myPage/MyPageContent.vue';
import MyPagePanel from '@/views/myPage/MyPagePanel.vue';
import MyPageSetting from '@/views/myPage/MyPageSetting.vue';
import MyPageWithdraw from '@/views/myPage/MyPageWithdraw.vue';
import MyPagePosts from '@/views/myPage/MyPagePosts.vue';
import MyPageWarning from '@/views/myPage/MyPageWarning.vue';
import DepositDetail from '@/views/menu/desposit/DepositDetail.vue';
import SignCoverImage from '@/views/SignCoverImage.vue';
import ActiveInvestment from '@/views/menu/community/ActiveInvestment.vue';
import AggressiveInvestment from '@/views/menu/community/AggressiveInvestment.vue';
import Stability from '@/views/menu/community/Stability.vue';
import Neutral from '@/views/menu/community/Neutral.vue';
import Community from '@/views/menu/community/Community.vue';
import LoginPage from '@/views/auth/LoginPage.vue';
import LoginPageForKakao from '@/views/auth/LoginPageForKakao.vue';
import JoinPage from '@/views/auth/JoinPage.vue';
import JoinPageForKakao from '@/views/auth/JoinPageForKakao.vue';
import PrivacyPage from '@/views/PrivacyPage.vue';
import CommunityPrivacy from '@/views/menu/community/CommunityPrivacy.vue';
import FundTheme from '@/views/menu/fund/FundTheme.vue';
import CurrencyExchange from '@/views/menu/currencyExchange/CurrencyExchange.vue';

const routes = [
  {
    path: '/stock/:stockCode',
    name: 'StockChart',
    component: StockChart,
    props: (route) => ({
      stockCode: route.params.stockCode,
      stockName: route.query.stockName,
      currentPrice: route.query.currentPrice,
      priceChange: route.query.priceChange,
      priceChangePct: route.query.priceChangePct,
      volume: route.query.volume,
      htsAvls: route.query.htsAvls, // HTS 시가총액 추가
      w52Hgpr: route.query.w52Hgpr, // 52주일 최고가 추가
      w52Lwpr: route.query.w52Lwpr, // 52주일 최저가 추가
    }),
  },
  {
    path: '/stockcategory',
    name: 'StockCategory',
    component: StockCategory,
  },
  {
    path: '/currencyExchange',
    name: 'currencyExchange',
    component: CurrencyExchange,
  },
  {
    path: '/communityPrivacy',
    name: 'communityPrivacy',
    component: CommunityPrivacy,
  },
  {
    path: '/privacyPage',
    name: 'privacyPage',
    component: PrivacyPage,
  },
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin,
    meta: { hideHeaderFooter: true, hidePadding: true, requiresGuest: true },
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
    meta: { hideHeaderFooter: true, hidePadding: true, requiresGuest: true },
  },
  {
    path: '/news',
    name: 'NewsPage',
    component: NewsPage,
  },
  {
    path: '/test-start',
    name: 'TestStartPage',
    component: TestStartPage,
  },
  {
    path: '/test-process',
    name: 'TestProcessPage',
    component: TestProcessPage,
  },
  {
    path: '/test-result',
    name: 'TestResultPage',
    component: TestResultPage,
  },
  {
    path: '/show-type',
    name: 'ShowType',
    component: ShowType,
  },
  {
    path: '/stockMain',
    name: 'StockMain',
    component: StockMain,
  },
  {
    path: '/stockdetail',
    name: 'StockDetail',
    component: StockDetail,
  },
  {
    path: '/dictionary',
    name: 'dictionary',
    component: Dictionary,
    props: (route) => ({ searchQuery: route.query.search || '' }),
  },
  {
    path: '/savings',
    name: 'savings',
    component: Savings,
  },
  {
    path: '/saving/:savingId',
    name: 'savingDetail',
    component: SavingsDetail,
  },
  {
    path: '/auth/login',
    name: 'login',
    component: LoginPage,
    meta: { hideHeaderFooter: true, hidePadding: true, requiresGuest: true },
  },
  {
    path: '/auth/kakaologin',
    name: 'kakaologin',
    component: LoginPageForKakao,
    meta: { hideHeaderFooter: true, hidePadding: true, requiresGuest: true },
  },
  {
    path: '/auth/join',
    name: 'join',
    component: JoinPage,
    meta: { hideHeaderFooter: true, hidePadding: true, requiresGuest: true },
  },
  {
    path: '/auth/kakaojoin',
    name: 'kakaojoin',
    component: JoinPageForKakao,
    meta: { hideHeaderFooter: true, hidePadding: true, requiresGuest: true },
  },

  {
    path: '/gold',
    name: 'gold',
    component: Gold,
  },
  {
    path: '/goldInvestmentPage',
    name: 'goldInvestmentPage',
    component: GoldInvestmentPage,
  },
  {
    path: '/calculator',
    name: 'calculator',
    component: Calculator,
  },
  {
    path: '/deposit', // Deposit 페이지 경로 추가
    name: 'deposit',
    component: Deposit,
  },
  {
    path: '/deposit/:savingId', // Deposit 페이지 경로 추가
    name: 'depositDetail',
    component: DepositDetail,
  },
  {
    path: '/funds', // Fund 페이지 경로 추가
    name: 'funds',
    component: Fund,
  },
  {
    path: '/fundTheme', // Fund 페이지 경로 추가
    name: 'fundTheme',
    component: FundTheme,
  },
  {
    path: '/fundDetail', // Fund 페이지 경로 추가
    name: 'fundDetail',
    component: FundDetail,
  },
  {
    path: '/isa', // ISA 페이지 경로 추가
    name: 'ISA',
    component: ISA,
  },
  {
    path: '/myPage',
    name: 'myPage',
    component: MyPage,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPageContent',
    name: 'myPageContent',
    component: MyPageContent,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPagePanel',
    name: 'myPagePanel',
    component: MyPagePanel,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPageSetting',
    name: 'myPageSetting',
    component: MyPageSetting,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPageWithdraw',
    name: 'myPageWithdraw',
    component: MyPageWithdraw,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPagePosts',
    name: 'myPagePosts',
    component: MyPagePosts,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPageWarning',
    name: 'myPageWarning',
    component: MyPageWarning,
    meta: { requiresAuth: true },
  },
  {
    path: '/signCoverImage',
    name: 'signCoverImage',
    component: SignCoverImage,
    meta: { hideHeaderFooter: true },
  },
  {
    path: '/community',
    component: Community,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'stability',
        name: 'stability',
        component: Stability,
        props: true,
      },
      {
        path: 'neutral',
        name: 'neutral',
        component: Neutral,
        props: true,
      },
      {
        path: 'activeInvestment',
        name: 'activeInvestment',
        component: ActiveInvestment,
        props: true,
      },
      {
        path: 'aggressiveInvestment',
        name: 'aggressiveInvestment',
        component: AggressiveInvestment,
        props: true,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  const isLogin = authStore.isLogin;

  if (to.meta.requiresAuth && !isLogin) {
    next({ name: 'login' });
  } else if (to.meta.requiresGuest && isLogin) {
    next({ name: 'Home' });
  } else {
    next();
  }
});

export default router;
