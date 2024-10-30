<template>
  <section>
    <br><br>
    <div class="todayBox text-center">
      <br><br>
      <h2 class="review-section__title mb-4">TODAY</h2>
      <div class="d-flex justify-content-center text-start gap-4">
        <div class="todayStock">
          <h3>현재 증시</h3>
          <div class="todayCo">
            <div v-for="(stock, index) in currentStocks" :key="index"
              :class="{ 'positive-card': stock.change && stock.change.includes('+'), 'negative-card': stock.change && stock.change.includes('-') }"
              class="stock-card">
              <p class="stock-title">{{ stock.name }}</p>
              <p class="amount-txt">{{ stock.amount }}</p>
              <p>
                <span v-if="stock.change && stock.change.includes('+')" class="positive upDown">
                  {{ stock.change }}
                </span>
                <span v-else-if="stock.change && stock.change.includes('-')" class="negative upDown">
                  {{ stock.change }}
                </span>
                <span v-else>
                  {{ stock.change ? stock.change : 'N/A' }}
                </span>
              </p>
              <div class="line-c">
                <Line :data="stock.chartData" :options="chartOptions" />
              </div>
            </div>
          </div>
        </div>
        <div class="todayExchange">
          <div class="subjectBetween">
            <h3 class="d-inline">환율 정보</h3>
            <a class="d-inline" href="/currencyExchange">더보기<i class="ai-chevron-right"></i></a>
          </div>
          <div class="exchangeBox">
            <div class="content">
              <div class="line-c">
                <Line :data="chartData" :options="chartOptions" v-if="chartData" />
              </div>
              <div class="new-section" v-if="exchangeData">
                <h4 class="d-inline-block title"><img
                    :src="nationalMoney.find(currency => currency.code === exchangeData.currencyCode)?.flagUrl" alt="국기"
                    class="flag-img" /> {{ exchangeData.currencyName }}({{ exchangeData.currencyCode }})</h4>
                    <br>
                <h4 class="d-inline-block">{{ exchangeData.basePrice.toLocaleString() }}원</h4>
                <h4 class="d-inline-block">
                  <span v-if="exchangeData.baseRateDifference > 0" class="positive upDown">
                    {{ exchangeData.baseRateDifference.toLocaleString() }}
                  </span>
                  <span v-else-if="exchangeData.baseRateDifference < 0" class="negative upDown">
                    {{ exchangeData.baseRateDifference.toLocaleString() }}
                  </span>
                  <span v-else>
                    {{ exchangeData.baseRateDifference.toLocaleString() }}
                  </span>
                </h4>
                <br>
                <h4 class="date">{{ exchangeData.exchangeRateDate }}</h4>
              </div>
            </div>
            <div class="currency-buttons">
              <button @click="selectCurrency(23)" :class="{ 'active': selectedCurrency === 23 }"
                class="exchangeCountry1 btn_country">미국</button>
              <button @click="selectCurrency(13)" :class="{ 'active': selectedCurrency === 13 }"
                class="exchangeCountry2 btn_country">일본</button>
              <button @click="selectCurrency(9)" :class="{ 'active': selectedCurrency === 9 }"
                class="exchangeCountry1 btn_country">EU</button>
              <button @click="selectCurrency(2)" :class="{ 'active': selectedCurrency === 2 }"
                class="exchangeCountry2 btn_country">호주</button>
              <button @click="selectCurrency(10)" :class="{ 'active': selectedCurrency === 10 }"
                class="exchangeCountry1 btn_country">영국</button>
            </div>
          </div>
        </div>
      </div>
      <br><br>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { Line } from 'vue-chartjs';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

// Chart.js 구성 요소 등록
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const currentStocks = ref([]);
const loading = ref(false);
const exchangeData = ref(null);

const nationalMoney = ref([
  { currencyId: 2, code: 'AUD', name: '호주 달러', countryCode: 'AU', flagUrl: 'https://flagcdn.com/24x18/au.png' },
  { currencyId: 9, code: 'EUR', name: 'EU 유로', countryCode: 'EU', flagUrl: 'https://flagcdn.com/24x18/eu.png' },
  { currencyId: 10, code: 'GBP', name: '영국 파운드', countryCode: 'GB', flagUrl: 'https://flagcdn.com/24x18/gb.png' },
  { currencyId: 13, code: 'JPY', name: '일본 옌', countryCode: 'JP', flagUrl: 'https://flagcdn.com/24x18/jp.png' },
  { currencyId: 23, code: 'USD', name: '미국 달러', countryCode: 'US', flagUrl: 'https://flagcdn.com/24x18/us.png' }
]);

const chartData = ref(null);
const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    x: {
      display: true,
      grid: {
        display: false,
      },
      ticks: {
        callback: function (value, index, values) {
          if (index === values.length - 1) {
            return values[index].label;
          }
          return null;
        },
      },
    },
    y: {
      display: true,
      grid: {
        display: true,
        drawBorder: true,
        drawOnChartArea: false,
      },
      ticks: {
        callback: function (value) {
          return value.toLocaleString();
        },
        maxTicksLimit: 3,
      },
    },
  },
  plugins: {
    legend: {
      display: false,
    },
    tooltip: {
      enabled: true,
      callbacks: {
        title: function (tooltipItems) {
          return tooltipItems[0].label;
        },
        label: function (tooltipItem) {
          return `가격: ${tooltipItem.raw.toLocaleString()}`;
        },
      },
    },
  },
  elements: {
    point: {
      radius: 2,
    },
    line: {
      tension: 0.1,
    },
  },
});

const fetchStockData = async () => {
  try {
    const kospiResponse = await axios.get('/api/index/kospi');
    const kosdaqResponse = await axios.get('/api/index/kosdaq');

    // 최근 주가 변동을 고려한 더미 데이터 생성
    const generateStockData = (startValue, numPoints, volatility) => {
      let labels = [];
      let data = [];
      let currentValue = startValue;

      for (let i = 0; i < numPoints; i++) {
        const date = new Date();
        date.setDate(date.getDate() - (numPoints - i)); // 날짜 계산
        labels.push(date.toISOString().slice(0, 10)); // 'YYYY-MM-DD' 형식의 날짜 생성

        // 최근 변동성을 반영한 주가 변동 (예: -1% ~ +1% 변동)
        const change = (Math.random() * 2 - 1) * volatility;
        currentValue += currentValue * change;
        data.push(Math.round(currentValue * 100) / 100); // 소수점 2자리까지
      }

      return { labels, data };
    };

    // KOSPI, KOSDAQ의 최근 주가 변동을 반영한 100개의 데이터 생성
    const kospiStockData = generateStockData(2570, 100, 0.01); // KOSPI 시작값 2570, 변동성 1%
    const kosdaqStockData = generateStockData(1000, 100, 0.015); // KOSDAQ 시작값 1000, 변동성 1.5%

    // 차트 데이터 설정
    currentStocks.value = [
      {
        name: 'KOSPI',
        amount: kospiResponse.data.코스피,
        change: kospiResponse.data.변동,
        chartData: {
          labels: kospiStockData.labels,
          datasets: [
            {
              label: 'KOSPI',
              data: kospiStockData.data, // 최근 변동을 반영한 데이터 적용
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
              data: kosdaqStockData.data, // 최근 변동을 반영한 데이터 적용
              borderColor: 'rgba(153, 102, 255, 1)',
              backgroundColor: 'rgba(153, 102, 255, 0.2)',
              fill: true,
              pointRadius: 2,
            },
          ],
        },
      },
    ];
  } catch (error) {
    console.error('주식 데이터를 가져오는 중 오류 발생:', error);
  }
};



const fetchExchangeRates = async (currencyId) => {
  loading.value = true;
  fetchDailyExchangeData(currencyId);
  try {
    const response = await axios.get(`/api/exchange/detail/${currencyId}`);
    const exchangeList = response.data[0]?.exchangeList || [];

    chartData.value = {
      labels: exchangeList.map(item => new Date(item.exchangeRateDate).toLocaleDateString()),
      datasets: [
        {
          label: 'Base Price',
          data: exchangeList.map(item => item.basePrice),
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
        },
      ],
    };
  } catch (error) {
    console.error('환율 데이터를 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};

const selectedCurrency = ref(null); // 선택된 통화를 추적하는 상태

const selectCurrency = (currencyId) => {
  selectedCurrency.value = currencyId; // 선택된 통화 업데이트
  fetchExchangeRates(currencyId); // 선택한 통화의 환율 정보 가져오기
};


const fetchDailyExchangeData = async (currencyId) => {
  loading.value = true;
  try {
    const response = await axios.get('/api/exchange/daily', {
      params: {
        currencyId: currencyId,
      },
    });
    const data = response.data;
    exchangeData.value = {
      basePrice: data.exchange.basePrice,
      exchangeRateDate: new Date(data.exchange.exchangeRateDate).toLocaleDateString(),
      currencyCode: data.currency.currencyCode,
      currencyName: data.currency.currencyName,
      dailyChangeRate: data.dailyChangeRate,
      baseRateDifference: data.baseRateDifference,
    };
    console.log('저장된 환율 데이터:', exchangeData.value);
  } catch (error) {
    console.error('환율 데이터를 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};
onMounted(async () => {
  fetchExchangeRates(23);
  fetchStockData();
});
</script>

<style scoped>
.subjectBetween {
  display: flex;
  justify-content: space-between;
}

.todayExchange a {
  text-decoration: none;
  color: black;
  font-size: 20px;
  font-weight: 600;
  margin-top: 20px;
}

.review-section__title {
  font-size: 40px;
  font-family: J6;
}

.currency-buttons {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
}

.todayBox {
  width: 100%;
  background-color: rgba(249, 250, 251, 1);
}

.todayBox h3 {
  font-size: 30px;
}

.todayCo {
  width: 600px;
  height: 400px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 30px;
  background-color: white;
}

.exchangeBox {
  width: 600px;
  height: 450px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 20px;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content {
  display: flex;
  gap: 15px;
  width: 100%;
}

.line-c {
  width: 300px;
  /* 원하는 너비 */
  height: 220px;
  /* 원하는 높이 */
  margin: 0 auto;
  /* 가운데 정렬 */
}

.new-section {
  flex: 1;
  min-height: 250px;
  border-radius: 15px;
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 250px;
}

.exchangeCountry1,
.exchangeCountry2 {
  width: 80px;
  height: 40px;
  border-radius: 20px;
  border: none;
  color: white;
  font-size: 18px;
}

.exchangeCountry1 {
  background-color: rgba(67, 139, 115, 1);
}

.exchangeCountry2 {
  background-color: rgba(144, 218, 170, 1);
}

h3 {
  font-family: J4;
  margin: 20px;
}

.stock-card {
  flex: 1;
  padding: 15px;
  display: flex;
  flex-direction: column;
  font-family: J3;
  align-items: center;
  gap: 10px;
}

.todayCo {
  display: flex;
  flex-direction: row;
  gap: 20px;
  width: 100%;
  height: auto;
  padding: 20px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  font-family: J4;
  background-color: white;
}

.btn_country {
  font-family: J3;
  font-weight: bold;
}

.upDown {
  font-size: 18px;
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

.amount-txt {
  font-size: 25px;
  font-weight: bold;
}

.stock-title {
  font-family: J4;
  font-size: 20px;
}

.d-inline-block {
  font-family: J4;
}

.active {
  background-color: #ffcc00; /* 선택된 버튼의 배경색 */
  color: white; /* 선택된 버튼의 텍스트 색상 */
  border: 2px solid #ffcc00; /* 선택된 버튼의 테두리 */
}

.date {
  font-family: J3;
  font-weight: bold;
}

.title {
  font-size: 25px
}

</style>
