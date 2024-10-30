<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { Swiper, SwiperSlide } from 'swiper/vue'; // Swiper와 SwiperSlide import
import 'swiper/swiper-bundle.css';
import { useAuthStore } from '@/stores/auth';

const auth = useAuthStore();
const savings = ref([]);
const topSavings = ref([]);
const loading = ref(true);
const currentPage = ref(1);
const totalPages = ref(1); // 총 페이지 수
const totalCount = ref(0); // 총 페이지 수
const expanded = ref(false);
const primaryBankExpanded = ref(false);
const otherBankExpanded = ref(false);
const router = useRouter();

const searchTerm = ref('');
const selectedBanks = ref([]);
const selectedDuration = ref(null);
const selectedInterestType = ref(null);

const bankList = ref([]);
const primaryBankList = ref([]);
const otherBankList = ref([]);
const termList = ref([]);
const interestTypeList = ref([]);

const fetchdepositCategory = async () => {
  try {
    const response = await axios.get('/api/deposit/category');
    if (response.data) {
      bankList.value = response.data.bankList.map(bank => bank.bankName);

      // 분류 로직
      response.data.bankList.forEach(bank => {
        if (bank.bankType === 1) {
          primaryBankList.value.push(bank.bankName);
        } else {
          otherBankList.value.push(bank.bankName);
        }
      });
      console.log(primaryBankList);
      console.log(otherBankList);
      termList.value = response.data.saveTerm;
      interestTypeList.value = response.data.interestType;
    }
  } catch (error) {
    console.error('카테고리 받아오기 실패', error);
  }
};

const getPaginationPages = () => {
  const pages = [];
  const maxPagesToShow = 5;
  const startPage = Math.max(1, currentPage.value - Math.floor(maxPagesToShow / 2));
  const endPage = Math.min(totalPages.value, startPage + maxPagesToShow - 1);

  for (let i = startPage; i <= endPage; i++) {
    pages.push(i);
  }
  return pages;
};


const goToDetail = (savingId) => {
  if (savingId) {
    router.push({ name: 'savingDetail', params: { savingId } });
  } else {
    console.warn('Invalid savingId provided:', savingId);
  }
};

const removeFilter = (arrayRef, value) => {
  const index = arrayRef.indexOf(value);
  if (index !== -1) {
    arrayRef.splice(index, 1);
  }
};


function toggleSelection(arrayRef, value) {
  const index = arrayRef.value.indexOf(value);
  if (index === -1) {
    arrayRef.value.push(value);
  } else {
    arrayRef.value.splice(index, 1);
  }
}

const selectAll = (arrayRef, items) => {
  const isAllSelected = items.every(item => arrayRef.value.includes(item));
  if (isAllSelected) {
    arrayRef.value = arrayRef.value.filter(item => !items.includes(item));
  } else {
    arrayRef.value.push(...items.filter(item => !arrayRef.value.includes(item)));
  }
};

const selectBank = (bank) => {
  toggleSelection(selectedBanks, bank);
};

const selectDuration = (duration) => {
  selectedDuration.value = selectedDuration.value === duration ? null : duration;
};

const selectInterestType = (interestRateType) => {
  selectedInterestType.value = selectedInterestType.value === interestRateType ? null : interestRateType;
};

const resetInterestType = () => {
  selectedInterestType.value = null; 
  fetchSavings();
};

const resetDuration = () => {
  selectedDuration.value = null; 
  fetchSavings();
};

const selectAllBanksInPrimaryBankList = () => selectAll(selectedBanks, primaryBankList.value);

const selectAllBanksInOtherBankList = () => selectAll(selectedBanks, otherBankList.value);

const highlightInput = (event) => {
  event.target.classList.add('highlight');
};

const resetInput = (event) => {
  event.target.classList.remove('highlight');
};

const fetchTopSavings = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/saving/top', {
      headers: {
      Authorization: `Bearer ${auth.getToken()}` 
      }
    });
    if (response.data && response.data.length > 0) {
      topSavings.value = response.data;
      console.log(topSavings);
    } else {
      topSavings.value = [];
      console.warn('데이터가 없습니다.');
    }
  } catch (error) {
    console.error('예금 상품 목록을 가져오는 중 오류 발생:', error);
    topSavings.value = [];
  } finally {
    loading.value = false;
  }
};

const LIST_LIMIT = 9;

const fetchSavings = async () => {
  loading.value = true;
  try {
    const filterRequest = {
      searchValue: searchTerm.value, 
      bankNameList: selectedBanks.value, 
      saveTerm: selectedDuration.value || 12, 
      page: currentPage.value, 
      interestRateType: selectedInterestType.value || "단리", 
    };
    console.log(filterRequest);
    const response = await axios.post('/api/saving', filterRequest);
    console.log(response.data);
    if (response.data && response.data.savings) {
      savings.value = response.data.savings;
      totalCount.value = response.data.totalCount; // 총 상품 개수 업데이트
      totalPages.value = Math.ceil(totalCount.value / LIST_LIMIT); // 페이지 수 계산
    } else {
      savings.value = [];
      totalCount.value = 0; // 검색 결과가 없을 경우 0으로 설정
      totalPages.value = 1; // 페이지 수 1로 설정
      console.warn('검색 결과가 없습니다.');
    }
  } catch (error) {
    console.error('예금 상품 목록을 가져오는 중 오류 발생:', error);
    savings.value = [];
    totalCount.value = 0; // 오류 발생 시 총 개수를 0으로 설정
    totalPages.value = 1; // 오류 발생 시 페이지 수 1로 설정
  } finally {
    loading.value = false;
  }
};

onMounted(() => {  
  fetchTopSavings();
  fetchdepositCategory();
  fetchSavings();
});

watch(searchTerm, () => {
  currentPage.value = 1;
  fetchSavings(); // 검색어가 변경될 때마다 데이터를 가져옴
});

// 필터링 배열이 변경될 때도 페이지를 1로 설정하고 데이터 다시 가져오기
watch([selectedBanks, selectedDuration, selectedInterestType], () => {
  currentPage.value = 1;
  fetchSavings(); // 필터가 변경될 때마다 데이터를 가져옴
});

const toggleText = () => {
  expanded.value = !expanded.value; // 상태 반전
  if (expanded.value) {
    fetchSavings();
  }
};

const togglePrimaryBankList = () => {
  primaryBankExpanded.value = !primaryBankExpanded.value; // 상태 반전
  if (primaryBankExpanded.value) {
    fetchSavings();
  }
};

const toggleOtherBankList = () => {
  otherBankExpanded.value = !otherBankExpanded.value; // 상태 반전
  if (otherBankExpanded.value) {
    fetchSavings();
  }
};

const changePage = (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage; // 현재 페이지 업데이트
    fetchSavings(); // 새 페이지에 맞는 데이터 가져오기
  }
};

// 텍스트 자르기 함수
const truncateText = (text, maxLength) => {
  if (typeof text !== 'string') return '';
  return text.length > maxLength ? text.slice(0, maxLength) + '...' : text;
};
</script>

<template>
  <div class="container text-center animate-on-load">
    <br><br>
    <h1 class="d-inline">적금 </h1>
    <p class="d-inline">꿈을 모아서</p>
    <br><br>
    <div class="savingBest">
      <div class="text-start"  v-if="!auth.investType || auth.investType === ''">
        <h2>고객님들이 선택한 BEST 인기상품</h2>
        <h4>가장 많이 사랑 받은 적금 상품</h4>
        <h5 style="color: rgba(68, 140, 116, 1);">가장 적은 개월 수에 많은 금리</h5><br><br>
      </div>
      <div class="text-start" v-else>
        <h2>{{ auth.name }} 고객님을 위한 추천 상품</h2>
        <h4>{{ auth.investType }}성향의 고객님들이 많이 조회한 적금 상품이에요.</h4>
        <h5 style="color: rgba(68, 140, 116, 1);">다양한 상품을 찾아 보세요.</h5><br><br>
      </div>
      <!-- 로딩 메시지 -->
      <div v-if="loading" class="loading-box">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">데이터를 불러오는 중...</span>
        </div>
      </div>
      <div v-else class="itemBoxDiv">
        <ul>
          <li v-for="(topSaving, index) in topSavings" :key="topSaving.savingId"
              class="d-inline-block itemBox text-start"
              :style="{ animationDelay: (index * 0.2 + 0.1) + 's' }">
            <div class="ranking">
              <span>
                {{ index === 0 ? '🥇' : index === 1 ? '🥈' : '🥉' }}
              </span>
            </div>
            <div class="goToDetail" @click="goToDetail(topSaving.savingId)">
              <div class="savingDepositMethod">
                <div class="savingMethod text-center">{{ topSaving.joinWay }}</div>
                <br>
                <div class="bankLogo d-inline">
                  <a :href="topSaving.bank?.bankUrl">
                    <img
                        style="height: 30px;"
                        :src="`/img/bank/${topSaving.bankId}.png`"
                        alt="Bank Logo"
                        @error="handleImageError"
                    />
                  </a>
                  <h3 class="d-inline">{{ topSaving.bank.bankName }}</h3>
                </div>
              </div>
              <br/>
              <div style="width: 300px;">
                <h4 class="savingName">{{ truncateText(topSaving.savingName, 15) }}</h4>
                <br/>
              </div>
              <div>
                <h3 style="font-weight: 600">{{ topSaving.interestRateList.interestRateType }}</h3>
              </div>
              <div style="display: flex; justify-content: space-between; color: grey">
                <div>
                  <li v-for="(rate, index) in topSaving.interestRateList" :key="index">
                    <h3>{{ rate.interestRateType }}</h3>
                    <h3 class="d-inline" style="color: rgba(68, 140, 116, 1);">
                      {{ rate.savingTerm }}</h3>
                    <h3 class="d-inline">개월</h3>
                  </li>
                </div>
                <div>
                  <h3>최고금리</h3>
                  <ul>
                    <li v-for="(rate, index) in topSaving.interestRateList" :key="index">
                      <h3>{{ rate.interestMaxRate }}%</h3>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <br><br>
    <div class="text-start">
      <h4 class="search">상품 검색</h4>
      <form @submit.prevent="fetchSavings">
        <div class="searchContainer">
          <input
              class="searchBar"
              type="text"
              placeholder="검색어를 입력해주세요"
              v-model="searchTerm"
              @focus="highlightInput"
              @blur="resetInput"
          />
          <button class="searchBtn" type="submit">검색</button>
        </div>

        <br>
        <div v-if="expanded" class="additional-info">
          <ul class="filterBar">
            <li>
              <h4 style="font-weight: 700;">은행</h4>
                <br>
                <div>
                  <h4 style="font-weight: 700;" @click="togglePrimaryBankList">
                    1금융권 은행
                    <i :class="primaryBankExpanded ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
                  </h4>
                  <div class="filter d-inline">
                    <input type="button" id="primary-all" @click="() => {selectAllBanksInPrimaryBankList(); fetchSavings();}" />
                    <label for="primary-all">전체</label>
                  </div>
                  <div v-show="primaryBankExpanded">
                    <div class="filter d-inline" v-for="(bank, index) in primaryBankList" :key="'primary' + index">
                      <input
                        type="checkbox"
                        :id="'primary-bank' + index"
                        @change="() => { selectBank(bank); fetchSavings(); }"
                        :checked="selectedBanks.includes(bank)"
                      />
                      <label :for="'primary-bank' + index" :class="{ 'selected': selectedBanks.includes(bank) }">{{ bank }}</label>
                    </div>
                  </div>
                </div>
                <div>
                  <h4 style="font-weight: 700;" @click="toggleOtherBankList">
                    기타 은행
                    <i :class="otherBankExpanded ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
                  </h4>
                  <div class="filter d-inline">
                    <input type="button" id="other-all" @click="() => {selectAllBanksInOtherBankList(); fetchSavings();}" />
                    <label for="other-all">전체</label>
                  </div>
                  <div v-show="otherBankExpanded">
                    <div class="filter d-inline" v-for="(bank, index) in otherBankList" :key="'other' + index">
                      <input
                        type="checkbox"
                        :id="'other-bank' + index"
                        @change="() => { selectBank(bank); fetchSavings(); }"
                        :checked="selectedBanks.includes(bank)"
                      />
                      <label :for="'other-bank' + index" :class="{ 'selected': selectedBanks.includes(bank) }">{{ bank }}</label>
                    </div>
                  </div>
                </div>
            </li>
            
            <!-- 저축 기간 필터 -->
            <li>
              <h4 style="font-weight: 700;">저축 기간</h4>
              <div class="filter d-inline" v-for="(duration, index) in termList" :key="index">
                <input
                  type="checkbox"
                  :id="'duration' + index"
                  @change="(event) => { selectDuration(duration); fetchSavings(); }"
                  :checked="selectedDuration === duration" 
                />
                <label :for="'duration' + index" :class="{ 'selected': selectedDuration === duration }">
                  {{ duration }}개월
                </label>
              </div>
            </li>
            <li>
              <h4 style="font-weight: 700;">이자 유형</h4>
              <div class="filter d-inline" v-for="(interestRateType, index) in interestTypeList" :key="index">
                <input
                  type="checkbox"
                  :id="'interestRateType' + index"
                  @change="(event) => { selectInterestType(interestRateType); fetchSavings(); }"
                  :checked="selectedInterestType === interestRateType" 
                />
                <label :for="'interestRateType' + index" :class="{ 'selected': selectedInterestType === interestRateType }">
                  {{ interestRateType }}
                </label>
              </div>
            </li>
          </ul>
          <div class="checkedFilterBox">
            <div
              class="selected-filters"
              v-if="selectedBanks.length || selectedDuration || selectedInterestType"
            >
              <Swiper
                :space-between="10"
                :loop="false"
                :slides-per-view="5.1"
                :centered-slides="false"
                :edge-swipe-detection="true"
                :pagination="{ clickable: true }"
              >
                <!-- 선택된 은행 -->
                <SwiperSlide v-for="(bank, index) in selectedBanks" :key="'bank' + index">
                  <div class="checkedFilter">
                    {{ bank }}
                    <button @click="removeFilter(selectedBanks, bank)">X</button>
                  </div>
                </SwiperSlide>
          
                <!-- 선택된 저축 기간 -->
                <SwiperSlide v-if="selectedDuration" :key="'duration' + selectedDuration">
                  <div class="checkedFilter">
                    {{ selectedDuration }}개월
                    <button @click="resetDuration">X</button>
                  </div>
                </SwiperSlide>
          
                <!-- 선택된 이자 유형 -->
                <SwiperSlide v-if="selectedInterestType" :key="'type' + selectedInterestType">
                  <div class="checkedFilter">
                    {{ selectedInterestType }}
                    <button @click="resetInterestType">X</button>
                  </div>
                </SwiperSlide>
              </Swiper>
            </div>
          </div>
        </div>
        <!-- 토글 버튼 -->
        <div class="toggle-wrapper">
          <br>
          <span class="more-text" v-if="!expanded" @click="toggleText">
            상세 검색 열기<i class="ai-chevron-down"></i>
          </span>
          <span class="less-text" v-if="expanded" @click="toggleText">
            상세 검색 닫기<i class="ai-chevron-up"></i>
          </span>
        </div>

      <!-- 검색 및 상세 검색 폼 끝 -->
      </form>
    </div>

    <br><br>
    <div class="savingsContent">
      <div class="text-start">
        <h2 class="d-inline"><b>검색 결과</b> 테마상품</h2>
        <br><br><br>
      </div>
      <!-- 로딩 메시지 -->
      <div v-if="loading" class="loading-box">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">데이터를 불러오는 중...</span>
        </div>
      </div>
      <div v-else>
        <div v-if="savings.length === 0 && !loading">검색 결과가 없습니다.</div>
        <div v-else class="itemBoxDiv">
          <ul>
            <li v-for="saving in savings" :key="saving.savingId"
                class="d-inline-block itemBox text-start"
                :style="{ animationDelay: '0.1s' }">
              <div class="goToDetail" @click="goToDetail(saving.savingId)">
                <div class="savingDepositMethod">
                  <div class="depositMethod text-center">{{ saving.joinWay }}</div>
                  <br>
                  <div class="bankLogo  d-inline">
                    <a :href="saving.bank?.bankUrl">
                      <img
                          style="height: 30px;"
                          :src="`/img/bank/00${saving.bank.bankId}.png`"
                          alt="Bank Logo"
                          @error="handleImageError"
                      />
                    </a>
                    <h3 class=" d-inline">{{ saving.bank.bankName }}</h3>
                  </div>
                </div>
                <br/>
                <div style="width: 300px;"><h4 class="savingName">{{ saving.savingName }}</h4><br/></div>
                <div>
                  <h3 style="font-weight: 600">{{ saving.interestRateList.interestRateType }}</h3>
                  <h3 class="d-inline" style="color: rgba(68, 140, 116, 1);">
                    {{ saving.interestRateList.savingTerm }}</h3>
                  <h3 class="d-inline">개월</h3>
                </div>
                <div style="display: flex; justify-content: space-between; color: grey">
                  <div>
                    <h3>기본금리</h3>
                    <h3>{{ saving.interestRateList.interestRate }}%</h3>
                  </div>
                  <div>
                    <h3>최고금리</h3>
                    <h3>{{ saving.interestRateList.interestMaxRate }}%</h3>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <nav aria-label="Page navigation">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <button class="page-link" @click="changePage(currentPage - 1)" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </button>
          </li>
          <template v-for="page in getPaginationPages()" :key="page">
            <li class="page-item" :class="{ active: currentPage === page }">
              <button class="page-link" @click="changePage(page)">{{ page }}</button>
            </li>
          </template>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <button class="page-link" @click="changePage(currentPage + 1)" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </button>
          </li>
        </ul>
      </nav>
    </div>
    <br><br>
  </div>
</template>

<style scoped>
.bankLogo h3{
  margin: 10px;
  vertical-align: middle;
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

.selected-filters {
  overflow: hidden;
}

.checkedFilter {
  background-color: rgba(68, 140, 116, 1);
  color: white;
  font-size: 12px;
  border: 1px solid #bebebe;
  border-radius: 20px;
  padding: 5px;
  width: 160px;
  height: 40px;
  text-align: start;
  margin-left: 20px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.checkedFilter button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  color: white;
}

li {
  list-style: none;
}

@keyframes riseUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-on-load > * {
  opacity: 0;
  transform: translateY(20px);
  animation: riseUp 1s ease-out forwards;
}

.animate-on-load > *:nth-child(1) {
  animation-delay: 0.1s;
}

.animate-on-load > *:nth-child(2) {
  animation-delay: 0.2s;
}

.animate-on-load > *:nth-child(3) {
  animation-delay: 0.3s;
}

.animate-on-load > *:nth-child(4) {
  animation-delay: 0.4s;
}

.animate-on-load > *:nth-child(5) {
  animation-delay: 0.5s;
}

.animate-on-load > *:nth-child(6) {
  animation-delay: 0.6s;
}

.animate-on-load > *:nth-child(7) {
  animation-delay: 0.7s;
}

.animate-on-load > *:nth-child(8) {
  animation-delay: 0.8s;
}

.animate-on-load > *:nth-child(9) {
  animation-delay: 0.9s;
}

.animate-on-load > *:nth-child(10) {
  animation-delay: 1s;
}

.ranking {
  font-size: 30px;
  font-weight: bold;
  color: rgba(68, 140, 116, 1);
  margin-top: -25px;
}

.goToDetail {
  cursor: pointer;
}

.savingName {
  color: rgba(68, 140, 116, 1);
  font-weight: 700;
  text-align: start;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowing text */
  text-overflow: ellipsis; /* Show ellipsis when text overflows */
}

.savingDepositMethod {
  position: relative;
}

.savingMethod {
  position: absolute;
  top: -20px;
  right: 5px;
  border-radius: 20px;
  border: 1px solid lightgrey;
  width: 100px;
}

.depositMethod {
  position: absolute;
  top: -10px;
  right: 5px;
  border-radius: 20px;
  border: 1px solid lightgrey;
  width: 100px;
}

.savingRank > tbody tr {
  padding: 20px;
}

.checkedFilterBox {
  margin-top: 10px;
}

.selected-filters {
  text-align: start;
  width: 850px;
  margin-left: 200px;
}

.itemBoxDiv {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.itemBox {
  padding: 20px;
  margin: 20px;
  max-width: 400px;
  box-sizing: border-box;
}


.checkedFilter button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  color: white;
}

.filter label {
  width: auto; /* 기존 고정 너비 제거 */
  min-width: 100px; /* 최소 너비 설정 */
  max-width: 200px; /* 최대 너비 설정하여 너무 길어지지 않도록 */
  padding: 10px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size: 15px;
  text-align: center;
  margin: 5px;
  cursor: pointer;
  flex-grow: 1; /* Flexbox를 사용하여 가용 공간을 활용 */
  white-space: nowrap; /* 텍스트가 길어질 경우 줄바꿈 방지 */
  overflow: hidden; /* 넘치는 텍스트 숨기기 */
  text-overflow: ellipsis; /* 넘치는 텍스트에 ... 표시 */
}

.filter.d-inline-flex {
  display: flex;
  flex-wrap: wrap;
  gap: 10px; /* 필터 간 간격 추가 */
}

.filter .selected {
  background-color: rgba(68, 140, 116, 1) !important;
  color: white;
  border: 1px solid rgba(68, 140, 116, 1);
  transition: background-color 0.3s ease, color 0.3s ease;
}

.filter .selected:hover {
  background-color: white;
  border: 1px solid rgba(48, 120, 96, 1);
}

.filter label:hover {
  border: 1px solid rgba(68, 140, 116, 1);
  color: rgba(68, 140, 116, 1);
}

.filter .selected:hover {
  color: white;
}

input[type="checkbox"] {
  display: none;
}

input[type="button"] {
  display: none;
}

.toggle-wrapper {
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  color: black;
}

.filterBar {
  margin-left: 13%;
  list-style: none;
  width: 70%;
  margin-bottom: 5%;
}

.filterBtn {
  width: 130px;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size: 15px;
  margin: 5px;
}

.filterBtn:hover {
  color: rgba(68, 140, 116, 1);
  border: 1px solid rgba(68, 140, 116, 1);
  font-size: 16px;
}

.savingBest {
  background-color: rgba(67, 140, 116, 0.06);
  border-radius: 30px;
  padding: 50px;
}

.itemBox {
  border: 1px solid rgba(231, 236, 243, 1);
  border-radius: 30px;
  background-color: white;
  list-style: none;
}

.savingsContent {
  background-color: rgba(247, 249, 252, 1);
  border-radius: 30px;
  padding: 50px;
}

.themeItem {
  font-weight: 700;
}

.searchBar {
  width: 70%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 30px;
}

.searchBtn {
  width: 80px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
}

.searchBtn:active {
  background-color: lightgrey;
  color: black;
}

.search {
  margin-left: 15%;
  font-weight: 700;
}

.searchContainer {
  margin-left: 15%;
}

.container {
  width: 80%;
  font-family: J3;
}

.selected {
  background-color: rgba(68, 140, 116, 1) !important;
  color: white;
  border: 1px solid rgba(68, 140, 116, 1);
  transition: background-color 0.3s ease, color 0.3s ease
}

.selected:hover {
  background-color: white;
  border: 1px solid rgba(48, 120, 96, 1);
}

.page-link {
  color: #0c0c0c;
}

.active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  color: white;
  border: none;
}

.page-link:hover {
  color: rgba(68, 140, 116, 1);
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}

.pagination button {
  background-color: #e5e5e5;
}
</style>

