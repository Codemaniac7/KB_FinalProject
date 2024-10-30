<template>
  <section>
    <div class="suggestion">
      <div class="container text-center">
        <br><br>
        <h1 class="text-start">주요 서비스 한 눈에 보기</h1>
        <br>
        <router-link class="link" to="/community">
          <div class="suggest">
            <p>커뮤니티</p><br>
            <p class="text-end">👨‍👩‍👧‍</p>
          </div>
        </router-link>
        <router-link class="link" to="/dictionary">
          <div class="suggest">
            <p>용어사전</p><br>
            <p class="text-end">📚</p>
          </div>
        </router-link>
        <router-link class="link" to="/test-start">
          <div class="suggest">
            <p>투자성향TEST</p><br>
            <p class="text-end">💖</p>
          </div>
        </router-link>
        <router-link class="link" to="/StockMain">
          <div class="suggest">
            <p>주식목록</p><br>
            <p class="text-end">📈</p>
          </div>
        </router-link>
        <br><br>
      </div>
    </div>
  </section>
</template>

<style scoped>
.link {
  text-decoration: none;
  color: black;
}

.suggest {
  transition: transform 0.6s ease;
  cursor: pointer;
}

.suggest:hover {
  transform: scale(0.9);
  z-index: 11111;
}


.suggest {
  display: inline-block;
  border: 1px solid rgba(153, 153, 153, 0.6);
  border-radius: 20px;
  width: 250px;
  height: 200px;
  margin: 10px;
  padding: 25px;
  font-size: 30px;
  text-align: start;
  font-family: J4;
}

.suggestion {
  width: 100%;
  position: relative;
  padding: 20px 0;
  background-color: rgba(249, 250, 251, 1);
}

.text-start {
  margin-left: 40px;
  font-family: J6;
}
</style>

<script setup lang="ts">
</script>
