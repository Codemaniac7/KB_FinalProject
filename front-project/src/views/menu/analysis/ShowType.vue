<template>
    <div class="container">
      <h1 class="title">투자성향 보기</h1>
      <div
        class="outer-box"
        style="
          background-color: rgba(135, 140, 67, 0.06);
          border-radius: 30px;
          padding: 30px;
          width: 1300.5px;
          max-width: 1600px;
          margin: auto;
          position: relative;
          margin: 0 -10px; /* 좌우 여백을 동일하게 설정 */
        "
      >
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
        investmentResults: [
          {
            title: 'IPWC',
            description:
              '안정성을 최우선으로 고려하며, 주로 단기적이고 소규모로 신중하게 투자합니다.',
          },
          {
            title: 'IPMC',
            description:
              '리스크를 철저히 관리하며 안정적인 자산 운용을 추구합니다.',
          },
          {
            title: 'IBWC',
            description:
              '안정적인 상품에 주로 투자하면서도, 미래 성장 가능성을 고려한 투자를 선호합니다.',
          },
          {
            title: 'IBMC',
            description:
              '안정성을 중시하면서도, 적절한 위험을 감수하여 장기적인 성장을 목표로 합니다.',
          },
          {
            title: 'IPML',
            description:
              '투자 전략을 철저히 분석하며, 안정적이면서도 성장 가능성이 있는 상품에 투자합니다.',
          },
          {
            title: 'IPWL',
            description:
              '금융 지식이 풍부하며, 다각화된 자산에 신중하게 투자합니다.',
          },
          {
            title: 'IBML',
            description:
              '디지털 자산과 새로운 금융 상품에 관심이 많으며, 혁신적인 투자에 도전합니다.',
          },
          {
            title: 'IBWL',
            description:
              '위험을 감수하더라도 성장 가능성이 높은 자산에 투자합니다. 고위험 상품에도 과감히 투자합니다.',
          },
          {
            title: 'APWL',
            description:
              '자산을 다양한 곳에 분산하여 리스크를 줄이면서도 높은 수익을 노립니다.',
          },
          {
            title: 'APML',
            description:
              '리더십과 판단력이 뛰어나며, 공격적인 투자 방식으로 높은 수익을 추구합니다.',
          },
          {
            title: 'ABWC',
            description:
              '직관적이고 창의적인 방법으로 시장의 변화를 빠르게 감지하여 투자합니다.',
          },
          {
            title: 'APMC',
            description:
              '혁신적이며 리스크를 감수하더라도 트렌드를 선도하는 자산에 투자합니다.',
          },
          {
            title: 'ABWL',
            description:
              '도전적이며 불확실한 시장에서도 과감한 결정을 내리고, 고위험 자산에 투자합니다.',
          },
          {
            title: 'APWC',
            description:
              '풍부한 금융 지식을 바탕으로 복잡한 금융 상품에도 적극적으로 투자하며, 큰 수익을 기대합니다.',
          },
          {
            title: 'ABMC',
            description:
              '남다른 통찰력과 미래 지향적인 투자 성향으로, 시장을 선도하며 고위험 자산에도 투자합니다.',
          },
          {
            title: 'ABML',
            description:
              '현실적인 계획을 바탕으로 극단적인 위험을 감수하며, 장기적으로도 큰 수익을 목표로 합니다.',
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
    background-color: white; /* 배경 색상 변경 */
  }
  .title {
    font-size: 36px;
    margin: 40px 0;
    color: black;
    font-weight: bold;
    background-color: transparent; /* 제목 배경색 없애기 */
    font-family: J3;
  }
  .boxes {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px; /* 박스 사이의 간격 유지 */
    margin: 0 auto; /* 중앙 정렬 유지 */
    max-width: 1200px; /* 최대 너비 설정 */
    transform: translateX(20px); /* 오른쪽으로 20px 이동 */
  }
  .box {
    background-color: #FFFFFF;
    border: 1px solid #ccc;
    border-radius: 30px; /* 둥근 모서리 변경 */
    padding: 20px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 그림자 효과 추가 */
    width: 250px; /* 정사각형 가로 길이 */
    height: 250px; /* 정사각형 세로 길이 */
    display: flex;
    flex-direction: column;
    transition: transform 0.3s; /* 호버 시 애니메이션 추가 */
  }
  .box:hover {
    transform: translateY(-5px); /* 호버 시 박스 위로 이동 */
  }
  .box-title {
    margin: 0;
    font-size: 21px; /* 제목 크기 증가 */
    color: #438C74; /* 제목 색상 */
    font-weight: bold;
    text-align: center; /* 제목 중앙 정렬 */
  }
  .box h3 {
    font-family: J3;
  }
  .box p {
    font-size: 15px;
    margin: 60px 0; /* 여백을 더 늘려 내용의 높이를 조정 */
    color: #555; /* 텍스트 색상 변경 */
    flex-grow: 1; /* 내용이 공간을 차지하게 하여 박스 높이 조정 */
    display: flex;
    align-items: flex-start; /* 내용의 상단 정렬 */
    font-family: J3;
  }
  .button-container {
    display: flex;
    justify-content: center; /* 버튼 중앙 정렬 */
    margin-top: 40px; /* 위쪽 여백 조정 */
  }
  .test-button {
    padding: 12px 24px; /* 패딩 증가 */
    font-size: 18px; /* 버튼 글자 크기 증가 */
    background-color: #438C74; /* 버튼 배경 색상 변경 */
    color: white;
    text-decoration: none;
    border-radius: 30px; /* 둥근 모서리 */
    transition: background-color 0.3s, transform 0.3s; /* 호버 애니메이션 추가 */
    font-family: J4;
  }
  .test-button:hover {
    background-color: #367760; /* 버튼 호버 시 색상 변경 */
    transform: translateY(-2px); /* 호버 시 버튼 위로 이동 */
  }
  .description {
    font-size: 16px;
    max-width: 600px;
    margin: 0 auto;
  }
  </style>