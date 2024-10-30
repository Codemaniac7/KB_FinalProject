<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

const searchQuery = ref('');
const allFunds = ref([]); // 전체 펀드 데이터
const displayedFunds = ref([]); // 현재 페이지에 표시할 펀드 데이터
const currentPage = ref(1);
const totalPages = ref(1);
const pageSize = ref(20); // 페이지당 항목 수
const selectedGrade = ref(null); // 선택된 등급

const router = useRouter();

const navigateToFund = (grade) => {
    updateGradeFilter(grade); // 상태 업데이트
      // 라우터로 이동
      router.push({ path: '/funds', query: { grade } }); // this 대신 router 사용
    };

const selectedCategory = ref(null); // 선택된 카테고리
const selectedGradeButton = ref(null); // 선택된 등급 버튼 상태
const selectedCategoryButton = ref(null); // 선택된 카테고리 버튼 상태


const isLoading = ref(false);
const error = ref(null);

// 초기 정렬 키와 정렬 순서 설정
const sortKey = ref('suikRt1'); // 예: 1개월 수익률
const sortOrder = ref('desc');   // 내림차순

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  const year = date.getFullYear() % 100;
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${year}.${month}.${day}`;
};

const filterFunds = () => {
  const isReturnAvailable = (returnValue) => {
    return returnValue !== null && returnValue !== undefined && returnValue !== '' && returnValue !== '없음';
  };

  const filteredFunds = allFunds.value.filter(fund => {
    const hasReturns = 
      isReturnAvailable(fund.suikRt1) ||
      isReturnAvailable(fund.suikRt3) ||
      isReturnAvailable(fund.suikRt6) ||
      isReturnAvailable(fund.suikRt12);

    if (!selectedCategory.value) {
      return hasReturns; // 카테고리 선택 안 된 경우 수익률이 있는 펀드만 반환
    }

    const fundName = fund.fundFnm.toLowerCase();
    const category = selectedCategory.value.toLowerCase();

    switch (category) {
      case 'stock':
        return fundName.includes('주식') && hasReturns;
      case 'bond':
        return fundName.includes('채권') && hasReturns;
      case 'mixed':
        return fundName.includes('혼합') && hasReturns;
      case 'etc':
        return hasReturns; // 기타 카테고리도 수익률이 있어야 반환
      default:
        return hasReturns; // 기타도 수익률이 존재해야 반환
    }
  });

  return filteredFunds;
};



// 카테고리 버튼 클릭 시 필터링과 페이지네이션 업데이트
const updateCategoryFilter = (category) => {
  selectedCategoryButton.value = category; // 선택된 카테고리 버튼 업데이트
  selectedCategory.value = category;
  searchQuery.value = '';
  fetchAllFunds(); // 카테고리 업데이트 후 데이터 새로 불러오기
};

// fetchAllFunds 함수에서 필터링된 데이터로 displayedFunds 업데이트
const fetchAllFunds = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await axios.get('/api/funds', {
      params: {
        grade: selectedGrade.value,
        category: selectedCategory.value,
      },
    });
    allFunds.value = response.data;

    console.log('Fetched Funds:', allFunds.value); // 여기 추가


    // 필터링된 데이터로 displayedFunds 업데이트
    displayedFunds.value = filterFunds();

    totalPages.value = Math.ceil(displayedFunds.value.length / pageSize.value);
    currentPage.value = 1;
    paginateFunds(); // 첫 페이지 데이터를 표시
  } catch (err) {
    error.value = '펀드 데이터를 불러오는 데 실패했습니다.';
    console.error(err);
  } finally {
    isLoading.value = false;
  }
};


const searchFundsFunc = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await axios.get('/api/funds/search', {
      params: {
        keyword: searchQuery.value,
      },
    });
    allFunds.value = response.data;

    sortFunds(sortKey.value, false);

    totalPages.value = Math.ceil(allFunds.value.length / pageSize.value);
    currentPage.value = 1;
    paginateFunds();
  } catch (err) {
    error.value = '검색 결과를 불러오는 데 실패했습니다.';
    console.error(err);
  } finally {
    isLoading.value = false;
  }
};

// 전체보기 버튼 클릭 시 등급과 카테고리에 따른 데이터 가져오기
const viewAll = () => {
  selectedGradeButton.value = null; // 선택된 등급 초기화
  selectedCategoryButton.value = null; // 선택된 카테고리 초기화
  selectedGrade.value = null; // 선택된 등급 초기화
  selectedCategory.value = null; // 선택된 카테고리 초기화
  fetchAllFunds(); // 전체 데이터 가져오기
};

const updateGradeFilter = (grade) => {
    selectedGradeButton.value = grade; // 선택된 등급 버튼 업데이트
    selectedGrade.value = grade;
    searchQuery.value = '';
    fetchAllFunds();
};

const goToFund  = (fundCd) => {
  window.location.href = `https://www.samsungfund.com/fund/product/view.do?id=${fundCd}`;
};


// paginateFunds 함수를 수정하여 인자를 받을 수 있도록
const paginateFunds = (funds = allFunds.value) => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  displayedFunds.value = funds.slice(start, end); // 인자로 받은 데이터로 표시
};

const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    paginateFunds();
  }
};

const goToPreviousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
    paginateFunds();
  }
};

const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
    paginateFunds();
  }
};

const sortFunds = (key, toggleOrder = true) => {
  if (sortKey.value === key) {
    if (toggleOrder) {
      sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
    }
  } else {
    sortKey.value = key;
    sortOrder.value = 'desc';
  }

  allFunds.value = [...allFunds.value].sort((a, b) => {
    let aVal = a[key];
    let bVal = b[key];

    let comparison = 0;

    if (key === 'fundFnm') {
      aVal = aVal.startsWith('삼성') ? aVal.slice(2).trim() : aVal;
      bVal = bVal.startsWith('삼성') ? bVal.slice(2).trim() : bVal;
      comparison = aVal.localeCompare(bVal, 'ko');
    } else if (typeof aVal === 'string' && typeof bVal === 'string') {
      comparison = aVal.localeCompare(bVal, 'ko');
    } else if (typeof aVal === 'number' && typeof bVal === 'number') {
      comparison = aVal - bVal;
    } else {
      comparison = String(aVal).localeCompare(String(bVal), 'ko');
    }

    return sortOrder.value === 'asc' ? comparison : -comparison;
  });

  paginateFunds();
};


const sortIconClass = (key) => {
  if (sortKey.value === key) {
    return sortOrder.value === 'asc' ? 'ai ai-chevron-up' : 'ai ai-chevron-down';
  }
  return ''; // 다른 열에는 아이콘을 표시하지 않음
};

// 정렬 버튼의 클래스를 동적으로 결정하는 함수
const getSortButtonClass = (key) => {
  if (sortKey.value === key) {
    return 'sort-button-active';
  } else {
    return 'sort-button-inactive';
  }
};

// 정렬 상태에 따른 툴팁을 반환하는 함수
const getSortTooltip = (key) => {
  if (sortKey.value === key) {
    return sortOrder.value === 'asc' ? '오름차순 활성화' : '내림차순 활성화';
  } else {
    return sortOrder.value === 'asc' ? '오름차순 비활성화' : '내림차순 비활성화';
  }
};

const getRateClass = (value) => {
  if (value > 0) return 'text-danger';
  if (value < 0) return 'text-primary';
  return 'text-muted'; // 회색 (없음)
};

const visiblePages = computed(() => {
  const pages = [];
  const maxVisible = 5; // 최대 5개 페이지 버튼
  let start = Math.max(currentPage.value - 2, 1);
  let end = Math.min(start + maxVisible - 1, totalPages.value);

  if (end - start < maxVisible - 1) {
    start = Math.max(end - maxVisible + 1, 1);
  }

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

const route = useRoute();

onMounted(() => {
  const grade = route.query.grade; // 쿼리 파라미터에서 grade 가져오기
  fetchAllFunds();
});
</script>

<template>
  <div class="bc">
    <br><br><br><br>
    <div class="container">
      <h1 class="text-center">펀드 찾기</h1>
      <br><br><br>
      <router-link class="fundThemeBox" to="/fundTheme">
        <div style="display:flex;">
          <h3 class="fundTheme">🎪테마별 펀드</h3>
        </div>
      </router-link>
      <br>
      <div class="text-center">
        <h2 class="d-inline search">상품 검색</h2>
        <input
            class="searchBar"
            type="text"
            placeholder="검색어를 입력해주세요"
            v-model="searchQuery"
            @keyup.enter="searchFundsFunc"
        />
        <button class="searchBtn" type="button" @click="searchFundsFunc">검색</button>
        <button class="searchBtn" type="button" @click="viewAll">전체보기</button>
      </div>

      <div>
  <div class="filter-container" style="display: flex; justify-content: space-between; align-items: center;">
    <div style="display: flex; align-items: center;">
      <h2 class="d-inline search">등급</h2>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedGradeButton === '1-2' }" 
          @click="navigateToFund('1-2')"
      >
          고위험
      </button>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedGradeButton === '3-4' }" 
          @click="navigateToFund('3-4')"
      >
          중위험
      </button>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedGradeButton === '5-6' }" 
          @click="navigateToFund('5-6')"
      >
          저위험
      </button>
      
    </div>

    <div style="display: flex; align-items: center; text-align: right;">
      <h2 class="d-inline search">카테고리</h2>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedCategoryButton === 'stock' }" 
          @click="updateCategoryFilter('stock')"
      >
          주식
      </button>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedCategoryButton === 'bond' }" 
          @click="updateCategoryFilter('bond')"
      >
          채권
      </button>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedCategoryButton === 'mixed' }" 
          @click="updateCategoryFilter('mixed')"
      >
          혼합
      </button>
      <button 
          class="filterBtn" 
          :class="{ 'active': selectedCategoryButton === 'etc' }" 
          @click="updateCategoryFilter('etc')"
      >
          기타
      </button>
    </div>
  </div>
</div>
<span style="color:dimgray; font-size: 14px;"> * 등급은 6등급까지 존재하며 0등급에 가까울수록 고위험 상품입니다.</span>

        <!-- 로딩 메시지 -->
        <div v-if="isLoading" class="loading-box">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">데이터를 불러오는 중...</span>
            </div>
        </div>


      <!-- 에러 메시지 -->
      <div v-if="error" class="text-center text-danger mt-4">
        <p>{{ error }}</p>
      </div>

      <div v-if="!isLoading && !error" class="fund text-center">
        <div class="fundSearchResult text-center">
          <table class="fundSearchResultTable text-center">
            <thead>
            <tr>
              <th
                  :class="getSortButtonClass('fundFnm')"
                  style="width: 40%; cursor: pointer;"
                  rowspan="2"
                  @click="sortFunds('fundFnm')"
                  :title="getSortTooltip('fundFnm')"
              >
                상품명
                <i v-if="sortKey === 'fundFnm'" :class="sortIconClass('fundFnm')" style="font-size: 2rem;"></i>
              </th>
              <th style="width: 10%;">기준가</th>
              <th style="width: 35%;" colspan="4">수익률(%)</th>
              <th style="width: 6%;" rowspan="2">총 보수(연)</th>
              <th style="width: 6%;" rowspan="2">기준일</th>
            </tr>
            <tr>
              <th>순자산(억원)</th>
              <th
                  :class="getSortButtonClass('suikRt1')"
                  style="width: 3%; cursor: pointer;"
                  @click="sortFunds('suikRt1')"
                  :title="getSortTooltip('suikRt1')"
                  id="sortBtn"
              >
                1개월
                <i v-if="sortKey === 'suikRt1'" :class="sortIconClass('suikRt1')" style="font-size: 2rem;"></i>
              </th>
              <th
                  :class="getSortButtonClass('suikRt3')"
                  style="width: 3%; cursor: pointer;"
                  @click="sortFunds('suikRt3')"
                  :title="getSortTooltip('suikRt3')"
                  id="sortBtn"
              >
                3개월
                <i v-if="sortKey === 'suikRt3'" :class="sortIconClass('suikRt3')" style="font-size: 2rem;"></i>
              </th>
              <th
                  :class="getSortButtonClass('suikRt6')"
                  style="width: 3%; cursor: pointer;"
                  @click="sortFunds('suikRt6')"
                  :title="getSortTooltip('suikRt6')"
                  id="sortBtn"
              >
                6개월
                <i v-if="sortKey === 'suikRt6'" :class="sortIconClass('suikRt6')" style="font-size: 2rem;"></i>
              </th>
              <th
                  :class="getSortButtonClass('suikRt12')"
                  style="width: 3%; cursor: pointer;"
                  @click="sortFunds('suikRt12')"
                  :title="getSortTooltip('suikRt12')"
                  id="sortBtn"
              >
                1년
                <i v-if="sortKey === 'suikRt12'" :class="sortIconClass('suikRt12')" style="font-size: 2rem;"></i>
              </th>
            </tr>
            </thead>
          </table>
        </div>
        <br>
        <div class="fundSearchResult text-center">
          <table class="fundSearchResultTable text-center">
            <tbody v-for="fund in displayedFunds" :key="fund.id">
            <tr>
              <td class="fundName" style="width: 40%;" rowspan="2" @click="goToFund(fund.fundCd)">
                <span v-for="(part, index) in fund.fundFnm.split('[')" :key="part.index">
                  <h4 v-if="index === 0">{{ part }}</h4>
                  <h5 style="color: grey;" v-else>
                    <br/>[{{ part }}
                  </h5>
                </span>
              </td>
              <td style="width: 10%;"><h4>{{ fund.gijunGa }}</h4></td>
              <td style="width: 35%;" colspan="4">
                <div class="grade"><h4>{{ fund.investGrade }}등급</h4></div>
              </td>
              <td style="width: 6%;" class="rate" rowspan="2"><h4>{{ fund.feeTot }}</h4></td>
              <td style="width: 6%;" rowspan="2"><h5>{{ formatDate(fund.gijunYmd) }}</h5></td>
            </tr>
            <tr>
              <td><h4>{{ fund.navTot }}</h4></td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt1)">
                  {{ fund.suikRt1 != null ? fund.suikRt1 : '없음' }}
                </h4>
              </td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt3)">
                  {{ fund.suikRt3 != null ? fund.suikRt3 : '없음' }}
                </h4>
              </td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt6)">
                  {{ fund.suikRt6 != null ? fund.suikRt6 : '없음' }}
                </h4>
              </td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt12)">
                  {{ fund.suikRt12 != null ? fund.suikRt12 : '없음' }}
                </h4>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <nav aria-label="Page navigation example" v-if="totalPages > 1">
      <ul class="pagination justify-content-center mt-4">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <a @click.prevent="goToPreviousPage" class="page-link" aria-label="Prev page">
            <i class="ai ai-arrow-left fs-4"></i>
          </a>
        </li>
        <li
            class="page-item"
            v-for="page in visiblePages"
            :key="page"
            :class="{ active: page === currentPage }"
        >
          <a @click.prevent="goToPage(page)" class="page-link">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <a @click.prevent="goToNextPage" class="page-link" aria-label="Next page">
            <i class="ai ai-arrow-right fs-4"></i>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>


<style scoped>
.fundThemeBox {
  text-decoration: none;
}

.fundTheme {
  margin-right: auto;
  padding: 20px;
  color: black;
  font-size: 30px;
}

.fundName{
  font-family: J3;
  color: rgba(68, 140, 116, 1);
  cursor: pointer;
}

.grade {
  border: 1px solid lightgrey;
  border-radius: 30px;
  color: rgb(121, 121, 121);
  background-color: white;
  width: 60px;
  margin: 0 auto;
}



.fundSearchResultTable th {
  font-size: 17px;
}

.sortIconClass {
  font-size: 25px;
}



thead tr th {
  border-left: none;

}

thead tr:nth-child(2) th:last-child {
  border-left: none;
  border-bottom: none;
}

thead tr:last-child th {
  border-bottom: none;
  border-right: none;
}

thead tr th:first-child {
  border-bottom: none;
}

thead tr:first-child th:last-child {
  border-bottom: none;
  border-right: none;
}


thead tr:last-child th:last-child {
  border: none;
}

.fundSearchResult {
  border-radius: 30px;
  padding: 10px;
  background-color: white;
}
.fundSearchResult table{
  border-radius: 30px;
}
#sortBtn {
  height: 40px;
}

.fundSearchResultTable {
  width: 100%;
  height: 100px;
  table-layout: fixed;
  text-align: start;
  background-color: white;
  border-collapse: separate;
  border-spacing: 0;
}


.loading-box {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
  padding: 30px;
  margin: 20px;
  text-align: center;
}

.bc {
  padding: 50px;
  background-color: #F9FAFB;
  border-radius: 30px;
  font-family: J3;
}

.searchBar {
  width: 50%;
  max-width: 500px;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 0 20px;
  margin: 10px 0;
  font-size: 20px;
}

.fund {
  margin-top: 30px;
  border-radius: 40px;
  padding: 20px;
}

.searchBtn {
  font-size: 20px;
  width: 100px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
  cursor: pointer;
  transition: background-color 0.3s;
}

.searchBtn:active,
.searchBtn:hover {
  background-color: #e5e4e4;
  color: black;
}

.search {
  margin: 30px;
}

.page-link {
  color: black;
}

.active > .page-link {
  background-color: rgba(68, 140, 116, 1);;
  border: none;
  color: white;
}

tbody:hover {
  background-color: rgba(68, 140, 116, 0.2);
  transition: background-color 0.3s;

}
tbody{

}

tbody td {
  padding: 10px;
  transition: all 0.3s;

}

.page-link:hover {
  color: white;
  background-color: rgba(68, 140, 116, 1);;
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);;
  --bs-pagination-hover-color: rgb(255, 255, 255);
}

.text-danger {
  color: red;
}

.text-primary {
  color: blue;
}

.text-muted {
  color: grey;
}

.icon-large {
  font-size: 2rem;
}

.icon-medium {
  font-size: 1.5rem;
}

.icon-small {
  font-size: 1rem;
}

.sort-button-active {
  background-color: #F9FAFB;
  color: black;
  border-radius: 30px;
}

.sort-button-inactive {
  background-color: #F9FAFB;
  color: black;
  border-radius: 30px;
}

.sort-button-active:hover,
.sort-button-inactive:hover {
  opacity: 0.8;
}


.filter-container {
    display: flex;
    align-items: center; /* 버튼 세로 정렬 */
    margin-bottom: 10px; /* 아래쪽 여백 */
}

.filter-container h2 {
    margin-right: 10px; /* 제목과 버튼 사이 여백 */
}

.filterBtn {
  font-size: 20px; /* 버튼 글꼴 크기 */
  height: 50px; /* 버튼 높이 */
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1); /* 기존 버튼 색상 */
  margin: 5px; /* 버튼 간 간격 조정 */
  cursor: pointer;
  transition: background-color 0.3s;
}

.filterBtn:last-child {
    margin-right: 0; /* 마지막 버튼 여백 제거 */
}

.filterBtn:active,
.filterBtn:hover {
  background-color: #e5e4e4; /* 호버 색상 */
  color: black;
}

.filterBtn.active {
  background-color: #e5e4e4; /* 선택된 버튼의 배경색 */
  color: black; /* 선택된 버튼의 글자색 */
}

.text-right {
  text-align: right;
}

li.page-item {
  cursor: pointer;
}

</style>
