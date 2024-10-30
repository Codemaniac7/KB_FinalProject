<template>
  <section>
    <br><br><br><br>
    <div class="f4_trendMain">
      <!-- trendMenu -->
      <div class="trendMenu">
        <button class="trendBtn  p-1">국내주식</button>
        <h1 class="d-inline">트렌드<br></h1>
        <h1 class="d-inline m-l">한눈에 보기</h1><br>
        <br><button class="goToStock_btn" @click="goToStock">전체 운용상품 보러가기 <i class="ai-chevron-right"></i></button>
        <img src="/img/emoji/trend.png" class="trendImage">
      </div>

      <!-- trendGraph -->
      <div class="trendGraph">
        <div class="btn1 ">
          <img src="/img/samsung.png" class="logo">
          <span>삼성전자</span>
          <img src="/img/mi1.png" class="graph">
        </div>
        <div class="btn2 ">
          <img src="/img/hanhwa.png" class="logo">
          <span>한화에어로스페이스</span>
          <img src="/img/pl1.png" class="graph">
        </div>
        <div class="btn1 ">
          <img src="/img/hana.png" class="logo">
          <span>하나금융지주</span>
          <img src="/img/mi2.png" class="graph">
        </div>
        <div class="btn2 ">
          <img src="/img/LGE.png" class="logo">
          <span>LG에너지솔루션</span>
          <img src="/img/pl2.png" class="graph">
        </div>
      </div>
    </div>
    <br><br><br><br>
  </section>
</template>

<script setup>
import router from '@/router';

const goToStock = () => {
  router.push('/StockMain'); // 지정된 경로로 페이지 이동
};

</script>

<style scoped>
.f4_trendMain {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1200px;
  margin: 0 auto;
  padding: 0 50px;
}

.m-1 {
  z-index: 99;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.f4_trendMain h1 {
  font-size: 50px;
  font-weight: 700;
  letter-spacing: -1px;
  z-index: 4;
}

.trendBtn {
  width: 220px;
  font-size: 50px;
  font-weight: 700;
  border: none;
  border-radius: 30px;
  background-color: rgba(241, 250, 247, 1);
  color: rgba(68, 140, 116, 1);
  z-index: 2;
}


.trendImage {
  margin-top: -35%;
  margin-left: 60%;
  z-index: -1;
  width: 200px;
}

.trendGraph {
  display: flex;
  font-family: J3;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 400px;
  margin-left: 60px;
}

.trendGraph span {
  font-size: 20px;
  width: 300px;
  font-weight: 700;
}

.trendGraph div {
  width: 370px;
  height: 80px;
  background-color: rgba(217, 217, 217, 0.3);
  border-radius: 30px;
  margin: 10px 0;
  display: flex;
  align-items: center;
  padding: 0 20px;
}

.logo{
  width: 50px;
  height: 50px;
  margin: 0 27px;
  border-radius: 50%;
}

.trendGraph div:nth-child(odd) {
  margin-left: -60px;
}

.trendGraph div:nth-child(even) {
  margin-left: 60px;
}

.m-l {
  margin-left: 25px;
}

.goToStock_btn {
  margin-left: 25px;
  margin-top: 20px;
  font-size: 25px;
  border: none;
  background: none;
}

.trendMenu {
  font-family: J5;
}

.graph {
  height: 50px;
  margin: 0 27px;
}
</style>


