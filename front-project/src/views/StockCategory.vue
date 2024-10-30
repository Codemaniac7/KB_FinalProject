<template>
  <div class="stock-category">
    <h1>전체 카테고리</h1>
    <div class="category-list">
      <div
        v-for="category in categories"
        :key="category.name"
        class="category-item"
      >
        <div class="category-header">
          <img :src="getCategoryIcon(category.name)" :alt="category.name" />
          <h2>{{ category.name }}</h2>
          <p
            :class="{
              positive: category.change > 0,
              negative: category.change < 0,
            }"
          >
            {{ category.change.toFixed(2) }}%
          </p>
        </div>
        <table class="stock-table">
          <thead>
            <tr>
              <th>종목명</th>
              <th>현재가</th>
              <th>전일대비</th>
              <th>등락률</th>
              <th>누적거래대금(백만)</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="stock in category.stocks" :key="stock.stockCode">
              <td>{{ stock.stockName }}</td>
              <td>{{ formatNumber(stock.currentPrice) }}</td>
              <td
                :class="{
                  positive: stock.change > 0,
                  negative: stock.change < 0,
                }"
              >
                {{ formatNumber(stock.change) }}
              </td>
              <td
                :class="{
                  positive: stock.changePercent > 0,
                  negative: stock.changePercent < 0,
                }"
              >
                {{ stock.changePercent.toFixed(2) }}%
              </td>
              <td>{{ formatNumber(stock.accumulatedTradingValue) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      categories: [],
    };
  },
  async mounted() {
    await this.fetchCategoryData();
  },
  methods: {
    async fetchCategoryData() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/categories"
        );
        this.categories = response.data;
      } catch (error) {
        console.error("카테고리 데이터를 가져오는 중 오류 발생:", error);
      }
    },
    getCategoryIcon(categoryName) {
      // 카테고리 아이콘 매핑
      const iconMap = {
        통신업: require("@/assets/img/stock/통신업.png"),
        비금속광물: require("@/assets/img/stock/비금속광물.png"),
        보험: require("@/assets/img/stock/보험.png"),
        인프라투용: require("@/assets/img/stock/인프라투용.png"),
        의약품: require("@/assets/img/stock/의약품.png"),
        서비스업: require("@/assets/img/stock/서비스업.png"),
        의료정밀: require("@/assets/img/stock/의료정밀.png"),
        증권: require("@/assets/img/stock/증권.png"),
        금융업: require("@/assets/img/stock/금융업.png"),
        섬유의복: require("@/assets/img/stock/섬유.의복.png"),
        리츠: require("@/assets/img/stock/리츠.png"),
        종이목재: require("@/assets/img/stock/종이.목재.png"),
        전기가스업: require("@/assets/img/stock/전기.가스업.png"),
        운수장비: require("@/assets/img/stock/운수.장비.png"),
        건설업: require("@/assets/img/stock/건설업.png"),
        유통업: require("@/assets/img/stock/유통업.png"),
        철강금속: require("@/assets/img/stock/철강.금속.png"),
        전기전자: require("@/assets/img/stock/전기.전자.png"),
        기계: require("@/assets/img/stock/기계.png"),
        운수창고: require("@/assets/img/stock/운수.창고.png"),
        음식료품: require("@/assets/img/stock/음식료품.png"),
        화학: require("@/assets/img/stock/화학.png"),
        외국증권: require("@/assets/img/stock/외국증권.png"),
      };
      return iconMap[categoryName] || "/icons/default.png";
    },
    formatNumber(value) {
      return new Intl.NumberFormat("ko-KR").format(value);
    },
  },
};
</script>

<style scoped>
.stock-category {
  padding: 20px;
  background-color: #f5f8f4;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.category-list {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.category-item {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 5px 4px rgba(0, 0, 0, 0.1);
}

.category-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.category-header img {
  width: 40px;
  height: 40px;
  margin-right: 15px;
}

.category-header h2 {
  margin: 0;
  flex-grow: 1;
  font-size: 24px;
  color: #333;
}

.stock-table {
  width: 100%;
  border-collapse: collapse;
}

.stock-table th,
.stock-table td {
  padding: 10px;
  text-align: right;
  border-bottom: 1px solid #eee;
}

.stock-table th:first-child,
.stock-table td:first-child {
  text-align: left;
}

.stock-table th {
  background-color: #f0f0f0;
  font-weight: bold;
  color: #333;
}

.positive {
  color: #e31b23;
}

.negative {
  color: #1261c4;
}
</style>
