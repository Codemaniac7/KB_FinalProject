<template>
  <div class="bic">
    <div class="d-lg-flex position-relative h-100">
      <!-- Home button-->
      <a class="text-nav btn btn-icon bg-light border rounded-circle position-absolute top-0 end-0 p-0 mt-3 me-3 mt-sm-4 me-sm-4" href="/" data-bs-toggle="tooltip" data-bs-placement="left" title="Back to home">
        <i class="ai-home"></i>
      </a>
      <!-- Sign in form-->
      <div class="signInPage d-flex flex-column align-items-center justify-content-center w-lg-50 px-3 px-lg-5">
        <div class="w-100" style="max-width: 526px;">
          <h1 style="font-size: 40px; font-weight: 700;">로그인</h1>
          <br>
          <p class="pb-3 mb-3 mb-lg-4">
            아직 계정이 없으신가요? <router-link class="signUp" to="/signup">회원가입</router-link>
          </p>
          <form @submit.prevent="login" class="needs-validation" novalidate>
            <div class="pb-3 mb-3">
              <div class="emailCheck position-relative">
                <input type="text" class="form-control form-control-lg ps-5" placeholder="사용자 ID" v-model="member.id" required />
              </div>
            </div>
            <div class="mb-4">
              <div class="position-relative">
                <div class="password-toggle">
                  <input type="password" class="form-control form-control-lg ps-5" placeholder="비밀번호" v-model="member.password" required />
                  <label class="password-toggle-btn" aria-label="Show/hide password">
                    <input type="checkbox" class="password-toggle-check" />
                    <span class="password-toggle-indicator"></span>
                  </label>
                </div>
              </div>
            </div>
            <div v-if="error" class="text-danger">{{ error }}</div>
            <div class="d-flex flex-wrap align-items-center justify-content-between pb-4">
              <div class="form-check my-1">
                <input class="form-check-input" type="checkbox" id="keep-signedin" />
                <label class="form-check-label ms-1" for="keep-signedin">로그인 유지</label>
              </div>
              <router-link class="passwordFound fs-sm fw-semibold text-decoration-none my-1" to="/forgot-password">비밀번호 찾기</router-link>
            </div>
            <button class="signInBtn w-100 mb-4" type="submit" :disabled="disableSubmit">
              로그인
            </button>
            <h2 style="font-size: 15px;font-weight: 700;" class="h6 text-center pt-3 pt-lg-4 mb-4">간편 로그인</h2>
            <div class="row row-cols-1 row-cols-sm-2 gy-3">
              <div class="col">
                <a class="btn btn-icon btn-outline-secondary btn-google btn-lg w-100" href="#"><i class="ai-google fs-xl me-2"></i>Google</a>
              </div>
              <div class="col">
                <a class="btn btn-icon btn-outline-secondary btn-facebook btn-lg w-100" href="#"><i class="ai-facebook fs-xl me-2"></i>Facebook</a>
              </div>
            </div>
          </form>
        </div>
      </div>
      <!-- Cover image-->
      <signCoverImage></signCoverImage>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth'; // auth store를 가져옵니다.
import signCoverImage from "@/views/SignCoverImage.vue"; // 필요 시 import

const router = useRouter();
const auth = useAuthStore();

const member = reactive({
  id: '',
  password: '',
});

const error = ref('');

const disableSubmit = computed(() => !(member.id && member.password));

const login = async () => {
  console.log(member);
  try {
    await auth.login(member);
    router.push('/dashboard'); // 로그인 성공 시 대시보드로 이동
  } catch (e) {
    console.log('에러=======', e);
    error.value = e.response.data || '로그인 실패'; // 에러 메시지를 설정합니다.
  }
};
</script>

<style scoped>
.signInBtn {
  width: 526px;
  height: 56px;
  border-radius: 30px;
  background-color: #37715d;
  color: white;
  font-size: 20px;
  border: none;
}

.signInBtn:hover {
  border: 1px solid lightgrey;
  background-color: lightgrey;
  color: black;
}

.passwordFound {
  color: #37715d;
  font-size: 16px;
}

.password-toggle input {
  width: 526px;
  height: 61px;
  border-radius: 20px;
}

.emailCheck input {
  width: 526px;
  height: 61px;
  border-radius: 20px;
}

.w-100 p {
  font-size: 15px;
}

.signUp {
  color: #37715d;
}

.form-check-label.ms-1 a {
  color: #37715d;
}

.form-check-label.ms-1 {
  font-size: 15px;
}

.bic {
  height: 100vh;
}

.signInPage {
  margin: 0 auto;
  justify-content: center;
  height: 100vh; /* 100% height */
}

.form-check input:checked {
  background-color: rgba(68, 140, 116, 1);
  border-color: rgba(68, 140, 116, 1);
}
</style>
