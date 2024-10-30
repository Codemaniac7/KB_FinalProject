<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';

const tradingViewContainer = ref(null);

const goldData = ref([]);
const page = ref(1);  // 현재 페이지 상태
const pageInfo = ref(null);  // 기본값을 null로 설정하여 초기 로딩 상태 반영
const loading = ref(false); // 로딩 상태

// API 호출하여 데이터 가져오기
const fetchGoldData = async () => {
  if (loading.value) return; // 이미 로딩 중이면 중복 요청 방지

  loading.value = true; // 로딩 시작
  try {
    const response = await axios.get(`/api/gold/list?page=${page.value}`);
    goldData.value = [...goldData.value, ...response.data.golds]; // 이전 데이터에 새 데이터 추가
    pageInfo.value = response.data.pageInfo || { maxPage: 1 }; // 받아온 pageInfo가 없을 경우 기본값 설정
  } catch (error) {
    console.error('Failed to fetch gold data:', error);
  } finally {
    loading.value = false; // 로딩 완료
  }
};

// "더보기" 버튼 클릭 시 페이지 증가 후 데이터 추가 요청
const loadMore = () => {
  if (page.value < pageInfo.value.maxPage) {
    page.value += 1;  // 페이지 증가
    fetchGoldData();  // 다음 페이지 데이터 불러오기
  }
}

// 컴포넌트가 마운트되면 데이터 가져오기
onMounted(() => {
  fetchGoldData();

  // TradingView 위젯 설정
  const script = document.createElement('script');
  script.type = 'text/javascript';
  script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js';
  script.async = true;
  script.innerHTML = JSON.stringify({
    width: 1200,
    height: 610,
    autosize: true,
    symbol: 'TVC:GOLD',
    interval: 'D',
    timezone: 'Etc/UTC',
    theme: 'light',
    style: '1',
    locale: 'kr',
    allow_symbol_change: true,
    calendar: false,
    support_host: 'https://www.tradingview.com',
  });

  tradingViewContainer.value.appendChild(script);
});
</script>



<template>
  <div class="bc">
  <div class="gold">
    <br><br>
      <h1 class="text-center">금</h1>
<!--    <div class="container text-center">-->
<!--      <br>-->
<!--      <div class="goldQuote">-->
<!--        <p class="fs-2 text-start">순금 시세</p>-->
<!--        <p class="text-end">현재 날짜</p>-->
<!--        <br>-->
<!--        <div class="goldQuoteText">-->
<!--          <br><br>-->
<!--          <table class="quoteTable">-->
<!--            <thead>-->
<!--              <tr>-->
<!--                <td><h3>내가 살 때(VAT포함)</h3></td>-->
<!--                <td><h3>내가 팔 때(VAT포함)</h3></td>-->
<!--              </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--              <tr>-->
<!--                <td><h3>40000</h3></td>-->
<!--                <td><h3>40000</h3></td>-->
<!--              </tr>-->
<!--              <tr>-->
<!--                <td><h3>40</h3></td>-->
<!--                <td><h3>40</h3></td>-->
<!--              </tr>-->
<!--            </tbody>-->
<!--          </table>-->
<!--          <br><br>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div><br><br>-->
    
    <!-- 금 시세 테이블 -->
    <div class="text-center container">
      <h2 class="text-start">최근 금 시세</h2><br>
      <div class="fiveThQuote">
        <table>
          <thead class="fiveThQuoteThead">
            <tr>
              <th><h2>고시날짜</h2></th>
              <th><h2>시가 (3.75kg)</h2></th>
              <th><h2>변동률</h2></th>
              <th><h2>한돈(3.75g) 가격</h2></th>
            </tr>
          </thead>
          <tbody>
          <tr v-for="(gold, index) in goldData" :key="index">
            <td>{{ gold.basDd }}</td>
            <td>{{ gold.tddOpnPrc.toLocaleString() }} 원</td>
            <td :style="{ color: gold.flucRt < 0 ? 'blue' : 'red' }">
              {{ gold.flucRt }} %
            </td>
            <td>{{ Math.floor(gold.price).toLocaleString() }} 원</td> <!-- 소숫점 제거 -->
          </tr>
          </tbody>
        </table>
        <button @click="loadMore" class="btn mt-4" :disabled="loading">
          {{ loading ? '로딩 중...' : '더보기' }}
        </button>
        <br><br>
      </div>
      <br><br>
      
      <!-- 금 시세 그래프 -->
<!--      <div class="goldQuoteGraph">-->
<!--        <h2 class="d-inline">금 시세</h2><p class="d-inline" style="font-size: 27px; font-weight: 700;">그래프</p>-->
<!--        <div>그래프</div>-->
<!--      </div>-->
      <br><br>
      <div class="text-start">
        <h2 class="d-inline">일별 국제 금시세</h2><h5 class="d-inline">By TradingView</h5>
      </div><br><br>
      <div class="tradingview-widget-container" ref="tradingViewContainer" style="height: 100%; width: 100%">
        <div class="tradingview-widget-container__widget" style="height: 100%; width: 100%"></div>
        <div class="tradingview-widget-copyright">
          <a href="https://kr.tradingview.com/" rel="noopener nofollow" target="_blank">
            <span class="blue-text">트레이딩뷰에서 모든 시장 추적</span>
          </a>
        </div>
      </div>
    </div>
  </div>
    <br><br>
  </div>
  </template>
  

<style scoped>
.fiveThQuote tbody tr td{
  padding: 15px;
}
.gold{
  font-family: J3;
  margin: 0 auto;
  width: 80%;
}
.bc{
  background-color: rgba(129, 140, 67, 0.06);
}

.goldQuote{
  border-radius: 30px;
  background-color: rgba(129, 140, 67, 0.06);
  padding: 5%;
}
.goldQuoteText{
  border-radius: 30px;
  background-color: rgba(0, 0, 0, 0.65);
}
.btn{
  width: 100px;
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

h3{
  display: inline-block;
  color: white;
}
div h2{
  margin: 20px;
}
.quoteTable {
  margin-right: auto;
  width: 100%;
}

.fiveThQuote{
  text-align: center;
  border-radius: 30px;
  border: 1px solid rgba(153, 153, 153, 1);
  background-color: rgba(255, 255, 255, 0.6);
  width: 90%;
  margin: 40px;
}
table th:last-child,
table td:last-child {
  border-right: 0;
}

.goldQuoteGraph{
  background-color: rgba(245, 248, 244, 1);
  border-radius: 30px;
}
.fiveThQuote tr{
  font-size: 20px;
  font-weight: lighter;

}
.fiveThQuote th{
  border: 1px solid rgba(153, 153, 153, 1);
  border-left: none;
  border-top: none;
  font-weight: lighter;
  font-size: 20px;
  padding: 10px;
  width: 10%;
}


.tradingview-widget-container {
  position: relative;
}
.tradingview-widget-copyright {
  text-align: center;
  padding-top: 8px;
}
.trandingViewGoldQuote{
  margin-left: 50px;
}


</style>