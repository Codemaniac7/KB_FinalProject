<template>
  <header class="appHeader navbar navbar-expand-lg fixed-top bg-light">
    <div class="container">
      <router-link to="/" class="navbar-brand pe-sm-3">
        <span class="text-primary flex-shrink-0 me-2">
        </span><img class="invetiLogo" src="/img/inveti.png">
      </router-link>
      <button class="navbar-toggler ms-sm-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <nav class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav navbar-nav-scroll me-auto" style="--ar-scroll-height: 520px;">
          <li v-for="(item, index) in menuItems" :key="index" class="nav-item" :class="{ dropdown: item.subItems }"
            @mouseenter="hoverDropdown(index, true)" @mouseleave="hoverDropdown(index, false)">
            <a style="font-size:18px;" v-if="!item.subItems" class="nav-link" :class="{ active: item.active }"
              :href="item.href">{{ item.text
              }}</a>
            <template v-else>
              <a style="font-size:18px;" class="nav-link dropdown-toggle" :class="{ active: item.active }" href="#"
                data-bs-toggle="dropdown" :data-bs-auto-close="item.autoClose" aria-expanded="false">
                {{ item.text }}
                <i :class="dropdownStates[index] ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
              </a>
              <ul class="dropdown-menu" :class="{ 'overflow-hidden p-0': item.megaMenu }">
                <template v-if="item.megaMenu">
                  <div class="d-lg-flex">
                    <div v-for="(column, colIndex) in item.subItems" :key="colIndex"
                      class="mega-dropdown-column pt-1 pt-lg-3 pb-lg-4">
                      <ul class="list-unstyled mb-0">
                        <li v-for="(subItem, subIndex) in column" :key="subIndex">
                          <a style="font-size:17px;" class="dropdown-item" :href="subItem.href">{{
                            subItem.text
                            }}</a>
                          <span v-if="subItem.bgImage"
                            class="mega-dropdown-column position-absolute top-0 end-0 h-100 bg-size-cover bg-repeat-0 rounded-3 rounded-start-0"
                            :style="{
                              backgroundImage: `url(${subItem.bgImage})`,
                            }"></span>
                        </li>
                      </ul>
                    </div>
                    <div class="mega-dropdown-column position-relative border-start zindex-3"></div>
                  </div>
                </template>
                <template v-else>
                  <li v-for="(subItem, subIndex) in item.subItems" :key="subIndex"
                    :class="{ dropdown: subItem.subItems }">
                    <template v-if="!subItem.subItems">
                      <a style="font-size:17px;" class="dropdown-item" :href="subItem.href">{{
                        subItem.text
                        }}</a>
                    </template>
                    <template v-else>
                      <a style="font-size:17px;" class="dropdown-item dropdown-toggle" href="#"
                        data-bs-toggle="dropdown" aria-expanded="false">{{ subItem.text }}</a>
                      <ul class="dropdown-menu" style=";">
                        <li v-for="(grandChild, grandIndex) in subItem.subItems" :key="grandIndex">
                          <a style="font-size:17px;" class="dropdown-item" :href="grandChild.href">{{
                            grandChild.text
                            }}</a>
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
          <template v-if="islogin">
            <li id="headerLoginBtn" class="nav-item dropdown" @mouseenter="hoverLoginDropdown(true)"
              @mouseleave="hoverLoginDropdown(false)">

              <router-link style="font-size:17px;" class="dropdown-item" to="/myPage">
                {{ props.username }} <i :class="loginDropdownState ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
              </router-link>
              <ul class="dropdown-menu" v-show="loginDropdownState">
                <li>
                  <router-link style="font-size:17px;" class="dropdown-item" to="/mypage">마이페이지</router-link>
                </li>
                <li>
                  <a style="font-size:17px;" class="dropdown-item" @click.prevent="logout">로그아웃</a>
                </li>
              </ul>
            </li>
          </template>
          <template v-else>
            <li id="headerLoginBtn" class="nav-item dropdown" @mouseenter="hoverLoginDropdown(true)"
              @mouseleave="hoverLoginDropdown(false)">
              <router-link style="font-size:17px;" class="dropdown-item" to="/auth/login">
                로그인 <i :class="loginDropdownState ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
              </router-link>
              <ul class="dropdown-menu" v-show="loginDropdownState">
                <li>
                  <a style="font-size:17px;" class="dropdown-item" href="#">내 계정</a>
                </li>
                <li>
                  <router-link style="font-size:17px;" class="dropdown-item" to="/auth/join">회원가입</router-link>
                </li>
              </ul>
            </li>
          </template>
        </ul>

        <div class="d-sm-none p-3 mt-n3">
          <a class="btn btn-primary w-100 mb-1"
            href="https://themes.getbootstrap.com/product/around-multipurpose-template-ui-kit/" target="_blank"
            rel="noopener">
            <i class="ai-cart fs-xl me-2 ms-n1"></i>Buy now
          </a>
        </div>
      </nav>
    </div>
  </header>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';

const auth = useAuthStore();
const islogin = computed(() => auth.isLogin);
const id = computed(() => auth.id);
const props = defineProps({ username: String });

const avatar = `/api/member/${props.username}/avatar`;

const store = useAuthStore();
const router = useRouter();

const logout = (e) => {
  // 로그아웃
  store.logout();
  router.push('/');
};

const menuItems = ref([
  {
    text: '투자성향',
    href: '#',
    subItems: [
      { text: '테스트하기', href: '/test-start' },
      {
        text: '투자성향 보기', href: '/show-type'
      },
    ],
  },
  {
    text: '투자상품',
    href: '#',
    subItems: [
      {
        text: '주식',
        href: '#',
        subItems: [
          { text: '전체 주식', href: '/StockMain' },
          { text: '투자 성향별 주식', href: '/StockDetail' },
        ],
      },
      {
        text: '펀드',
        href: '#',
        subItems: [
          { text: '펀드 찾기', href: '/funds' },
          { text: '테마별 펀드', href: '/fundTheme' },
        ],
      },
      {
        text: '예금',
        href: '/deposit',
      },
      {
        text: '적금',
        href: '/savings',
      },
      {
        text: 'ISA',
        href: '/isa',
      },
    ],
  },
  { text: '투자 커뮤니티', href: '/community' },
  { text: '용어사전', href: '/dictionary' },
  {
    text: '추가기능',
    href: '#',
    subItems: [
      {
        text: '금',
        href: '/gold',
        subItems: [
          { text: '금 시세', href: '/gold' },
          { text: '금투자', href: '/goldInvestmentPage' },
        ],
      },
      { text: '환전', href: '/currencyExchange' },
      { text: '뉴스', href: '/news' },
      { text: '마이페이지', href: '/myPage' },
    ],
  },
]);

const dropdownStates = ref(menuItems.value.map(() => false));
const loginDropdownState = ref(false);
const hoverDropdown = (index, isHovering) => {
  dropdownStates.value[index] = isHovering;
};

const hoverLoginDropdown = (state) => {
  loginDropdownState.value = state;
};
</script>

<style lang="scss">
// @import '@/assets/scss/_utilities.scss';
// @import '@/assets/scss/components/_dropdown.scss';
// @import '@/assets/scss/components/_navbar.scss';
// @import '@/assets/css/navbar.css';
// @import "@/assets/scss/_position.scss";
// @import "@/assets/scss/_containers.scss";
.row.justify-content-center.text-center.pt-md-2.pt-xl-2.pb-5.mb-md-2 {
  height: 600px;
}

// 부트스트랩 색상
.bg-primary.position-absolute.top-0.start-0.w-100.h-100 {
  background-color: #448c74 !important;
}

.text-primary.flex-shrink-0.me-2 {
  color: #448c74 !important;
}

.container-xxl {
  margin-top: 80px !important;

}
</style>

<style scoped>
.navbar-expand-lg .dropdown:hover>.dropdown-menu {
  border-radius: 15px;
}

.header.navbar.navbar-expand-lg.fixed-top.bg-light {
  --ar-navbar-toggler-padding-y: 0.625rem;
  padding: 0.25rem;
}

a.nav-link {
  color: black;
}

.appHeader {
  height: 80px;
}

/* 드롭다운 메뉴 애니메이션 및 화살표 회전 */
.appHeader a.nav-link.dropdown-toggle {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.appHeader a.nav-link i {
  transition: transform 0.6s ease;
}

/* 아이콘이 부드럽게 회전하도록 설정 */
.appHeader a.nav-link.dropdown-toggle[aria-expanded="true"] i {
  transform: rotate(180deg);
}

/* 기타 스타일 */
.header.navbar.navbar-expand-lg.fixed-top.bg-light {
  --ar-navbar-toggler-padding-y: 0.625rem;
}

#headerLoginBtn {
  width: 100px;
  height: 40px;
  border-radius: 10px;
  border: 1px solid lightgrey;
  background-color: rgba(67, 140, 116, 1);
  text-align: center;
  color: white;
  padding: 5px;
}

#headerLoginBtn:hover {
  color: black;
  background-color: lightgrey;
}

.invetiLogo {
  width: 100px;
}

.dropdown-menu {
  --bs-dropdown-link-active-bg: none;
}

.dropdown-item .dropdown-toggle {
  color: white;
}

.nav-item {
  font-family: J3;
}
.avatar {
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: inline-block;
  cursor: pointer;
  text-align: center;
}
</style>

<!-- <style lang="scss" scoped>
@import '~@/assets/css/navbar.css';

@import '~@/assets/scss/_utilities.scss';

</style> -->
