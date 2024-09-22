<template>
  <div id="app">
    <!-- AppHeader와 AppFooter를 라우터의 meta 정보에 따라 조건부 렌더링 -->
    <AppHeader v-if="!$route.meta.hideHeaderFooter" />
    <router-view></router-view>
    <AppFooter v-if="!$route.meta.hideHeaderFooter" />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppFooter from './components/AppFooter.vue';

export default {
  components: {
    AppHeader,
    AppFooter,
  },
};
</script>

<style>
/* 전역 스타일 적용 */
@import './assets/css/navbar.css';
@import './assets/css/theme.css';
@import './assets/css/theme.min.css';
@import './assets/css/style.bundle';

</style>
