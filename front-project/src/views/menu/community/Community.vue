<template>
  <div class="content fs-6 d-flex flex-column flex-column-fluid" id="kt_content">
    <div class="post container fs-6 d-flex flex-column-fluid" id="kt_post">
      <div class="container-xxl d-flex">
        <!-- 왼쪽에 Community Panel -->
        <div class="col-xl-3">
          <CommunityPanel />
        </div>

        <!-- 중앙에 CommunityWriter와 포스트 목록을 포함하는 스크롤 컨테이너 -->
        <div class="col-xl-6 d-flex flex-column">
          <!-- 스크롤 가능한 컨테이너 -->
          <div class="scrollable-container flex-grow-1 overflow-auto">
            <!-- CommunityWriter 페이지에 접근 권한 전달 -->
            <CommunityWriter :isAuthorized="isAuthorized" />

            <!-- 포스트 목록을 렌더링하는 router-view -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
    <!-- 푸터가 여기에 위치 -->
    <Footer />
  </div>
</template>

<script setup>

import { useRouter, useRoute } from 'vue-router';
import CommunityPanel from "@/views/menu/community/CommunityPanel.vue";
import CommunityWriter from "@/views/menu/community/CommunityWriter.vue";


// Router 인스턴스
const router = useRouter();
const route = useRoute();

</script>

<style scoped>
.container-xxl {
  display: flex;
  align-content: center;
  justify-content: space-between;
  gap: 30px;
  width: 90%;
}

.content{
  background-color: rgba(249, 249, 249, 1);
  align-content: center;
}

/* 패널 크기 */
.col-xl-3 {
  width: 280px; /* CommunityPanel의 고정 너비 */
}

/* 메인 콘텐츠 크기 */
.col-xl-6 {
  flex-grow: 1; /* 나머지 공간을 차지하게 설정 */
  height: 100vh;
}
.scrollable-container {
  /* 기존 스타일 유지 */
  flex-grow: 1;
  overflow-y: auto; /* 세로 스크롤 가능 */
  padding: 10px; /* 필요시 패딩 추가 */
  display: flex;
  flex-direction: column;

  /* 스크롤바 숨기기 */
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* Internet Explorer 10+ */
}

.scrollable-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}
</style>
