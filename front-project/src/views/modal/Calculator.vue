<script setup>
import { ref } from 'vue';

const depositAmount = ref(''); // 예치금액
const depositPeriod = ref(''); // 예치기간
const interestType = ref('simple'); // 단리 or 복리
const interestRate = ref(''); // 연이자율
const taxType = ref('normal'); // 과세유형

const principal = ref(0);
const preTaxInterest = ref(0);
const taxAmount = ref(0);
const postTaxInterestRate = ref(0);
const finalAmount = ref(0);

const calculate = () => {
  const P = parseFloat(depositAmount.value); // 원금
  const r = parseFloat(interestRate.value) / 100; // 연이자율
  const t = parseFloat(depositPeriod.value) / 12; // 예치기간 (연 단위)

  if (isNaN(P) || isNaN(r) || isNaN(t)) {
    alert('모든 입력 값을 정확히 입력해주세요.');
    return;
  }

  let interest;
  if (interestType.value === 'simple') {
    interest = P * r * t; // 단리 계산
  } else {
    interest = P * Math.pow(1 + r, t) - P; // 복리 계산
  }

  // 세금 계산 (15.4% 일반 과세)
  let taxRate;
  if (taxType.value === 'normal') {
    taxRate = 0.154;
  } else if (taxType.value === 'preferential') {
    taxRate = 0.095;
  } else {
    taxRate = 0; // 비과세
  }

  preTaxInterest.value = Math.round(interest); // 세전 이자를 반올림하여 정수로 출력
  taxAmount.value = Math.round(interest * taxRate); // 세금을 반올림하여 정수로 출력
  finalAmount.value = Math.round(P + interest - taxAmount.value); // 최종 금액을 반올림하여 정수로 출력

  // 수익률 계산 (세후 수익률을 반올림하여 소수점 없이)
  postTaxInterestRate.value = Math.round(((interest - taxAmount.value) / P) * 100);
  principal.value = Math.round(P); // 원금도 반올림
};
</script>

<template>
  <div class="container text-center">
    <div class="calculator-container">
      <button class="exitModal" @click="closeModal"><h3>x</h3></button>
      <h1>적금 계산기</h1>
      <div class="calculator">
        <table class="calTable text-start">
          <tbody>
          <tr class="calSubject">
            <td><h2>예치금액</h2></td>
            <td><input v-model="depositAmount" type="text" placeholder="₩"></td>
          </tr>
          <tr class="calSubject">
            <td><h2>예치기간</h2></td>
            <td><input v-model="depositPeriod" type="text" placeholder="개월"></td>
          </tr>
          <tr class="calSubject">
            <td><h2>이자구분</h2></td>
            <td>
              <button @click="interestType = 'simple'" :class="{ active: interestType === 'simple' }">단리</button>
              <button @click="interestType = 'compound'" :class="{ active: interestType === 'compound' }">복리</button>
            </td>
          </tr>
          <tr class="calSubject">
            <td><h2>연이자율</h2></td>
            <td><input v-model="interestRate" type="text" placeholder="%"></td>
          </tr>
          <tr class="calSubject">
            <td><h2>이자과세</h2></td>
            <td>
              <button @click="taxType = 'normal'" :class="{ active: taxType === 'normal' }">일반 과세</button>
              <button @click="taxType = 'preferential'" :class="{ active: taxType === 'preferential' }">세금 우대</button>
              <button @click="taxType = 'taxFree'" :class="{ active: taxType === 'taxFree' }">비과세</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <button type="submit" class="calSubmit" @click="calculate">계산하기</button>
      <div class="calculator">
        <table class="calTable text-start">
          <tbody>
          <tr class="calResult">
            <td colspan="2"><h2>계산결과</h2></td>
          </tr>
          <tr class="calResult">
            <td colspan="2"><hr></td>
          </tr>
          <tr class="calResult">
            <td><h2>원금</h2></td>
            <td class="text-end"><h2>{{ principal }} 원</h2></td>
          </tr>
          <tr class="calResult">
            <td><h2>세전이자</h2></td>
            <td class="text-end"><h2>{{ preTaxInterest }} 원</h2></td>
          </tr>
          <tr class="calResult">
            <td><h2>수익률</h2></td>
            <td class="text-end"><h2>{{ postTaxInterestRate }} %</h2></td>
          </tr>
          <tr class="calResult">
            <td><h2>이자과세</h2></td>
            <td class="text-end"><h2>{{ taxAmount }} 원</h2></td>
          </tr>
          <tr class="calResult">
            <td><h2>세후수익률</h2></td>
            <td class="text-end"><h2>{{ postTaxInterestRate }} %</h2></td>
          </tr>
          <tr class="calResult">
            <td><h2>실수령액</h2></td>
            <td class="text-end"><h2>{{ finalAmount }} 원</h2></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.calculator-container{
  border: 1px solid rgba(153, 153, 153, 1);
  border-radius: 30px;
  width: 70%;
  margin: 5%;
  height: 100%;
  padding: 5%;
  background-color: rgba(247, 249, 252, 1);
}

.calculator{
  border: 1px solid rgba(153, 153, 153, 1);
  border-radius: 30px;
  width: 90%;
  margin: 5%;
  max-height: 700px;
  padding: 5%;
  background-color: white;
}
.exitModal{
  width:50px;
  height:50px;
  border: 1px solid rgba(153, 153, 153, 1);
  border-radius: 50%;
  background-color: transparent;
  margin-left: 90%;
  margin-bottom: -30px;
}
.calSubject td{
  border-bottom: 1px solid rgba(153, 153, 153, 0.29);
}
.calResult td{
  padding: 5px;
}

.calTable{
  width: 100%;
}

.calSubject td{
  padding: 1%;
}

.calSubject button{
  width: 100px;
  height: 50px;
  border: 1px solid rgba(220, 225, 230, 1);
  background-color: white;
  border-radius: 20px;
  margin-right: 20px;
}

.calSubject button:hover{
  background-color: rgba(67, 140, 116, 1);
  color: white;
}

.calSubmit:hover{
  background-color: white;
  color: rgba(67, 140, 116, 1);
}

.calSubmit{
  width: 200px;
  height: 50px;
  border: 1px solid rgba(220, 225, 230, 1);
  background-color: rgba(67, 140, 116, 1);
  border-radius: 20px;
  margin-right: 20px;
  color: white;
}

.calSubject input{
  border: 1px solid rgba(220, 225, 230, 1);
  border-radius: 30px;
  width: 100%;
  height: 50px;
  padding: 20px;
}
.calSubject button.active {
  background-color: rgba(67, 140, 116, 1);
  color: white;
}

</style>