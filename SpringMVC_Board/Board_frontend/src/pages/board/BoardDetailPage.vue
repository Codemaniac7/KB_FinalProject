<script setup>
import { useRoute, useRouter } from 'vue-router';
import api from '@/api/boardApi';
import { ref } from 'vue';
import moment from 'moment';
import { useAuthStore } from '@/stores/auth';
import { downloadFile } from '@/util/download';

const auth = useAuthStore();
const route = useRoute();
const router = useRouter();

const no = route.params.no;
const article = ref({});

const reply = ref('');

const back = () => {
  router.push({ name: 'board/list', query: route.query });
};

const update = () => {
  router.push({ name: 'board/update', params: { no: no }, query: route.query });
};

const download = async (no) => {
  const URL = '/api/board/download/' + no;
  console.log(URL);
  await downloadFile(URL);
};

const remove = async () => {
  if (!confirm('삭제할까요?')) return;
  await api.delete(no);
  router.push({ name: 'board/list', query: route.query });
};

const load = async () => {
  article.value = await api.get(no);
  console.log('DETAIL', article.value);
};

load();

const sendReply = async ()=> {
  if(reply.value == null || reply.value.length == 0){
    alert('내용이 없습니다.');
    return;
  }
  const sendObj = {bno:no,content:reply.value,writer:auth.id};
  const result = await api.sendReply(sendObj);
  console.log(result);
  if(result != null){
    alert('댓글이 등록 되었습니다.');
    load();
  }else{
    alert('댓글 등록에 실패 하였습니다.');
  }
}

const deleteReply = async (rno)=> {
  const result = await api.deleteReply(rno);
  console.log(result);
  if(result != null){
    alert('댓글이 삭제 되었습니다.');
    load();
  }else{
    alert('댓글 삭제에 실패 하였습니다.');
  }
}

</script>

<template>
  <h1>{{ article.title }}</h1>
  <div class="my-3 d-flex justify-content-between">
    <div>
      <i class="fa-solid fa-user"></i>
      {{ article.memberName }} ({{ article.memberId }})
    </div>
    <div>
      <i class="fa-regular fa-clock"></i>
      {{ moment(article.updateDate).format('YYYY-MM-DD HH:mm') }}
    </div>
  </div>

  <hr />

  <div class="text-end">
    <div v-for="file in article.boardAttachFileList" :key="file.fno" class="attach">
      <span @click="download(file.fno)">
        <i class="fa-solid fa-paperclip"></i>
        {{ file.originalFilename }}
      </span>
    </div>
  </div>

  <div class="content">{{ article.content }}</div>

  <div class="my-5">
    <button class="btn btn-primary me-2" @click="back"><i class="fa-solid fa-list"></i> 목록</button>

    <!-- 로그인 사용자와 작성자가 같은 경우 -->
    <template v-if="auth.id == article.memberId">
      <button class="btn btn-primary me-2" @click="update"><i class="fa-regular fa-pen-to-square"></i> 수정</button>
      <button class="btn btn-danger" @click="remove"><i class="fa-solid fa-trash-can"></i> 삭제</button>
    </template>
  </div>


  <div class="row mt-2">
    <div class="col-11">
      <label class="form-label">댓글</label>
      <input  v-model="reply"  type="text" class="form-control" placeholder="내용을 입력하세요.">
    </div>
    <div class="col-1">
      <label  class="form-label"> &nbsp <span class="text-body-secondary"></span></label>
      <button class="form-control btn btn-primary" @click="sendReply()">작성</button>
    </div>
  </div>

  <table class="table table-striped mt-4">
    

    <thead>
      <tr>
        <th style="width: 60px">No</th>
        <th>댓글 내용</th>
        <th style="width: 150px">작성자</th>
        <th style="width: 120px">작성일</th>
        <th style="width: 80px"></th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="reply in article.replyList" :key="reply.rno">
        <td>{{ reply.rno }}</td>
        <td>
          {{ reply.content }}
        </td>
        <td>{{ reply.memberName }}({{reply.memberId}})</td>
        <td>{{ moment(reply).format('YYYY-MM-DD') }}</td>
        <td> 
          <span v-if="reply.memberId == auth.id">
            <button class="btn btn-close" @click="deleteReply(reply.rno)"></button> 
          </span>
        </td>
      </tr>
    </tbody>
  </table>
  
</template>





<style scoped>
.attach {
  font-size: 0.8rem;
  cursor: pointer;
}
.content {
  white-space: pre-line;
}
</style>
