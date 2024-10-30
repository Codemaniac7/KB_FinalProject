<template>
  <div class="container">
    <h1 class="title">금 투자 상품 비교</h1>
    <div class="comparison-section">
      <div class="card-container">
        <div class="card" v-for="(product, index) in products" :key="index">
          <h2 class="product-title">{{ product.name }}</h2>
          <p class="risk-level" :class="product.riskClass">
            {{ product.risk }}
          </p>
          <p class="recommendations">
            추천 성향: {{ product.recommendations }}
          </p>
        </div>
      </div>
    </div>

    <h1 class="title">금 투자 전략 및 가이드</h1>
    <div class="outer-box">
      <div class="boxes">
        <div
            class="box"
            v-for="(result, index) in investmentResults"
            :key="index"
        >
          <h3 class="box-title">{{ result.title }}</h3>
          <p>{{ result.description }}</p>
        </div>
      </div>
      <div class="button-container">
        <router-link to="/test-start" class="test-button">
          투자 성향 테스트하러 가기
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: [
        {
          name: '실물 금',
          risk: '높음',
          recommendations: 'IBWL, APWL, ABWL',
          riskClass: 'high-risk',
        },
        {
          name: '금 통장',
          risk: '낮음',
          recommendations: 'IPWC, IBWC, IBMC',
          riskClass: 'low-risk',
        },
        {
          name: '금펀드',
          risk: '중간',
          recommendations: 'IBMC, IPML, ABMC',
          riskClass: 'medium-risk',
        },
        {
          name: '국내 금 ETF',
          risk: '중간',
          recommendations: 'IBMC, IPML, APWC',
          riskClass: 'medium-risk',
        },
        {
          name: '미국 금 ETF',
          risk: '중간',
          recommendations: 'IBMC, APWL, APMC',
          riskClass: 'medium-risk',
        },
        {
          name: 'KRX 금현물',
          risk: '높음',
          recommendations: 'ABWL, APMC, APML',
          riskClass: 'high-risk',
        },
      ],
      investmentResults: [
        {
          title: 'IPWC',
          description:
              '안정성을 최우선으로 고려하며, 금 통장이나 금펀드 같은 안전 자산에 소규모로 신중하게 투자합니다.',
        },
        {
          title: 'IPMC',
          description:
              '리스크를 철저히 관리하며 금 ETF와 같은 안정적인 자산 운용을 추구합니다.',
        },
        {
          title: 'IBWC',
          description:
              '안정적인 금 상품에 주로 투자하면서도, 미래 금 가격 상승 가능성을 고려한 투자를 선호합니다.',
        },
        {
          title: 'IBMC',
          description:
              '안정성을 중시하면서도, 실물 금과 같은 자산에 적절한 위험을 감수하여 장기적인 성장을 목표로 합니다.',
        },
        {
          title: 'IPML',
          description:
              '투자 전략을 철저히 분석하며, 안정적이면서도 성장 가능성이 있는 금 관련 상품에 투자합니다.',
        },
        {
          title: 'IPWL',
          description:
              '금융 지식이 풍부하며, 금과 관련된 다양한 자산에 신중하게 투자합니다.',
        },
        {
          title: 'IBML',
          description:
              '금 투자와 새로운 금융 상품에 관심이 많으며, 혁신적인 금 투자 전략에 도전합니다.',
        },
        {
          title: 'IBWL',
          description:
              '위험을 감수하더라도 금 가격 상승 가능성이 높은 자산에 투자합니다. 고위험 금 상품에도 과감히 투자합니다.',
        },
        {
          title: 'APWL',
          description:
              '자산을 다양한 금 관련 상품에 분산하여 리스크를 줄이면서도 높은 수익을 노립니다.',
        },
        {
          title: 'APML',
          description:
              '리더십과 판단력이 뛰어나며, 공격적인 금 투자 방식으로 높은 수익을 추구합니다.',
        },
        {
          title: 'ABWC',
          description:
              '직관적이고 창의적인 방법으로 금 시장의 변화를 빠르게 감지하여 투자합니다.',
        },
        {
          title: 'APMC',
          description:
              '혁신적이며 리스크를 감수하더라도 금 시장의 트렌드를 선도하는 자산에 투자합니다.',
        },
        {
          title: 'ABWL',
          description:
              '도전적이며 불확실한 금 시장에서도 과감한 결정을 내리고, 고위험 금 자산에 투자합니다.',
        },
        {
          title: 'APWC',
          description:
              '풍부한 금융 지식을 바탕으로 복잡한 금 상품에도 적극적으로 투자하며, 큰 수익을 기대합니다.',
        },
        {
          title: 'ABMC',
          description:
              '남다른 통찰력과 미래 지향적인 금 투자 성향으로, 시장을 선도하며 고위험 금 자산에도 투자합니다.',
        },
        {
          title: 'ABML',
          description:
              '현실적인 계획을 바탕으로 극단적인 금 시장의 위험을 감수하며, 장기적으로도 큰 수익을 목표로 합니다.',
        },
      ],
    };
  },
};
</script>

<style scoped>
.container {
  text-align: center;
  padding: 20px;
  background-color: white;
}
.title {
  font-size: 36px;
  margin: 40px 0;
  color: black;
  font-weight: bold;
  font-family: J3;
}
.comparison-section {
  margin-bottom: 40px;
  padding: 20px;
  background-color: #878c430f;
  border-radius: 30px;
  font-family: NM;
}
.card-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  max-width: 1200px;
  margin: auto;
}
.card {
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 30px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}
.card:hover {
  transform: translateY(-5px);
}
.product-title {
  font-size: 24px;
  color: #438c74;
  font-weight: bold;
  font-family: J3;
}
.risk-level {
  font-size: 18px;
  font-weight: bold;
}
.high-risk {
  color: #c0392b;
}
.medium-risk {
  color: #f39c12;
}
.low-risk {
  color: #27ae60;
}
.recommendations {
  font-size: 16px;
  color: #555;
}
.outer-box {
  background-color: rgba(135, 140, 67, 0.06);
  border-radius: 30px;
  padding: 30px;
  max-width: 1600px;
  margin: auto;
}
.boxes {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin: 0 auto;
  max-width: 1200px;
}
.box {
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 30px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}
.box-title {
  margin: 0;
  font-size: 21px;
  color: #438c74;
  font-weight: bold;
  text-align: center;
  font-family: J3;
}
.box p {
  font-size: 15px;
  margin: 10px 0;
  color: #555;
  font-family: J3;
}
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}
.test-button {
  padding: 12px 24px;
  font-size: 18px;
  background-color: #438c74;
  color: white;
  text-decoration: none;
  border-radius: 30px;
  transition: background-color 0.3s, transform 0.3s;
  font-family: J4;
}
.test-button:hover {
  background-color: #367c64;
  transform: scale(1.05);
}
</style>
