<script setup>
const emit = defineEmits(['close']); // 부모 컴포넌트로 닫기 이벤트 전달
import axios from "axios";
import { ref, reactive, computed, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';

const authStore = useAuthStore();
const form = reactive({
  title: '',
  content: '',
});
const files = ref(null); // Ref for the file input
const selectedFiles = ref([]); // For handling multiple file uploads
const isSubmitting = ref(false);
const isError = ref(false);
const errorMessage = ref('');
const userInvestType = ref(); // Store user's invest type

const route = useRoute();
const router = useRouter();



async function fetchUserInvestType() {
  const authValue = localStorage.getItem('auth');
  if (authValue) {
    try {
      const authData = JSON.parse(authValue);
      if (authData && authData.id && authData.token) {
        const response = await axios.get(`/api/member/${authData.id}`, {
          headers: {
            'Authorization': `Bearer ${authData.token}`,
          },
        });
        userInvestType.value = response.data.investType; // Fetch and set user's invest type
      } else {
        console.error('Invalid auth data');
      }
    } catch (error) {
      console.error('Error fetching user invest type:', error);
      isError.value = true;
      errorMessage.value = '사용자 정보를 불러오는 중 오류가 발생했습니다.';
    }
  } else {
    isError.value = true;
    errorMessage.value = '로그인이 필요합니다.';
  }
}

// Computed property to ensure userInvestType is available before using investTypeMapping


async function createBoardPost(token) {
  const formData = new FormData();
  formData.append('title', form.title);
  formData.append('content', form.content);

  // Append files if there are any
  selectedFiles.value.forEach(file => {
    formData.append('files', file);
  });

  // API 요청에서 userInvestType을 type으로 사용
  const type = userInvestType.value; // 사용자의 investType을 type으로 사용

  // Axios POST request
  try {
    const postResponse = await axios.post(`/api/board/${type}`, formData, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log('Post created:', postResponse.data);
  } catch (error) {
    console.error('Error creating post:', error);
    console.error('Error details:', error.response ? error.response.data : error.message);
    throw error;
  }
}


// Form submission function
const submit = async () => {
  if (!confirm('등록할까요?')) return;
  isError.value = false;

  const authValue = localStorage.getItem('auth'); // auth 데이터 가져오기
  let token = null; // token 변수 초기화
  if (authValue) {
    const authData = JSON.parse(authValue);
    token = authData.token; // token 설정
  }

  try {
    await createBoardPost(token);
    router.push(`/community/${userInvestType.value}`);
    reloadPosts(); // 페이지 새로 고침 추가
  } catch (error) {
    console.error('Error creating board post:', error);
    errorMessage.value = '게시물을 작성하는 중 오류가 발생했습니다. 다시 시도해주세요.';
    isError.value = true;
  } finally {
    isSubmitting.value = false;
  }
};


// Cancel post function
function cancelPost() {
  form.title = '';
  form.content = '';
  selectedFiles.value = [];
  if (files.value) {
    files.value.value = ''; // Reset file input
  }
}

// File change handler
function handleFileChange(event) {
  selectedFiles.value = Array.from(event.target.files); // Handle file selection
}

function closePopup() {
  emit('close');
}


const reloadPosts = async () => {
  location.reload();
};

onMounted(() => {
  fetchUserInvestType();
});
</script>

<template>
  <div class="writer popup">
    <div class="card popup-content">
      <!-- 이미지와 버튼을 좌우로 배치 -->
      <form @submit.prevent="submit">
          <div class="card-body pb-0">
            <div class="d-flex align-items-center">
              <div class="button-group position-absolute top-0 end-0 mt-3 me-5">
                <button type="button" class="cancel me-3">취소</button>
                <button type="submit" class="submit" >작성</button>
              </div>
            </div>
            <br><br>
            <div>
              <!-- Hidden input for the page value -->
              <input type="hidden" :value="route.params.postType" />
              <input
                  v-model="form.title"
                  id="title"
                  class="subject"
                  placeholder="제목을 입력하세요"
                  required
              />
              <textarea
                  v-model="form.content"
                  id="content"
                  class="writer"
                  placeholder="무슨 일이 일어나고 있나요?"
                  required
              ></textarea>
              <div class="filebox">
                <label for="files" class="file-label">
                  <i class="ai-image"></i>
                </label>
                <input type="file" id="files" ref="files" @change="handleFileChange" style="display: none;" />
              </div>
            </div>
          </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.card{
  border:none;
}
.filebox {
  display: inline-block;
  cursor: pointer;
}

.ai-image {
  font-size: 24px;
  cursor: pointer;
  color: rgba(67, 140, 116, 1);
}

.popup-content img {
  width: 300px;
  height: 300px;
  border-radius: 30px;
}

.writer {
  font-family: J3;
  width: 500px;
  height: 250px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-top: 30px;
  padding: 20px;
}
.subject{
  width: 550px;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-bottom: -20px;
  padding: 20px;
}
.popup {
  position: fixed;
  top: 50%;
  height: 450px;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  background-color: white;
  padding: 20px;
  border-radius: 20px;
  z-index: 100; /* 흐린 배경 위에 위치 */
}

.popup-content {
  display: flex;
  flex-direction: column;
}


.subject{
  width: 100%;
  height: 40px;
  margin-top: -50px;
  padding: 20px;
  border-radius: 20px;
  border: 1px solid lightgrey;
}

.submit {
  width: 100px;
  height: 40px;
  border-radius: 10px;
  margin-top: -30px;
  background-color: rgba(67, 140, 116, 1);
  color: white;
  border: none;
  cursor: pointer;
}

.cancel {
  width: 100px;
  height: 40px;
  border-radius: 10px;
  margin-top: -30px;
  background-color: rgb(192, 192, 192);
  color: black;
  border: none;
  cursor: pointer;
}

</style>
