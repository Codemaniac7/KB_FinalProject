<template>
  <div class="d-lg-flex position-relative h-100">
    <!-- Home button -->
    <router-link class="text-nav btn btn-icon bg-light border rounded-circle position-absolute top-0 end-0 p-0 mt-3 me-3 mt-sm-4 me-sm-4" to="/" data-bs-toggle="tooltip" data-bs-placement="left" title="Back to home">
      <i class="ai-home"></i>
    </router-link>
    
    <!-- Sign up form -->
    <div class="d-flex flex-column align-items-center w-lg-50 h-100 px-3 px-lg-5 pt-5">
      <div class="w-100 mt-auto" style="max-width: 526px;">
        <h1>No account? Sign up</h1>
        <p class="pb-3 mb-3 mb-lg-4">
          Have an account already?&nbsp;&nbsp;
          <router-link to="/signin">Sign in here!</router-link>
        </p>
        <form class="needs-validation" @submit.prevent="handleSignup" novalidate>
          <div class="row row-cols-1 row-cols-sm-2">
            <div class="col mb-4">
              <input v-model="name" class="form-control form-control-lg" type="text" placeholder="Your name" required>
            </div>
            <div class="col mb-4">
              <input v-model="email" class="form-control form-control-lg" type="email" placeholder="Email address" required>
            </div>
          </div>
          <div class="password-toggle mb-4">
            <input v-model="password" class="form-control form-control-lg" type="password" placeholder="Password" required>
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
          <div class="password-toggle mb-4">
            <input v-model="confirmPassword" class="form-control form-control-lg" type="password" placeholder="Confirm password" required>
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
          <div class="pb-4">
            <div class="form-check my-2">
              <input v-model="agreeTerms" class="form-check-input" type="checkbox" id="terms">
              <label class="form-check-label ms-1" for="terms">I agree to <a href="#">Terms &amp; Conditions</a></label>
            </div>
          </div>
          <button class="btn btn-lg btn-primary w-100 mb-4" type="submit">Sign up</button>
          <h2 class="h6 text-center pt-3 pt-lg-4 mb-4">Or sign in with your social account</h2>
          <div class="row row-cols-1 row-cols-sm-2 gy-3">
            <div class="col"><a class="btn btn-icon btn-outline-secondary btn-google btn-lg w-100" href="#"><i class="ai-google fs-xl me-2"></i>Google</a></div>
            <div class="col"><a class="btn btn-icon btn-outline-secondary btn-facebook btn-lg w-100" href="#"><i class="ai-facebook fs-xl me-2"></i>Facebook</a></div>
          </div>
        </form>
      </div>
      <!-- Copyright -->
      <p class="w-100 fs-sm pt-5 mt-auto mb-5" style="max-width: 526px;">
        <span class="text-muted">&copy; All rights reserved. Made by</span>
        <a class="nav-link d-inline-block p-0 ms-1" href="https://createx.studio/" target="_blank" rel="noopener">Createx Studio</a>
      </p>
    </div>
    <!-- Cover image -->
    <div class="w-50 bg-size-cover bg-repeat-0 bg-position-center" :style="{ backgroundImage: `url(${coverImage})` }"></div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import coverImage from '@/assets/img/account/cover.jpg';

const router = useRouter();
const name = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const agreeTerms = ref(false);

const handleSignup = () => {
  // 여기에 회원가입 로직을 구현합니다.
  console.log('Name:', name.value);
  console.log('Email:', email.value);
  console.log('Password:', password.value);
  console.log('Confirm Password:', confirmPassword.value);
  console.log('Agree to Terms:', agreeTerms.value);
  
  // 회원가입 성공 시 로그인 페이지로 이동
  router.push('/signin');
};
</script>

<style scoped>
.w-50 {
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
</style>