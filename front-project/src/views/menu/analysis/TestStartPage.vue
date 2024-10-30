<template>
  <div class="test-start-view">
    <h1 class="animated-text">투자 성향 테스트</h1>
    <p class="animated-text">
      간단한 테스트로 성향을 파악하고, <br />
      딱 맞는 투자 콘텐츠를 추천받아보세요!
    </p>
    <img src="@/assets/img/analysis/test_start.jpg" alt="Test Direction Image" class="test-image animated-text" />
    <button @click="startTest" class="start-test-button animated-text">테스트 시작하기</button>
  </div>
</template>


<script>
  export default {
    methods: {
      startTest() {
        // 테스트 시작 로직
        this.$router.push('/test-process');
      }
    }
  }
  </script>

<style scoped>
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

.test-start-view {
  font-family: J5;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
}

.test-start-view h1,
.test-start-view p,
.test-image,
.start-test-button {
  animation: slideIn 0.5s ease-out forwards; /* 모든 요소에 애니메이션 적용 */
}

.test-start-view h1 {
  font-size: 36px;
  margin-bottom: 20px;
}

.test-start-view p {
  font-size: 18px;
  color: #666;
  margin-bottom: 40px;
}

.test-image {
  width: 200px;
  height: auto;
  margin-bottom: 40px;
}

.start-test-button {
  background-color: #438C74;
  color: white;
  border: none;
  padding: 15px 100px;
  font-size: 18px;
  cursor: pointer;
  border-radius: 5px;
}

.start-test-button:hover {
  background-color: #45a049;
}
</style>