<template>
  <div class="result-view">
    <br /><br />
    <h4 class="animated-text" style="color: #45a049">투자 성향 테스트 결과</h4>
    <h1 class="animated-text">
      <p>나의 투자 성향은?</p>
    </h1>

     <!-- 투자 성향 카드 -->
     <div class="result-cards">
      <div
        v-for="(result, index) in filteredResults"
        :key="index"
        class="result-card animated-text"
      >
        <h3>{{ result.title }}</h3>
        <p>{{ result.description }}</p>
        <Vue3Lottie :animationData="result.lottiePath" />
      </div>
    </div>

    <!-- 추천 콘텐츠 -->

    <div class="recommended-content" v-if="recommendedContent.length">
      <b class="animated-text">
        <p class="type_txt">{{ userType }}</p>
        타입을 가진 당신을 위해 아래 콘텐츠를 준비했어요!
      </b>

      <div class="content-cards">
        <div
          v-for="(content, index) in recommendedContent"
          :key="index"
          class="content-card animated-text"
        >
          <p>{{ content.title }}</p>

          <h4>{{ content.description }}</h4>

          <img :src="content.icon" alt="Content Icon" />

          <router-link
  :to="getLinkForContent(content)"
  class="content-link"
>
  ➔
</router-link>



        </div>
      </div>
    </div>

    <!-- 하단 버튼 -->

    <div class="action-buttons">
      <button @click="restartTest" class="restart-button animated-text">
        테스트 다시하기
      </button>

      <button @click="shareResults" class="share-button animated-text">
        공유
      </button>
    </div>
  </div>
</template>

<script>
import { Vue3Lottie } from "vue3-lottie"; // vue3-lottie 컴포넌트 가져오기

import futureOrientedCollaborator from "@/assets/img/analyResult/1.json";
import forwardThinker from "@/assets/img/analyResult/2.json";
import knowledgeableMaster from "@/assets/img/analyResult/3.json";
import braveExplorer from "@/assets/img/analyResult/4.json";
import trendsetter from "@/assets/img/analyResult/5.json";
import insightfulArtist from "@/assets/img/analyResult/6.json";
import naturalLeader from "@/assets/img/analyResult/7.json";
import diversifiedExpert from "@/assets/img/analyResult/8.json";
import confidentInvestor from "@/assets/img/analyResult/9.json";
import digitalInnovator from "@/assets/img/analyResult/10.json";
import exploratorySeeker from "@/assets/img/analyResult/11.json";
import strategicResearcher from "@/assets/img/analyResult/12.json";
import strategicPlanner from "@/assets/img/analyResult/13.json";
import buddingInvestor from "@/assets/img/analyResult/14.json";
import experiencedIcon from "@/assets/img/analyResult/15.json";
import cautiousObserver from "@/assets/img/analyResult/16.json";
import axios from "axios";
import { useAuthStore } from '@/stores/auth';




export default {
  components: {
    Vue3Lottie,
  },
  data() {
    return {
      userId: "",  // 사용자 ID 저장
      userType: "", // 사용자의 투자 성향 타입을 저장
      totalScore: 0, // 총 점수 저장
      investmentResults: [
        {
          type: "IPWC",
          title: "IPWC",
          description:
            "안정성을 최우선으로 고려하며, 주로 단기적이고 소규모로 신중하게 투자합니다.",
          lottiePath: cautiousObserver,
        },
        {
          type: "IPMC",
          title: "IPMC",
          description: "리스크를 철저히 관리하며 안정적인 자산 운용을 추구합니다.",
          lottiePath: experiencedIcon,
        },
        {
          type: "IBWC",
          title: "IBWC",
          description:
            "안정적인 상품에 주로 투자하면서도, 미래 성장 가능성을 고려한 투자를 선호합니다.",
          lottiePath: buddingInvestor,
        },
        {
          type: "IBMC",
          title: "IBMC",
          description:
            "안정성을 중시하면서도, 적절한 위험을 감수하여 장기적인 성장을 목표로 합니다.",
          lottiePath: strategicPlanner,
        },
        {
          type: "IPWL",
          title: "IPWL",
          description:
            "투자 전략을 철저히 분석하며, 안정적이면서도 성장 가능성이 있는 상품에 투자합니다.",
          lottiePath: strategicResearcher,
        },
        {
          type: "IBML",
          title: "IBML",
          description:
            "디지털 자산과 새로운 금융 상품에 관심이 많으며, 혁신적인 투자에 도전합니다.",
          lottiePath: digitalInnovator,
        },
        {
          type: 'IPML',
          title: 'IPML',
          description:
            '투자 전략을 철저히 분석하며, 안정적이면서도 성장 가능성이 있는 상품에 투자합니다.',
          lottiePath: strategicResearcher,
        },
        {
          type: 'IPWL',
          title: 'IPWL',
          description:
            '금융 지식이 풍부하며, 다각화된 자산에 신중하게 투자합니다.',
          lottiePath: strategicResearcher,
        },
        {
          type: "IBWL",
          title: "IBWL",
          description:
            "위험을 감수하더라도 성장 가능성이 높은 자산에 투자합니다. 고위험 상품에도 과감히 투자합니다.",
          lottiePath: confidentInvestor,
        },
        {
          type: "APWL",
          title: "APWL",
          description:
            "자산을 다양한 곳에 분산하여 리스크를 줄이면서도 높은 수익을 노립니다.",
          lottiePath: diversifiedExpert,
        },
        {
          type: "APML",
          title: "APML",
          description:
            "리더십과 판단력이 뛰어나며, 공격적인 투자 방식으로 높은 수익을 추구합니다.",
          lottiePath: naturalLeader,
        },
        {
          type: "ABWC",
          title: "ABWC",
          description:
            "직관적이고 창의적인 방법으로 시장의 변화를 빠르게 감지하여 투자합니다.",
          lottiePath: insightfulArtist,
        },
        {
          type: "APMC",
          title: "APMC",
          description:
            "혁신적이며 리스크를 감수하더라도 트렌드를 선도하는 자산에 투자합니다.",
          lottiePath: trendsetter,
        },
        {
          type: "ABWL",
          title: "ABWL",
          description:
            "도전적이며 불확실한 시장에서도 과감한 결정을 내리고, 고위험 자산에 투자합니다.",
          lottiePath: braveExplorer,
        },
        {
          type: "APWC",
          title: "APWC",
          description:
            "풍부한 금융 지식을 바탕으로 복잡한 금융 상품에도 적극적으로 투자하며, 큰 수익을 기대합니다.",
          lottiePath: knowledgeableMaster,
        },
        {
          type: "ABMC",
          title: "ABMC",
          description:
            "남다른 통찰력과 미래 지향적인 투자 성향으로, 시장을 선도하며 고위험 자산에도 투자합니다.",
          lottiePath: forwardThinker,
        },
        {
          type: "ABML",
          title: "ABML",
          description:
            "현실적인 계획을 바탕으로 극단적인 위험을 감수하며, 장기적으로도 큰 수익을 목표로 합니다.",
          lottiePath: futureOrientedCollaborator,
        },
      ],
      recommendedContent: [],
      stockData: [], // API로 받아온 주식 데이터를 저장
    };
  },
  created() {
    // 라우터 쿼리에서 totalScore 값 가져오기
    this.auth = useAuthStore();
    this.totalScore = parseInt(this.$route.query.totalScore, 10);
    this.classifyUserType();
    this.fetchUserId();  // 로그인 후 사용자 ID를 받아옴
    this.fetchStockData(); // 사용자의 투자 성향에 맞는 주식 데이터를 요청
    this.saveInvestmentType();
    this.setRecommendedContent();
  },
  methods: {
      // 사용자 ID를 가져오는 함수
      async fetchUserId() {
        this.userId = this.auth.id;  // 응답에서 사용자 ID를 가져와 설정
    },

  // 투자 성향 저장/업데이트 함수
  async saveInvestmentType() {
    try {
    const response = await axios.put(
      'http://localhost:8080/api/member/updateInvestType',
      {
        id: this.userId,          // 사용자 ID
        investType: this.userType // 테스트 결과로 나온 투자 성향 (예: ABML)
      },
      { 
        headers: {
          Authorization: `Bearer ${this.auth.getToken()}` 
        }
      }
    );
        console.log(response.data);
        this.auth.changeInvestType(this.userType);
      } catch (error) {
        console.error("투자 성향을 저장하는 중 오류 발생:", error);
      }
    },

  // 테스트 다시하기
  async restartTest() {
      this.userType = '';  // 투자 성향 초기화
      await this.saveInvestmentType();  // 초기화된 성향을 DB에 저장
      this.$router.push('/test-start');  // 테스트 시작 페이지로 이동
    },



    getLinkForContent(content) {
    // 주식 관련 콘텐츠일 경우 StockDetail로 이동
    if (content.title.includes('주식') || content.link === '/StockDetail') {
      return { path: '/StockDetail', query: { userType: this.userType } };
    }
    // `dictionary` 링크일 경우에만 `param` 추가
    if (content.link === '/dictionary') {
      const query = { userType: this.userType };
      if (content.param) {
        query.search = content.param;  // `param`이 있는 경우에만 `search` 파라미터 추가
      }
      return { path: content.link, query };
    }
    // 주식이 아닌 경우 원래의 링크로 이동
    return { path: content.link, query: { userType: this.userType } };
  },
    classifyUserType() {
      // 성향 분류 로직 => totalScore에 따라 성향을 결정
      if (this.totalScore >= 44) {
        this.userType = "ABML";
      } else if (this.totalScore >= 43) {
        this.userType = "ABMC";
      } else if (this.totalScore >= 42) {
        this.userType = "APWC";
      } else if (this.totalScore >= 41) {
        this.userType = "ABWL";
      } else if (this.totalScore >= 40) {
        this.userType = "APMC";
      } else if (this.totalScore >= 39) {
        this.userType = "ABWC";
      } else if (this.totalScore >= 38) {
        this.userType = "APML";
      } else if (this.totalScore >= 37) {
        this.userType = "APWL";
      } else if (this.totalScore >= 35) {
        this.userType = "IBWL";
      } else if (this.totalScore >= 34) {
        this.userType = "IBML";
      } else if (this.totalScore >= 33) {
        this.userType = "IPWL";
      } else if (this.totalScore >= 32) {
        this.userType = "IPML";
      } else if (this.totalScore >= 30) {
        this.userType = "IBMC";
      } else if (this.totalScore >= 28) {
        this.userType = "IBWC";
      } else if (this.totalScore >= 26) {
        this.userType = "IPMC";
      } else {
        this.userType = "IPWC";
      }
    },
    fetchStockData() {
      let apiUrl = "";

      // 사용자 성향에 맞는 API 경로 설정
      switch (this.userType) {
        case "IPWC":
          apiUrl = "http://localhost:8080/api/stocks/stable";
          break;
        case "IBWC":
        case "IBMC":
          apiUrl = "http://localhost:8080/api/stocks/growth";
          break;
        case "IPMC":
        case "IPML":
          apiUrl = "http://localhost:8080/api/stocks/dividend";
          break;
        case "IBWL":
        case "APWL":
          apiUrl = "http://localhost:8080/api/stocks/volatile";
          break;
        case "APML":
        case "ABWC":
          apiUrl = "http://localhost:8080/api/stocks/aggressive";
          break;
      }

      // API 호출로 데이터를 받아옴
      axios
        .get(apiUrl)
        .then((response) => {
          this.stockData = response.data;
        })
        .catch((error) => {
          console.error("주식 데이터를 가져오는 중 오류 발생:", error);
        });
    },
    setRecommendedContent() {
      // 사용자의 성향에 맞는 추천 콘텐츠를 설정
      if (this.userType === "IBWL") {
        // 매우 보수적인 투자 성향
        this.recommendedContent = [
        {
            title: "ISA 상품",
            description:
              "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/isa",
          },
          {
            title: "펀드",
            description: "채권 위주의 매우 안정적인 투자를 하세요.",
            icon: require("@/assets/img/analysis/5.png"),
            link: "/funds?grade=5-6",
          },
          {
            title: "국채 투자",
            description:
              "안전한 국채를 통해 리스크 없이 수익을 올릴 수 있는 방법을 알아보세요.",
            icon: require("@/assets/img/analysis/6.png"),
            link: "/dictionary",
            param : "국채", 
          },
        ];
      } else if (this.userType === "IPWL") {
        // 보수적 투자 성향
        this.recommendedContent = [
        {
            title: "금 투자 ",
            description:
              "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/gold",
          },
          {
            title: "ISA 상품",
            description:
              "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/isa",
          },
          {
            title: "외화예금 상품 ",
            description: "환율 변동을 활용한 수익과 함께 안정적인 이자 수익을 얻을 수 있습니다.",
            icon: require("@/assets/img/analysis/15.png"),
            link: "/dictionary",
            param : "외화예금", 
            // 대호님추가 해당 상품에 맞는 id값 넘겨주면 될듯요?
          },
          
        ];
      } 
      else if (this.userType === "IBML") {
        // 보수적 투자 성향
        this.recommendedContent = [
        {
            title: "외화예금 상품 ",
            description: "환율 변동을 활용한 수익과 함께 안정적인 이자 수익을 얻을 수 있습니다.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/dictionary", 
            param : "외화예금",
            // 대호님추가
          },
          {
            title: "ISA 상품",
            description:
              "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/isa",
          },
        ];
      }else if (this.userType === "IBWC") {
        // 보수적인 성장형 투자 성향
        this.recommendedContent = [
        {
            title: "예적금 상품",
            description:
              "안전한 예적금상품을 확인해보세요",
            icon: require("@/assets/img/analysis/15.png"),
            link: "/deposit",
          },
          {
            title: "금 투자 ",
            description:
              "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/gold",
          },
          {
            title: "성장형 주식 상품",
            description:
              "성장 가능성을 고려하며 안정성과 장기적인 성장을 함께 추구합니다",
            icon: require("@/assets/img/analysis/10.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "IPMC") {
        // 안정적인 성장형 투자 성향
        this.recommendedContent = [
        {
            title: "예적금 상품",
            description:
              "안전한 예적금상품을 확인해보세요",
            icon: require("@/assets/img/analysis/15.png"),
            link: "/deposit",
          },
          {
            title: "금 투자 ",
            description:
              "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/gold",
          },
          {
            title: "안전성 중심 주식투자",
            description:
              "리스크를 철저히 관리하며 안정적인 자산 운용을 추구합니다.",
            icon: require("@/assets/img/analysis/16.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "IPML") {
        // 전략적인 투자 연구자
        this.recommendedContent = [
        {
            title: "금 투자 ",
            description:
              "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/gold",
          },
          {
            title: "ISA 상품",
            description:
              "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/isa",
          },
          {
            title: "변동성 높은 주식 상품",
            description:
              "고위험 고수익을 목표로 하는 성향으로, 리스크를 감수하면서도 높은 성과를 기대합니다",
            icon: require("@/assets/img/analysis/14.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "IBMC") {
        // 중립적인 투자 성향
        this.recommendedContent = [
          {
            title: "금 투자 ",
            description:
              "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/gold",
          },
          {
            title: "ISA 상품",
            description:
              "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/isa",
          },
          {
            title: "성장형 주식 상품",
            description:
              "성장 가능성을 고려하며 안정성과 장기적인 성장을 함께 추구합니다",
            icon: require("@/assets/img/analysis/10.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "IPWC") {
        // 디지털 혁신 투자 지휘관
        this.recommendedContent = [
        {
            title: "예적금 상품",
            description:
              "안전한 예적금상품을 확인해보세요",
            icon: require("@/assets/img/analysis/15.png"),
            link: "/deposit",
          },
          {
            title: "금 투자 ",
            description:
              "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require("@/assets/img/analysis/12.png"),
            link: "/gold",
          },
          {
            title: "안전성 중심 주식투자",
            description:
              "리스크를 철저히 관리하며 안정적인 자산 운용을 추구합니다.",
            icon: require("@/assets/img/analysis/16.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "APWC") {
        // 적극적인 투자 성향
        this.recommendedContent = [
        {
          title: "옵션 결합 선물 전략",
    description: "옵션과 선물을 결합한 고도의 투자 전략으로 큰 수익을 추구합니다.",

            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "옵션", 
          },
          {
            title: "펀드",
            description:
              "저위험 채권 혼합형 펀드. 안정적이면서도 수익을 추구합니다.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=5-6",
          },
        ];
      } else if (this.userType === "APWL") {
        // 분산투자 능력자
        this.recommendedContent = [
        {
          title: "REIT 투자",
          description: "부동산 시장에 간접 투자하여 안정적인 배당 수익을 노려보세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "REIT 투자",
            // 대호님추가
          },
          {
            title: "펀드",
            description:
              "채권 위주에 주식을 조금 추가해서 위험성은 크지 않으면서 적당한 수익을 추구.",
            icon: require("@/assets/img/analysis/18.png"),
            link: "/funds?grade=3-4",
          },
          {
            title: "변동성 높은 주식 상품",
            description:
              "고위험 고수익을 목표로 하는 성향으로, 리스크를 감수하면서도 높은 성과를 기대합니다",
            icon: require("@/assets/img/analysis/14.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "APML") {
        // 리더형 투자자
        this.recommendedContent = [
        {
          title: "테마 ETF",
          description: "특정 산업이나 트렌드에 집중 투자하여 높은 수익을 추구합니다.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "테마", 
            
          },
          // 대호님추가
          {
            title: "펀드",
            description:
              "주식과 다른 상품을 혼합하여 수익을 추구하면서도 안정성을 놓치지 않고자 하는 투자 상품",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=3-4",
          },
          {
            title: "공격적인 주식 상품",
            description: "직관적이고 창의적인 방법으로 시장의 변화를 빠르게 감지하여 투자합니다.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "ABWL") {
        // 공격적인 투자 성향
        this.recommendedContent = [
        {
          title: "장외 주식 투자",
    description: "비상장 기업에 투자하여 IPO 전 높은 수익을 기대할 수 있습니다.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "장외 주식 투자",
            // 대호님추가 
          },
          {
            title: "펀드",
            description:
              "주식형 펀드. 중간 위험성을 가지고 있으며 조금 더 적극적인 투자를 할 수 있습니다.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=3-4",
          },
        ];
      } else if (this.userType === "ABML") {
        // 트렌드세터 투자자
        this.recommendedContent = [
        {
          title: "합성 ETF",
    description: "파생상품을 활용하여 만든 초고위험 ETF로, 극단적인 수익을 노릴 수 있습니다.",

            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "합성 ETF",
            // 대호님추가 
          },
          {
            title: "펀드",
            description:
              "주식과 다른 상품들을 혼합하여 적당한 수익을 추구하면서도 큰 위험성이 없는 투자",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=3-4",
          },
        ];
      } else if (this.userType === "ABWC") {
        // 도전형 투자자
        this.recommendedContent = [
        {
          title: "복잡한 구조의 고수익 ELS",
    description: "높은 수익을 노릴 수 있는 복잡한 구조의 ELS 상품에 투자해보세요.",
            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "ELS",
            // 대호님추가 
          },
          {
            title: "펀드",
            description:
              "주식 위주의 적극적인 수익 추구형 투자.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=3-4",
          },
          {
            title: "공격적인 주식 상품",
            description: "직관적이고 창의적인 방법으로 시장의 변화를 빠르게 감지하여 투자합니다.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/StockDetail",
          },
        ];
      } else if (this.userType === "APMC") {
        // 투자 달인
        this.recommendedContent = [
        {
          title: "암호화폐 펀드",
    description: "빠르게 성장하는 암호화폐 시장에 전문가가 운용하는 펀드로 투자해보세요.",

            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "암호화폐 펀드",
            // 대호님추가 
          },
          {
            title: "펀드",
            description:
              "주식 위주의 고수익 추구형 펀드. 대부분 주식을 포트폴리오에 담은 공격적인 성향의 펀드입니다.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=1-2",
          },
          {
            title: "고수익 금융 상품",
            description: "금융 전문가를 위한 고수익 상품에 대해 알아보세요.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/dictionary",
            param: "선물",
          },
        ];
      } else if (this.userType === "ABMC") {
        // 시대를 앞서가는 투자 명장
        this.recommendedContent = [
        {
          title: "메자닌 투자",
    description: "주식과 채권의 특성을 결합한 복잡한 구조의 투자로 높은 수익을 추구합니다.",

            icon: require("@/assets/img/analysis/3.png"),
            link: "/dictionary",
            param : "메자닌 투자",
            // 대호님추가 
          },
          {
            title: "펀드",
            description:
              "선물, 옵션, 스왑 등 상당히 공격적으로 투자하는 고수익 추구형 펀드",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/funds?grade=1-2",
          },
        ];
      } else {
        // 미래지향적 투자 협력가
        this.recommendedContent = [
          {
            title: "장기적 큰 수익 목표",
            description: "장기적으로 큰 수익을 목표로 하는 전략을 확인하세요.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/long-term-profit",
          },
          {
            title: "고위험 장기 투자",
            description:
              "극단적인 리스크를 감수하고 장기적인 성장을 추구하는 투자 방법을 알아보세요.",
            icon: require("@/assets/img/analysis/20.png"),
            link: "/high-risk-longterm",
          },
        ];
      }
    },
    shareResults() {
      alert("결과를 공유합니다.");
    },
  },
  computed: {
  filteredResults() {
    return this.investmentResults.filter(result => result.type === this.userType);
  }
}
};
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

.result-view {
  font-family: J5;
  text-align: center;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.animated-text {
  animation: slideIn 0.5s ease-out forwards;
}

h1,
h2,
.result-card h3,
.content-card p,
.content-card h4 {
  animation: slideIn 0.5s ease-out forwards;
}

h1 {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 40px;
}

b {
  font-size: 30px;
}

.result-cards {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.result-card {
  font-family: J3;
  background-color: #eccb50;
  padding: 20px;
  border-radius: 15px;
  color: white;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
}

.result-card h3 {
  font-family: J5;
}

.result-icon {
  height: 80px;
  margin-top: 20px;
}

.recommended-content {
  margin-top: 80px;
}

.content-cards {
  font-family: J3;
  display: flex;
  color: white;
  margin-top: 35px;
  justify-content: center;
  gap: 20px;
}

.content-card {
  background-color: #eda150;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  width: 250px;
  text-align: center;
}

.content-link {
  display: block;
  font-size: 24px;
  color: #438c74;
  margin-top: 10px;
}

.action-buttons {
  margin-top: 40px;
  display: flex;
  gap: 20px;
}

.restart-button,
.share-button {
  background-color: #438c74;
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

.type_txt {
  color: #438c74;
}
</style>
