<template>
<!-- <div class="page-loading active">
      <div class="page-loading-inner">
        <div class="page-spinner"></div><span>Loading...</span>
      </div>
    </div> -->
  <header class="navbar navbar-expand-lg fixed-top bg-light">
    <div class="container">
     <router-link to="/" class="navbar-brand pe-sm-3">
        <span class="text-primary flex-shrink-0 me-2">
          <svg version="1.1" width="35" height="32" viewBox="0 0 36 33" xmlns="http://www.w3.org/2000/svg">
            <path fill="currentColor" d="M35.6,29c-1.1,3.4-5.4,4.4-7.9,1.9c-2.3-2.2-6.1-3.7-9.4-3.7c-3.1,0-7.5,1.8-10,4.1c-2.2,2-5.8,1.5-7.3-1.1c-1-1.8-1.2-4.1,0-6.2l0.6-1.1l0,0c0.6-0.7,4.4-5.2,12.5-5.7c0.5,1.8,2,3.1,3.9,3.1c2.2,0,4.1-1.9,4.1-4.2s-1.8-4.2-4.1-4.2c-2,0-3.6,1.4-4,3.3H7.7c-0.8,0-1.3-0.9-0.9-1.6l5.6-9.8c2.5-4.5,8.8-4.5,11.3,0L35.1,24C36,25.7,36.1,27.5,35.6,29z"></path>
          </svg>
        </span>Around
      </router-link>
      <button class="navbar-toggler ms-sm-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <nav class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav navbar-nav-scroll me-auto" style="--ar-scroll-height: 520px;">
          <li v-for="(item, index) in menuItems" :key="index" class="nav-item" :class="{ 'dropdown': item.subItems }">
            <a v-if="!item.subItems" class="nav-link" :class="{ 'active': item.active }" :href="item.href">{{ item.text }}</a>
            <template v-else>
              <a class="nav-link dropdown-toggle" :class="{ 'active': item.active }" href="#" data-bs-toggle="dropdown" :data-bs-auto-close="item.autoClose" aria-expanded="false">{{ item.text }}</a>
              <ul class="dropdown-menu" :class="{ 'overflow-hidden p-0': item.megaMenu }">
                <template v-if="item.megaMenu">
                  <div class="d-lg-flex">
                    <div v-for="(column, colIndex) in item.subItems" :key="colIndex" class="mega-dropdown-column pt-1 pt-lg-3 pb-lg-4">
                      <ul class="list-unstyled mb-0">
                        <li v-for="(subItem, subIndex) in column" :key="subIndex">
                          <a class="dropdown-item" :href="subItem.href">{{ subItem.text }}</a>
                          <span v-if="subItem.bgImage" class="mega-dropdown-column position-absolute top-0 end-0 h-100 bg-size-cover bg-repeat-0 rounded-3 rounded-start-0" :style="{ backgroundImage: `url(${subItem.bgImage})` }"></span>
                        </li>
                      </ul>
                    </div>
                    <div class="mega-dropdown-column position-relative border-start zindex-3"></div>
                  </div>
                </template>
                <template v-else>
                  <li v-for="(subItem, subIndex) in item.subItems" :key="subIndex" :class="{ 'dropdown': subItem.subItems }">
                    <template v-if="!subItem.subItems">
                      <a class="dropdown-item" :href="subItem.href">{{ subItem.text }}</a>
                    </template>
                    <template v-else>
                      <a class="dropdown-item dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">{{ subItem.text }}</a>
                      <ul class="dropdown-menu">
                        <li v-for="(grandChild, grandIndex) in subItem.subItems" :key="grandIndex">
                          <a class="dropdown-item" :href="grandChild.href">{{ grandChild.text }}</a>
                        </li>
                      </ul>
                    </template>
                  </li>
                </template>
              </ul>
            </template>
          </li>
        </ul>

        <!-- 로그인 버튼을 투자성향과 동일하게 변경 -->
        <ul class="navbar-nav">
          <li class="nav-item dropdown">
           <router-link class="dropdown-item" to="/signin">로그인</router-link>
            <ul class="dropdown-menu">
              <li>
                <a class="dropdown-item" href="#">내 계정</a>
              </li>
              <li>
                <router-link class="dropdown-item" to="/signup">회원가입</router-link>
              </li>
            </ul>
          </li>
        </ul>

        <div class="d-sm-none p-3 mt-n3">
          <a class="btn btn-primary w-100 mb-1" href="https://themes.getbootstrap.com/product/around-multipurpose-template-ui-kit/" target="_blank" rel="noopener">
            <i class="ai-cart fs-xl me-2 ms-n1"></i>Buy now
          </a>
        </div>
      </nav>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue';

const menuItems = ref([
  { text: '투자성향', href: '#', active: true, megaMenu: true, subItems: [
    [
      { text: '테스트하기', href: 'test-start' },
      { text: '공격투자형', href: 'index.html', bgImage: 'assets/img/megamenu/landings.jpg' },
      { text: '적극투자형', href: 'landing-mobile-app-showcase.html', bgImage: 'assets/img/megamenu/mobile-app.jpg' },
      { text: '위험중립형', href: 'index.html', bgImage: 'assets/img/megamenu/landings.jpg' },
      { text: '안정추구형', href: 'landing-mobile-app-showcase.html', bgImage: 'assets/img/megamenu/mobile-app.jpg' },
      { text: '안정형', href: 'landing-mobile-app-showcase.html', bgImage: 'assets/img/megamenu/mobile-app.jpg' },
    ]
  ]},
  { text: '금융상품', href: '#', subItems: [
    { text: '예금', href: '#', subItems: [
      { text: '~~예금', href: 'portfolio-list-v1.html' },
      { text: '~~예금', href: 'portfolio-list-v2.html' },
    ]},
    { text: '적금', href: '#', subItems: [
      { text: '~~~적금', href: 'shop-catalog.html' },
      { text: '~~~적금', href: 'shop-catalog.html' },
      { text: '~~~적금', href: 'shop-catalog.html' }
    ]},
    { text: '주식', href: '#', subItems: [
      { text: '국내주식', href: 'stock' },
    ]},
    { text: '펀드', href: '#', subItems: [
      { text: '~~~펀드', href: 'shop-catalog.html' },
      { text: '~~~펀드', href: 'shop-catalog.html' },
      { text: '~~~펀드', href: 'shop-catalog.html' }
    ]},
    { text: 'ISA', href: '#', subItems: [
      { text: '~~ISA', href: 'shop-catalog.html' },
      { text: '~~ISA', href: 'shop-catalog.html' },
      { text: '~~ISA', href: 'shop-catalog.html' }
    ]}
  ]},
  { text: '투자 커뮤니티', href: 'analysis' },
  { text: '용어사전', href: '#' },
  { text: '추가기능', href: '#', subItems: [
    { text: '금', href: '#', subItems: [
      { text: '금 시세', href: 'account-signin.html' },
      { text: '금투자', href: 'account-signup.html' },
    ]},
    { text: '환전', href: 'account-overview.html' },
    { text: '뉴스', href: 'news' },
  ]}
]);
</script>

<style lang="scss">
// @import '@/assets/scss/_utilities.scss';
// @import '@/assets/scss/components/_dropdown.scss';
// @import '@/assets/scss/components/_navbar.scss';
// @import '@/assets/css/navbar.css';
// @import "@/assets/scss/_position.scss";
// @import "@/assets/scss/_containers.scss";


// 부트스트랩 색상
.bg-primary.position-absolute.top-0.start-0.w-100.h-100 {
  background-color: #448C74 !important;
}

.text-primary.flex-shrink-0.me-2 {
  color: #448C74 !important;
}
.container-xxl {
    margin-top: 50px !important;
}

</style>

<style>
.header.navbar.navbar-expand-lg.fixed-top.bg-light{
  --ar-navbar-toggler-padding-y: 0.625rem;
    padding: 0.25rem;
}
a.nav-link {
    color: black;
}
</style>

<!-- <style lang="scss" scoped>
@import '~@/assets/css/navbar.css';

@import '~@/assets/scss/_utilities.scss';

</style> -->