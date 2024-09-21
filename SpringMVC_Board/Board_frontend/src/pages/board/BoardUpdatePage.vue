<script setup>
import boardApi from '@/api/boardApi';
import { ref, reactive } from 'vue';
// import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';

// const auth = useAuthStore();

const route = useRoute();
const router = useRouter();

const no = route.params.no;
const article = reactive({});
const attachments = ref([]);
const orgArticle = ref({});
const files = ref(null);
const types = ref([]);

const back = () => {
  router.push({ name: 'board/detail', params: { no }, query: route.query });
};

const reset = () => {
  article.bno = orgArticle.value.bno;
  article.title = orgArticle.value.title;
  article.type = orgArticle.value.type;
  article.writer = orgArticle.value.writer;
  article.content = orgArticle.value.content;
  console.log(article);
};

const removeFile = async (fno, filename) => {
  if (!confirm(filename + '을 삭제할까요?')) return;
  await boardApi.deleteAttachment(fno);

  const ix = attachments.value.findIndex((f) => f.fno === fno);
  attachments.value.splice(ix, 1);
};

const submit = async () => {
  if (!confirm('수정할까요?')) return;

  if (files.value.files.length > 0) {
    article.files = files.value.files;
  }

  const result = await boardApi.update(article);
  if(result != null){
    alert('게시글이 수정 되었습니다.')
    router.push({ name: 'board/detail', params: { no }, query: route.query });
  }else{
    alert('게시글이 수정에 실패 하였습니다.')
    router.push({ name: 'board/detail', params: { no }, query: route.query });

  }
};

const load = async () => {
  const data = await boardApi.get(no);
  orgArticle.value = { ...data };
  attachments.value = data.boardAttachFileList;
  reset();
};

load();

const getTypes = async () => {
  try {
    types.value = await boardApi.getTypes();
    console.log(types.value);
  } catch { }
};

getTypes();

</script>

<template>
  <h1><i class="fa-regular fa-pen-to-square"></i> 글 수정</h1>

  <form @submit.prevent="submit">

    <div class="mb-3 mt-3 row">
      <div class="col-8">
        <label for="title" class="form-label"> 제목 </label>
        <input type="text" class="form-control" placeholder="제목" id="title" v-model="article.title" />  
      </div>
      <div class="col-4">
        <label for="title" class="form-label"> Type </label>
        <select v-model="article.type" class="form-select" required>
          <!-- <tr v-for="article in articles" :key="article.bno"> -->
          <option v-for="item in types" :key="item" :value="item.type" >{{item.name}}</option>
        </select>  
      </div>
    </div>

    <div class="mb-3 mt-3">
      <label class="form-label"> 기존 첨부파일 </label>
      <div v-for="file in attachments" :key="file.fno" class="attach">
        <i class="fa-solid fa-paperclip"></i>
        {{ file.originalFilename }}
        <i class="fa-solid fa-trash-can text-danger ms-2" @click="removeFile(file.fno, file.originalFilename)"></i>
      </div>
    </div>

    <div class="mb-3 mt-3">
      <label for="files" class="form-label"> 첨부파일 </label>
      <input type="file" class="form-control" placeholder="첨부파일" id="files" ref="files" multiple />
    </div>

    <div class="mb-3 mt-3">
      <label for="content" class="form-label"> 내용 </label>
      <textarea class="form-control" placeholder="내용" id="content" v-model="article.content" rows="10"></textarea>
    </div>

    <div class="my-5 text-center">
      <button type="submit" class="btn btn-primary me-3">
        <i class="fa-solid fa-check"></i>
        확인
      </button>

      <button type="button" class="btn btn-primary me-3" @click="reset">
        <i class="fa-solid fa-undo"></i>
        취소
      </button>

      <button class="btn btn-primary" @click="back">
        <i class="fa-solid fa-arrow-left"></i>
        돌아가기
      </button>
    </div>
  </form>
</template>
<style>
.fa-trash-can {
  cursor: pointer;
}
</style>
