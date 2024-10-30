<template>
  <div class="stock-chart">
    <!-- 상단 정보 -->
    <div class="stock-header">
      <div class="stock-title">
        <h1>{{ stockName }}</h1> 
        <p class="stock-code">{{ stockCode }}</p>
      </div>
      <div class="stock-price">
        <h2>{{ formatNumber(currentPrice) }}원</h2>
        <p :class="{ 'positive': priceChange > 0, 'negative': priceChange < 0 }">
          {{ priceChange > 0 ? '+' : '' }}{{ formatNumber(priceChange) }}원 ({{ priceChangePct }}%)
        </p>
      </div>
    </div>

    <!-- 세부 정보 -->
    <div class="stock-details">
      <div class="detail-item"><span>누적 거래량</span><span>{{ formatNumber(volume) }}주</span></div>
      <div class="detail-item"><span>시가총액</span><span>{{ formatNumber(htsAvls) }}억원</span></div>
      <div class="detail-item"><span>52주 최고</span><span>{{ formatNumber(w52Hgpr) }}원</span></div>
      <div class="detail-item"><span>52주 최저</span><span>{{ formatNumber(w52Lwpr) }}원</span></div>
    </div>

    <!-- 로딩 상태 -->
    <div v-if="isLoading" class="loading">
      데이터를 불러오는 중입니다...
    </div>

    <!-- 탭 메뉴 -->
    <div v-if="!isLoading" class="tabs">
      <button v-for="tab in tabs" :key="tab" @click="currentTab = tab" :class="{ active: currentTab === tab }">
        {{ tab }}
      </button>
    </div>

    <!-- 탭 콘텐츠 -->
    <div v-if="!isLoading" class="tab-content">
      <!-- 차트 탭 -->
      <div v-if="currentTab === '차트'" class="chart-container">
        <div class="time-range">
          <button v-for="range in timeRanges" :key="range" :class="{ active: selectedRange === range }" @click="changeTimeRange(range)">
            {{ range }}
          </button>
        </div>
        <apexchart type="candlestick" height="350" :options="chartOptions" :series="chartSeries"></apexchart>
      </div>

      <!-- 뉴스 탭 -->
      <div v-else-if="currentTab === '뉴스'" class="news-container">
        <div v-for="news in newsItems" :key="news.link" class="news-item" @click="openNewsModal(news)">
  <h3 v-html="news.title"></h3>
  <p v-html="news.description"></p>
  <p class="news-date">{{ news.pubDate }}</p> <!-- 날짜와 시간을 표시 -->
</div>
        <div class="pagination">
          <button @click="loadMoreNews" :disabled="isLoadingNews">더 보기</button>
        </div>
      </div>
    </div>

    <!-- 뉴스 모달 -->
    <div v-if="showNewsModal" class="modal-overlay" @click="closeNewsModal">
  <div class="modal-content" @click.stop>
    <button class="close-button" @click="closeNewsModal">×</button>
    <h2 v-html="selectedNews.title"></h2> <!-- 제목 표시 -->
    <p class="news-date">{{ selectedNews.pubDate }}</p> <!-- 날짜 표시 -->
    
    <div v-if="selectedNews.image">
      <img :src="selectedNews.image" :alt="selectedNews.title" class="news-image">
    </div>

    <p v-html="selectedNews.description"></p> <!-- 소제목 표시 -->
    
    <!-- 본문 내용 추가 -->
    <div class="news-content" v-html="selectedNews.content"></div>
    <p v-html="selectedNews.content"></p> 
    <a :href="selectedNews.link" target="_blank" class="read-more">원문 보기</a>
  </div>
</div>
  </div>
</template>

<script>
import axios from 'axios';
import VueApexCharts from 'vue3-apexcharts';

export default {
  components: {
    apexchart: VueApexCharts,
  },
  props: [
    'stockCode', 'stockName', 'currentPrice', 'priceChange', 'priceChangePct', 'volume', 'htsAvls', 'w52Hgpr', 'w52Lwpr'
  ],
  data() {
    return {
      tabs: ['차트', '뉴스'],
      currentTab: '차트',
      timeRanges: ['1일', '1주', '1개월', '3개월', '1년', '3년', '5년'],
      selectedRange: '3개월', // 기본값을 3개월로 변경
      newsItems: [],
      newsPage: 1,
      isLoadingNews: false,
      isLoading: true,
      showNewsModal: false,
      selectedNews: null,
      chartOptions: {
        chart: {
          type: 'candlestick',
        },
        xaxis: {
          type: 'datetime',
          labels: {
            formatter: function(val) {
              return new Date(val).toLocaleDateString();
            }
          }
        },
        yaxis: {
          tooltip: {
            enabled: true,
          },
        },
      },
      chartSeries: [],
    };
  },
  methods: {
    formatNumber(value) {
      if (!value) return '0';
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    changeTimeRange(range) {
      const rangeMap = {
        '1일': '1day',
        '1주': '1week',
        '1개월': '1month',
        '3개월': '3months',
        '1년': '1year',
        '3년': '3years',
        '5년': '5years'
      };
      this.selectedRange = range;
      this.fetchChartData(rangeMap[range] || '3months');
    },
    async fetchChartData(range = '3months') {
      try {
        this.isLoading = true;
        const response = await axios.get(`http://localhost:8080/api/stockcandle/${this.stockCode}/${range}`);
        
        if (response.data) {
          const chartData = response.data
            .filter(item => {
              const date = new Date(item.stockCandleDay);
              return date.getDay() !== 0 && date.getDay() !== 6; // 주말 제외
            })
            .map(item => ({
              x: new Date(item.stockCandleDay).getTime(),
              y: [item.stockCandleOpen, item.stockCandleHigh, item.stockCandleLow, item.stockCandleClose]
            }));
          this.chartSeries = [{ name: '주가', data: chartData }];
          
          // x축 범위 설정
          if (chartData.length > 0) {
            this.chartOptions = {
              ...this.chartOptions,
              xaxis: {
                ...this.chartOptions.xaxis,
                min: chartData[0].x,
                max: chartData[chartData.length - 1].x
              }
            };
          }
        }
      } catch (error) {
        console.error('차트 데이터를 가져오는 중 오류 발생:', error);
      } finally {
        this.isLoading = false;
      }
    },
    async fetchNews() {
  if (this.isLoadingNews) return;

  this.isLoadingNews = true;
  try {
    const query = `${this.stockName} 경제`;
    const response = await axios.get('/api/news', {
      params: {
        query: query,                  // 검색어 (UTF-8로 인코딩된 상태로 요청됨)
        display: 10,                    // 한 번에 표시할 검색 결과 개수 (기본값 10)
        start: (this.newsPage - 1) * 10 + 1,  // 검색 시작 위치
        sort: 'sim'                     // 정확도순으로 정렬 (기본값)
      }
    });

    if (response.data && response.data.items) {
      const newItems = response.data.items.map(this.processNewsItem);
      this.newsItems = [...this.newsItems, ...newItems];
      this.newsPage++;  // 페이지 증가
    }
  } catch (error) {
    console.error('뉴스 데이터를 가져오는 데 실패했습니다:', error);
  } finally {
    this.isLoadingNews = false;
  }
},
processNewsItem(item) {
  return {
    title: item.title.replace(/<\/?[^>]+(>|$)/g, ""), // HTML 태그 제거
    description: item.description.replace(/<\/?[^>]+(>|$)/g, ""), // HTML 태그 제거
    link: item.link,
    pubDate: new Date(item.pubDate).toLocaleString(), // 날짜와 시간 포맷

    image: item.image
  };
},
    loadMoreNews() {
      this.fetchNews();
    },
    openNewsModal(news) {
      this.selectedNews = news;
      this.showNewsModal = true;
    },
    closeNewsModal() {
      this.showNewsModal = false;
      this.selectedNews = null;
    }
  },
  watch: {
    currentTab(newTab) {
      if (newTab === '뉴스' && this.newsItems.length === 0) {
        this.fetchNews();
      }
    },
    stockCode: {
      immediate: true,
      handler(newCode) {
        if (newCode) {
          this.fetchChartData();
        }
      }
    }
  },
  mounted() {
    this.fetchChartData(); // 페이지 로드 시 3개월 차트 데이터 로드
  }
};
</script>
  
<style scoped>

* {
  font-family: 'J5', sans-serif;
}
p {
  font-family: 'J2' !important;
}
.loading {
  text-align: center;
  padding: 20px;
  font-size: 18px;
  color: #999;
}
.stock-chart {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    background-color: #F9FAFB;
    color: black;
    border-radius: 8px;
    font-size: 18px;
}

.stock-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 20px;
    padding-top: 30px;
}

.stock-title h1 {
    margin: 0;
    font-size: 24px;
}

.stock-code {
    color: #888;
    margin: 5px 0;
}

.stock-price {
    text-align: right;
}

.stock-price h2 {
    margin: 0;
    font-size: 28px;
}

.tabs {
    display: flex;
    margin-bottom: 20px;
}

.tabs button {
    background: none;
    border: none;
    color: #888;
    cursor: pointer;
    padding: 10px 15px;
    font-size: 16px;
}

.tabs button.active {
    color: #438B73;
    border-bottom: 2px solid #438B73;
}

.chart-container,
.order-book,
.news-container,
.disclosure {
    min-height: 300px;
    background-color: white;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 20px;
}
.news-content {
  margin-top: 20px;
  font-size: 16px;
  line-height: 1.5;
  color: #333;
}

.time-range {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.time-range button {
    background: none;
    border: none;
    color: #438B73;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 15px;
}

.time-range button.active {
    background-color: #F9FAFB;
    color: #438B73;
}

.stock-details {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 10px;
}

.detail-item {
    display: flex;
    justify-content: space-between;
    padding: 5px 0;
    border-bottom: 1px solid #333;
}

.positive {
    color: #e15241;
}

.negative {
    color: #2988de;
}

.news-item {
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #333;
    cursor: pointer;
    transition: background-color 0.3s;
}

.news-item:hover {
    background-color: #F9FAFB;
}

.pagination {
    text-align: center;
    margin-top: 20px;
}

.pagination button {
    background-color: #F9FAFB;
    color: black;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
}

.pagination button:disabled {
    background-color: white;
    color : black;
    cursor: not-allowed;
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: #F9FAFB;
    padding: 20px;
    border-radius: 8px;
    max-width: 600px;
    max-height: 80vh;
    overflow-y: auto;
    position: relative;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 24px;
    color: #fff;
    cursor: pointer;
}

.news-date {
    color: #888;
    font-size: 0.9em;
    margin-bottom: 10px;
    margin-top: 5px;
}

.news-image {
    max-width: 100%;
    height: auto;
    margin-bottom: 15px;
}

.read-more {
    display: inline-block;
    margin-top: 15px;
    color: #2988de;
    text-decoration: none;
}

.read-more:hover {
    text-decoration: underline;
}
</style>