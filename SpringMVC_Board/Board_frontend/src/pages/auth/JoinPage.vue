<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const router = useRouter();
const avatar = ref(null);
const checkError = ref('');
  
//////////////////////////////////////////////////////////
const member = reactive({
  id: 'test',
  name: '홍길동',
  email: 'hong@gmail.com',
  password: '1212',
  password2: '1212',
  avatar: null,
});
  //////////////////////////////////////////////////////////
const disableSubmit = ref(true);
const checkId = async () => {
  if (!member.id) {
    return alert('사용자 ID를 입력하세요.');
  }

  disableSubmit.value = await authApi.checkId(member.id);
  console.log(disableSubmit.value, typeof disableSubmit.value);
  checkError.value = disableSubmit.value ? '이미 사용중인 ID입니다.' : '사용가능한 ID입니다.';
};

const changeId = () => {
  disableSubmit.value = true;
  if (member.id) {
    checkError.value = 'ID 중복 체크를 하셔야 합니다.';
  } else {
    checkError.value = '';
  }
};

const join = async () => {
  if (member.password != member.password2) {
    return alert('비밀번호가 일치하지 않습니다.');
  }

  if (avatar.value.files.length > 0) {
    member.avatar = avatar.value.files[0];
  }

  try {
    await authApi.create(member);
    router.push({ name: 'home' });
  } catch (e) {
    console.error(e);
  }
};
</script>

<template>
  <div class="mt-5 mx-auto" style="width: 500px">
    <h1 class="my-5">
      <i class="fa-solid fa-user-plus"></i>
      회원 가입
    </h1>

    <form @submit.prevent="join">
      <div class="mb-3 mt-3">
        <label for="id" class="form-label">
          <i class="fa-solid fa-user"></i>
          사용자 ID :
          <button type="button" class="btn btn-success btn-sm py-0 me-2" @click="checkId">ID 중복 확인</button>
          <span :class="disableSubmit.value ? 'text-primary' : 'text-danger'">{{ checkError }}</span>
        </label>
        <input type="text" class="form-control" placeholder="사용자 ID" id="id" @input="changeId" v-model="member.id" />
      </div>

      <div>
        <label for="avatar" class="form-label">
          <i class="fa-solid fa-user-astronaut"></i>
          아바타 이미지:
        </label>
        <input type="file" class="form-control" ref="avatar" id="avatar" accept="image/png, image/jpeg" />
      </div>

      <div class="mb-3 mt-3">
        <label for="email" class="form-label">
          <i class="fa-solid fa-envelope"></i>
          email
        </label>
        <input type="email" class="form-control" placeholder="Email" id="email" v-model="member.email" />
      </div>

      <div class="mb-3 mt-3">
        <label for="name" class="form-label">
          <i class="fa-solid fa-user"></i>
          name
        </label>
        <input type="text" class="form-control" placeholder="Name" id="name" v-model="member.name" />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          비밀번호:
        </label>
        <input type="password" class="form-control" placeholder="비밀번호" id="password" v-model="member.password" />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          비밀번호 확인:
        </label>
        <input type="password" class="form-control" placeholder="비밀번호 확인" id="password2" v-model="member.password2" />
      </div>

      <button type="submit" class="btn btn-primary mt-4" :disabled="disableSubmit">
        <i class="fa-solid fa-user-plus"></i>
        확인
      </button>
    </form>

</div>
</template>
