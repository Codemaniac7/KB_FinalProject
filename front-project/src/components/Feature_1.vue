<template>
  <section>
    <div id="carouselExample" class="carousel slide" data-bs-ride="carousel" data-bs-interval="5000">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <div class="position-absolute top-0 start-0 w-100" style="height:95%;">
            <div class="bg-primary position-absolute top-0 start-0 w-100 h-100"></div>
            <div class="position-absolute start-0 bottom-0 w-100 overflow-hidden mb-n1"
              style="padding-bottom: 6.2%; color: var(--ar-body-bg);">
              <svg class="position-absolute start-0 bottom-0 w-100 h-100" viewBox="0 0 3000 185.4"
                xmlns="http://www.w3.org/2000/svg">
                <path fill="white" d="M3000,0v185.4H0V0c496.4,115.6,996.4,173.4,1500,173.4S2503.6,115.6,3000,0z"></path>
              </svg>
            </div>
          </div>
          <div class="container position-relative zindex-5 pt-5 mt-4">
            <div class="row justify-content-center text-center pt-md-2 pt-lg-0 pt-xl-2 pb-5 mb-md-2">
              <div class="col-lg-10 col-xl-9 col-xxl-8 text-start">
                <div class="d-flex justify-content-between align-items-center ">
                  <h1 class="font-t display-3 mb-sm-4 me-2" style="width: 140%; height: 100%; text-align: start;"><span
                      class="badgeText badge bg-faded-light fs-sm text-white px-3 ">
                      <h3>Let's have a dream</h3>
                    </span><br>취향대로 투자하자</h1>
                  <div class="logoYong">
                    <img src="/img/logoYong.png" alt="Logo">
                  </div>
                </div>
                <h3 class="pb-3 font-c">투자 성향을 분석하고 <br> 성향 기반 투자 상품 추천받기</h3>
              </div>
              <div class="text-center mt-4">
                <button class="investChoiceBtn" @click="testStart">투자 성향 분석 Click!</button>
              </div>
            </div>
            <div class="overflow-hidden" style="border-radius: calc(var(--ar-border-radius) * 1.25);"></div>
          </div>
        </div>

        <div class="carousel-item">
          <div class="position-absolute top-0 start-0 w-100" style="height:95%;">
            <div class="mode2Color position-absolute top-0 start-0 w-100 h-100"></div>
            <div class="position-absolute start-0 bottom-0 w-100 overflow-hidden mb-n1"
                 style="padding-bottom: 6.2%; color: var(--ar-body-bg);">
              <svg class="position-absolute start-0 bottom-0 w-100 h-100" viewBox="0 0 3000 185.4"
                   xmlns="http://www.w3.org/2000/svg">
                <path fill="white" d="M3000,0v185.4H0V0c496.4,115.6,996.4,173.4,1500,173.4S2503.6,115.6,3000,0z"></path>
              </svg>
            </div>
          </div>
          <div class="container position-relative zindex-5 pt-5 mt-4">
            <div class="row justify-content-center text-center pt-md-2 pt-lg-0 pt-xl-2 pb-5 mb-md-2">
              <div class="col-lg-10 col-xl-9 col-xxl-8 text-start">
                <div class="d-flex justify-content-between align-items-center ">
                  <h1 class="font-t display-3 mb-sm-4 me-2" style="width: 140%; height: 100%; text-align: start;"><span
                      class="badgeText badge bg-faded-light fs-sm text-white px-3 ">
                      <h3>Choose at a glance</h3>
                    </span><br>투자 상품을 한 눈에</h1>
                  <div class="logoYong">
                    <img src="/img/logoYong2.png" alt="Logo">
                  </div>
                </div>
                <h3 class="pb-3 font-c">다양한 투자 상품을<br>
                  하나의 플랫폼에서 한 눈에 비교</h3>
              </div>
              <div class="text-center mt-4">
                <button class="investChoiceBtn" @click="testStart">투자 성향 분석 Click!</button>
              </div>
            </div>
            <div class="overflow-hidden" style="border-radius: calc(var(--ar-border-radius) * 1.25);"></div>
          </div>
        </div>
        <div class="carousel-item">
          <div class="position-absolute top-0 start-0 w-100" style="height:95%;">
            <div class="mode3Color position-absolute top-0 start-0 w-100 h-100"></div>
            <div class="position-absolute start-0 bottom-0 w-100 overflow-hidden mb-n1"
                 style="padding-bottom: 6.2%; color: var(--ar-body-bg);">
              <svg class="position-absolute start-0 bottom-0 w-100 h-100" viewBox="0 0 3000 185.4"
                   xmlns="http://www.w3.org/2000/svg">
                <path fill="white" d="M3000,0v185.4H0V0c496.4,115.6,996.4,173.4,1500,173.4S2503.6,115.6,3000,0z"></path>
              </svg>
            </div>
          </div>
          <div class="container position-relative zindex-5 pt-5 mt-4">
            <div class="row justify-content-center text-center pt-md-2 pt-lg-0 pt-xl-2 pb-5 mb-md-2">
              <div class="mode3 col-lg-10 col-xl-9 col-xxl-8 text-start">
                <div class="d-flex justify-content-between align-items-center ">
                  <h1 class="font-t display-3 mb-sm-4 me-2" style="width: 140%; height: 100%; text-align: start;"><span
                      class="mode3Badge badgeText badge bg-faded-light fs-sm text-white px-3 ">
                      <h3>Dreams Come True</h3>
                    </span><br>꿈을 향한 투자</h1>
                  <div class="logoYong">
                    <img src="/img/logoDaram.svg" alt="Logo">
                  </div>
                </div>
                <h3 class="pb-3 font-c">내 성향에 따른 방법대로<br> 꿈을 이루는 포트폴리오</h3>
              </div>
              <div class="text-center mt-4">
                <button class="investChoiceBtn" @click="testStart">투자 성향 분석 Click!</button>
              </div>
            </div>
            <div class="overflow-hidden" style="border-radius: calc(var(--ar-border-radius) * 1.25);"></div>
          </div>
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </section>
</template>

<script setup>

import router from "@/router";

const testStart = () => {
  router.push('/test-start');
}

</script>

<style scoped>
.logoYong img {
  margin-left: 200px;
  margin-top: 30px;
}

.badgeText {
  font-size: 30px;
}

.display-3 {
  color: white;
  line-height: 1.5;
}

h3.pb-3 {
  color: white;
  font-size: 28px;
  line-height: 1.3;
}

.mode3 h1 {
  color: rgba(50, 98, 82, 1);
}

.mode3 h3 {
  color: rgba(67, 139, 115, 1);
}

.mode2Color {
  background-color: rgba(162, 202, 129, 1);
}

.mode3Color {
  background-color: rgba(241, 252, 232, 1);
}

.mode3Badge h3 {
  color: rgba(67, 139, 115, 1);
  background-color: rgb(214, 255, 188);
  border-radius: 10px;
  width: 230px;
  height: 40px;
  padding: 5px;
  margin-left: -10px;
  margin-top: -10px;
  text-align: start;
}

.investChoiceBtn {
  transition: transform 0.6s ease;
  width: 300px;
  height: 60px;
  background-color: rgba(236, 203, 80, 1);
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 25px;
  font-family: J3;
  cursor: pointer;
  margin-top: -50px;
  cursor: pointer;
}

.investChoiceBtn:hover {
  background-color: rgba(236, 203, 80, 1);
  transform: scale(1.2);
  z-index: 11111;
}

.font-t {
  font-family: J5;
}

.font-c {
  font-family: J4;
}
</style>