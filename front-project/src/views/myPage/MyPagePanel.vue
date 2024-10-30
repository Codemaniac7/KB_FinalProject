<script setup>
import { ref,onMounted,defineEmits } from 'vue';

// eslint-disable-next-line vue/valid-define-emits
const emit = defineEmits();

const selectedMenu = ref('');

const navigateTo = (page) => {
  selectedMenu.value = page;
  emit('update-page', page);
};
import axios from "axios";

const avatar = ref();
const myPage = ref([]);
const loading = ref(true);
const token = JSON.parse(localStorage.getItem("auth")); // JSON 파싱

const fetchMyPage = async () => {
  loading.value = true;
  const id = token.id; //
  try {
    const response = await axios.get(`/api/member/${id}`);
    console.log(response);
    myPage.value = response.data;
  } catch (error) {
    console.error('마이페이지 오류:', error);
  } finally {
    loading.value = false;
  }
};
const fetchMyAvatar = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/member/{id}');
    console.log(response);
    avatar.value = response.data.file;
  } catch (error) {
    console.error('아바타 가져오기 오류:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchMyPage();
  fetchMyAvatar();
});

</script>

<template>
  <div class="my-page-panel">
    <div class="nameBox">
      <img class="d-block profile" :src="avatar">
      <h3 class="name">{{  myPage.name }}</h3>
      <h3 class="nim">님</h3><br><br>
      <p class="email">{{ myPage.email }}</p>
    </div>
    <br><br>
    <div class="panel">
      <h4>마이 페이지</h4><br>
    </div>
    <div class="i">
      <div
          @click="navigateTo('profile')"
          :class="{ active: selectedMenu === 'profile' }"
      >
        <i class="ai-user-check d-inline"></i><h3 class="d-inline"> 프로필 정보</h3>
      </div>
      <div
          @click="navigateTo('settings')"
          :class="{ active: selectedMenu === 'settings' }"
      >
        <i class="ai-settings d-inline"></i><h3 class="d-inline"> 설정</h3>
      </div>
      <div
          @click="navigateTo('posts')"
          :class="{ active: selectedMenu === 'posts' }"
      >
        <i class="ai-file-text d-inline"></i><h3 class="d-inline"> 내가 쓴 글</h3>
      </div>
      <br><br><br><br><br>
      <div
          @click="navigateTo('withdraw')"
          :class="{ active: selectedMenu === 'withdraw' }"
      >
        <i class="ai-logout d-inline"></i><h3 class="d-inline"> 회원 탈퇴</h3>
      </div>
    </div>
  </div>
</template>

<style scoped>
.my-page-panel {
  width: 200px;
  background-color: rgba(247, 249, 252, 1);
  padding: 80px 20px 20px;
}

.profile {
  border-radius: 50%;
  width: 50%;
}

.name {
  font-size: 30px;
  display: inline-block;
}

.nim {
  color: #919090;
  font-size: 30px;
  display: inline-block;
}

.email {
  font-size: 15px;
  font-weight: 100;
  margin-top: -20px;
}

.panel h4 {
  color: #919090;
}

.i {
  font-size: 27px;
  vertical-align: text-bottom;
}

.i div {
  padding-bottom: 10px;
  cursor: pointer;
  transition: color 0.3s;
}

.i div:hover {
  color: rgba(68, 140, 116, 1);
}

.i div.active {
  color: green;
}
</style>
