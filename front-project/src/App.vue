<template>
  <div id="app">
    <AppHeader v-if="!$route.meta.hideHeaderFooter" />
    <router-view id="mainContent" :class="{ 'no-padding': $route.meta.hidePadding }"></router-view>
    <AppFooter v-if="!$route.meta.hideHeaderFooter" />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppFooter from './components/AppFooter.vue';
import { useRouter } from "vue-router";
import { onMounted } from "vue";

export default {
  components: {
    AppHeader,
    AppFooter,
  },
  setup() {
    const router = useRouter();

    onMounted(() => {
      router.afterEach(() => {
        window.scrollTo(0, 0);
      });
    });
  },
};
</script>

<style>
/* 전역 스타일 적용 */
@import './assets/css/navbar.css';
@import './assets/css/theme.css';
@import './assets/css/theme.min.css';
@import './assets/css/style.bundle';

@font-face {
  font-family: 'NB';
  src: url('/public/fonts/NEXON Lv2 Gothic Bold.ttf') format('truetype');
}

@font-face {
  font-family: 'NL';
  src: url('/public/fonts/NEXON Lv2 Gothic Light.ttf') format('truetype');
}

@font-face {
  font-family: 'NM';
  src: url('/public/fonts/NEXON Lv2 Gothic Medium.ttf') format('truetype');
}

@font-face {
  /*레귤러체*/
  font-family: 'NR';
  src: url('/public/fonts/NEXON Lv2 Gothic.ttf') format('truetype');
}

@font-face {
  /*볼드체로만 사용*/
  font-family: 'BB';
  src: url('/public/fonts/Recipekorea 레코체 FONT.ttf') format('truetype');
}

@font-face {
  font-family: 'J1';
  src: url('/public/fonts/The Jamsil 1 Thin.ttf') format('truetype');
}

@font-face {
  font-family: 'J2';
  src: url('/public/fonts/The Jamsil 2 Light.ttf') format('truetype');
}

@font-face {
  font-family: 'J3';
  src: url('/public/fonts/The Jamsil 3 Regular.ttf') format('truetype');
}

@font-face {
  font-family: 'J4';
  src: url('/public/fonts/The Jamsil 4 Medium.ttf') format('truetype');
}

@font-face {
  font-family: 'J5';
  src: url('/public/fonts/The Jamsil 5 Bold.ttf') format('truetype');
}

@font-face {
  font-family: 'J6';
  src: url('/public/fonts/The Jamsil 6 ExtraBold.ttf') format('truetype');
}


#mainContent {
  padding-top: 80px;
}

.no-padding {
  padding-top: 0 !important;
}
</style>
