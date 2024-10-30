<template>
  <div>
    <br><br><br><br>
    <h2>프로필 정보</h2><br><br>
    <div class="profileBox">
      <i class="d-inline ai-user"></i>
      <h3 class="d-inline">계정 정보</h3>
      <br><br>
      <table>
        <tbody>
        <tr>
          <td>ID</td>
          <td>{{ myPage.id }}</td>
        </tr>
        <tr>
          <td>이름</td>
          <td>{{ myPage.name }}</td>
        </tr>
        <tr>
          <td>이메일</td>
          <td>{{ myPage.email }}</td>
        </tr>
        <tr>
          <td>가입날짜</td>
          <td>{{ formatDate(myPage.createDate) }}</td> <!-- 날짜 포맷 함수 사용 -->
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
h1 {
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
}
.ai-user {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
table tbody tr td {
  font-size: 20px;
  padding: 10px;
}
table tbody tr td:nth-child(2) {
  color: #6c6c6c;
}
</style>

<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const myPage = ref({});
const loading = ref(true);
const token = JSON.parse(localStorage.getItem("auth")); // JSON 파싱

const fetchMyPage = async () => {
  loading.value = true;
  const id = token.id; // 직접 접근
  try {
    const response = await axios.get(`/api/member/${id}`);
    console.log(response);
    myPage.value = response.data;
  } catch (error) {
    console.error('적금 상품 목록을 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};

// 날짜 포맷 함수
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월을 두 자리로 포맷
  const day = String(date.getDate()).padStart(2, '0'); // 일을 두 자리로 포맷
  return `${year}-${month}-${day}`; // 'YYYY-MM-DD' 형식으로 반환
};

onMounted(() => {
  fetchMyPage();
});
</script>

<style scoped>
h1 {
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
}
.ai-user {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
table tbody tr td {
  font-size: 20px;
  padding: 10px;
}
table tbody tr td:nth-child(2) {
  color: #6c6c6c;
}
</style>


