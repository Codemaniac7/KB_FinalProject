<template>
  <div class="container">
    <div class="col">
      <form @submit.prevent="submit" v-if="canPost">
        <div class="feeds card mb-5 mb-xxl-8">
          <div class="card-body pb-0">
            <div class="d-flex align-items-center">
              <div class="button-group position-absolute top-0 end-0 mt-3 me-5">
                <button type="button" class="cancel me-3">취소</button>
                <button type="submit" class="submit">작성</button>
              </div>
            </div>
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
              <!-- File input and error message here -->
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
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
const investTypeMapping = {
  'stability' : 1,
  'neutral' : 2,
  'activeInvestment': 3,
  'aggressiveInvestment': 4
};

// Call fetchUserInvestType when component is mounted
onMounted(() => {
  fetchUserInvestType();
});


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

// Function to create a board post
async function createBoardPost() {
  const formData = new FormData();
  formData.append('title', form.title);
  formData.append('content', form.content);

  // Append files if there are any
  selectedFiles.value.forEach(file => {
    formData.append('files', file);
  });

  // Determine bno based on postType
  let bno;
  switch (route.params.postType) {
    case 'stability':
      bno = 1;
      break;
    case 'neutral':
      bno = 2;
      break;
    case 'activeInvestment':
      bno = 3;
      break;
    case 'aggressiveInvestment':
      bno = 4;
      break;
    default:
      console.error('Invalid postType');
      throw new Error('유효하지 않은 게시판 유형입니다.');
  }

  // Append bno and type to formData
  formData.append('bno', bno);
  formData.append('type', route.params.postType);

  // Get auth token
  const token = authStore.getToken();

  // Axios POST request
  try {
    const postResponse = await axios.post(`/api/board/${route.params.postType}`, formData, {
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

  try {
    await createBoardPost();
    router.push(`/community/${route.params.postType}`);
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
</script>

<style scoped>
/* Style updates based on your requirements */
.filebox {
  display: inline-block;
  cursor: pointer;
  margin-left: 20px;
  margin-bottom: 20px;
}

.ai-image {
  font-size: 20px;
  cursor: pointer;
  color: rgba(67, 140, 116, 1);
  margin-left: -10px;
}

.submit {
  width: 80px;
  height: 30px;
  border-radius: 20px;
  border: none;
  background-color: rgb(67, 140, 116);
  color: white;
  font-size: 12px;
  cursor: pointer;
}

.submit:disabled {
  background-color: grey;
  cursor: not-allowed;
}

.cancel {
  width: 80px;
  height: 30px;
  border-radius: 20px;
  background-color: lightgrey;
  color: white;
  border: none;
  font-size: 12px;
  cursor: pointer;
}

.feeds {
  width: 700px;
  height: auto;
  border-radius: 30px;
  border: none;
  background-color: white;
  padding: 20px;
}

.writer {
  text-align: start;
  width: 100%;
  min-height: 100px;
  border-radius: 20px;
  padding: 20px;
  border: 1px solid lightgrey;
  resize: vertical;
  font-size: 15px;
  margin-top: 10px;
  margin-bottom: 10px;
}

.subject {
  text-align: start;
  width: 100%;
  height: 40px;
  border-radius: 20px;
  margin-top: 20px;
  padding: 10px;
  border: 1px solid lightgrey;
  font-size: 15px;
}

.alert {
  margin-top: 20px;
  color: red;
}
</style>
