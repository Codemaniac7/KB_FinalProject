<template>
  <div class="stock-dashboard">
    <h1><b>투자 성향별 주식</b></h1>
    <div class="container animate-on-load">
      <!-- 현재 상위권 TOP3 -->
      <p class="middle-title">오늘의 추천 종목 ⭐️</p>
      <section class="top3-stocks">
        <div class="top3-cards">
          <div v-for="(stock, index) in top3Stocks" :key="index" class="top3-card" @click="goToStockChart(stock)">
            <h3>{{ stock.stockName }}</h3>
            <p>{{ stock.currentPrice }}원</p>
            <p :class="{
              positive: stock.priceChangePct > 0,
              negative: stock.priceChangePct < 0,
            }">
              {{ stock.priceChange }} ({{ stock.priceChangePct }}%)
            </p>
          </div>
        </div>
      </section>

      <!-- 주식 목록 섹션 -->
      <section class="stock-list">
        <p class="title">투자 성향별 추천 주식 목록</p>
        <div class="search-bar">
          <input v-model="searchKeyword" placeholder="키워드를 입력해주세요" @keyup.enter="searchStocks"/>
          <button @click="fetchStocksByType('ALL')" :class="{'btn_type': true, 'active': selectedType === 'ALL'}">전체</button>
          <button @click="fetchStocksByType('IPWC')" :class="{'btn_type': true, 'active': selectedType === 'IPWC'}">IPWC</button>
          <button @click="fetchStocksByType('IBMC')" :class="{'btn_type': true, 'active': selectedType === 'IBMC'}">IBMC</button>
          <button @click="fetchStocksByType('IPMC')" :class="{'btn_type': true, 'active': selectedType === 'IPMC'}">IPMC</button>
          <button @click="fetchStocksByType('IBWL')" :class="{'btn_type': true, 'active': selectedType === 'IBWL'}">IBWL</button>
          <button @click="fetchStocksByType('IPML')" :class="{'btn_type': true, 'active': selectedType === 'IPML'}">IPML</button>
          <button @click="fetchStocksByType('APWL')" :class="{'btn_type': true, 'active': selectedType === 'APWL'}">APWL</button>
          <button @click="fetchStocksByType('ABWC')" :class="{'btn_type': true, 'active': selectedType === 'ABWC'}">ABWC</button>
        </div>
        

        <!-- 주식 목록 데이터 -->
        <table class="stock-table">
          <thead>
            <tr>
              <th @click="sortBy('stockName')" :class="{ active: sortKey === 'stockName' }">
                종목명
                <span v-if="sortKey === 'stockName'" :class="{
                  'sort-arrow': true,
                  'sort-reverse': sortOrder === -1,
                }"></span>
              </th>
              <th @click="sortBy('currentPrice')" :class="{ active: sortKey === 'currentPrice' }">
                현재가
                <span v-if="sortKey === 'currentPrice'" :class="{
                  'sort-arrow': true,
                  'sort-reverse': sortOrder === -1,
                }"></span>
              </th>
              <th @click="sortBy('priceChange')" :class="{ active: sortKey === 'priceChange' }">
                등락률
                <span v-if="sortKey === 'priceChangePct'" :class="{
                  'sort-arrow': true,
                  'sort-reverse': sortOrder === -1,
                }"></span>
              </th>
              <th @click="sortBy('volume')" :class="{ active: sortKey === 'volume' }">
                누적 거래량(주)
                <span v-if="sortKey === 'volume'" :class="{
                  'sort-arrow': true,
                  'sort-reverse': sortOrder === -1,
                }"></span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(stock, index) in paginatedStocks" :key="index" @click="goToStockChart(stock)">
              <td>{{ stock.stockName }}</td>
              <td>{{ stock.currentPrice.toLocaleString() }}원</td>
              <td :class="{
                positive: stock.priceChange > 0,
                negative: stock.priceChange < 0,
              }">
                {{ stock.priceChange }} ({{ stock.priceChangePct }}%)
              </td>
              <td>{{ stock.volume.toLocaleString() }}주</td>
            </tr>
          </tbody>
        </table>

        <!-- 페이지네이션 -->
        <div class="pagination">
          <button @click="changePage(-1)" :disabled="currentPage === 1" class="navi">
            이전
          </button>
          <span class="pageNum">{{ currentPage }} / {{ totalPages }}</span>
          <button @click="changePage(1)" :disabled="currentPage === totalPages" class="navi">
            다음
          </button>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      selectedType: 'ALL', // 선택된 버튼의 상태
      searchKeyword: '',
      stocks: [], // 전체 주식 목록
      currentStocks: [], // KOSPI, KOSDAQ, KOSPI200 데이터
      top3Stocks: [], // 등락률 상위 3개 주식
      error: null, // 에러 메시지

      currentPage: 1, // 현재 페이지
      totalPages: 1, // 전체 페이지 수
      itemsPerPage: 10, // 한 페이지에 표시할 항목 수
      sortKey: "stockName", // 정렬 기준 키
      sortOrder: 1, // 정렬 순서 (1: 오름차순, -1: 내림차순)
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            display: false,
          },
          y: {
            display: false,
          },
        },
        plugins: {
          legend: {
            display: false,
          },
          tooltip: {
            enabled: false,
          },
        },
        elements: {
          point: {
            radius: 0,
          },
          line: {
            tension: 0.1,
          },
        },
      },
    };
  },

  computed: {
    sortedStocks() {
      return [...this.stocks].sort((a, b) => {
        let aValue = a[this.sortKey];
        let bValue = b[this.sortKey];

        if (typeof aValue === "string" && !isNaN(aValue)) {
          aValue = parseFloat(aValue);
          bValue = parseFloat(bValue);
        }

        if (aValue < bValue) return -1 * this.sortOrder;
        if (aValue > bValue) return 1 * this.sortOrder;
        return 0;
      });
    },
    paginatedStocks() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.sortedStocks.slice(start, end);
    },
  },

  methods: {
    async fetchStocksByType(type) {
      this.selectedType = type;
      let apiUrl = "";
      switch (type) {
        case "IPWC":
          apiUrl = "http://localhost:8080/api/stocks/stable";
          break;
        case "IBWC":
        case "IBMC":
          apiUrl = "http://localhost:8080/api/stocks/growth";
          break;
        case "IPMC":
        case "IPML":
          apiUrl = "http://localhost:8080/api/stocks/dividend";
          break;
        case "IBWL":
        case "APWL":
          apiUrl = "http://localhost:8080/api/stocks/volatile";
          break;
        case "APML":
        case "ABWC":
          apiUrl = "http://localhost:8080/api/stocks/aggressive";
          break;
        default:
          apiUrl = "http://localhost:8080/api/stocks/all";
      }

      try {
        const response = await axios.get(apiUrl);
        this.stocks = response.data;
        this.currentPage = 1;
        this.totalPages = Math.ceil(this.stocks.length / this.itemsPerPage);
      } catch (error) {
        this.error = "주식 데이터를 불러오는 중 오류가 발생했습니다.";
      }
    },

    searchStocks() {
      const filteredStocks = this.stocks.filter((stock) =>
        stock.stockName.toLowerCase().includes(this.searchKeyword.toLowerCase())
      );
      this.stocks = filteredStocks;
      this.currentPage = 1;
      this.totalPages = Math.ceil(this.stocks.length / this.itemsPerPage);
      this.updateTop3Stocks();
    },

    updateTop3Stocks() {
      this.top3Stocks = [...this.stocks]
        .sort((a, b) => b.priceChangePct - a.priceChangePct)
        .slice(0, 3);
    },

    changePage(direction) {
      this.currentPage += direction;
    },

    sortBy(key) {
      if (this.sortKey === key) {
        this.sortOrder *= -1;
      } else {
        this.sortKey = key;
        this.sortOrder = 1;
      }
      this.currentPage = 1;
    },

    goToStockChart(stock) {
      this.$router.push({
        path: `/stock/${stock.stockCode}`,
        query: {
          stockName: stock.stockName,
          currentPrice: stock.currentPrice,
          priceChange: stock.priceChange,
          priceChangePct: stock.priceChangePct,
          volume: stock.volume,
          htsAvls: stock.htsAvls,
          w52Hgpr: stock.w52Hgpr,
          w52Lwpr: stock.w52Lwpr,
        },
      });
    },
    async fetchCurrentStocks() {
      try {
        const kospiResponse = await axios.get("http://localhost:8080/api/index/kospi");
        const kosdaqResponse = await axios.get("http://localhost:8080/api/index/kosdaq");
        const kospi200Response = await axios.get("http://localhost:8080/api/index/kospi200");

        // 더미 데이터 생성 함수
        const generateStockData = (startValue, numPoints, volatility) => {
          let labels = [];
          let data = [];
          let currentValue = startValue;

          for (let i = 0; i < numPoints; i++) {
            const date = new Date();
            date.setDate(date.getDate() - (numPoints - i));
            labels.push(date.toISOString().slice(0, 10));

            const change = (Math.random() * 2 - 1) * volatility;
            currentValue += currentValue * change;
            data.push(Math.round(currentValue * 100) / 100);
          }

          return { labels, data };
        };

        // 각 주식 데이터 생성
        const kospiStockData = generateStockData(2570, 100, 0.01);
        const kosdaqStockData = generateStockData(1000, 100, 0.015);
        const kospi200StockData = generateStockData(330, 100, 0.008);

        // currentStocks에 데이터 추가
        this.currentStocks = [
          {
            name: 'KOSPI',
            amount: kospiResponse.data.코스피,
            change: kospiResponse.data.변동,
            chartData: {
              labels: kospiStockData.labels,
              datasets: [
                {
                  label: 'KOSPI',
                  data: kospiStockData.data,
                  borderColor: 'rgba(75, 192, 192, 1)',
                  backgroundColor: 'rgba(75, 192, 192, 0.2)',
                  fill: true,
                  pointRadius: 2,
                },
              ],
            },
          },
          {
            name: 'KOSDAQ',
            amount: kosdaqResponse.data.코스닥,
            change: kosdaqResponse.data.변동,
            chartData: {
              labels: kosdaqStockData.labels,
              datasets: [
                {
                  label: 'KOSDAQ',
                  data: kosdaqStockData.data,
                  borderColor: 'rgba(153, 102, 255, 1)',
                  backgroundColor: 'rgba(153, 102, 255, 0.2)',
                  fill: true,
                  pointRadius: 2,
                },
              ],
            },
          },
          {
            name: 'KOSPI200',
            amount: kospi200Response.data.코스피200,
            change: `${kospi200Response.data.전일대비} (${kospi200Response.data.등락률})`,
            chartData: {
              labels: kospi200StockData.labels,
              datasets: [
                {
                  label: 'KOSPI200',
                  data: kospi200StockData.data,
                  borderColor: 'rgba(255, 159, 64, 1)',
                  backgroundColor: 'rgba(255, 159, 64, 0.2)',
                  fill: true,
                  pointRadius: 2,
                },
              ],
            },
          }
        ];
      } catch (error) {
        console.error("주식 데이터를 가져오는 중 오류 발생:", error);
      }
    },

  },

  async mounted() {
  try {
    // URL 쿼리에서 userType을 가져옴, 없을 경우 기본값은 'ALL'
    const userType = this.$route.query.userType || 'ALL'; 
    console.log(userType); 
    // userType에 맞는 주식 목록을 가져옴
    await this.fetchStocksByType(userType);

    // 현재 KOSPI, KOSDAQ, KOSPI200 데이터 불러오기
    await this.fetchCurrentStocks();

    // 상위 3개 주식 업데이트
    this.updateTop3Stocks();
  } catch (error) {
    this.error = "데이터를 불러오는 중 오류가 발생했습니다.";
  }
}

};


</script>


<style scoped>
.stock-dashboard {
  font-family: J5;
  background-color: white;
  padding: 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.top3-cards,
.category-cards {
  display: flex;
  padding: 20px;
}

a.more-link {
  display: flex;
  flex-direction: column-reverse;
}

.title {
  font-size: 30px;
}

.more-link {
  color: #448c74;
  text-decoration: none;
  font-weight: bold;
}

.current-stocks,
.top3-stocks,
.stock-list,
.categories {
  margin-bottom: 40px;
  background-color: white;
  border-radius: 30px;
  padding: 20px;
  justify-content: space-between;
  gap: 20px;
  font-size: 16px;
}

.stock-card,
.top3-card,
.category-card {
  background-color: white;
  padding: 15px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex: 1;
  text-align: center;
}

.current-stocks .stock-cards {
  display: flex;
  justify-content: space-around;
  gap: 20px;
}

.stock-card {
  flex: 1;
  text-align: center;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.positive-card {
  background-color: #fff8f9;
  /* 상승할 때 배경색 */
}

.negative-card {
  background-color: #f9fbff;
  /* 하락할 때 배경색 */
}

.positive {
  color: #ff0008;
}

.negative {
  color: #005cf6;
}

.positive::before {
  content: "▲";
  /* 상승 화살표 */
  color: #ff0008;
  margin-right: 5px;
}

.negative::before {
  content: "▼";
  /* 하락 화살표 */
  color: #005cf6;
  margin-right: 5px;
}

.category-card img {
  width: 50px;
  height: 50px;
  margin-bottom: 10px;
}

.stock-table {
  width: 100%;
  border-collapse: collapse;
}

.middle-title {
  margin-left: 30px;
  font-size: 30px;
  margin-bottom: 15px;
}

.amount-txt {
  font-size: 25px;
}

.stock-title {
  font-size: 15px;
}

.line-c {
  width: 80%;
  height: 150px;
  margin-left: 20px;
  margin-bottom: 20px;
  font-size: 30px;
}

.stock-list-subject {
  display: flex;
  justify-content: space-between;
  align-content: center;
}

.stock-list .search-bar {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.stock-list .search-bar input {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  width: 300px;
}

.btn_type {
  background-color: #448c74;
  color: white;
  margin-left: 10px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn_type:hover {
  background-color: #90DAAA;
}

.btn_type:active {
  background-color: #2a5b49;
}

/* 선택된 버튼에 적용될 active 클래스 */
.btn_type.active {
  background-color: #90DAAA;
  color: white;
}

.stock-table th,
.stock-table td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.stock-table th {
  cursor: pointer;
  user-select: none;
  position: relative;
}

.stock-table th.active {
  background-color: #f0f0f0;
}

.sort-arrow {
  display: inline-block;
  width: 0;
  height: 0;
  margin-left: 5px;
  vertical-align: middle;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-bottom: 4px solid #000;
}

.sort-arrow.sort-reverse {
  border-bottom: none;
  border-top: 4px solid #000;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}

/* 준섭 css 추가*/

.categories {
  margin-bottom: 40px;
  background-color: white;
  border-radius: 30px;
  padding: 20px;
}

.category-cards {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
}

.category-card {
  background-color: white;
  padding: 15px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex-basis: calc(33.333% - 20px);
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.category-card img {
  width: 50px;
  height: 50px;
  margin-bottom: 10px;
}

.category-card h3 {
  margin-bottom: 5px;
}

.category-card p {
  font-weight: bold;
}

.positive {
  color: #ff0008;
}

.negative {
  color: #005cf6;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  font-size: 18px;
}

.modal-content {
  background-color: #fff;
  border-radius: 10px;
  width: 80%;
  max-width: 800px;
  max-height: 80vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
}

.close-button {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}

.modal-body {
  padding: 20px;
}

.category-list {
  display: flex;
  flex-direction: column;
}

.category-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.category-rank {
  width: 30px;
  font-weight: bold;
}

.category-icon img {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.category-name {
  flex: 1;
}

.category-change {
  width: 80px;
  text-align: right;
  font-weight: bold;
}

.category-detail {
  width: 150px;
  text-align: right;
  color: #666;
}

.positive {
  color: #ff0008;
}

.negative {
  color: #005cf6;
}

/* 주식 목록 hover 효과 */
.stock-row {
  cursor: pointer;
  background-color: #90DAAA;
  transition: background-color 0.3s ease;
}

.stock-row:hover {
  background-color: #90DAAA;
}

/* Top3 주식 카드 hover 효과 */
.top3-card {
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border: 1px solid #e0e0e0;
  padding: 15px;
  border-radius: 8px;
}

.top3-card:hover {
  transform: translateY(-5px);
  /* 마우스를 올리면 약간 위로 올라가는 효과 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* 그림자 효과 */
  background-color: #F1FAF7;
  /* 배경색 변경 */
}

tbody tr {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

tbody tr:hover {
  background-color: #F1FAF7;
}

/* Top3 주식 hover 효과 */
.top3-card {
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  /* 부드러운 전환 효과 */
}

.top3-card:hover {
  background-color: #F1FAF7;
  transform: translateY(-5px);
}

/* 카테고리 카드 hover 효과 */
.category-card {
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.category-card:hover {
  background-color: #f0f8ff;
  transform: translateY(-5px);
}

/* 모달 테이블의 주식 항목 hover 효과 */
.modal-body tbody tr {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.modal-body tbody tr:hover {
  background-color: #f0f8ff;
}

.navi {
  padding: 10px 20px;
  margin: 35px;
  border-radius: 10px;
  border: none;
  background-color: #448c74;
  color: white;
}

.pageNum {
  padding: 10px 20px;
  margin: 35px 0px;
  border-radius: 10px;
  border: none;
  color: black;
  font-size: 20px;
}


@media (max-width: 900px) {
  .main-news {
      flex-direction: column;
  }

  .main-news-image,
  .main-news-content {
      width: 100%;
  }

  .news-grid {
      grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .news-grid {
      grid-template-columns: 1fr;
  }
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


</style>
