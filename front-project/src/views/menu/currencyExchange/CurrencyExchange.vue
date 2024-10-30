<script setup>
import { ref, watch, computed } from 'vue';
import axios from 'axios';

// 리액티브 변수 선언
const exchangeFees = ref([]);
const exchangeRateData = ref(null); // 환율 데이터를 저장할 변수 추가
const loadingFees = ref(false); // 환전 수수료 로딩 상태
const loadingRate = ref(false); // 환율 데이터 로딩 상태
const errorMessage = ref('');

// 선택된 통화 ID
const selectedCurrencyId = ref(null);

// 국가 목록 업데이트
const nationalMoney = ref([
  { currencyId: 2, code: 'AUD', name: '호주 달러', countryCode: 'AU', flagUrl: 'https://flagcdn.com/24x18/au.png', largeFlagUrl: 'https://flagcdn.com/w640/au.png' },
  { currencyId: 5, code: 'CAD', name: '캐나다 달러', countryCode: 'CA', flagUrl: 'https://flagcdn.com/24x18/ca.png', largeFlagUrl: 'https://flagcdn.com/w640/ca.png' },
  { currencyId: 6, code: 'CHF', name: '스위스 프랑', countryCode: 'CH', flagUrl: 'https://flagcdn.com/24x18/ch.png', largeFlagUrl: 'https://flagcdn.com/w640/ch.png' },
  { currencyId: 9, code: 'EUR', name: 'EU 유로', countryCode: 'EU', flagUrl: 'https://flagcdn.com/24x18/eu.png', largeFlagUrl: 'https://flagcdn.com/w640/eu.png' }, // EU는 별도의 이미지 필요
  { currencyId: 10, code: 'GBP', name: '영국 파운드', countryCode: 'GB', flagUrl: 'https://flagcdn.com/24x18/gb.png', largeFlagUrl: 'https://flagcdn.com/w640/gb.png' },
  { currencyId: 11, code: 'HKD', name: '홍콩 달러', countryCode: 'HK', flagUrl: 'https://flagcdn.com/24x18/hk.png', largeFlagUrl: 'https://flagcdn.com/w640/hk.png' },
  { currencyId: 13, code: 'JPY', name: '일본 엔', countryCode: 'JP', flagUrl: 'https://flagcdn.com/24x18/jp.png', largeFlagUrl: 'https://flagcdn.com/w640/jp.png' },
  { currencyId: 18, code: 'NZD', name: '뉴질랜드 달러', countryCode: 'NZ', flagUrl: 'https://flagcdn.com/24x18/nz.png', largeFlagUrl: 'https://flagcdn.com/w640/nz.png' },
  { currencyId: 21, code: 'SGD', name: '싱가포르 달러', countryCode: 'SG', flagUrl: 'https://flagcdn.com/24x18/sg.png', largeFlagUrl: 'https://flagcdn.com/w640/sg.png' },
  { currencyId: 22, code: 'THB', name: '태국 바트', countryCode: 'TH', flagUrl: 'https://flagcdn.com/24x18/th.png', largeFlagUrl: 'https://flagcdn.com/w640/th.png' },
  { currencyId: 23, code: 'USD', name: '미국 달러', countryCode: 'US', flagUrl: 'https://flagcdn.com/24x18/us.png', largeFlagUrl: 'https://flagcdn.com/w640/us.png' }
]);

// 통화 목록을 두 개의 행으로 분할
const firstRowCurrencies = computed(() => nationalMoney.value.slice(0, 6));
const secondRowCurrencies = computed(() => nationalMoney.value.slice(6, 11));

// 선택된 통화의 정보를 가져오는 컴퓨티드 프로퍼티
const selectedCurrency = computed(() => {
  return nationalMoney.value.find(nat => nat.currencyId === selectedCurrencyId.value) || {};
});

// 텍스트를 지정된 길이로 자르는 메서드
const truncate = (text, length) => {
  if (text.length > length) {
    return text.substring(0, length) + '...';
  }
  return text;
};

const fetchExchangeRate = async () => {
  loadingRate.value = true;
  errorMessage.value = '';
  exchangeRateData.value = null;

  try {
    const params = {
      currencyId: selectedCurrencyId.value,
      date: new Date().toISOString().split('T')[0]
    };

    const response = await axios.get('/api/exchange/daily', { params });

    if (response.data) {
      exchangeRateData.value = response.data;
    } else {
      exchangeRateData.value = null;
      console.warn('환율 데이터를 찾을 수 없습니다.');
    }

  } catch (error) {
    console.error('환율 정보를 가져오는 중 오류 발생:', error);
    errorMessage.value = '환율 정보를 가져오는 중 오류가 발생했습니다.';
    exchangeRateData.value = null;
  } finally {
    loadingRate.value = false;
  }
};


// 환전 수수료 정보 가져오기 함수
const fetchExchangeFees = async () => {
  loadingFees.value = true;
  errorMessage.value = '';
  try {
    const limit = 16; // 일관된 limit 값 사용
    const params = {
      limit: limit
    };
    if (selectedCurrencyId.value) {
      params.currencyId = selectedCurrencyId.value;
    }
    const response = await axios.get('/api/exchange/fee', { params });

    if (Array.isArray(response.data) && response.data.length > 0) {
      exchangeFees.value = response.data;
    } else {
      exchangeFees.value = [];
      console.warn('검색 결과가 없습니다.');
    }
  } catch (error) {
    console.error('환전 수수료 목록을 가져오는 중 오류 발생:', error);
    errorMessage.value = '환전 수수료 정보를 가져오는 중 오류가 발생했습니다.';
    exchangeFees.value = [];
  } finally {
    loadingFees.value = false;
  }
};

// 날짜 포맷팅 함수
const formatDate = (timestamp) => {
  return new Date(timestamp).toLocaleDateString();
};

watch(selectedCurrencyId, (newVal) => {
  if (newVal !== null) { // 선택된 통화가 있을 때만 호출
    fetchExchangeRate();  // 환율 정보 가져오기
    fetchExchangeFees();  // 수수료 정보 가져오기
  }
});

</script>

<template>
  <div class="container text-center animate-on-load">
    <br /><br />
    <h1 class="d-inline">환전</h1>
    <br /><br /><br /><br />
    <div class="text-start">
      <form @submit.prevent="fetchExchangeFees">
        <br />
        <div class="additional-info">
          <ul class="filterBar">
            <li>
              <h2 style="font-weight: 700;">국가</h2>
            </li>
            <li class="filter">
              <!-- 첫 번째 행: 6개 -->
              <div class="radio-group row">
                <div class="radio-row">
                  <label
                      v-for="national in firstRowCurrencies"
                      :key="national.currencyId"
                      :class="{ selected: selectedCurrencyId === national.currencyId }"
                      class="radio-label"
                  >
                    <input
                        type="radio"
                        name="nationalMoney"
                        :value="national.currencyId"
                        v-model="selectedCurrencyId"
                        aria-label="국가 선택: {{ national.name }}"
                    />
                    <img :src="national.flagUrl" :alt="national.name + ' flag'" class="flag-image" />
                    <span>{{ national.name }}</span>
                  </label>
                </div>
              </div>
              <!-- 두 번째 행: 5개 -->
              <div class="radio-group row">
                <div class="radio-row">
                  <label
                      v-for="national in secondRowCurrencies"
                      :key="national.currencyId"
                      :class="{ selected: selectedCurrencyId === national.currencyId }"
                      class="radio-label"
                  >
                    <input
                        type="radio"
                        name="nationalMoney"
                        :value="national.currencyId"
                        v-model="selectedCurrencyId"
                        aria-label="국가 선택: {{ national.name }}"
                    />
                    <img :src="national.flagUrl" :alt="national.name + ' flag'" class="flag-image" />
                    <span>{{ national.name }}</span>
                  </label>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </form>
    </div>
    <br /><br />
    <!-- 환율 정보 표시 -->
    <div class="exchangeContent text-start">
      <div v-if="selectedCurrencyId">
        <h1 class="selected-country">{{ selectedCurrency.name }} ({{ selectedCurrency.code }}) </h1>
        <br>  <br>
        <div class="flex-between">
          <div class="exchangeRate text-center" v-if="exchangeRateData && exchangeRateData.exchange">
            <h1 class="text-start"><b>환율 정보</b></h1>
            <div class="nationalFlag">
              <img :src="selectedCurrency.largeFlagUrl" alt="Flag of {{ selectedCurrency.name }}">
            </div>
            <div class="erBox text-start">
              <h3>살 때 {{ exchangeRateData.exchange.receivingPrice }}원</h3>
              <h3>팔 때 {{ exchangeRateData.exchange.sendingPrice }}원</h3>
              <h3>기준 {{ exchangeRateData.exchange.basePrice }}원</h3>
              <h4>전일 대비 변동률 {{ exchangeRateData.dailyChangeRate }}%</h4>
              <h4>전일 대비 차이 {{ exchangeRateData.baseRateDifference }}원</h4>
              <h4 class="text-end">기준일 {{ formatDate(exchangeRateData.exchange.exchangeRateDate) }}</h4>
            </div>
          </div>
        </div>
        <br><br><br>
        <h1><b>환전수수료율</b></h1>
      </div>
      <br /><br /><br />
      <!-- 로딩 메시지 -->
      <div v-if="loadingFees || loadingRate" class="loading-box">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">데이터를 불러오는 중...</span>
        </div>
      </div>
      <!-- 에러 메시지 표시 -->
      <div v-else-if="errorMessage" class="error-box">
        <p>{{ errorMessage }}</p>
      </div>
      <!-- 환전 수수료 정보 표시 -->
      <div v-else>
        <ul class="itemBoxDiv text-start">
          <li
              v-for="fee in exchangeFees"
              :key="fee.currencyId + fee.bankName + fee.baseDate"
              class="itemBox"
          >
            <a :href="fee.bankUrl" target="_blank" rel="noopener noreferrer" class="itemBoxLink">
              <div class="bankName">
                <h3><strong>{{ truncate(fee.bankName, 7) }}</strong></h3>
              </div>
              <br>
              <div class="divExchange">
                <br>
                <h4 class="text-center">살 때 {{ fee.buyingFee }}%</h4>
                <h4 class="text-center">팔 때 {{ fee.sellingFee }}%</h4>
                <p class="text-center">기준일 {{ formatDate(fee.baseDate) }}</p>
                <p class="text-end m-3">더보기 ></p>
              </div>
            </a>
          </li>
        </ul>
        <!-- 검색 결과가 없을 때 메시지 표시 -->
        <div v-if="exchangeFees.length === 0 && !loading && !errorMessage">
          <h2 class="text-center" style="color: rgba(68, 140, 116, 1);"><i class="ai-bulb-alt"></i> 국가를 선택하시면 은행별 환전수수료율이 나타납니다</h2>
          <br><br>
          <div class="noSearch">
            <h4>은행에서 많이 환전하는 통화를 대상으로 비교공시하고 있으며,<br> 은행 사정에 따라 환전이 불가한 통화 등의 경우 비교공시대상에서 제외됩니다.</h4>
            <h4><strong>정보 이용시 유의사항 안내</strong></h4>
            <p>
              은행연합회 소비자포털 사이트 비교공시 정보는 은행의 다양한 금융상품 중 일부 중요 상품을 중심으로 금리와 수수료 등을 은행간에 개략적으로 비교할 수 있도록 참고로 제공하는 것입니다.
              은행연합회와 각 은행은 최신 정보를 제공하기 위해 적극 노력하고 있으나 은행의 상품별 금리, 수수료, 기타 거래조건이 수시로 변경됨에 따라 공시가 지연되는 경우도 있으므로, 보다 정확한 최신 정보를 파악하기 위해서는 해당 은행에 반드시 문의하시기 바랍니다.
            </p>
            <h4><strong>환전수수료율이란?</strong></h4>
            <p>
              환전수수료율은 외국환은행에서 외국통화 수출입시 발생하는 각종 비용 등을 반영한 것으로 외국통화별로 다르기 때문에 통화별 환전수수료율에 차이가 있습니다.
              외국통화를 사신 후 다시 원화로 파실 때 환율변동 및 환전수수료율 차에 따라 환차손(환전되는 원화금액의 손해)이 발생할 수 있으니, 현찰을 사실 때와 파실 때 환율 및 환전수수료율을 확인하시기 바랍니다.
            </p>
            <p>(사용 예) A 은행의 미 달러화 매매기준율이 1,070원이고, 환전수수료율이 사실 때 1.75%인 경우로 가정하면 현찰 사실 때 및 파실 때 환율은 다음과 같습니다.</p>
            <p>
              현찰 사실 때 환율 1,088.73 = 1,070.00 + (1,070.00 x 1.75%)<br>
              현찰 파실 때 환율 1,051.28 = 1,070.00 - (1,070.00 x 1.75%)
            </p>
            <p>
              (유의사항) 외화 환전 시 환전수수료율이 낮은 은행이 무조건 유리한 것은 아니므로 은행별 금액기준 환율, 고객우대 조건, 환전이벤트 등을 충분히 확인하여 환전은행을 선택할 필요가 있음
            </p>
            <h4><strong>보다 유리하게 환전할 수 있는 방법</strong></h4>
            <ul>
              <li>인터넷(우대율 최대 90%)을 통해 환전 신청 후 외화 수령 (단, 수령가능 영업점 및 사전 환전신청 후 공항환전소에서 수령 가능한 시점 등은 은행별로 확인 필요)</li>
              <li>주거래은행(계좌개설 및 신용카드 보유 등) 영업점을 찾아 적용가능한 우대율을 확인한 후 직접 환전</li>
              <li>출국 전 시간여유가 없을 경우 공항에서 인터넷을 통해 환전 신청 후 수령 (단, 당일 수령가능 여부 및 우대율 적용 가능 여부는 은행별로 확인 필요)</li>
            </ul>
            <h4><strong>출국 전 환전안내</strong></h4>
            <p>
              출국 전 환전은 충분한 시간을 가지고 주거래은행의 인터넷, 어플(앱), 일반 영업점 방문 등을 이용하여 환전하시기 바라며, 인천공항지점에서 환전할 경우 국제공항이라는 특수성(대부분의 업무가 환전업무에 치중되어 있는 반면 임차료 등의 운영비용이 일반 영업점에 비하여 상대적으로 높음) 등으로 인하여 일반 영업점과 다른 환전수수료율이 적용되오니 참고하시기 바랍니다.
              환전수수료율이 무조건 낮은 은행이 유리한 것은 아니므로 환전할 금액, 주거래 여부 등을 충분히 (비교)확인하여 자신에게 유리한 곳을 선택하시기 바랍니다.
            </p>
          </div>
        </div>
      </div>
      <br><br><br>
    </div>
  </div>
</template>

<style scoped>

.exchangeRate {
  position: relative; /* 추가 */
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: white;
  width: 500px;
  height: 330px;
  border-radius: 30px;
  padding: 20px;
}

.nationalFlag img {
  position: absolute; /* 추가 */
  top: 50px; /* 상단 여백 설정 */
  right: 50px; /* 오른쪽 여백 설정 */
  width: 180px; /* 이미지 크기 조정 */
  height: 120px;
  border-radius: 20px;
  border: 1px solid lightgrey;
}

.exchangeRate h1{
  padding: 20px;
}


.erBox h4{
  color: rgba(68, 140, 116, 1);
}
.erBox {
  width: 100%;
  padding: 10px;
  color: rgb(42, 42, 42);
}

.exchangeRate h2 {
  font-size: 30px;
  padding: 20px;
}


@media (max-width: 768px) {
  .currency-info {
    flex-direction: column;
    text-align: center;
  }

  .nationalFlag img, .exchangeRate {
    width: 100%;
  }
}

.ai-bulb-alt {
  font-size: 40px;
  vertical-align: text-bottom;
}

.noSearch {
  border-radius: 30px;
  border: 1px solid lightgrey;
  padding: 40px;
}

.container {
  font-family: J3;
}

.exchangeContent {
  width: 80%;
  margin: 0 auto;
}

.bankName:hover {
  background-color: rgba(231, 236, 243, 1);
  color: rgba(68, 140, 116, 1);
}

.selected-country {
  color: rgba(68, 140, 116, 1);
  margin-top: 10px;
  font-size: 50px;
}

.bankName {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
}

.divExchange {
  border-radius: 20px;
  background-color: white;
  color: rgba(68, 140, 116, 1);
  width: 100%;
}

.flag-image {
  width: 24px;
  height: auto;
  margin-right: 8px;
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

.error-box {
  color: red;
  padding: 20px;
  margin: 20px;
  text-align: center;
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

.itemBoxDiv {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.itemBox {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

.itemBoxLink {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 20px;
  height: 100%;
  width: 100%;
  border: 1px solid rgba(231, 236, 243, 1);
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  text-decoration: none;
  transition: box-shadow 0.3s ease, transform 0.3s ease;
  color: white;
}

.itemBoxLink:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transform: translateY(-5px);
  background-color: rgba(231, 236, 243, 1);
  color: rgba(68, 140, 116, 1);
}

.radio-group {
  display: flex;
  flex-wrap: wrap;
  max-height: 300px;
  overflow-y: auto;
}

.radio-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.radio-label {
  display: inline-flex;
  align-items: center;
  width: auto;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size: 18px;
  text-align: center;
  margin: 5px;
  padding: 0 10px;
  cursor: pointer;
}

.radio-label:hover {
  border: 1px solid rgba(68, 140, 116, 1);
  color: rgba(68, 140, 116, 1);
}

.filter .selected {
  background-color: rgba(68, 140, 116, 1) !important;
  color: white;
  border: 1px solid rgba(68, 140, 116, 1);
  transition: background-color 0.3s ease, color 0.3s ease;
}

.filter .selected:hover {
  background-color: white;
  color: rgb(38, 108, 84);
  border: 1px solid rgba(48, 120, 96, 1);
}

input[type="radio"] {
  display: none;
}

.filterBar {
  margin-left: 13%;
  list-style: none;
  width: 80%; /* 너비를 80%로 조정 */
  margin-bottom: 5%;
}

/* 반응형 디자인 추가 */
@media (max-width: 1200px) {
  .itemBoxDiv {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 992px) {
  .itemBoxDiv {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) {
  .itemBoxDiv {
    grid-template-columns: 1fr;
  }
}

</style>
