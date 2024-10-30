<template>
  <div class="bc text-center">
    <div class="container">
      <div v-if="loading">로딩중..</div>
      <div v-else class="savingDetailBox">
        <div class="text-start m-4 rank-section animated-item">
          <!-- <img class="rankMedal" src="/img/emoji/goldmedal.png" alt="Gold Medal"> -->
          <div class="rank-content">
            <!-- <h4 class="rank-title">적금 최고금리 순위</h4>
            <h4 class="rank">{{ deposit.rank }}위</h4> -->
          </div>
        </div>

        <ul class="text-start">
          <li>
            <div class="bankLogo d-inline">
              <img
                  :src="`/img/bank/${deposit.bankId}.png`"
                  alt="Bank Logo"
                  @error="handleImageError"
              />
            </div>&nbsp;
            <p class="bankName d-inline">{{ deposit.bank.bankName }}</p>
          </li>
          <li><br /><br /></li>
          <li><h1>{{ deposit.savingName }}</h1></li>
          <li><br /><br /></li>
        </ul>

        <div class="rate-container animated-item">
          <div class="rate-row">
            <span class="subject2">최고 금리</span>
            <span class="subject2">기본 금리 ({{ maxSavingTerm }}개월)</span>
          </div>
          <div class="rate-row">
            <span class="bestSubject">{{ formatRate(maxInterestMaxRate) }}%</span>
            <span class="normalSubject">{{ formatRate(maxSavingTermRate) }}%</span>
          </div>
        </div>

        <ul class="text-start mt-5">
          <li>
            <a :href="deposit.bank?.bankUrl || '#'" target="_blank" rel="noopener noreferrer">
              {{ deposit.bank?.bankName || '은행명' }}에서 보기 <i class="ai-chevron-right"></i>
            </a>
          </li>
        </ul>

        <hr class="mt-5 hr" />

        <div class="text-start m-4">
          <h3>이자 계산기</h3>
          <br /><br />
          <div class="basicRates text-start m-4">
            <div class="basicRateBoxes">
              <div
                  v-for="rate in filteredInterestRateList"
                  :key="rate.interestRateId"
                  :class="['calBox', { selected: rate.interestRateId === selectedBaseRate }]"
                  @click="selectBaseRate(rate.interestRateId)"
              >
                <h5>{{ rate.savingTerm }}개월</h5>
                <p>{{ formatRate(rate.interestRate) }}%</p>
              </div>
            </div>
          </div>
        </div>

        <div class="calRate text-center"><br />
          <p class="d-inline">금리 {{ formatRate(totalRate) }}%</p>
        </div>

        <div class="text-center">
          <p class="d-inline">기본 {{ formatRate(selectedBaseRateRate) }}% + </p>
          <div class="calSpecialRate d-inline">
            <p class="d-inline">우대 {{ formatRate(specialRate) }}%</p>
          </div>
        </div><br /><br /><br />

        <div class="calNumber flex-container" @click="toggleKeypad">
          <img src="/img/emoji/calculator.png" alt="Calculator" class="calculator-icon">
          <div class="interest-info">
            <p style="font-size: 15px;">{{ formatCurrency(monthlyAmount) }}원 예금하면</p>
            <p style="font-size: 15px; font-weight: 600;">
              1년 후에 이자 {{ formatCurrency(calculatedInterest) }}원
            </p>
            <p style="font-size: 15px; color: #777777;">우대금리가 존재하는 상품엔 우대금리가 반영됩니다</p>
          </div>
          <i class="ai-chevron-right rightAiArrow" style="font-size: 30px;" aria-label="More details"></i>
        </div>
      </div>

      <table class="calTable text-start" v-if="deposit.primeRatesList && deposit.primeRatesList.length">
        <tbody>
        <tr v-for="primeRate in deposit.primeRatesList" :key="primeRate.primeRateId">
          <td style="width: 120%;">
            <label class="form-check-label" :for="`primeSwitch-${primeRate.primeRateId}`">
              {{ primeRate.primeRateDetail }} 달성 시
            </label>
          </td>
          <td>
            <label class="calPercent" :for="`primeSwitch-${primeRate.primeRateId}`">
              {{ formatRate(primeRate.primeRatePercent) }}%
            </label>
          </td>
          <td>
            <div class="form-check form-switch">
              <input
                  type="checkbox"
                  class="form-check-input"
                  :id="`primeSwitch-${primeRate.primeRateId}`"
                  v-model="activePrimeRates[primeRate.primeRateId]"
                  @change="calculateInterest"
              >
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <hr class="mt-5 hr" />
      <br>
      <div class="moreInfo text-start ">
        <h3>상품 정보</h3><br />
        <div class="default-info">
          <ul class="info-list">
            <li>
              <h4>가입 대상</h4>
              <h5>{{ deposit.joinMember }}</h5>
            </li>
            <li>
              <h4>가입 방법</h4>
              <h5>{{ deposit.joinWay }}</h5>
            </li>
          </ul>
          <div v-if="showAdditionalInfo" class="additional-info">
            <ul class="info-list">
              <li>
                <h4>가입 제한</h4>
                <h5>{{ deposit.note }}</h5>
              </li>
              <li>
                <h4>만기 후 이자율</h4>
                <h5>{{ deposit.maturityInterest }}%</h5>
              </li>
              <li>
                <h4>공시 날짜</h4>
                <h5>{{ formatDate(deposit.disclosureStartDay) }}</h5>
              </li>
            </ul>
          </div>
          <div class="toggle-wrapper">
            <span
                class="more-text"
                v-if="!showAdditionalInfo"
                @click="toggleText"
            >
              더보기<i class="ai-chevron-down"></i>
            </span>
            <span
                class="less-text"
                v-if="showAdditionalInfo"
                @click="toggleText"
            >
              줄이기<i class="ai-chevron-up"></i>
            </span>
          </div>
        </div>
      </div>

      <hr class="mt-5 hr" />
      <br />

      <div class="text-start moreInfo">
        <h3>기간별 금리</h3><br />
        <table class="dayInfo text-start">
          <thead>
          <tr>
            <th><h4>기간</h4></th>
            <th><h4>최고금리(기본금리)</h4></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="rate in filteredInterestRateList" :key="rate.interestRateId">
            <td><h3>{{ rate.savingTerm }}개월</h3></td>
            <td><h3>{{ formatRate(rate.interestMaxRate) }}%({{ formatRate(rate.interestRate) }}%)</h3></td>
            <td><br><br></td>
          </tr>
          </tbody>
        </table>
      </div>
      <br><br>
      <a
          class="detailMove"
          :href="deposit.bank?.bankUrl || '#'"
          target="_blank"
          rel="noopener noreferrer"
      >
        상품 페이지로 이동
      </a>
    </div>
    <br /><br />

    <div v-if="showKeypad" class="keypad-modal">
      <div class="keypad">
        <div class="keypad-display">{{ keypadInput }}</div>
        <div class="keypad-buttons">
          <button @click="appendKey('1')">1</button>
          <button @click="appendKey('2')">2</button>
          <button @click="appendKey('3')">3</button>
          <button @click="appendKey('4')">4</button>
          <button @click="appendKey('5')">5</button>
          <button @click="appendKey('6')">6</button>
          <button @click="appendKey('7')">7</button>
          <button @click="appendKey('8')">8</button>
          <button @click="appendKey('9')">9</button>
          <button @click="appendKey('0')">0</button>
          <button class="backspace" @click="backspace">←</button>
          <button class="clear" @click="clearKeypad">C</button>
          <button class="submit" @click="submitKeypad">OK</button>
          <img class="calLogo" src="/img/inveti.png">
        </div>
      </div>
      <div class="keypad-overlay" @click="toggleKeypad"></div>
    </div>
  </div>
</template>


<script setup>
import { ref, reactive, onMounted, computed, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';

const route = useRoute(); // 현재 경로 정보 가져오기
const loading = ref(true);
const deposit = ref({});
const monthlyAmount = ref(10000); // 기본값 10,000원
const calculatedInterest = ref(0);
const showAdditionalInfo = ref(false);
const activePrimeRates = reactive({});
const selectedBaseRate = ref(null);
const showKeypad = ref(false);
const keypadInput = ref('');
const auth = useAuthStore();
const interestType = ref('단리');

const filteredInterestRateList = computed(() => {
  if (!deposit.value.interestRateList || deposit.value.interestRateList.length === 0) {
    return [];
  }
  return deposit.value.interestRateList.filter(rate => rate.interestRateType === interestType.value);
});

const maxInterestMaxRate = computed(() => {
  if (!deposit.value.interestRateList || deposit.value.interestRateList.length === 0) return 0;
  const maxRate = Math.max(...deposit.value.interestRateList.map(rate => rate.interestMaxRate || rate.interestRate || 0));
  return isFinite(maxRate) ? parseFloat(maxRate.toFixed(2)) : 0;
});

const maxSavingTermRate = computed(() => {
  if (!deposit.value.interestRateList || deposit.value.interestRateList.length === 0) return 0;
  const maxTermRate = deposit.value.interestRateList.reduce((prev, current) => {
    return (current.savingTerm > prev.savingTerm) ? current : prev;
  }, deposit.value.interestRateList[0]);
  return parseFloat(maxTermRate.interestRate || 0);
});

const maxSavingTerm = computed(() => {
  if (!deposit.value.interestRateList || deposit.value.interestRateList.length === 0) return 0;
  const maxTerm = deposit.value.interestRateList.reduce((prev, current) => {
    return (current.savingTerm > prev.savingTerm) ? current : prev;
  }, deposit.value.interestRateList[0]);
  return maxTerm.savingTerm || 0;
});

const getInterestRate = (term) => {
  const rateObj = deposit.value.interestRateList?.find(rate => rate.savingTerm === term);
  return rateObj ? parseFloat(rateObj.interestRate) : 0;
};

const getInterestRateById = (rateId) => {
  const rate = deposit.value.interestRateList.find(rate => rate.interestRateId === rateId);
  return rate ? parseFloat(rate.interestRate) : 0;
};

const formatCurrency = (value) => {
  return new Intl.NumberFormat('ko-KR', { maximumFractionDigits: 0 }).format(value);
};

const formatRate = (value) => {
  return new Intl.NumberFormat('ko-KR', { minimumFractionDigits: 0, maximumFractionDigits: 2 }).format(value);
};

const selectedBaseRateRate = computed(() => {
  return selectedBaseRate.value ? getInterestRateById(selectedBaseRate.value) : 0;
});

const specialRate = computed(() => {
  if (!deposit.value.primeRatesList || deposit.value.primeRatesList.length === 0) return 0;
  return deposit.value.primeRatesList.reduce((sum, rate) => {
    return sum + (activePrimeRates[rate.primeRateId] ? parseFloat(rate.primeRatePercent) : 0);
  }, 0);
});

const totalRate = computed(() => {
  const base = selectedBaseRateRate.value;
  const special = parseFloat(specialRate.value) || 0;
  return base + special;
});

const selectBaseRate = (rateId) => {
  selectedBaseRate.value = rateId;
};

const calculateInterest = () => {
  if (!selectedBaseRate.value) {
    calculatedInterest.value = 0;
    return;
  }
  const base = selectedBaseRateRate.value || 0;
  const special = parseFloat(specialRate.value) || 0;
  const total = base + special;
  const savingTerm = 1;
  calculatedInterest.value = Math.round(monthlyAmount.value * total * savingTerm / 100);
};

const updateMonthlyAmount = () => {
  monthlyAmount.value = monthlyAmount.value || 0; // NaN 방지
  calculateInterest();
};

watch(selectedBaseRate, () => {
  calculateInterest();
});

watch(activePrimeRates, () => {
  calculateInterest();
}, { deep: true });

watch(monthlyAmount, () => {
  calculateInterest();
});

const toggleKeypad = () => {
  showKeypad.value = !showKeypad.value;
  if (showKeypad.value) {
    keypadInput.value = monthlyAmount.value.toString();
  } else {
    keypadInput.value = '';
  }
};

const appendKey = (key) => {
  keypadInput.value += key;
};

const backspace = () => {
  keypadInput.value = keypadInput.value.slice(0, -1);
};

const clearKeypad = () => {
  keypadInput.value = '';
};

const submitKeypad = () => {
  monthlyAmount.value = parseInt(keypadInput.value, 10) || 0;
  keypadInput.value = '';
  toggleKeypad();
};

const fetchDeposit = async () => {
  loading.value = true;
  try {
    const savingId = route.params.savingId;
    const response = await axios.get(`/api/saving/detail/${savingId}`, {
      headers: {
      Authorization: `Bearer ${auth.getToken()}` 
    }
    });
    deposit.value = response.data;

    console.log('API 응답 데이터:', deposit.value);
    console.log('primeRatesList:', deposit.value.primeRatesList);

    if (deposit.value.primeRatesList && deposit.value.primeRatesList.length) {
      deposit.value.primeRatesList.forEach(primeRate => {
        activePrimeRates[primeRate.primeRateId] = false;
      });
      console.log('activePrimeRates 초기화:', activePrimeRates);
    }

    if (deposit.value.interestRateList && deposit.value.interestRateList.length) {
      const initialRate = deposit.value.interestRateList.reduce((prev, current) => {
        return (current.savingTerm > prev.savingTerm) ? current : prev;
      }, deposit.value.interestRateList[0]);
      selectedBaseRate.value = initialRate.interestRateId;
    }

    calculateInterest();
  } catch (error) {
    console.error('상세페이지 불러오기 실패', error);
  } finally {
    loading.value = false;
  }
};

const toggleText = () => {
  showAdditionalInfo.value = !showAdditionalInfo.value;
};

const formatDate = (timestamp) => {
  if (!timestamp) return '';
  const date = new Date(timestamp);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

onMounted(() => {
  fetchDeposit();
});
</script>

<style scoped>

.bankLogo img{
  width: 90px;
  height: 90px;
}
.bankName{
  font-size: 50px;
  margin: 20px;
  vertical-align: middle;
}

@keyframes slideIn {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.animated-item {
  animation: slideIn 0.5s ease-out forwards; /* 애니메이션 추가 */
}

/* 나머지 스타일링 */
.rate-container {
  width: 150%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 10px;
}



.rate-container {
  width: 150%;
  display: flex;
  flex-direction: column;
  align-items: start;
  margin-left: 30px;
}

.rate-row {
  width: 65%;
  display: flex;
  justify-content: space-between;
}

.subject2 {
  font-weight: bold;
  font-size: 16px;
}

.bestSubject, .normalSubject {
  font-size: 18px;
  color: #444;
}
.calNumber {
  cursor: pointer;
  text-align: justify;
}

.rank-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.rank-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  margin-left: 20px;
}

.rank-detail {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.rank-title {
  font-size: 24px;
  margin: 0;
  color: #333333;
}

.rank {
  font-size: 24px;
  color: #8ad3c5;
  vertical-align: middle;
}

.rankMedal {
  width: 60px;
  margin: 10px;
}
.calLogo {
  position: absolute;
  bottom: 40px;
  right: 30px;
  width: 100px;
}

.toggle-wrapper {
  display: inline-block;
  text-align: center;
  margin-top: 10px;
}

.moreInfo{
  width: 90%;
  margin: 0 auto;
  gap: 10px;
}
.info-list {
  display: flex;
  flex-direction: column;
}

.more-text,
.less-text {
  font-size: 16px;
  color: black;
  cursor: pointer;
}

.additional-info {
  margin-top: 10px;
  font-size: 14px;
}

.default-info ul {
  padding-left: 0px;
}

.calTable td {
  text-align: left;
  width: 10%;
  padding: 20px;
}

.calTable {
  margin: 0 auto;
  table-layout: fixed;
  width: 85%;
  border-bottom: 1px solid rgba(180, 178, 178, 0.47);
}

.dayInfo {
  table-layout: fixed;
  width: 100%;
  text-align: left;
}

.calPercent {
  font-weight: 500;
  font-size: 20px;
  color: rgba(67, 140, 116, 1);
}

.form-check-label {
  font-weight: 500;
  font-size: 15px;
  color: black;
}

.calBox {
  width: 100px;
  height: 100px;
  background-color: #4d4f5e;
  border-radius: 30px;
  margin: 10px;
  padding: 20px;
  color: white;
  cursor: pointer;
  display: inline-block;
  text-align: center;
  transition: background-color 0.3s, transform 0.3s;
}

.calBox:hover {
  background-color: #3b3e56;
  transform: scale(1.05);
}

.calBox.selected {
  background-color: #8ad3c5;
  color: #000;
}

.basicRateBoxes {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}

.calRate {
  font-size: 30px;
  color: rgba(67, 140, 116, 1);
  font-weight: 700;
}

.calSpecialRate {
  color: rgba(67, 140, 116, 1);
}

.bc {
  width: 60%;
  margin: 0 auto;
  font-family: J3;
}

.subject2 {
  font-size: 25px;
  font-weight: bold;
  color: #777777;
  margin-right: 50px;
}

.dayInfo h4{
  color: grey;
}

.bestSubject {
  font-size: 35px;
  font-weight: bold;
  color: #8ad3c5;
  margin-right: 50px;
}

.normalSubject {
  font-size: 35px;
  font-weight: bold;
  color: #777777;
  margin-right: 50px;
}

.hr {
  width: 90%;
  margin: 0px auto;
  border: 5px solid rgba(180, 178, 178, 0.47);
}

.savingDetailBox {
  padding: 50px;
}
.default-info h4{
  color: grey;
}
ul {
  list-style: none;
}

.detailMove {
  display: inline-block;
  border: none;
  background-color: rgba(67, 140, 116, 1);
  width: 200px;
  height: 50px;
  color: white;
  font-size: 20px;
  cursor: pointer;
  text-align: center;
  line-height: 50px;
  border-radius: 20px;
  text-decoration: none;
}

button {
  border-radius: 20px;
  margin: 20px;
}

a {
  color: inherit;
  text-decoration: none;
}

.flex-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border: 1px solid rgba(153, 153, 153, 0.6);
  border-radius: 30px;
  cursor: pointer;
  background-color: #f9f9f9;
}

.calculator-icon {
  width: 60px;
  margin-right: 20px;
}

.interest-info {
  flex: 1;
}

.rightAiArrow {
  color: rgba(67, 140, 116, 1);
  transition: transform 0.3s;
}

.flex-container:hover .rightAiArrow {
  transform: translateX(5px);
}

.keypad-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.keypad-overlay {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.keypad {
  position: relative;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 30px;
  z-index: 1001;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 500px;
}

.keypad-display {
  width: 100%;
  height: 50px;
  border: 2px solid #ccc;
  margin-bottom: 15px;
  text-align: right;
  padding: 10px;
  font-size: 24px;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.keypad-buttons {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 10px;
}

.keypad-buttons button {
  width: 80%;
  height: 60px;
  font-size: 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  background-color: #e0e0e0;
  transition: background-color 0.2s;
}

.keypad-buttons button:hover {
  background-color: #d5d5d5;
}

.keypad-buttons button:active {
  background-color: #cccccc;
}

.keypad-buttons button.backspace {
  background-color: #ff6961;
  color: white;
}

.keypad-buttons button.backspace:hover {
  background-color: rgba(255, 92, 92, 1);
}

.keypad-buttons button.clear {
  background-color: #f0ad4e;
  color: white;
}

.keypad-buttons button.clear:hover {
  background-color: #ec971f;
}

.keypad-buttons button.submit {
  background-color: #5cb85c;
  color: white;
}

.keypad-buttons button.submit:hover {
  background-color: #4cae4c;
}


.toggle-wrapper {
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  color: black;
}


</style>

