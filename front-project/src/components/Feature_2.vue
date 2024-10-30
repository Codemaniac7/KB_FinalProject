<template>
  <section class="review-section container pt-5 pb-4 mb-2 my-lg-3 my-xl-4 my-xxl-5">
    <h2 class="font-t review-section__title h1 mb-2 text-center">금융상품 알아보기</h2>
    <br>
    <div class="main1 flex-wrap justify-content-center "> <!-- Flexbox 사용 -->
      <router-link class="link" to="/savings">
        <div class="mainBtn m1">
            <h3>적금</h3>
          <img src="/img/emoji/savingHand.PNG" alt="Saving Hand Emoji" class="saving-emoji" />
        </div>
      </router-link>
      <router-link class="link" to="/deposit">
        <div class="mainBtn m2">
            <h3>예금</h3>
          <img src="/img/emoji/despositHand.PNG" alt="Deposit Emoji" class="saving-emoji" />
        </div>
      </router-link>
      <router-link class="link" to="/isa">
        <div class="mainBtn m1">
          <h3>ISA</h3>
          <img src="/img/emoji/isaEmoji.PNG" alt="ISA Emoji" class="saving-emoji" />
        </div>
      </router-link>
      <router-link class="link" to="/fund">
        <div class="mainBtn m2">
          <h3>펀드</h3>
          <img src="/img/emoji/fundemoji.PNG" alt="Fund Emoji" class="saving-emoji" />
        </div>
      </router-link>
      <router-link class="link" to="/stockMain">
        <div class="mainBtn m1">
          <h3>주식</h3>
          <img src="/img/emoji/investEmoji.PNG" alt="Investment Emoji" class="saving-emoji" />
        </div>
      </router-link>
    </div><br><br><br>
  </section>
</template>

<style scoped>

.review-section__title {
  font-size: 40px;
}

.main1 {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 30px;
  font-family: J4;
}

.mainBtn {
  position: relative;
  width: 200px;
  height: 200px;
  border-radius: 30px;
  padding: 20px;
  text-align: left;
  display: inline-block;
  transition: transform 0.6s ease;
  cursor: pointer;
}

.mainBtn:hover{
  transform: scale(0.9);
  z-index: 11111;
}

.m1 {
  background-color: rgba(144, 218, 170, 1);
}

.m2 {
  background-color: rgba(67, 139, 115, 1);
}

.mainBtn h3 {
  font-size: 40px;
  color: white;
  margin: 0;
}

.saving-emoji {
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 95px;
  height: 95px;
}
.link{
  text-decoration: none;
}

.font-t {
  font-family: J5;
}
</style>

<script setup>

</script>
