<template>
  <div class="survey-view">
    <!-- 진행 상황 표시 -->
    <div class="header">
      <div class="progress-container">
        <div class="progress-bar">
          <div class="progress" :style="{ width: progressWidth + '%' }"></div>
          <img src="/img/emoji/balloon.svg" class="balloon-icon" alt="Balloon Icon"
            :style="{ left: progressWidth + '%' }">
        </div>
      </div>
    </div>
    <br>
    <div class="main-content">
      <!-- 현재 질문 표시 -->
      <div class="question-card" :class="{ fade: fading }">
        <p class="question-number">Q. {{ currentQuestionIndex + 1 }}/{{ questions.length }}</p>
        <h2>{{ currentQuestion.question }}</h2>

        <!-- 아이콘 추가 -->
        <img v-if="currentQuestion.icon" :src="currentQuestion.icon" alt="Icon" class="question-icon" />

        <!-- 선택지 -->
        <div class="options">
          <button v-for="(option, index) in currentQuestion.options" :key="index"
            :class="{ selected: selectedOption === index }" @click="selectOption(index)">
            {{ option.text }}
          </button>
        </div>
      </div>
    </div>

    <!-- 하단 중앙에 고정된 이전 버튼 -->
    <div class="footer">
      <button v-if="currentQuestionIndex > 0" @click="prevQuestion" class="prev-button">이전으로</button>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      currentQuestionIndex: 0, // 현재 질문의 인덱스
      selectedOption: null, // 사용자가 선택한 옵션
      fading: false,
      questions: [
        {
          id: 1,
          question: "고객님의 연령대는 어떻게 되나요?",
          options: [
            { text: "20대", score: 1 },
            { text: "30대", score: 2 },
            { text: "40대", score: 3 },
            { text: "50대", score: 4 },
            { text: "60대 이상", score: 5 }
          ],
          icon: require('@/assets/img/analysis/1.png')
        },
        {
          id: 2,
          question: "고객님의 연소득은 어떻게 되나요?",
          options: [
            { text: "3천만원 이하", score: 1 },
            { text: "5천만원 이하", score: 2 },
            { text: "1억원 이하", score: 3 },
            { text: "3억원 이하", score: 4 },
            { text: "3억원 초과", score: 5 }
          ],
          icon: require('@/assets/img/analysis/2.png')
        },
        {
          id: 3,
          question: "고객님의 수입원은 무엇인가요?",
          options: [
            { text: "정기적인 수입 (급여, 임대 등)", score: 1 },
            { text: "정기적인 수입 (연금)", score: 2 },
            { text: "불안정한 수입 (변동 가능)", score: 3 },
            { text: "일정한 수입 없음", score: 4 }
          ],
          icon: require('@/assets/img/analysis/3.png')
        },
        {
          id: 4,
          question: "금융자산 중 투자 자금의 비중은 어느 정도인가요?",
          options: [
            { text: "10% 이내", score: 1 },
            { text: "30% 이내", score: 2 },
            { text: "50% 이내", score: 3 },
            { text: "80% 이내", score: 4 },
            { text: "80% 초과", score: 5 }
          ],
          icon: require('@/assets/img/analysis/4.png')
        },
        {
          id: 5,
          question: "투자해본 금융상품은 어떤 것이 있나요?",
          options: [
            { text: "은행 예적금 등 안전 자산", score: 1 },
            { text: "채권형 펀드 등 안정적 투자", score: 2 },
            { text: "혼합형 펀드 등 중간 위험 자산", score: 3 },
            { text: "주식형 펀드 등 고위험 자산", score: 4 },
            { text: "파생상품 등 초고위험 자산", score: 5 }
          ],
          icon: require('@/assets/img/analysis/5.png')
        },
        {
          id: 6,
          question: "파생상품 투자 경험은 얼마나 되나요?",
          options: [
            { text: "1년 미만", score: 1 },
            { text: "1년 이상 ~ 3년 미만", score: 2 },
            { text: "3년 이상", score: 3 }
          ],
          icon: require('@/assets/img/analysis/6.png')
        },
        {
          id: 7,
          question: "금융투자상품에 대한 이해도는 어느 정도인가요?",
          options: [
            { text: "매우 낮음 (예적금만 알고 있음)", score: 1 },
            { text: "낮음 (주식과 채권 차이를 구별할 수 있음)", score: 2 },
            { text: "높음 (주식, 채권, 펀드의 구조를 이해하고 있음)", score: 3 },
            { text: "매우 높음 (파생상품까지 이해하고 있음)", score: 4 }
          ],
          icon: require('@/assets/img/analysis/7.png')
        },
        {
          id: 8,
          question: "금융투자상품 가입 목적은 무엇인가요?",
          options: [
            { text: "자산 증식(여유 자금 투자)", score: 1 },
            { text: "노후 자금 마련", score: 2 },
            { text: "목적 자금 마련 (결혼자금, 주택구입자금 등)", score: 3 },
            { text: "사용 예정 자금 단기 운용 (전세금, 임차보증금 등)", score: 4 }
          ],
          icon: require('@/assets/img/analysis/8.png')
        },
        {
          id: 9,
          question: "자금의 예상 투자 기간은 얼마나 되나요?",
          options: [
            { text: "6개월 미만", score: 1 },
            { text: "1년 미만", score: 2 },
            { text: "3년 미만", score: 3 },
            { text: "3년 이상", score: 4 }
          ],
          icon: require('@/assets/img/analysis/9.png')
        },
        {
          id: 10,
          question: "기대 수익과 감내할 수 있는 손실은 어느 정도인가요?",
          options: [
            { text: "원금 보존 추구", score: 1 },
            { text: "20% 이내 손실 감내 가능", score: 2 },
            { text: "최대 100% 손실 감내 가능", score: 3 },
            { text: "원금 초과 손실 감내 가능", score: 4 }
          ],
          icon: require('@/assets/img/analysis/10.png')
        }
      ],
      answers: [], // 사용자의 응답을 저장
      totalScore: 0 // 총점 계산
    };
  },
  computed: {
    currentQuestion() {
      return this.questions[this.currentQuestionIndex];
    },
    progressWidth() {
      return (this.currentQuestionIndex / (this.questions.length)) * 100; // 바의 길이를 100%로 만들기
    }
  },
  methods: {
    selectOption(index) {
      this.selectedOption = index;
      this.nextQuestion();
    },
    nextQuestion() {
      this.fading = true;
      setTimeout(() => {
        const selectedScore = this.currentQuestion.options[this.selectedOption].score;
        this.answers.push({ questionId: this.currentQuestion.id, selectedOption: this.selectedOption, score: selectedScore });
        this.totalScore += selectedScore;
        this.selectedOption = null;

        if (this.currentQuestionIndex < this.questions.length - 1) {
          this.currentQuestionIndex++;
        } else {
          this.finishSurvey();
        }
        setTimeout(() => {
          this.fading = false;
        }, 50);
      }, 50);
    },
    prevQuestion() {
      if (this.currentQuestionIndex > 0) {
        const lastAnswer = this.answers.pop();
        this.totalScore -= lastAnswer.score;
        this.currentQuestionIndex--;
      }
    },
    finishSurvey() {
      console.log("설문이 완료되었습니다.", this.answers);
      console.log("총 점수: ", this.totalScore);
      this.$router.push({
        path: '/test-result',
        query: { totalScore: this.totalScore }
      });
    }
  }
};
</script>

<style scoped>
.survey-view {
  font-family: J2;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f9f9f9;
}

/* 상단 고정 영역 */
.header {
  flex: 0 0 auto;
  background-color: #f9f9f9;
  padding-top: 80px;
  /* 위쪽 여백 */
  width: 100%;
  box-sizing: border-box;
}

.progress-container {
  position: relative;
  /* 프로그레스 바와 아이콘이 위치할 컨테이너 */
  width: 60%;
  margin: 0 auto;
}

.progress-bar {
  background-color: #e0e0e0;
  height: 6px;
  border-radius: 5px;
}

.progress {
  height: 6px;
  background-color: #458D75;
  border-radius: 5px;
  width: 0;
  transition: width 0.3s ease;
}

/* 풍선 아이콘을 프로그레스 바 위에 위치하도록 설정 */
.balloon-icon {
  position: absolute;
  top: -45px;
  /* 프로그레스 바 위에 위치 조정 */
  transform: translateX(-50%);
  /* 아이콘을 프로그레스 바의 끝에 걸치게 조정 */
  width: 40px;
  height: 40px;
  z-index: 999;
  transition: left 0.3s ease;
  /* 아이콘 이동 애니메이션 */
  color: #458D75;
}

/* 중앙 고정된 질문 영역 */
.main-content {
  flex: 1 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
}

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
  transition: opacity 0.15s ease, transform 0.15s ease;
  opacity: 1;
  transform: scale(1);
}

.question-card.fade {
  opacity: 0;
  transform: scale(0.95);
}

.question-number {
  font-size: 16px;
  font-weight: bold;
  color: #eee;
  text-align: left;
  margin-bottom: 10px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
}

.question-icon {
  width: 60px;
  height: 60px;
  margin-bottom: 20px;
}

.options {
  display: grid;
  margin-bottom: 20px;
}

.options button {
  margin: 10px;
  padding: 15px 25px;
  border: none;
  font-weight: bold;
  background-color: white;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.2s ease;
}

.options button:hover {
  background-color: #6FBAA1;
  color: white;
}

.options button.selected {
  background-color: #6FBAA1;
  color: white;
}

/* 하단 중앙 고정 영역 */
.footer {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.prev-button {
  padding: 10px 30px;
  border: none;
  background-color: white;
  color: #4CAF50;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.prev-button:hover {
  background-color: #eee;
}

.prev-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>