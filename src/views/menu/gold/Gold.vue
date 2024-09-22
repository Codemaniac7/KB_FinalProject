<script setup>
import { onMounted, ref } from 'vue';

const tradingViewContainer = ref(null);

onMounted(() => {
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
    support_host: 'https://www.tradingview.com'
  });

  tradingViewContainer.value.appendChild(script);
});
</script>

<template>
  <div class="container text-center">
    <h1>금</h1>
    <br>
    <div class="goldQuote">
      <p class="fs-2 text-start">순금 시세</p>
      <p class="text-end">현재 날짜</p>
      <br>
      <div class="goldQuoteText">
        <br><br>
       <table class="quoteTable">
         <thead>
          <tr>
            <td><h3>내가 살 때(VAT포함)</h3></td>
            <td><h3>내가 팔 때(VAT포함)</h3></td>
          </tr>
         </thead>
         <tbody>
          <tr>
            <td><h3>40000</h3></td>
            <td><h3>40000</h3></td>
          </tr>
          <tr>
            <td><h3>40</h3></td>
            <td><h3>40</h3></td>
          </tr>
         </tbody>
       </table>
        <br><br>
      </div>
    </div>
  </div><br><br>
  <div class="text-center container">
    <h2 class="text-start"> 5일 동안의 금 시세</h2><br>
    <div class="fiveThQuote">
      <table>
        <thead class="fiveThQuoteThead">
          <tr>
            <th rowspan="2">고시날짜</th>
            <th>내가 살 때 (3.75kg)</th>
            <th class="th3" colspan="3">내가 팔 때 (3.75kg)</th>
          </tr>
          <tr>
            <th>순금</th>
            <th>순금</th>
            <th>순금</th>
            <th>순금</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>2024-09-13</td>
            <td>468,000</td>
            <td>468,000</td>
            <td>468,000</td>
            <td>468,000</td>
          </tr>
          <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>
    </div>
    <br><br><br>
    <div class="goldQuoteGraph">
      <h2 class="d-inline">금 시세</h2><p class="d-inline" style="font-size: 27px; font-weight: 700;">그래프</p>
      <div>그래프</div>
    </div>
    <br><br><br>
    <div class="text-start">
      <h2 class="d-inline">일별 국제 금시세</h2><h5 class="d-inline">By TrandingView</h5>
    </div><br><br>
    <div class="trandingViewGoldQuote">
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
</template>

<style scoped>
.goldQuote{
  border-radius: 30px;
  background-color: rgba(129, 140, 67, 0.06);
  padding: 5%;
}
.goldQuoteText{
  border-radius: 30px;
  background-color: rgba(0, 0, 0, 0.65);
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
  border: 1px solid black;
  background-color: rgba(240, 238, 238, 0.6);
  width: 100%;
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
  border: 1px solid black;
  border-left: none;
  border-top: none;
  font-weight: lighter;
  font-size: 20px;
  padding: 20px;
  width: 1200px;
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