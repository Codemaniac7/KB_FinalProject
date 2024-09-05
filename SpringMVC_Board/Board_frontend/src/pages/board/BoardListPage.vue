<script setup>
import api from '@/api/boardApi';
import { ref, reactive, computed, watch } from 'vue';
import moment from 'moment';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const page = ref({});

const articles = computed(() => page.value.boardList);

const pageRequest = reactive({
  page: parseInt(route.query.page) || 1,
  amount: parseInt(route.query.amount) || 12,
  searchType: '',
  searchValue: '',
  types: [],
});

// console.log('QUERY', cr.query);
// console.log('PAGE REQUEST', pageRequest);

// 페이지네이션 페이지 변경
const handlePageChange = async (pageNum) => {
  console.log('CLICK,,,,');
  // url 변경 --> query 파트만 변경되므로 컴포넌트가 다시 마운트되지 않음
  // watch를 통해 cr이 변경됨을 감지하여 페이지 로드해야 함
  router.push({
    query: { page: pageNum, 
            amount: pageRequest.amount, 
            searchType: pageRequest.searchType,
            searchValue: pageRequest.searchValue,
            types: pageRequest.types,
          },
  });
};

// 검색어 변경
const searchChange = async () => {
  console.log('CLICK,,,,');
  // url 변경 --> query 파트만 변경되므로 컴포넌트가 다시 마운트되지 않음
  // watch를 통해 cr이 변경됨을 감지하여 페이지 로드해야 함
  router.push({
    query: { page: pageRequest.page, 
            amount: pageRequest.amount, 
            searchType: pageRequest.searchType,
            searchValue: pageRequest.searchValue,
            types: pageRequest.types,
          },
  });
};

// pageRequest의 값 변경된 경우 호출
watch(route, async (newValue) => {
  console.log('WATCH', route.query.page);
  await load(route.query);
});

const load = async (query) => {
  try {
    page.value = await api.getList(query);
    console.log(page.value);

    if(pageRequest.types.length == 0){
      page.value.boardCategory.forEach(element => {
      pageRequest.types.push(element.type);
    });

    }
    
  } catch { }
};

load(pageRequest);

const typeChange = (evant) => {
  alert(evant.target.checked)
}

</script>

<template>
  <div>
    <h1 class="mb-3"><i class="fa-solid fa-paste"></i> 게시글 목록</h1>

    <div class="mt-4">
      <div class="form-label">type</div>      
      <div v-for="item in page.boardCategory" :key="item.type" class="form-check form-check-inline">
        <input v-model="pageRequest.types" checked class="form-check-input" type="checkbox" :id="item.type" :value="item.type">
        <label class="form-check-label" :for="item.type">{{item.name}}</label>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-3">
        <label class="form-label">검색 종류</label>
        <select v-model="pageRequest.searchType" class="form-select" required>
          <option value="">선택하세요.</option>
          <option value="all">전체</option>
          <option value="title">제목</option>
          <option value="content">내용</option>
          <option value="writer">글쓴이</option>
        </select>
      </div>
      <div class="col-8">
        <label class="form-label">검색어</label>
        <input v-model="pageRequest.searchValue" v-on:keyup.enter="searchChange" type="text" class="form-control" placeholder="내용을 입력하세요.">
      </div>
      <div class="col-1">
        <label  class="form-label"> &nbsp <span class="text-body-secondary"></span></label>
        <button class="form-control btn btn-primary" @click="searchChange">검색</button>
      </div>
    </div>


    <div class="mt-3 text-end">(총 {{ page.totalCount }}건)</div>

    <table class="table table-striped">
      <thead>
        <tr>
          <th style="width: 60px">No</th>
          <th style="width: 100px">Type</th>
          <th>제목</th>
          <th style="width: 150px">작성자</th>
          <th style="width: 120px">작성일</th>
          <th style="width: 80px">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="article in articles" :key="article.bno">
          <td>{{ article.bno }}</td>
          <td>{{ page.boardCategory.filter((value)=>{
                    if(value.type == article.type){
                      return article.type
                    }
                 })[0].name }}</td>
          <td>
            <router-link :to="{ name: 'board/detail', params: { no: article.bno }, query: route.query }"> {{
              article.title }} </router-link>
          </td>
          <td>{{ article.memberName }}({{ article.memberId }})</td>
          <td>{{ moment(article.regDate).format('YYYY-MM-DD') }}</td>
          <td>{{ article.readCount }}</td>
        </tr>
      </tbody>
    </table>

    <div class="my-5 d-flex">
      <div class="flex-grow-1 text-center">
        <!-- 페이지 네이션 -->
        <vue-awesome-paginate :total-items="page.totalCount" :items-per-page="pageRequest.amount" :max-pages-shown="5"
          :show-ending-buttons="true" v-model="pageRequest.page" @click="handlePageChange">
          <template #first-page-button><i class="fa-solid fa-backward-fast"></i></template>
          <template #prev-button><i class="fa-solid fa-caret-left"></i></template>
          <template #next-button><i class="fa-solid fa-caret-right"></i></template>
          <template #last-page-button><i class="fa-solid fa-forward-fast"></i></template>
        </vue-awesome-paginate>
      </div>
      <div>
        <router-link :to="{ name: 'board/create', query: route.query }" class="btn btn-primary"><i
            class="fa-solid fa-pen-to-square"></i> 글 작성</router-link>
      </div>
    </div>
  </div>
</template>
