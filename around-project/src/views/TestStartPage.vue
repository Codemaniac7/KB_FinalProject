<template>
    <div class="test-start-view">
      <h1>투자 성향 테스트</h1>
      <p>간단한 테스트로 성향을 파악하고, <br> 딱 맞는 투자 콘텐츠를 추천받아보세요!</p>
      <img src="@/assets/img/test/test_start.jpg" alt="Test Direction Image" class="test-image" />
      <button @click="startTest" class="start-test-button">테스트 시작하기</button>
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
  .test-start-view {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    text-align: center;
  }
  
  .test-start-view h1 {
    font-size: 36px;
    font-weight: bold;
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
  