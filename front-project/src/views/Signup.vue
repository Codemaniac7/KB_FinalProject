<template>
  <div class="dic">
    <div class="d-lg-flex h-100 position-relative">
      <!-- Home button -->
      <router-link
        class="text-nav btn btn-icon bg-light border rounded-circle position-absolute top-0 end-0 p-0 mt-3 me-3 mt-sm-4 me-sm-4"
        to="/" data-bs-toggle="tooltip" data-bs-placement="left" title="Back to home">
        <i class="ai-home"></i>
      </router-link>

      <!-- Sign up form -->
      <div class="signUpPage d-flex flex-column align-items-center justify-content-center w-lg-50 px-3 h-100 px-lg-5 pt-5">
        <div class="w-100" style="max-width: 526px;">
          <h1 style="font-size: 40px; font-weight: 700;">회원가입</h1><br>
          <p class="pb-3 mb-3 mb-lg-4">
            이미 계정이 있으신가요?&nbsp;&nbsp;
            <router-link class="login" to="/signin">로그인</router-link>
          </p>
          <form class="needs-validation" @submit.prevent="join" novalidate>
            <div class="row row-cols-1 row-cols-sm-2">
              <div class="col mb-4">
                <input v-model="member.name" class="form-control form-control-lg ps-5" type="text"
                  placeholder="이름을 입력하세요" required>
              </div>
              <div class="col mb-4">
                <input v-model="member.id" class="form-control form-control-lg ps-5" type="text"
                  placeholder="아이디를 입력하세요" required>
              </div>
            </div>
            <div class="password-toggle mb-4">
              <input v-model="member.email" class="form-control form-control-lg ps-5" type="email"
                placeholder="이메일을 입력하세요" required>
            </div>
            <div class="password-toggle mb-4">
              <input v-model="member.password" class="form-control form-control-lg ps-5" type="password"
                placeholder="비밀번호를 입력하세요" required>
              <label class="password-toggle-btn" aria-label="Show/hide password">
                <input class="password-toggle-check" type="checkbox">
                <span class="password-toggle-indicator"></span>
              </label>
            </div>
            <div class="password-toggle mb-4">
              <input v-model="member.password2" class="form-control form-control-lg ps-5" type="password"
                placeholder="비밀번호를 확인하세요" required>
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
            <div class="row row-cols-1 row-cols-sm-2 gy-3">
              <div class="col"><a class="btn btn-icon btn-outline-secondary btn-google btn-lg w-100" href="#"><i
                    class="ai-google fs-xl me-2"></i>Google</a></div>
              <div class="col"><a class="btn btn-icon btn-outline-secondary btn-facebook btn-lg w-100" href="#"><i
                    class="ai-facebook fs-xl me-2"></i>Facebook</a></div>
              <div class="col"><a class="btn btn-icon btn-outline-secondary btn-kakao btn-lg w-100" @click="loginWithKakao">
                  <img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
                    alt="Kakao Login" />카카오로 로그인
                </a>
              </div>
            </div>
          </form>
        </div>
      </div>
      <!-- Cover image -->
      <signCoverImage></signCoverImage>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import signCoverImage from "@/views/SignCoverImage.vue";
import authApi from '@/api/authApi';

const router = useRouter();
const checkError = ref('');
const user = ref(null);

//////////////////////////////////////////////////////////
const member = reactive({
  id: '',
  name: '',
  email: '',
  password: '',
  password2: '',
});
//////////////////////////////////////////////////////////
const disableSubmit = ref(true);

///////////////////////////카카오/////////////////////////////////
// 카카오 SDK 로드 함수
const loadKakaoSDK = () => {
  return new Promise((resolve, reject) => {
    if (window.Kakao) {
      resolve(window.Kakao);
    } else {
      const script = document.createElement('script');
      script.src = 'https://developers.kakao.com/sdk/js/kakao.js';
      script.onload = () => {
        if (window.Kakao) {
          resolve(window.Kakao);
        } else {
          reject('Kakao SDK 로드 실패');
        }
      };
      script.onerror = () => reject('Kakao SDK 로드 실패');
      document.head.appendChild(script);
    }
  });
};

// 카카오 로그인 처리
const loginWithKakao = async () => {
  try {
    const Kakao = await loadKakaoSDK();
    if (!Kakao.isInitialized()) {
      Kakao.init('YOUR_KAKAO_JAVASCRIPT_KEY');  // 카카오 JavaScript 키
    }

    Kakao.Auth.login({
      success: async (authObj) => {
        const code = authObj.access_token;  // 카카오 로그인 성공 시 얻는 토큰
        await fetchKakaoUserInfo(code);     // 사용자 정보 가져오기
      },
      fail: (err) => {
        console.error('카카오 로그인 실패:', err);
      },
    });
  } catch (error) {
    console.error('Kakao SDK 초기화 실패:', error);
  }
};

// 서버로부터 카카오 사용자 정보 가져오기
const fetchKakaoUserInfo = async (code) => {
  try {
    const kakaoUser = await authApi.getKakaoInfo(code);  // authApi.js의 getKakaoInfo 사용
    user.value = kakaoUser;
    console.log('카카오 사용자 정보:', user.value);

    // 회원가입 처리 또는 로그인 처리 후 페이지 이동
    await handleUserRegistration(kakaoUser);
  } catch (error) {
    console.error('카카오 사용자 정보 가져오기 실패:', error);
  }
};

// 카카오 사용자 정보로 회원가입 처리
const handleUserRegistration = async (kakaoUser) => {
  try {
    // 카카오 사용자 정보를 기반으로 회원가입을 처리합니다.
    const member = {
      id: kakaoUser.kakaoId,  // 카카오 ID
      name: kakaoUser.name,   // 카카오 사용자 이름
      email: kakaoUser.email, // 카카오 사용자 이메일
      password: 'kakaoLoginPassword',  // 비밀번호는 카카오 로그인 시 필요 없으므로 임의로 설정
      kakaoId: kakaoUser.kakaoId
    };

    await authApi.create(member);  // 회원가입 요청
    console.log('회원가입 완료');
    router.push({ name: 'home' }); // 회원가입 또는 로그인 완료 후 홈으로 이동
  } catch (error) {
    console.error('회원가입 처리 실패:', error);
  }
};

// 컴포넌트가 마운트될 때 카카오 SDK 로드
onMounted(async () => {
  try {
    await loadKakaoSDK();
    console.log('Kakao SDK 로드 완료');
  } catch (error) {
    console.error('Kakao SDK 로드 실패:', error);
  }
});
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
</style>
