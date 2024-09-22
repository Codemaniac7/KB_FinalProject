<template>
    <div class="survey-view">
      <!-- 진행 상황 표시 -->
      <div class="progress-bar">
        <div class="progress" :style="{ width: progressWidth + '%' }"></div>
      </div>
  
      <!-- 현재 질문 표시 -->
      <div class="question-card">
        <p class="question-number">Q. {{ currentQuestionIndex + 1 }}/{{ questions.length }}</p>
        <h2>{{ currentQuestion.question }}</h2>
  
        <!-- 아이콘 추가 -->
        <img v-if="currentQuestion.icon" :src="currentQuestion.icon" alt="Icon" class="question-icon"/>
  
        <!-- 선택지 -->
        <div class="options">
          <button
            v-for="(option, index) in currentQuestion.options"
            :key="index"
            :class="{ selected: selectedOption === index }"
            @click="selectOption(index)"
          >
            {{ option }}
          </button>
        </div>
      </div>
      <!-- 이전/다음 버튼 -->
      <div class="navigation-buttons">
        <button v-if="currentQuestionIndex > 0" @click="prevQuestion" class="prev-button">이전으로</button>
        <button v-if="selectedOption !== null" @click="nextQuestion" class="next-button">다음으로</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        currentQuestionIndex: 0, // 현재 질문의 인덱스
        selectedOption: null, // 사용자가 선택한 옵션
        questions: [
          {
            question: "현재 연금계좌에서 투자하고 있나요?",
            options: ["네, 투자하고 있어요", "아니요, 지금은 안하고 있어요"],
            icon: require('@/assets/img/test/investment-icon.png') 
          },
          {
            question: "분산투자보다 레버리지나 인버스 상품을 통한 투자를 선호하시나요?",
            options: ["네, 선호해요", "아니요, 선호하지 않아요"],
            icon: require('@/assets/img/test/leverage-icon.png') 
          },
          // 추가 질문을 여기에 추가합니다.
        ],
        answers: [] // 사용자의 응답을 저장
      };
    },
    computed: {
      // 현재 질문 가져오기
      currentQuestion() {
        return this.questions[this.currentQuestionIndex];
      },
      // 진행 상황 퍼센티지 계산
      progressWidth() {
        return ((this.currentQuestionIndex + 1) / this.questions.length) * 100;
      }
    },
    methods: {
      // 옵션 선택
      selectOption(index) {
        this.selectedOption = index;
      },
      // 다음 질문으로 이동
      nextQuestion() {
        this.answers.push(this.selectedOption);
        this.selectedOption = null;
        if (this.currentQuestionIndex < this.questions.length - 1) {
          this.currentQuestionIndex++;
        } else {
          // 설문 완료 처리
          this.finishSurvey();
        }
      },
      // 이전 질문으로 이동
      prevQuestion() {
        if (this.currentQuestionIndex > 0) {
          this.currentQuestionIndex--;
          this.answers.pop(); // 이전 질문의 응답을 제거
        }
      },
      // 설문 완료 처리
      finishSurvey() {
        console.log("설문이 완료되었습니다.", this.answers);
        // 설문 완료 후 추가 작업 (예: 결과 페이지로 이동)
        this.$router.push('/test-result');
      }
    }
  };
  </script>
  
  <style scoped>
  /* 전체 화면 스타일 */
  .survey-view {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background-color: #f9f9f9;
    padding: 20px;
  }
  
  /* 진행 바 스타일 */
  .progress-bar {
    width: 60%;
    background-color: #e0e0e0;
    height: 6px;
    border-radius: 5px;
    margin-bottom: 20px;
  }
  
  .progress {
    height: 6px;
    background-color: #458D75;
    border-radius: 5px;
    width: 0;
  }
  
  /* 질문 카드 스타일 */
  .question-card {
    background-color: #458D75;
    padding: 40px 30px;
    border-radius: 15px;
    box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 600px;
    text-align: center;
    color: white;
    position: relative;
  }
  
  /* 질문 번호와 질문 텍스트 스타일 */
  .question-number {
    font-size: 16px;
    font-weight: bold;
    color: #eee;
    margin-bottom: 10px;
  }
  
  h2 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 30px;
    color: white;
  }
  
  /* 질문 아이콘 스타일 */
  .question-icon {
    width: 60px;
    height: 60px;
    margin-bottom: 20px;
  }
  
  /* 옵션 버튼 스타일 */
  .options {
    display: flex;
    justify-content: space-around;
    margin-bottom: 20px;
  }
  
  .options button {
    padding: 15px 25px;
    border: none;
    background-color: white;
    border-radius: 10px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s ease;
  }
  
  .options button:hover {
    background-color: white;
  }
  
  .options button.selected {
    background-color: #45a049;
    color: white;
  }
  
  /* 이전/다음 버튼 스타일 */
  .navigation-buttons {
    display: flex;
    padding-top: 50px;
    justify-content: space-between;
  }
  
  .prev-button,
  .next-button {
    padding: 10px 30px;
    border: none;
    background-color: white;
    color: #4CAF50;
    border-radius: 10px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .prev-button:hover,
  .next-button:hover {
    background-color: #eee;
  }
  
  .prev-button:disabled,
  .next-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  </style>
  