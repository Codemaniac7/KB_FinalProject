<template>
  <div>
    <br><br><br><br>
    <h1>내가 쓴 글</h1><br><br>
    <div class="profileBox">
      <i class="d-inline ai-dashboard"></i>
      <h2 class="d-inline"> 타임라인</h2>
      <br> <br>
      <div
          v-for="(post, index) in visiblePosts"
          :key="post.postId"
          class="feeds mb-5 mb-xxl-8"
      >
      <div class="card mb-5 mb-xxl-8">
        <div class="card-body pb-0">
          <div class="d-flex align-items-center">
            <div class="symbol symbol-45px me-5">
              <img src="/img/imsi.png" alt="" />
            </div>
            <div class="d-flex flex-column">
              <a href="#" class="text-gray-800 text-hover-primary mb-1 fs-6 fw-bolder"><h2>{{ post.name }}</h2></a>
              <span class="text-gray-500 fw-semibold">{{ formatDate(post.createdDate) }}</span>
            </div>
          </div>
          <div class="pt-5">
              <div class="optionBtn text-end">
                <button
                    type="button"
                    class="btn btn-secondary btn-icon"
                    aria-label="Edit"
                    @click="handleReply(index)"
                >
                  <i class="ai-edit-alt"></i>
                </button>

                <button
                    type="button"
                    class="btn btn-outline-danger btn-icon"
                    aria-label="Delete"
                    @click="handleDelete(index)"
                >
                  <i class="ai-trash"></i>
                </button>
              </div>
            <h3 class="fw-normal subject mb-3">{{ post.title }}</h3>
            <h5 class="fw-normal content mb-3">{{ post.content }}</h5>
            <div v-if="post.boardAttachFileList && post.boardAttachFileList.length > 0">
              <div v-for="attachment in post.boardAttachFileList" :key="attachment.postId" class="mb-3">
                <img
                    :src="`/api/board/file/${encodeURIComponent(attachment.renamedFilename)}`"
                    alt="Post Image"
                    class="post-image"
                />
              </div>
            </div>
            <input type="hidden" :value="post.postId" :ref="`${post.postId}`">
            <div class="d-flex align-items-center">
              <a class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
                <i class="ai-message fs-2"></i>{{ post.commentCount }}
              </a>
              <a
                  class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2"
                  @click="handleLike(index)"
              >
                <i class="ai-heart fs-2"></i>{{ post.likesCount }}
              </a>
            </div>
          </div>
          <div class="replies">
            <div v-for="reply in replies[post.postId] || []" :key="reply.rno" class="reply">
              <h5>{{ reply.content }}</h5>
              <h5>{{ formatDate(reply.createDate) }}</h5>
            </div>
          </div>
          <div class="separator pt-5 mb-3"></div>
          <form class="reply position-relative pb-3">
          <textarea
              v-model="newReply"
              data-kt-autosize="true"
              class="form-control border-0 p-0 pe-10 resize-none min-h-25px"
              rows="1"
              placeholder="댓글"
          ></textarea>
            <div class="position-absolute top-0 end-0 me-n5">
            <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
              <i class="ai-edit-alt" @click="handleReply(index)"></i>
            </span>
            </div>
          </form>
        </div>
      </div>
      </div>
      <div class="d-flex justify-content-center">
      <br><button class="timeBtn">더보기</button><br>
      </div>
    </div>
  </div>
</template>

<style scoped>

.replies {

  margin-top: 20px; /* 댓글 목록 상단 여백 추가 */
}

.reply {
  display:flex;
  justify-content: space-between;
  padding: 10px; /* 댓글 여백 추가 */
  border-bottom: 1px solid lightgrey;
}

.btn {
  background-color: white;
}

.timeBtn{
  width: 250px;
  height: 40px;
  border-radius: 10px;
  color: white;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: rgba(68, 140, 116, 1);
  font-size:20px;
}
h1{
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 40px;
}
.ai-dashboard{
  font-size:27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}

.card{
  border-radius: 30px;
}

</style>

<script setup>
import {useAuthStore} from "@/stores/auth";
import api from '@/api/boardApi';
const auths = useAuthStore();
import axios from "axios";
import {computed, onMounted, ref} from "vue";
const visibleCount = ref(5);
const posts = ref([]);
const auth = JSON.parse(localStorage.getItem("auth"));
const postRefs = ref([]);
const replies = ref({});

const getPostIdFromRef = (index) => {
  if (index < 0 || index >= postRefs.value.length) {
    console.error(`Index ${index} is out of bounds. Available postRefs:`, postRefs.value);
    return null;
  }
  return postRefs.value[index];
};

const handleDelete = async (index) => {
  if (!confirm('삭제할까요?')) return;
  await api.delete(getPostIdFromRef(index));
  reloadPosts();  // 페이지 새로 고침 추가
};

const reloadPosts = async () => {
  await fetchBoardPosts(); // 게시글을 다시 불러오는 함수 호출
};
const formatDate = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    hour12: true,
  });
};
const fetchBoardPosts = async () => {
  try {
    const memberId = auth.id;
    const response = await axios.get(`/api/board/my/${memberId}`);
    if (response.data && response.data) {
      posts.value = response.data;
      postRefs.value = posts.value.map(post => post.postId);
      for (const post of posts.value) {
        await fetchReplies(post.postId); // 각 ID에 대해 개별적으로 댓글 가져오기
      }
    } else {
      posts.value = [];
    }
  } catch (error) {
    console.error("Error fetching posts:", error);
  }
};


// 보여질 게시글 계산
const visiblePosts = computed(() => {
  return Array.isArray(posts.value) ? posts.value.slice(0, visibleCount.value) : [];
});
const fetchReplies = async (postId) => {
  try {
    const response = await api.getReplies(postId); // 각 ID에 대해 개별적으로 요청
    replies.value[postId] = response; // 댓글 목록을 postId를 키로 하는 객체에 저장
  } catch (error) {
    console.error("Error fetching replies:", error);
  }
};

// 모든 게시글이 로드되었는지 확인
const allPostsLoaded = computed(() => {
  return visibleCount.value >= posts.value.length;
});


// 더 많은 게시글 로드하는 함수
const loadMore = () => {
  visibleCount.value += 5;
};

onMounted(() => {
  fetchBoardPosts();
});
</script>