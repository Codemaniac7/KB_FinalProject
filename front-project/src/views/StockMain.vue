<template>
  <div class="stock-dashboard">
    <h1><b>국내주식</b></h1>
    <div class="container">
      <div v-if="error" class="error-message">{{ error }}</div>

      <!-- 이 시각 증시 (KOSPI, KOSDAQ, KOSPI200) -->
      <section class="current-stocks">
        <div class="section-header">
          <p class="title">이 시각 증시</p>
        </div>
        <div class="stock-cards">
          <div
            v-for="(stock, index) in currentStocks"
            :key="index"
            :class="{
              'positive-card': stock.change && stock.change.includes('+'),
              'negative-card': stock.change && stock.change.includes('-'),
            }"
            class="stock-card"
          >
            <p class="stock-title">{{ stock.name }}</p>
            <p class="amount-txt">{{ stock.amount }}</p>
            <p>
              <span
                v-if="stock.change && stock.change.includes('+')"
                class="positive"
              >
                {{ stock.change }}
              </span>
              <span
                v-else-if="stock.change && stock.change.includes('-')"
                class="negative"
              >
                {{ stock.change }}
              </span>
              <span v-else>
                {{ stock.change ? stock.change : "N/A" }}
              </span>
            </p>
            <div class="line-c">
              <Line :data="stock.chartData" :options="chartOptions" />
            </div>
          </div>
        </div>
      </section>
      <p class="middle-title">현재 상위권 TOP3 🏆</p>
      <section class="top3-stocks">
        <div class="top3-cards">
          <div
            v-for="(stock, index) in top3Stocks"
            :key="index"
            class="top3-card"
            @click="goToStockChart(stock)"
          >
            <h3>{{ stock.stockName }}</h3>
            <p>{{ stock.currentPrice }}</p>
            <!-- 상승/하락에 따른 아이콘 표시 -->
            <p
              :class="{
                positive: stock.priceChangePct > 0,
                negative: stock.priceChangePct < 0,
              }"
            >
              {{ stock.priceChange }} ({{ stock.priceChangePct }}%)
            </p>
          </div>
        </div>
      </section>

      <!-- 주식 목록 섹션 -->
      <section class="stock-list">
        <div class="stock-list-subject">
          <p class="title">실시간 주식 목록</p>
          <router-link to="/stockdetail" class="more-link">더보기</router-link>
        </div>
        <!-- 주식 목록 데이터 -->
        <table class="stock-table">
          <thead>
            <tr>
              <th
                @click="sortBy('stockName')"
                :class="{ active: sortKey === 'stockName' }"
              >
                종목명
                <span
                  v-if="sortKey === 'stockName'"
                  :class="{
                    'sort-arrow': true,
                    'sort-reverse': sortOrder === -1,
                  }"
                ></span>
              </th>
              <th
                @click="sortBy('currentPrice')"
                :class="{ active: sortKey === 'currentPrice' }"
              >
                현재가
                <span
                  v-if="sortKey === 'currentPrice'"
                  :class="{
                    'sort-arrow': true,
                    'sort-reverse': sortOrder === -1,
                  }"
                ></span>
              </th>
              <th
                @click="sortBy('priceChange')"
                :class="{ active: sortKey === 'priceChange' }"
              >
                등락률
                <span
                  v-if="sortKey === 'priceChange'"
                  :class="{
                    'sort-arrow': true,
                    'sort-reverse': sortOrder === -1,
                  }"
                ></span>
              </th>
              <th
                @click="sortBy('volume')"
                :class="{ active: sortKey === 'volume' }"
              >
                누적 거래량(주)
                <span
                  v-if="sortKey === 'volume'"
                  :class="{
                    'sort-arrow': true,
                    'sort-reverse': sortOrder === -1,
                  }"
                ></span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(stock, index) in sortedStocks"
              :key="index"
              @click="goToStockChart(stock)"
            >
              <td>{{ stock.stockName }}</td>
              <td>{{ stock.currentPrice.toLocaleString() }}원</td>
              <td
                :class="{
                  positive: stock.priceChange > 0,
                  negative: stock.priceChange < 0,
                }"
              >
                {{ stock.priceChange }}
                ({{ stock.priceChangePct }})%
              </td>
              <td>{{ stock.volume.toLocaleString() }}주</td>
            </tr>
          </tbody>
        </table>
      </section>

      <section class="categories">
        <div class="section-header">
          <p class="title">카테고리</p>
          <button @click="showCategoryModal = true" class="more-link">
            더보기
          </button>
        </div>
        <div class="category-cards">
          <div
            v-for="(category, index) in categories.slice(0, 6)"
            :key="index"
            class="category-card"
            @click="showCategoryStocks(category)"
          >
            <img :src="getCategoryIcon(category.name)" :alt="category.name" />
            <h3>{{ category.name }}</h3>
            <p
              :class="{
                positive: category.avgChange >= 0,
                negative: category.avgChange < 0,
              }"
            >
              {{ category.avgChange.toFixed(2) }}%
            </p>
          </div>
        </div>
      </section>

      <!-- 카테고리 모달 -->
      <div
        v-if="showCategoryModal"
        class="modal-overlay"
        @click="showCategoryModal = false"
      >
        <div class="modal-content" @click.stop>
          <div class="modal-header">
            <h2>지금 뜨는 카테고리</h2>
            <button @click="showCategoryModal = false" class="close-button">
              ×
            </button>
          </div>
          <div class="modal-body">
            <div class="category-list">
              <div
                v-for="(category, index) in categories"
                :key="index"
                class="category-item"
                @click="showCategoryStocks(category)"
              >
                <div class="category-rank">{{ index + 1 }}</div>
                <div class="category-icon">
                  <img
                    :src="getCategoryIcon(category.name)"
                    :alt="category.name"
                  />
                </div>
                <div class="category-name">{{ category.name }}</div>
                <div
                  :class="[
                    'category-change',
                    {
                      positive: category.avgChange >= 0,
                      negative: category.avgChange < 0,
                    },
                  ]"
                >
                  {{ category.avgChange >= 0 ? "+" : ""
                  }}{{ category.avgChange.toFixed(2) }}%
                </div>
                <div class="category-detail">
                  {{ category.totalStocksCount }}개 중
                  {{ category.risingStocksCount }}개 종목 상승
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 카테고리별 주식 목록 모달 -->
      <div
        v-if="showStockListModal"
        class="modal-overlay"
        @click="showStockListModal = false"
      >
        <div class="modal-content" @click.stop>
          <div class="modal-header">
            <h2>{{ selectedCategory.name }} 주식 목록</h2>
            <button @click="showStockListModal = false" class="close-button">
              ×
            </button>
          </div>
          <div class="modal-body">
            <table class="stock-table">
              <thead>
                <tr>
                  <th>종목명</th>
                  <th>현재가</th>
                  <th>등락률</th>
                  <th>누적 거래량(주)</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="stock in categoryStocks"
                  :key="stock.stockCode"
                  @click="goToStockChart(stock)"
                >
                  <td>{{ stock.stockName }}</td>
                  <td>{{ stock.currentPrice.toLocaleString() }}원</td>
                  <td
                    :class="{
                      positive: stock.priceChange > 0,
                      negative: stock.priceChange < 0,
                    }"
                  >
                    {{ stock.priceChange }}
                    ({{ stock.priceChangePct }})%
                  </td>
                  <td>{{ stock.volume.toLocaleString() }}주</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "vue-chartjs";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);
export default {
  components: {
    Line,
  },
  data() {
    return {
      stockData: [], // 주식 데이터를 저장할 배열
      currentSort: "currentPrice", // 기본 정렬 필드
      currentSortDir: "desc", // 기본 정렬 방향 (내림차순)
      categoryIcons: {},
      currentStocks: [], // KOSPI, KOSDAQ, KOSPI200 데이터
      stocks: [], // 웹소켓에서 받아온 10개 주식 데이터
      top3Stocks: [], // 등락률 상위 3개 주식
      error: null, // 에러 메시지
      sortKey: "stockName", // 정렬 기준 키
      sortOrder: 1, // 정렬 순서 (1: 오름차순, -1: 내림차순)
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            display: false, // X축 숨기기
          },
          y: {
            display: false, // Y축 숨기기
          },
        },
        plugins: {
          legend: {
            display: false, // 범례 숨기기
          },
          tooltip: {
            enabled: false, // 툴팁 숨기기
          },
        },
        elements: {
          point: {
            radius: 0, // 데이터 포인트 숨기기
          },
          line: {
            tension: 0.1, // 부드러운 곡선 유지
          },
        },
      },
      showCategoryModal: false,
      showStockListModal: false,
      selectedCategory: null,
      categoryStocks: [],
      categories: [],
      allStocks: [], // 추가: 전체 주식 데이터를 저장할 배열
    };
  },

  computed: {
    // 정렬된 주식 목록
    sortedStocks() {
      return [...this.stocks].sort((a, b) => {
        let aValue = a[this.sortKey];
        let bValue = b[this.sortKey];

        // 숫자 정렬을 위한 변환
        if (typeof aValue === "string" && !isNaN(aValue)) {
          aValue = parseFloat(aValue);
          bValue = parseFloat(bValue);
        }

        if (aValue < bValue) return -1 * this.sortOrder;
        if (aValue > bValue) return 1 * this.sortOrder;
        return 0;
      });
    },
  },
  created() {
    this.loadCategoryIcons();
  },

  async mounted() {
    try {
      await this.fetchStockData();
      await this.fetchAllStocksData();
      await this.fetchCategoryData();
      this.startWebSocket();
    } catch (error) {
      console.error("초기 데이터 로드 중 오류 발생:", error);
      this.error = "주식 데이터를 불러오는 중 오류가 발생했습니다.";
    }
  },

  methods: {
    startWebSocket() {
      const ws = new WebSocket("ws://localhost:8080/api/websocket/stocks");

      ws.onopen = function () {
        console.log("WebSocket 연결 성공");
      };

      ws.onmessage = (event) => {
        const stockData = JSON.parse(event.data);
        this.updateStockData(stockData); // 실시간 데이터 업데이트
      };

      ws.onerror = function (error) {
        console.log("WebSocket 오류:", error);
      };

      ws.onclose = function () {
        console.log("WebSocket 연결 종료");
      };
    },
    createGradient(ctx, chartArea) {
      const { top, bottom } = chartArea;
      const gradient = ctx.createLinearGradient(0, top, 0, bottom);

      // 빨간색에서 투명한 빨간색으로 그라데이션 설정
      gradient.addColorStop(0, "rgba(255, 0, 0, 0.6)"); // 빨간색 위쪽
      gradient.addColorStop(1, "rgba(255, 0, 0, 0)"); // 아래쪽은 투명한 빨간색

      return gradient;
    },
    async fetchAllStocksData() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/stocks/all"
        );

        // 카테고리가 null이 아닌 항목들만 필터링
        const filteredStocks = Array.from(response.data).filter(
          (stock) => stock.industry !== null
        );
        this.allStocks = filteredStocks;

        console.log(
          "전체 주식 데이터 (카테고리가 null이 아닌 항목만):",
          this.allStocks
        );
      } catch (error) {
        console.error("전체 주식 데이터를 가져오는 중 오류 발생:", error);
        this.error = "전체 주식 데이터를 가져오는 중 오류가 발생했습니다.";
      }
    },
    // 초기 주식 데이터를 가져오는 메서드
    async fetchStockData() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/websocket/prices"
        );
        this.stocks = Object.values(response.data);
        this.updateTop3Stocks();

        const kospiResponse = await axios.get(
"http://localhost:8080/api/index/kospi"
        );
        const kosdaqResponse = await axios.get(
          "http://localhost:8080/api/index/kosdaq"
        );
        const kospi200Response = await axios.get(
          "http://localhost:8080/api/index/kospi200"
        );
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

    // 웹소켓에서 받은 데이터로 주식 정보 업데이트
    updateStockData(data) {
      const index = this.stocks.findIndex(
        (stock) => stock.stockCode === data.stockCode
      );
      if (index !== -1) {
        this.stocks[index] = {
          ...this.stocks[index],
          ...data,
        };
      } else if (this.stocks.length < 10) {
        this.stocks.push(data);
      }
      this.updateTop3Stocks();
    },

    // Top3 주식 업데이트 메서드
    updateTop3Stocks() {
      this.top3Stocks = [...this.stocks]
        .sort((a, b) => b.priceChangePct - a.priceChangePct)
        .slice(0, 3);
    },

    // 정렬 메서드
    sortBy(key) {
      if (this.sortKey === key) {
        this.sortOrder *= -1;
      } else {
        this.sortKey = key;
        this.sortOrder = 1;
      }
    },

    // 종목 상세 페이지로 이동
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

    // 카테고리 데이터를 가져오는 메서드
    async fetchCategoryData() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/stocks/categories"
        );
        this.categories = response.data
          .filter((category) => category.name && category.name.trim()) // name이 공백, null, undefined가 아닌 항목만 필터링
          .map((category) => ({
            name: category.name,
            avgChange: category.avgChange,
            risingStocksCount: category.risingStocksCount,
            totalStocksCount: category.totalStocksCount,
          }));
      } catch (error) {
        console.error("카테고리 데이터를 가져오는 중 오류 발생:", error);
        this.error = "카테고리 데이터를 가져오는 중 오류가 발생했습니다.";
      }
    },
    loadCategoryIcons() {
      const context = require.context("@/assets/img/stock", false, /\.png$/);
      context.keys().forEach((key) => {
        const categoryName = key.replace("./", "").replace(".png", "");
        this.categoryIcons[categoryName] = context(key);
      });
    },
    getCategoryIcon(categoryName) {
      try {
        // 이미지 경로 콘솔 출력
        const imagePath = require(`@/assets/img/stock/${categoryName.toLowerCase()}.png`);
        console.log(`이미지 경로: ${imagePath}`); // 경로가 제대로 설정되었는지 확인
        return imagePath;
      } catch (error) {
        console.error(`이미지 로딩 오류: ${categoryName}`, error); // 어떤 카테고리에서 오류가 발생했는지 확인
        return "/icons/default.png"; // 기본 이미지로 fallback
      }
    },
    async showCategoryStocks(category) {
      this.selectedCategory = category;
      this.showCategoryModal = false;
      this.showStockListModal = true;
      try {
        if (!category || !category.name) {
          throw new Error("선택된 카테고리 정보가 올바르지 않습니다.");
        }

        console.log("선택된 카테고리:", category);
        console.log("전체 주식 데이터:", this.allStocks);

        // 카테고리가 null이 아닌 항목 중 선택된 카테고리와 일치하는 주식만 필터링
        this.categoryStocks = this.allStocks.filter((stock) => {
          return (
            stock.industry &&
            stock.industry.toString().trim().toLowerCase() ===
              category.name.toString().trim().toLowerCase()
          );
        });

        console.log("필터링된 주식 데이터:", this.categoryStocks);
        this.categoryStocks.sort((a, b) => b.priceChangePct - a.priceChangePct);
      } catch (error) {
        console.error(
          `${category.name} 카테고리의 주식 데이터를 처리하는 중 오류 발생:`,
          error
        );
        this.error = "주식 데이터를 처리하는 중 오류가 발생했습니다.";
      }
    },
  },
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

  background: none; /* 배경 제거 */
  border: none; /* 테두리 제거 */
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

.stock-list .search-bar button {
  margin-left: 10px;
  padding: 10px 20px;
  background-color: #448c74;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
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
  font-size: 15px;
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
  cursor: pointer; /* 클릭 가능함을 나타내는 커서 추가 */
  transition: background-color 0.3s ease; /* 부드러운 전환 효과 추가 */
}
.category-item:hover {
  background-color: #f0f8ff; /* 마우스를 올리면 배경색 변경 */
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
  text-align: center;
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
  transition: background-color 0.3s ease;
}

.stock-row:hover {
  background-color: #f0f8ff;
  /* 배경색을 변경하여 hover 느낌을 줌 */
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
  background-color: #f0f8ff;
  /* 배경색 변경 */
}

/* 주식 목록과 카테고리 카드에 hover 효과 적용 */

/* 주식 목록 hover 효과 */
tbody tr {
  cursor: pointer;
  /* 클릭 가능함을 나타내는 커서 */
  transition: background-color 0.3s ease;
  /* 배경색 변경이 부드럽게 전환되도록 */
}

tbody tr:hover {
  background-color: #f0f8ff;
  /* 마우스를 올리면 배경색 변경 */
}

/* Top3 주식 hover 효과 */
.top3-card {
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  /* 부드러운 전환 효과 */
}

.top3-card:hover {
  background-color: #f0f8ff;
  /* 배경색 변경 */
  transform: translateY(-5px);
  /* 마우스를 올리면 살짝 올라가는 효과 */
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
</style>
