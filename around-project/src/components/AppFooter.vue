<template>
  <footer class="footer py-5">
    <div class="container pt-md-2 pt-lg-3 pt-xl-4">
      <div class="row pb-5 pt-sm-2 mb-lg-2">
        <!-- Company Info Column -->
        <div class="col-md-4 col-lg-3 pb-2 pb-md-0 mb-4 mb-md-0">
          <a class="navbar-brand py-0 mb-3 mb-md-4" href="index.html">
            <span class="text-primary flex-shrink-0 me-2">
              <svg version="1.1" width="35" height="32" viewBox="0 0 36 33" xmlns="http://www.w3.org/2000/svg">
                <path fill="currentColor" d="M35.6,29c-1.1,3.4-5.4,4.4-7.9,1.9c-2.3-2.2-6.1-3.7-9.4-3.7c-3.1,0-7.5,1.8-10,4.1c-2.2,2-5.8,1.5-7.3-1.1c-1-1.8-1.2-4.1,0-6.2l0.6-1.1l0,0c0.6-0.7,4.4-5.2,12.5-5.7c0.5,1.8,2,3.1,3.9,3.1c2.2,0,4.1-1.9,4.1-4.2s-1.8-4.2-4.1-4.2c-2,0-3.6,1.4-4,3.3H7.7c-0.8,0-1.3-0.9-0.9-1.6l5.6-9.8c2.5-4.5,8.8-4.5,11.3,0L35.1,24C36,25.7,36.1,27.5,35.6,29z"></path>
              </svg>
            </span>
            <span class="text-nav">Around</span>
          </a>
          <p class="fs-sm pb-2 pb-md-3 mb-3">
            Tellus non diam morbi quam vel venenatis proin sed. Dolor elementum nunc dictum interdum amet arcu aenean eu integer
          </p>
          <div class="d-flex">
            <a v-for="(social, index) in socials" :key="index" :class="['btn btn-icon btn-sm btn-secondary rounded-circle', social.class]" :href="social.href">
              <i :class="social.icon"></i>
            </a>
          </div>
        </div>
        
        <!-- Links and App Install Column -->
        <div class="col-md-8 col-lg-7 col-xl-6 offset-lg-2 offset-xl-3">
          <div class="row row-cols-1 row-cols-sm-3">
            <div v-for="(section, index) in footerSections" :key="index" class="col mb-4 mb-md-0">
              <h4 class="h6 fw-bold pb-lg-1">{{ section.title }}</h4>
              <ul class="nav flex-column">
                <li v-for="(link, linkIndex) in section.links" :key="linkIndex">
                  <a class="nav-link fw-normal py-1 px-0" :href="link.href">{{ link.text }}</a>
                </li>
              </ul>
            </div>
            
            <!-- App Install Section -->
            <div class="col">
              <h4 class="h6 fw-bold pb-2 mb-0 mb-lg-1">Install App</h4>
              <a v-for="(app, appIndex) in appStores" :key="appIndex" class="btn btn-secondary px-3 py-2 mt-3 me-3 me-md-0" :href="app.href">
                <img class="mx-1 d-dark-mode-none" :src="app.darkImage" :width="app.width" :alt="app.name">
                <img class="mx-1 d-none d-dark-mode-block" :src="app.lightImage" :width="app.width" :alt="app.name">
              </a>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Copyright -->
      <p class="fs-sm mb-0">
        <span class="opacity-70">&copy; All rights reserved. Made by </span>
        <a class="nav-link d-inline fw-normal p-0" href="https://createx.studio/" target="_blank" rel="noopener">Createx Studio</a>
      </p>
    </div>
  </footer>
</template>

<script setup>
import { ref } from 'vue';

const socials = ref([
  { class: 'btn-facebook me-3', href: '#', icon: 'ai-facebook' },
  { class: 'btn-instagram me-3', href: '#', icon: 'ai-instagram' },
  { class: 'btn-linkedin', href: '#', icon: 'ai-linkedin' }
]);

const footerSections = ref([
  {
    title: 'Company',
    links: [
      { text: 'Features', href: '#' },
      { text: 'Reviews', href: '#' },
      { text: 'How it works', href: '#' },
      { text: 'Pricing', href: '#' }
    ]
  },
  {
    title: 'Support',
    links: [
      { text: 'Help center', href: '#' },
      { text: 'Terms of service', href: '#' },
      { text: 'Legal', href: '#' },
      { text: 'Privacy policy', href: '#' }
    ]
  }
]);

const appStores = ref([
  {
    name: 'App Store',
    href: '#',
    darkImage: 'assets/img/market/appstore-dark.svg',
    lightImage: 'assets/img/market/appstore-light.svg',
    width: 120
  },
  {
    name: 'Google Play',
    href: '#',
    darkImage: 'assets/img/market/googleplay-dark.svg',
    lightImage: 'assets/img/market/googleplay-light.svg',
    width: 119
  }
]);
</script>