<template>
  <div>
    <br><br>
    <h2>설정</h2><br><br>

    <!-- 계정 정보 섹션 -->
    <div class="profileBox">
      <i class="d-inline ai-user"></i>
      <h3 class="d-inline"> 계정 정보</h3>
      <br><br>
      <form @submit.prevent="mySubmit">
        <div class="formMyInfo">
          <div class="form-group">
            <label for="name">이름</label><br>
            <input class="form" id="name" type="text" v-model="member.name" disabled>
          </div>
          <div class="form-group">
            <label for="email">이메일</label><br>
            <input class="form" id="email" type="email" v-model="member.email">
          </div>
        </div>
        <div class="btn d-flex justify-content-end align-items-end">
          <button class="cancelBtn" type="cancel">취소</button>
          <button class="submitBtn" type="submit" >확인</button>
        </div>
      </form>
    </div><br><br>
    <!-- 비밀번호 섹션 -->
    <div class="passwordBox">
      <i class="ai-lock-closed"></i>
      <h3 class="d-inline"> 비밀번호</h3>
      <br><br>

    <form @submit.prevent="passwordSubmit">
      <div class="formMyInfo d-flex flex-wrap">
        <div class="mb-3">
          <label class="form-label" for="pass-visibility1">현재 비밀번호</label>
          <div class="password-toggle">
            <input
                class="form-control"
                type="password"
                id="pass-visibility1"
                v-model="changePassword.oldPassword"
                @input="inputPassword"
                autocomplete="current-password"
            />
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input
                  class="password-toggle-check"
                  type="checkbox"
                  @click="togglePasswordVisibility('oldPassword')"
              />
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="pass-visibility2">새 비밀번호</label>
          <div class="password-toggle">
            <input
                class="form-control"
                type="password"
                id="pass-visibility2"
                v-model="changePassword.newPassword"
                @input="resetError"
                autocomplete="new-password"
            />
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input
                  class="password-toggle-check"
                  type="checkbox"
                  @click="togglePasswordVisibility('newPassword')"
              />
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="pass-visibility3">새 비밀번호 확인</label>
          <div class="password-toggle">
            <input
                class="form-control"
                type="password"
                id="pass-visibility3"
                v-model="changePassword.newPassword2"
                @input="resetError"
                autocomplete="new-password"
            />
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input
                  class="password-toggle-check"
                  type="checkbox"
                  @click="togglePasswordVisibility('confirmPassword')"
              />
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="warning">
        <i class="d-inline ai-circle-alert"></i>
        <h5 class="d-inline"> 비밀번호는 8자 이상 영문/숫자/특수문자 중, 2가지 이상 포함</h5>
      </div>
      <div class="btn d-flex justify-content-end align-items-end">
        <button class="cancelBtn" type="cancel">취소</button>
        <button class="submitBtn" type="submit" :disabled="passwordDisableSubmit">확인</button>
      </div>
      </form>
    </div><br><br>

    <!-- 프로필 사진 섹션 -->
    <div class="profileBox">
      <form @submit.prevent="avatarSubmit">
      <i class="d-inline ai-image"></i>
      <h2 class="d-inline"> 프로필 사진</h2>
      <h4 class="m-5">PNG, JPG 500px로 등록</h4>
      <br><br>
      <div class="avatarBox d-flex justify-content-center">
        <div class="avatar">
          <label for="avatar" class="form-label">
            <img :src="avatarPath" class="avatar avatar-lg me-4" />
            <i class="ai-camera"></i>
            <input
                type="file"
                class="form-control"
                ref="avatar"
                id="avatar"
                style="display:none"
                accept="image/png, image/jpeg"
                @change="uploadAvatar"
            />
          </label>
        </div>
      </div>
      <div class="btn d-flex justify-content-end align-items-end">
        <button class="cancelBtn" type="cancel">취소</button>
        <button class="submitBtn" type="submit" :disabled="avaterSubmit">확인</button>
      </div>
    </form>
    </div>
  </div>
</template>

<script setup>
import authApi from '@/api/authApi';
import { useAuthStore } from '@/stores/auth';
import { computed, reactive, ref } from 'vue';

const auth = useAuthStore();
const avatar = ref(null);
const avatarPath = `/api/member/${auth.id}/avatar`;
const member = reactive({
  username: auth.username,
  email: auth.email,
  password: '',
  avatar: null,
});
const disableSubmit = computed(() => !member.email || !member.password);
const mySubmit = async () =>{
  if (!confirm('수정하시겠습니까?')) return;
  if (avatar.value.files.length > 0) {
    member.avatar = avatar.value.files[0];
  }
  try {
    await authApi.update(member);
    error.value = '';
    auth.changeProfile(member);
    alert('정보를 수정하였습니다.');
  } catch (e) {
    error.value = e.response.data;
  }
};
const avatarSubmit = async () =>{
  if (!confirm('수정하시겠습니까?')) return;
  if (avatar.value.files.length > 0) {
    member.avatar = avatar.value.files[0];
  }
  try {
    await authApi.update(member);
    error.value = '';
    auth.changeProfile(member);
    alert('정보를 수정하였습니다.');
  } catch (e) {
    error.value = e.response.data;
  }
};

const changePassword = reactive({
  username: auth.username,
  oldPassword: '',
  newPassword: '',
  newPassword2: '',
});

const passwordDisableSubmit = computed(() => !changePassword.oldPassword || !changePassword.newPassword
    || !changePassword.newPassword2);
const error = ref('');
const inputPassword = () => (error.value = '');
const resetError = () => (error.value = '');
const passwordSubmit = async () => {
  if (changePassword.newPassword != changePassword.newPassword2) {
    error.value = '새 비밀번호가 일치하지 않습니다.';
    return;
  }
  try {
    console.log('changePassword' + changePassword);
    // 비밀번호 변경 요청을 백엔드(서버)로 전송
    await authApi.changePassword(auth.id, changePassword);
    alert('비밀번호를 수정했습니다.');
    // eslint-disable-next-line no-undef
    router.push({ name: 'myPageSetting' });
  } catch (e) {
    error.value = e.response.data;
  }
};
</script>


<!--// 사용자 ID 가져오기-->
<!--const getUserId = () => {-->
<!--  const auth = JSON.parse(localStorage.getItem("auth"));-->
<!--  if (auth && auth.id) {-->
<!--    return auth.id;-->
<!--  }-->
<!--  throw new Error("사용자 인증 정보가 없습니다.");-->
<!--};-->

<!--// 사용자 정보 불러오기-->
<!--const changeMyInfo = async () => {-->
<!--  loading.value = true;-->
<!--  try {-->
<!--    const userId = getUserId();-->
<!--    const response = await axios.get(`/api/member/${userId}`);-->
<!--    info.value.name = response.data.name;-->
<!--    info.value.email = response.data.email;-->
<!--    avatarPath.value = response.data.avatarPath || '/img/imsi.png'; // 서버에서 아바타 경로를 반환한다고 가정-->
<!--  } catch (error) {-->
<!--    console.error('정보 가져오기 에러:', error);-->
<!--    alert('사용자 정보를 불러오는 데 실패했습니다.');-->
<!--  } finally {-->
<!--    loading.value = false;-->
<!--  }-->
<!--};-->

<!--// 계정 정보 업데이트-->
<!--const updateInfo = async () => {-->
<!--  loading.value = true;-->
<!--  try {-->
<!--    const userId = getUserId();-->
<!--    await axios.put(`/api/member/${userId}`, {-->
<!--      name: info.value.name,-->
<!--      email: info.value.email-->
<!--    });-->
<!--    alert('정보가 성공적으로 업데이트되었습니다.');-->
<!--  } catch (error) {-->
<!--    console.error('정보 업데이트 에러:', error);-->
<!--    alert('정보 업데이트에 실패했습니다.');-->
<!--  } finally {-->
<!--    loading.value = false;-->
<!--  }-->
<!--};-->

<!--// 비밀번호 변경-->
<!--const changePassword = async () => {-->
<!--  if (password.value.newPassword !== password.value.confirmPassword) {-->
<!--    alert('새 비밀번호가 일치하지 않습니다.');-->
<!--    return;-->
<!--  }-->
<!--  // 비밀번호 강도 검증 추가 가능-->
<!--  loading.value = true;-->
<!--  try {-->
<!--    const userId = getUserId();-->
<!--    const response = await axios.post(`/api/member/${userId}`, {-->
<!--      oldPassword: password.value.oldPassword,-->
<!--      newPassword: password.value.newPassword-->
<!--    });-->
<!--    if (response.data) { // 서버가 true를 반환하면-->
<!--      alert('비밀번호가 성공적으로 변경되었습니다.');-->
<!--      emit('password-success'); // 부모 컴포넌트에 이벤트 전달-->
<!--      // 비밀번호 필드 초기화-->
<!--      password.value.oldPassword = '';-->
<!--      password.value.newPassword = '';-->
<!--      password.value.confirmPassword = '';-->
<!--    } else {-->
<!--      alert('비밀번호가 일치하지 않습니다.');-->
<!--    }-->
<!--  } catch (error) {-->
<!--    console.error('비밀번호 변경 에러:', error);-->
<!--    if (error.response && error.response.data) {-->
<!--      alert(error.response.data);-->
<!--    } else {-->
<!--      alert('비밀번호 변경에 실패했습니다.');-->
<!--    }-->
<!--  } finally {-->
<!--    loading.value = false;-->
<!--  }-->
<!--};-->

<!--// 아바타 업로드 미리보기-->
<!--const uploadAvatar = (event) => {-->
<!--  const file = event.target.files[0];-->
<!--  if (file) {-->
<!--    const reader = new FileReader();-->
<!--    reader.onload = (e) => {-->
<!--      avatarPath.value = e.target.result; // 업로드한 이미지를 미리보기로 표시-->
<!--    };-->
<!--    reader.readAsDataURL(file);-->
<!--  }-->
<!--};-->

<!--// 아바타 업데이트-->
<!--const updateAvatar = async () => {-->
<!--  const file = document.getElementById('avatar').files[0];-->
<!--  if (!file) {-->
<!--    alert('업로드할 파일을 선택해주세요.');-->
<!--    return;-->
<!--  }-->
<!--  const formData = new FormData();-->
<!--  formData.append('avatar', file);-->

<!--  loading.value = true;-->
<!--  try {-->
<!--    const userId = getUserId();-->
<!--    await axios.post(`/api/member/${userId}/avatar`, formData, {-->
<!--      headers: {-->
<!--        'Content-Type': 'multipart/form-data'-->
<!--      }-->
<!--    });-->
<!--    alert('프로필 사진이 성공적으로 변경되었습니다.');-->
<!--  } catch (error) {-->
<!--    console.error('아바타 업로드 에러:', error);-->
<!--    alert('프로필 사진 변경에 실패했습니다.');-->
<!--  } finally {-->
<!--    loading.value = false;-->
<!--  }-->
<!--};-->

<!--// 비밀번호 표시/숨기기 토글-->
<!--const togglePasswordVisibility = (passwordType) => {-->
<!--  let index;-->
<!--  if (passwordType === 'oldPassword') index = '1';-->
<!--  else if (passwordType === 'newPassword') index = '2';-->
<!--  else if (passwordType === 'confirmPassword') index = '3';-->
<!--  const passwordField = document.getElementById(`pass-visibility${index}`);-->
<!--  if (passwordField) {-->
<!--    passwordField.type = passwordField.type === 'password' ? 'text' : 'password';-->
<!--  }-->
<!--};-->

<!--// 정보 취소 버튼 동작-->
<!--const cancelInfo = () => {-->
<!--  // 원래 정보를 다시 로드하여 입력 필드 초기화-->
<!--  changeMyInfo();-->
<!--};-->

<!--// 비밀번호 취소 버튼 동작-->
<!--const cancelPassword = () => {-->
<!--  password.value.oldPassword = '';-->
<!--  password.value.newPassword = '';-->
<!--  password.value.confirmPassword = '';-->
<!--};-->

<!--// 아바타 취소 버튼 동작-->
<!--const cancelAvatar = () => {-->
<!--  // 원래 아바타 경로로 되돌리기-->
<!--  changeMyInfo();-->
<!--};-->

<!--// 컴포넌트 마운트 시 사용자 정보 로드-->
<!--onMounted(() => {-->
<!--  changeMyInfo();-->
<!--});-->
<!--</script>-->

<style scoped>
.ai-camera {
  font-size: 40px;
  margin-top: -90px;
  color: white;
  font-weight: 700;
}

.avatar {
  border-radius: 50%;
  width: 150px;
  height: 150px;
  display: inline-block;
  cursor: pointer;
  text-align: center;
}

.warning {
  text-align: center;
  width: 65%;
  height: 40px;
  margin: 20px;
  border-radius: 20px;
  padding: 10px;
  background-color: rgba(211, 211, 211, 0.24);
}

.ai-circle-alert {
  font-size: 20px;
  vertical-align: text-bottom;
}

.btn {
  display: flex;
  gap: 20px;
  font-size: 15px;
}

h1 {
  margin: 20px;
}

.form-check-input {
  width: 20px;
  height: 20px;
}

.form-check-input:checked {
  background-color: rgba(68, 140, 116, 1);
  border-color: rgba(68, 140, 116, 1);
}

.form-check-label {
  font-size: 20px;
  padding-left: 5px;
}

.form {
  width: 400px;
  height: 50px;
  border-radius: 20px;
  padding: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

.password-toggle {
  width: 400px;
  height: 50px;
  border-radius: 20px;
  padding: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

.formMyInfo {
  display: flex;
  justify-content: space-between;
}

label {
  font-size: 15px;
}

.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
  font-size: 20px;
}

.passwordBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
}

.passwordBox .form-control {
  border: none;
  margin-top: -25px;
  width: 350px;
  height: 50px;
  margin-left: -10px;
}

.cancelBtn {
  width: 150px;
  height: 40px;
  border-radius: 10px;
  background-color: lightgrey;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

.cancelBtn:active {
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

.submitBtn:active {
  background-color: lightgrey;
  color: black;
}

.submitBtn {
  width: 150px;
  height: 40px;
  border-radius: 10px;
  color: white;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: rgba(68, 140, 116, 1);
}

.ai-user {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}

.ai-lock-closed {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}

.ai-image {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
</style>
