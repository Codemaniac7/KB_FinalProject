<template>
    <div class="stock-dashboard">
        <!-- 국내주식 헤더 -->
        <h1>국내주식</h1>

        <!-- 이 시각 증시 섹션 -->
        <section class="current-stocks">
            <h2>이 시각 증시</h2>
            <div class="stock-cards">
                <div v-for="(stock, index) in currentStocks" :key="index" class="stock-card">
                    <h3>{{ stock.name }}</h3>
                    <p>{{ stock.price }}원</p>
                    <p>{{ stock.change }} {{ stock.percentChange }}%</p>
                    <img :src="stock.graph" alt="Graph" />
                </div>
            </div>
        </section>

        <!-- 상위권 TOP3 섹션 -->
        <section class="top3-stocks">
            <h2>현재 상위권 TOP3 🏆</h2>
            <div class="top3-cards">
                <div v-for="(stock, index) in top3Stocks" :key="index" class="top3-card">
                    <h3>{{ stock.name }}</h3>
                    <p>{{ stock.price }}원</p>
                    <img :src="stock.graph" alt="Graph" />
                    <router-link :to="'/stock/' + stock.id">자세히 보기</router-link>
                </div>
            </div>
        </section>


        <!-- 검색 섹션 -->
        <section class="stock-search">
            <h2>주식 검색</h2>

            <!-- 키워드 입력 -->
            <div>
                <input v-model="searchKeyword" placeholder="키워드를 입력해주세요" />
                <button @click="searchStocks">검색</button>
            </div>

            <!-- 필터링 옵션 -->
            <div class="filter-options">
                <div>
                    <label>가격 범위:</label>
                    <input v-model.number="priceMin" placeholder="최소 가격" type="number" />
                    ~
                    <input v-model.number="priceMax" placeholder="최대 가격" type="number" />
                </div>
                <div>
                    <label>변동률:</label>
                    <input v-model.number="changeMin" placeholder="최소 변동률" type="number" />
                    ~
                    <input v-model.number="changeMax" placeholder="최대 변동률" type="number" />
                </div>
                <div>
                    <label>시장 종류:</label>
                    <select v-model="marketType">
                        <option value="">모든 시장</option>
                        <option value="코스피">코스피</option>
                        <option value="코스닥">코스닥</option>
                    </select>
                </div>
            </div>


            <!-- 검색 결과 -->
            <div class="search-results">
                <div v-for="(stock, index) in searchResults" :key="index" class="search-result">
                    <p>{{ stock.name }}</p>
                    <p>{{ stock.price }}원</p>
                    <p>{{ stock.change }} ({{ stock.percentChange }}%)</p>
                    <router-link :to="'/stock/' + stock.id">자세히 보기</router-link>
                </div>
            </div>
        </section>

        <!-- 더 보기 버튼 -->
        <button @click="loadMoreStocks" class="load-more-button">더 보기</button>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            currentStocks: [],
            searchKeyword: '',
            priceMin: null,
            priceMax: null,
            changeMin: null,
            changeMax: null,
            marketType: '',
            searchResults: [],
            page: 1
        };
    },
    mounted() {
        this.fetchCurrentStocks();
        this.fetchTop3Stocks();
    },
    methods: {
        async fetchCurrentStocks() {
            try {
                const response = await axios.get('https://api.example.com/current-stocks');
                this.currentStocks = response.data;
            } catch (error) {
                console.error('Error fetching current stocks:', error);
            }
        },
        async fetchTop3Stocks() {
            try {
                const response = await axios.get('https://api.example.com/top3-stocks');
                this.top3Stocks = response.data;
            } catch (error) {
                console.error('Error fetching top3 stocks:', error);
            }
        },
        async searchStocks() {
            try {
                // 필터링 조건을 추가한 API 요청
                const response = await axios.get('https://api.example.com/search-stocks', {
                    params: {
                        keyword: this.searchKeyword,
                        price_min: this.priceMin,
                        price_max: this.priceMax,
                        change_min: this.changeMin,
                        change_max: this.changeMax,
                        market_type: this.marketType,
                        page: this.page
                    }
                });
                this.searchResults = response.data;
            } catch (error) {
                console.error('Error searching stocks:', error);
            }
        },
        async loadMoreStocks() {
            try {
                this.page += 1;
                const response = await axios.get('https://api.example.com/search-stocks', {
                    params: {
                        keyword: this.searchKeyword,
                        price_min: this.priceMin,
                        price_max: this.priceMax,
                        change_min: this.changeMin,
                        change_max: this.changeMax,
                        market_type: this.marketType,
                        page: this.page
                    }
                });
                this.searchResults = [...this.searchResults, ...response.data];
            } catch (error) {
                console.error('Error loading more stocks:', error);
            }
        }
    }
};
</script>

<style scoped>
/* 기본 레이아웃 스타일 */
.stock-dashboard {
    padding: 20px;
}

h1,
h2 {
    font-weight: bold;
    margin-bottom: 20px;
}

.current-stocks {
    display: flex;
    flex-direction: column;
}

.stock-cards {
    display: flex;
    justify-content: space-around;
    background-color: #F5F8F4;
}

.stock-card {
    background-color: #f5f5f5;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
}

.stock-card img {
    width: 100px;
    height: 50px;
}

/* 검색 섹션 스타일 */
.stock-search {
    margin-top: 40px;
    text-align: center;
}

.stock-search input {
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    width: 300px;
}

.stock-search button {
    margin-left: 10px;
    padding: 10px 20px;
    background-color: #448C74;
    color: white;
    border: none;
    border-radius: 5px;
}

.filter-options {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    gap: 20px;
}

.filter-options div {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.filter-options label {
    margin-bottom: 5px;
}

.filter-options input,
.filter-options select {
    padding: 5px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

/* 검색 결과 스타일 */
.search-results {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
}

.search-result {
    background-color: #f9f9f9;
    padding: 15px;
    border-radius: 10px;
    width: 500px;
    margin-top: 10px;
    text-align: center;
}

.load-more-button {
    display: block;
    margin: 20px auto;
    padding: 10px 20px;
    background-color: #448C74;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
</style>