import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import Vue3Lottie from 'vue3-lottie';

// import './assets/styles/main.scss'

// CSS 파일들
import 'bootstrap/dist/css/bootstrap.min.css';
import 'aos/dist/aos.css';
import 'lightgallery/css/lightgallery.css';
import 'lightgallery/css/lg-zoom.css';
import 'lightgallery/css/lg-video.css';

// JS 파일들
import 'bootstrap';
import SmoothScroll from 'smooth-scroll';
import 'img-comparison-slider';
import AOS from 'aos';
import lightGallery from 'lightgallery';

// 플러그인 초기화
new SmoothScroll('a[href*="#"]');
AOS.init();

// Vue 인스턴스 생성 및 마운트
const app = createApp(App);

// lightGallery를 전역적으로 사용할 수 있도록 설정
app.config.globalProperties.$lightGallery = lightGallery;
app.use(createPinia());
app.use(Vue3Lottie);

app.config.globalProperties.$formatNumber = (value) => {
    if (!value) return '0'
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
  }

createApp(App).use(router).mount('#app')

