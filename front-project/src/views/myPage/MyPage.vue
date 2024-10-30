<script setup>
import { ref } from 'vue';
import MyPagePanel from './MyPagePanel.vue';
import MyPageContent from './MyPageContent.vue';
import MyPageSettings from './MyPageSetting.vue';
import MyPagePosts from './MyPagePosts.vue';
import MyPageWithdraw from './MyPageWithdraw.vue';
import MyPageWarning from "@/views/myPage/MyPageWarning.vue";

const selectedPage = ref('profile'); // 초기 페이지를 사용자 정보로 설정
const showWarning = ref(false); // 경고창 표시 여부

const handlePageUpdate = (page) => {
  if (page === 'settings') {
    showWarning.value = true; // 설정 페이지로 이동 시 경고창 표시
  } else {
    showWarning.value = false; // 다른 페이지로 이동 시 경고창 숨김
    selectedPage.value = page; // 선택된 페이지 업데이트
  }
};

const handlePasswordSuccess = () => {
  // 비밀번호 확인 성공 시, 설정 페이지로 이동
  selectedPage.value = 'settings';
  showWarning.value = false; // 경고창 숨기기
};
</script>

<template>
  <div class="bc">
    <br><br>
    <div class="container">
      <MyPagePanel @update-page="handlePageUpdate" />
      <div class="content">
        <MyPageWarning v-if="showWarning" @password-success="handlePasswordSuccess" />
        <MyPageSettings v-if="selectedPage === 'settings' && !showWarning" />
        <MyPageContent v-if="selectedPage === 'profile' && !showWarning" />
        <MyPagePosts v-if="selectedPage === 'posts' && !showWarning" />
        <MyPageWithdraw v-if="selectedPage === 'withdraw' && !showWarning" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.bc {
  background-color: rgba(247, 249, 252, 1);
}

.container {
  display: flex;
  width: 60%;
}

.content {
  flex: 1;
  padding: 20px;
}
</style>