<template>
  <section>
    <br><br><br><br><br>
    <div class="container text-center investDictionary" @click="goToDic">
      <div class="text-container">
        <div class="text-content">
          <b class="title-txt">HOW TO</b><br>
          <b class="title-txt">INVEST</b><br>
          <b class="content-txt">투자의 A to Z를 알려드립니다</b><br>
          <br><br><br><br>
          <h4>용어사전 바로가기 <i class="ai-arrow-right"></i></h4>
        </div>
        <div class="image-container">
          <img src="/img/how.png" alt="How to invest image">
        </div>
      </div>
    </div>
    <br><br><br><br><br>
  </section>
</template>

<style scoped>
.investDictionary {
  transition: transform 0.6s ease;
  cursor: pointer;
}

.investDictionary:hover{
  transform: scale(1.1);
  z-index: 11111;
}

.investDictionary{
  color: white;
  padding: 60px;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  width: 100%;
  max-width: 1200px;
  height: auto;
}

.text-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  position: relative;
}

.text-content {
  text-align: left;
}

.image-container {
  position: absolute;
  right: 0;
  bottom: 0;
  margin-right: -100px;
  margin-bottom: -50px;
  width: 700px;
}

.image-container img {
  height: auto;
  object-fit: contain;
}

.title-txt {
  font-size: 50px;
  line-height: 1.1;
  font-family: J4;
}

.content-txt {
  font-size: 30px;
  line-height: 2;
  font-family: J3;
}

@media (min-width: 768px) {
  .text-container {
    flex-direction: row;
    justify-content: space-between;
    align-items: flex-start;
  }

  .image-container img {
    width: 80%;
  }
}
</style>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter();

const goToDic = () => {
  router.push('/dictionary');
}
</script>
