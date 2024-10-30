<template>
  <div class="dic">
    <div class="d-lg-flex h-100 position-relative">
      <!-- Home button -->
      <router-link class="text-nav btn btn-icon bg-light border rounded-circle position-absolute top-0 end-0 p-0 mt-3 me-3 mt-sm-4 me-sm-4" to="/" data-bs-toggle="tooltip" data-bs-placement="left" title="Back to home">
        <i class="ai-home"></i>
      </router-link>

      <!-- Sign up form -->
      <div class="signUpPage d-flex flex-column align-items-center justify-content-center w-lg-50 px-3 h-100 px-lg-5 pt-5">
        <div class="w-100" style="max-width: 526px;">
          <h1 style="font-size: 40px; font-weight: 700;">회원가입</h1><br>
          <p class="pb-3 mb-3 mb-lg-4">
            이미 계정이 있으신가요?&nbsp;&nbsp;
            <router-link class="login" to="/auth/login">로그인</router-link>
          </p>
          <form class="needs-validation" @submit.prevent="join" novalidate>
            <div class="row row-cols-1 row-cols-sm-2">
              <div class="col mb-4">
                <input
                    v-model="member.id"
                    class="form-control form-control-lg ps-5"
                    type="text"
                    placeholder="아이디를 입력하세요"
                    required
                    @blur="checkId"
                >
                <span :class="disableSubmit ? 'text-primary' : 'text-danger'">{{ checkError }}</span>
              </div>
              <div class="col mb-4">
                <input v-model="member.name" class="form-control form-control-lg ps-5" type="text" placeholder="이름을 입력하세요" required>
              </div>
            </div>
            <div class="password-toggle mb-4">
              <input v-model="member.email" class="form-control form-control-lg ps-5" type="email" placeholder="이메일을 입력하세요" required @blur="checkEmail">
              <span :class="emailValid ? 'text-primary' : 'text-danger'">{{ emailError }}</span>
            </div>
            <div class="password-toggle mb-4">
              <input v-model="member.password" class="form-control form-control-lg ps-5" type="password" placeholder="비밀번호를 입력하세요" required @blur="checkPassword">
              <span :class="passwordValid ? 'text-primary' : 'text-danger'">{{ passwordError }}</span>
              <label class="password-toggle-btn" aria-label="Show/hide password">
                <input class="password-toggle-check" type="checkbox">
                <span class="password-toggle-indicator"></span>
              </label>
            </div>
            <div class="password-toggle mb-4">
              <input v-model="member.password2" class="form-control form-control-lg ps-5" type="password" placeholder="비밀번호를 확인하세요" required @blur="checkPasswordMatch">
              <span :class="passwordMatch ? 'text-primary' : 'text-danger'">{{ passwordMatchError }}</span>
              <label class="password-toggle-btn" aria-label="Show/hide password">
                <input class="password-toggle-check" type="checkbox">
                <span class="password-toggle-indicator"></span>
              </label>
            </div>
            <div class="pb-4">
              <div class="form-check my-2">
                <input v-model="agreeTerms" class="form-check-input" type="checkbox" id="terms">
                <label class="form-check-label ms-1" for="terms"><a href="#">이용약관</a>에 동의합니다</label>
              </div>
            </div>
            <button class="signUpBtn w-100 mb-4" type="submit">회원가입</button>
            <h2 style="font-size: 15px;font-weight: 700;" class="h6 text-center pt-3 pt-lg-4 mb-4">간편 로그인</h2>
            <div class="text-center">
              <a style="cursor: pointer;" @click.prevent="kakaoJoin">
                <img src="/img/kakao_join.png">
              </a>
            </div>
          </form>
        </div>
      </div>
      <!-- Cover image -->
      <signCoverImage></signCoverImage>
    </div>
  </div>

  <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="successModalLabel">축하합니다!</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
        </div>
        <div class="modal-body">
          회원가입이 성공적으로 완료되었습니다.
        </div>
        <div class="modal-footer">
          <button type="button" class="btn" @click="goToLogin">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import signCoverImage from "@/views/SignCoverImage.vue";
import authApi from '@/api/authApi';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // Bootstrap JS 가져오기

const router = useRouter();
const route = useRoute();
const checkError = ref('');
const emailError = ref('');
const passwordError = ref('');
const passwordMatchError = ref('');

const member = reactive({
  id: '',
  name: '',
  email: '',
  password: '',
  password2: '',
});

const disableSubmit = ref(false);
const emailValid = ref(false);
const passwordValid = ref(false);
const passwordMatch = ref(false);
const agreeTerms = ref(false); // 이용약관 동의 상태 추가

const successModal = ref(null);

onMounted(() => {
  const modalElement = document.getElementById('successModal');
  if (modalElement) {
    const bootstrap = require('bootstrap');
    successModal.value = new bootstrap.Modal(modalElement);
  }
});

const checkId = async () => {
  const idPattern = /^[a-zA-Z0-9]{5,20}$/;

  if (!member.id) {
    disableSubmit.value = false;
    checkError.value = '사용자 ID를 입력하세요.';
    return;
  }

  if (member.id.length < 5 || member.id.length > 20) {
    disableSubmit.value = false;
    checkError.value = '아이디는 5글자 이상 20글자 미만이어야 합니다.';
    return;
  }

  if (!idPattern.test(member.id)) {
    disableSubmit.value = false;
    checkError.value = '아이디는 영문자와 숫자만 사용할 수 있습니다.';
    return;
  }

  try {
    disableSubmit.value = await authApi.checkId(member.id);
    checkError.value = disableSubmit.value ? '사용 가능한 ID입니다.' : '이미 사용중인 ID입니다.';
    console.log(disableSubmit.value);
  } catch (error) {
    checkError.value = 'ID 중복 확인에 실패했습니다.';
    console.error('ID 중복 확인 오류:', error);
  }
};

const checkEmail = () => {
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  emailValid.value = emailPattern.test(member.email);
  emailError.value = emailValid.value ? '사용 가능한 이메일입니다.' : '유효한 이메일 형식을 입력하세요.';
};

const checkPassword = () => {
  const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$/;
  passwordValid.value = passwordPattern.test(member.password);
  passwordError.value = passwordValid.value ? '사용 가능한 비밀번호입니다.' : '비밀번호는 문자와 숫자를 포함하여 8자 이상이어야 합니다.';
};

const checkPasswordMatch = () => {
  passwordMatch.value = member.password === member.password2;
  passwordMatchError.value = passwordMatch.value ? '비밀번호가 일치합니다.' : '비밀번호가 일치하지 않습니다.';
};

const join = async () => {
  if (!disableSubmit.value || !passwordValid.value || !passwordMatch.value || !emailValid.value || !agreeTerms.value) {
    return alert('모든 필드를 올바르게 입력하세요.');
  }

  try {
    await authApi.create(member);
    if (successModal.value) {
      successModal.value.show();
    } else {
      // 모달이 초기화되지 않은 경우 기본 알림 사용
      alert('회원가입이 성공적으로 완료되었습니다.');
      router.push({name:'login', replace: true});
    }
  } catch (e) {
    console.error(e);
    alert('회원가입에 실패했습니다.');
  }
};

const goToLogin = () => {
  if (successModal.value) {
    successModal.value.hide();
  }
  router.push({ name: 'login', replace: true });
};

const kakaoJoin = () => {
  const kakaoUrl = 'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=f5c0ffbadcd4586df232b26623c1f227&redirect_uri=http://localhost:8081/auth/kakaojoin';
  window.location.href = kakaoUrl;
};
</script>

<style scoped>
.signUpBtn {
  width: 526px;
  height: 56px;
  border-radius: 30px;
  background-color: #37715d;
  color: white;
  font-size: 20px;
  border: none;
}

.signUpBtn:hover {
  border: 1px solid lightgrey;
  background-color: lightgrey;
  color: black;
}

.col.mb-4 input {
  width: 252px;
  height: 61px;
  border-radius: 20px;
}

.password-toggle.mb-4 input {
  width: 526px;
  height: 61px;
  border-radius: 20px;
}

.w-100 p {
  font-size: 15px;
}

.login {
  color: #37715d;
}

.form-check-label.ms-1 a {
  color: #37715d;
}

.form-check-label.ms-1 {
  font-size: 15px;
}

.dic {
  height: 100vh;
}

.signUpPage {
  margin: 0 auto;
  justify-content: center;
  padding: 20px;
}

.form-check input:checked {
  background-color: rgba(68, 140, 116, 1);
  border-color: rgba(68, 140, 116, 1);
}

/* 모달 너비 조정 (선택 사항) */
.modal-content {
  max-width: 600px;
  max-height: 400px;
  border-radius: 30px;
}

.modal{
  color: black;
  font-size: 20px;
}

.modal-body{
  padding: 20px;
}

.btn{
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

</style>

