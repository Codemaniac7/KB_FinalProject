<script setup>
import { ref, reactive, onMounted, } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const route = useRoute();
const router = useRouter();
const auth = useAuthStore();
const error = ref('');
const member = reactive({
  code: '',
  redirectUrl: '',
});


onMounted(async () => {
  try {
    const host = window.location.origin;
    member.redirectUrl = `${host}/auth/kakaologin`;
    member.code = route.query.code;
    console.log(member);
    try {
      await auth.login(member);
      alert('로그인이 완료 되었습니다.');
      router.push('/');
    }catch(e) {
      alert('로그인에 실패했습니다.');
      router.push('login');
    }
    
  } catch (e) {
    // 로그인 에러
    console.log('에러=======', e);
    error.value = e.response.data;
  }

});


const login = async () => {
  console.log(member);
  try {
    await auth.login(member);
    if (router.query.next) {
      router.push({ name: route.query.next });
    } else {
      // 일반
      router.push('/');
    }
  } catch (e) {
    // 로그인 에러
    console.log('에러=======', e);
    error.value = e.response.data;
  }
};
//////////////////////////////////////////////////////////
</script>

<template>
  <div class="mt-5 mx-auto" style="width: 100px">
    <div class="spinner-border" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
  </div>
</template>
