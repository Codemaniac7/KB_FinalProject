<template>
    <div class="result-view">
      <h4 style="color: #45a049;">투자 성향 테스트 결과</h4>
      <h1><p>나의 투자 성향은?</p></h1>
  
      <!-- 투자 성향 카드 -->
      <div class="result-cards">
        <div v-for="(result, index) in investmentResults" :key="index" class="result-card">
          <h3>{{ result.title }}</h3>
          <p>{{ result.description }}</p>
          <img :src="result.icon" alt="Investment Icon" class="result-icon" />
        </div>
      </div>
  
      <!-- 추천 콘텐츠 -->
      <div class="recommended-content">
        <h3> {{ userType }} 타입을 가진 당신을 위해 아래 콘텐츠를 준비했어요!</h3>
        <div class="content-cards">
          <div v-for="(content, index) in recommendedContent" :key="index" class="content-card">
            <p>{{ content.title }}</p>
            <h4>{{ content.description }}</h4>
            <img :src="content.icon" alt="Content Icon" />
            <router-link :to="content.link" class="content-link">➔</router-link>
          </div>
        </div>
      </div>
  
      <!-- 하단 버튼 -->
      <div class="action-buttons">
        <button @click="restartTest" class="restart-button">테스트 다시하기</button>
        <button @click="shareResults" class="share-button">공유</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        userType: "위험중립형", // 사용자의 투자 성향 타입을 받아서 표시
        investmentResults: [
          {
            title: "연금투자형",
            description: "노후를 대비하고 세금 혜택도 받고 싶다면, 연금투자를 선호하세요!",
            icon: require('@/assets/img/test/pension-icon.png')
          },
          {
            title: "성장테마형",
            description: "성장 가능성이 높은 산업에 관심이 많고, 미래의 가치를 찾고 싶어요!",
            icon: require('@/assets/img/test/growth-icon.png')
          }
        ],
        recommendedContent: [
          {
            title: "한국인의 생애주기에 맞는 연금투자가 궁금하다면?",
            description: "ISA",
            icon: require('@/assets/img/test/isa-icon.png'),
            link: "/isa"
          },
          {
            title: "든든한 노후를 준비하고 싶다면?",
            description: "예금저축",
            icon: require('@/assets/img/test/savings-icon.png'),
            link: "/savings"
          }
        ]
      };
    },
    methods: {
      restartTest() {
        // 테스트를 다시 시작하는 로직 (예: 첫 질문 페이지로 이동)
        this.$router.push('/test-start');
      },
      shareResults() {
        // 결과 공유 로직 (소셜 미디어 또는 링크 복사 기능 추가 가능)
        alert('결과를 공유합니다.');
      }
    }
  }
  </script>
  
  <style scoped>
  .result-view {
    text-align: center;
    padding: 40px 20px;
    background-color: #f9f9f9;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
  }
  
  h1 {
    font-size: 32px;
    margin-bottom: 20px;
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 40px;
  }
  
  .result-cards {
    display: flex;
    justify-content: center;
    gap: 20px;
  }
  
  .result-card {
    background-color: #ECCB50;
    padding: 20px;
    border-radius: 15px;
    color: white;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    width: 300px;
    text-align: center;
  }
  
  .result-icon {
    height: 80px;
    margin-top: 20px;
  }
  
  .recommended-content {
    margin-top: 40px;
  }
  
  .content-cards {
    display: flex;
    color: white;
    justify-content: center;
    gap: 20px;
  }
  
  .content-card {
    background-color: #EDA150;
    padding: 20px;
    border-radius: 15px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    width: 250px;
    text-align: center;
  }
  
  .content-link {
    display: block;
    font-size: 24px;
    color: #438C74;
    margin-top: 10px;
  }
  
  .action-buttons {
    margin-top: 40px;
    display: flex;
    gap: 20px;
  }
  
  .restart-button,
  .share-button {
    background-color: #438C74;
    color: white;
    border: none;
    padding: 15px 30px;
    border-radius: 10px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .restart-button:hover,
  .share-button:hover {
    background-color: #45a049;
  }
  </style>
  