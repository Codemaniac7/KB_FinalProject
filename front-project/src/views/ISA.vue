<template>
  <div
    class="container"
    style="
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      margin: auto;
      width: 100%;
      max-width: 1200px;
      margin: 20;
    "
  >
    <div class="text-center" style="transform: scale(0.9)">
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: center;
          margin-bottom: 20px;
          position: relative;
          height: 80px;
          margin-top: -100px;
        "
      >
        <div
          style="
            flex-grow: 1;
            text-align: center;
            margin-top: 8px;
            margin-left: 500px;
          "
        >
          <h1
            style="
              margin: 0;
              display: inline;
              padding-top: 8px;
              font-weight: bold;
              font-size: 50px;
            "
          >
            ISA
          </h1>
          <p
            style="
              margin: 0;
              display: inline;
              font-size: 20px;
              margin-top: -5px;
            "
          >
            ISA 하나로 모든 투자를 해결
          </p>
        </div>
        <img
          src="@/assets/img/isa1.png"
          alt="ISA위험성"
          style="
            height: auto;
            width: 57.5px;
            margin-right: 20px;
            margin-top: 5.5px;
          "
        />
        <img
          src="@/assets/img/isa2.png"
          alt="ISA위험성2"
          style="
            height: auto;
            width: 60px;
            margin-right: 20px;
            margin-top: 18px;
          "
        />
        <img
          src="@/assets/img/isa3.png"
          alt="ISA위험성3"
          style="height: auto; width: 85px; margin-right: 8px; margin-top: 21px"
        />
      </div>

      <div
        class="outer-box"
        style="
          background-color: rgba(135, 140, 67, 0.06);
          border-radius: 30px;
          padding: 30px;
          width: 100%;
          max-width: 1600px;
          margin: auto;
          position: relative;
          height: 500px;
        "
      >
        <div
          style="
            margin-left: 20px;
            text-align: left;
            margin-top: 35px;
            background-color: transparent;
            border-radius: 30px;
            width: 600px;
            padding: 20px;
          "
        >
          <h2
            style="
              margin-left: 0px;
              font-weight: 600;
              font-size: 48px;
              color: #438c74;
            "
          >
            ISA 계좌란?
          </h2>
          <br />
          <p
            class="description"
            style="
              font-weight: 400;
              font-size: 38px;
              color: black;
              width: 100%;
              word-break: break-word;
            "
          >
            하나의 계좌로 다양한 금융상품을 관리하면서, 절세혜택도 누릴 수 있는
            <span style="font-weight: 600; font-size: 42px; color: #438c74"
              >"통합계좌"</span
            >입니다.
          </p>
          <p style="font-weight: 400; font-size: 15px; color: black">
            채권, 국내상장주식, 펀드, 리츠, ELS, RP 등 다양한 상품을 한 계좌에
            담아 본인의 투자 전략에 맞게 운용하면서 최대 400만원까지 비과세,
            초과분은 9.9% 분리과세 적용을 받을 수 있습니다.
          </p>
        </div>

        <div
          style="
            position: absolute;
            top: 40px;
            right: 88px;
            display: flex;
            align-items: flex-start;
            margin-top: 10px;
          "
        >
          <div
            class="input-box"
            style="position: relative; width: 250px; height: 300px"
          >
            <div class="overlay-text" style="color: #438c74; font-size: 20px">
              조건 1
            </div>
            <div
              style="
                background-color: white;
                border-radius: 20px;
                padding: 10px;
                height: calc(100% - 35px);
                box-sizing: border-box;
                color: #438c74;
                display: flex;
                flex-direction: column;
                justify-content: center;
              "
            >
              <span style="font-size: 20px">만 19세 이상 또는</span>
              <span style="font-size: 20px">직전연도 근로소득이</span>
              <span style="font-size: 20px">있는 만 15~19세 미만</span>
              <span style="font-size: 20px">대한민국 거주자</span>
            </div>
          </div>

          <div
            style="
              font-size: 60px;
              font-weight: bold;
              color: #438c74;
              margin: 0 5px;
              margin-top: 80px;
            "
          >
            +
          </div>

          <div
            class="input-box"
            style="position: relative; width: 250px; height: 300px"
          >
            <div class="overlay-text" style="color: #438c74; font-size: 20px">
              조건 2
            </div>
            <div
              style="
                background-color: white;
                border-radius: 20px;
                padding: 10px;
                height: calc(100% - 35px);
                box-sizing: border-box;
                color: #438c74;
                display: flex;
                flex-direction: column;
                justify-content: center;
              "
            >
              <span style="font-size: 20px">직전 3개년 중 1회</span>
              <span style="font-size: 20px">이상 금융소득종합과세</span>
              <span style="font-size: 20px">대상이 아닌자</span>
            </div>
          </div>
        </div>

        <div
          style="
            width: 100%;
            max-width: 555px;
            background-color: rgba(68, 140, 116, 1);
            border-radius: 20px;
            padding: 20px;
            box-sizing: border-box;
            margin-left: 670px;
            position: relative;
            top: -70px;
            text-align: center;
            margin-bottom: 15px;
          "
        >
          <span style="font-size: 23px; font-weight: bold; color: white"
            >조건 1과 조건 2가 충족 시 가입 가능 합니다!</span
          >
        </div>
      </div>

      <br />

      <div style="margin-bottom: 60px"></div>
      <!-- 조건 3과 조건 4 박스 -->
      <div
        style="
          display: flex;
          justify-content: center;
          gap: 40px;
          margin-bottom: 40px;
        "
      >
        <!-- 간격을 넓힘 -->
        <div
          class="input-box"
          style="
            position: relative;
            width: 650px;
            height: 350px;
            background-color: #f1f3f8;
            border-radius: 30px;
          "
        >
          <div
            class="overlay-text"
            style="
              font-weight: 600;
              font-size: 40px;
              margin-left: 35px;
              margin-top: 20px; /* 아래로 더 내리기 위한 여백 추가 */
              color: black;
            "
          >
            ISA 일임형
          </div>
          <div
            style="
              border-radius: 30px;
              padding: 10px;
              height: calc(100% - 35px);
              box-sizing: border-box;
              color: #438c74;
              display: flex;
              flex-direction: column;
              justify-content: flex-start; /* 상단 정렬 */
              align-items: flex-start; /* 왼쪽 정렬 */
              background-color: transparent;
              margin-top: 80px; /* 내용 아래로 더 내리기 */
            "
          >
            <div
              style="
                margin-left: 43px; /* 원래 위치에서 오른쪽으로 이동 */
                font-size: 25px; /* 글자 크기 키움 */
                font-weight: 400;
                margin-top: 35px;
                color: black;
              "
            >
              <span style="display: block; text-align: left"
                >투자자가 투자 의사결정을 전문가에게 위임</span
              >
              <span style="display: block; text-align: left">
                하여,
                <span style="color: #438c74"
                  >금융 기관이 자동으로 자산을 운용<span style="color: black">
                    하는</span
                  ></span
                >
              </span>
              <span style="display: block; text-align: left">방식입니다.</span>
              <span style="display: block; text-align: left"
                >수익률을 극대화할 수 있지만, 투자자 본인의</span
              >
              <span style="display: block; text-align: left"
                >선택권은 제한됩니다.</span
              >
            </div>
          </div>
        </div>

        <div
          class="input-box"
          style="
            position: relative;
            width: 650px;
            height: 350px;
            background-color: #f1f3f8;
            border-radius: 30px;
          "
        >
          <div
            class="overlay-text"
            style="
              font-weight: 600;
              font-size: 40px;
              margin-left: 35px;
              margin-top: 20px; /* 아래로 더 내리기 위한 여백 추가 */
              color: black;
            "
          >
            ISA 중개형
          </div>
          <div
            style="
              border-radius: 30px;
              padding: 10px;
              height: calc(100% - 35px);
              box-sizing: border-box;
              color: #438c74;
              display: flex;
              flex-direction: column;
              justify-content: flex-start; /* 상단 정렬 */
              align-items: flex-start; /* 왼쪽 정렬 */
              background-color: transparent;
              margin-top: 80px; /* 내용 아래로 더 내리기 */
            "
          >
            <div
              style="
                margin-left: 43px; /* 원래 위치에서 오른쪽으로 이동 */
                font-size: 25px; /* 글자 크기 키움 */
                font-weight: 400;
                margin-top: 35px;
                color: black;
              "
            >
              <span style="display: block; text-align: left; color: #438c74"
                >투자자가 직접 투자 상품을 선택하고</span
              >
              <span style="display: block; text-align: left; color: #438c74"
                >관리하는 방식<span style="color: black"
                  >입니다. 투자자 본인의 판단에</span
                ></span
              >
              <span style="display: block; text-align: left"
                >따라 다양한 자산에 투자할 수 있으며,</span
              >
              <span style="display: block; text-align: left"
                >더 큰 유연성과 통제권을 가집니다.</span
              >
            </div>
          </div>
        </div>
      </div>

      <!-- 새로운 가로로 긴 박스 -->
      <div
        class="input-box"
        style="
          position: relative;
          width: 1350px; /* 가로로 긴 박스 너비 */
          height: 90px; /* 높이 설정 */
          background-color: #f1f3f8;
          border-radius: 30px;
          margin: 0 auto; /* 중앙 정렬 */
        "
      >
        <div
          style="
            padding: 23px;
            color: black;
            text-align: center; /* 텍스트 중앙 정렬 */
            font-size: 28px; /* 글자 크기 키움 */
            font-weight: 400;
            margin-top: 35px;
          "
        >
          두 유형 모두 세제 혜택이 있으며, 자산의 종류와 운용 방식에 따라
          투자자가 선택할 수 있습니다.
        </div>
      </div>

      <div style="margin-bottom: 30px"></div>

      <br />

      <div
        class="product-background"
        style="
          background-color: rgba(135, 140, 67, 0.06);
          border-radius: 30px;
          padding: 30px;
          width: 100%;
          max-width: 1813px;
          margin: auto;
          position: relative;
          height: 1450px;
          margin-bottom: -350px;
        "
      >
        <h2
          style="
            margin-left: 43px;
            margin-top: 33px;
            text-align: left;
            margin-bottom: 40px;
            font-size: 40px;
            font-weight: bold;
          "
        >
          ISA 신탁형
        </h2>

        <div
          style="
            margin-left: 43px;
            font-size: 28px;
            font-weight: 400;
            margin-top: 35px;
            color: black;
          "
        >
          <span style="display: block; text-align: left; color: black">
            신탁형 ISA는 투자자가
            <span style="color: #438c74"
              >신탁 회사를 통해 자산을 관리하며, 전문 자산 운용자가 투자
              결정을</span
            >
          </span>
          <span style="display: block; text-align: left; color: #438c74">
            내리는 방식<span style="color: black"
              >입니다. 세제 혜택이 제공되어 투자 수익에 대한 세금이 면제되며,
              장기투자에 유</span
            >
          </span>
          <span style="display: block; text-align: left"
            >리합니다. 반적으로 안정적인 투자 전략을 취해 다양한 자산에
            투자하며, 상대적으로 안정적</span
          >
          <span style="display: block; text-align: left"
            >인 수익을 기대할 수 있습니다.</span
          >
        </div>

        <div class="isa-container">
          <table
            style="
              width: 130%; /* 표의 너비 유지 */
              max-width: 1400px; /* 최대 너비 제한 */
              border-collapse: collapse;
              overflow-x: auto;
              border-radius: 30px;
              background-color: white;
              margin: 85px auto; /* 자동으로 중앙 정렬 */
              margin-left: -110px; /* 왼쪽으로 더 이동 */
              font-size: 18px; /* 글자 크기 유지 */
            "
          >
            <thead>
              <tr>
                <th
                  rowspan="2"
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    padding: 15px;
                    width: 150px;
                  "
                >
                  은행
                </th>
                <th
                  rowspan="2"
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    padding: 15px;
                    width: 250px;
                  "
                >
                  상품명
                </th>
                <th
                  colspan="5"
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    padding: 15px;
                  "
                >
                  금리
                </th>
                <th
                  rowspan="2"
                  style="
                    height: 60px;
                    border-left: 1px solid #ccc;
                    padding: 15px;
                    width: 150px;
                  "
                >
                  은행 확인일
                </th>
              </tr>
              <tr>
                <th
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    border-top: 1px solid #ccc;
                    padding: 15px;
                    width: 80px;
                  "
                >
                  3개월
                </th>
                <th
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    border-top: 1px solid #ccc;
                    padding: 15px;
                    width: 80px;
                  "
                >
                  6개월
                </th>
                <th
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    border-top: 1px solid #ccc;
                    padding: 15px;
                    width: 80px;
                  "
                >
                  12개월
                </th>
                <th
                  style="
                    height: 60px;
                    border-right: 1px solid #ccc;
                    border-top: 1px solid #ccc;
                    padding: 15px;
                    width: 80px;
                  "
                >
                  24개월
                </th>
                <th
                  style="
                    height: 60px;
                    border-top: 1px solid #ccc;
                    padding: 15px;
                    width: 80px;
                  "
                >
                  36개월
                </th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td
                  colspan="8"
                  style="border-top: 1px solid #ccc; height: 1px; padding: 0"
                ></td>
              </tr>
              <template v-for="(product, index) in products" :key="index">
                <tr style="height: 60px">
                  <td
                    style="
                      border-right: 1px solid #ccc;
                      vertical-align: middle;
                      padding: 15px;
                    "
                  >
                    {{ product.bank }}
                  </td>
                  <td
                    style="
                      border-right: 1px solid #ccc;
                      vertical-align: middle;
                      padding: 15px;
                    "
                  >
                    {{ product.productName }}
                  </td>
                  <td
                    v-for="(rate, idx) in product.rates"
                    :key="idx"
                    style="
                      border-right: 1px solid #ccc;
                      vertical-align: middle;
                      padding: 15px;
                    "
                  >
                    {{ rate }}
                  </td>
                  <td
                    style="
                      border-left: 1px solid #ccc;
                      vertical-align: middle;
                      padding: 15px;
                    "
                  >
                    {{ product.date }}
                  </td>
                </tr>
                <tr v-if="index < products.length - 1">
                  <td
                    colspan="8"
                    style="border-top: 1px solid #ccc; height: 1px; padding: 0"
                  ></td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

// 검색 쿼리 상태
const searchQuery = ref('');
// 제품 목록 초기화
const products = ref([
  {
    bank: 'IBK기업은행',
    productName: 'ISA 정기예금',
    rates: [2.88, 3.0, 3.04, 2.76, 2.76],
    date: '24/09/25',
  },
  {
    bank: '신한은행',
    productName: '신한은행 ISA 정기예금',
    rates: [2.85, 3.0, 3.1, 2.7, 2.7],
    date: '24/09/24',
  },
  {
    bank: '제주은행',
    productName: 'ISA정기예금',
    rates: [2.9, 3.0, 3.1, 2.75, 2.75],
    date: '24/09/26',
  },
  {
    bank: '광주은행',
    productName: '신탁형ISA',
    rates: [3.2, 3.15, 3.1, 2.64, 2.5],
    date: '24/09/26',
  },
  {
    bank: 'iM뱅크',
    productName: '개인종합자산관리계좌',
    rates: [2.8, 3.05, 3.1, 2.7, 2.75],
    date: '24/09/24',
  },
  {
    bank: 'NH농협은행',
    productName: 'ISA 정기예금',
    rates: [2.85, 2.98, 3.0, 2.7, 2.75],
    date: '24/09/26',
  },
  {
    bank: '전북은행',
    productName: '전북은행ISA정기예금',
    rates: [2.9, 3.05, 3.29, 2.5, 2.4],
    date: '24/09/26',
  },
  {
    bank: 'BNK경남은행',
    productName: '경남은행 ISA 정기예금',
    rates: [2.85, 3.0, 3.2, 2.7, 2.5],
    date: '24/09/25',
  },
  {
    bank: 'Sh수협은행',
    productName: 'ISA(개인종합자산관리계좌)',
    rates: [2.95, 3.0, 3.1, 2.25, 2.05],
    date: '24/09/25',
  },
  {
    bank: 'KB국민은행',
    productName: 'KB개인종합자산관리계좌',
    rates: [2.85, 2.98, 3.05, 2.7, 2.7],
    date: '24/09/26',
  },
  {
    bank: '하나은행',
    productName: 'ISA정기예금',
    rates: [2.8, 2.97, 3.05, 2.68, 2.67],
    date: '24/09/24',
  },
  {
    bank: '우리은행',
    productName: 'ISA 정기예금',
    rates: [2.9, 2.97, 2.99, 2.54, 2.47],
    date: '24/09/24',
  },
  {
    bank: 'BNK부산은행',
    productName: '마이플랜 ISA 정기예금',
    rates: [2.5, 2.8, 3.0, 2.5, 2.5],
    date: '24/09/25',
  },
]);

// 제품을 가져오는 함수
const fetchProducts = async () => {
  try {
    const response = await fetch('http://localhost:3000/api/products/trust'); // 신탁형 데이터 API 주소
    if (!response.ok) throw new Error('네트워크 오류');
    products.value = await response.json(); // JSON 형태로 데이터 변환
  } catch (error) {
    console.error('데이터를 가져오는 데 오류가 발생했습니다:', error);
  }
};

// 컴포넌트가 마운트될 때 데이터 fetch
onMounted(() => {
  fetchProducts();
});

// 현재 페이지와 페이지당 항목 수 상태
const currentPage = ref(1);
const itemsPerPage = 3;

// 필터링된 제품 목록 계산
const filteredProducts = computed(() => {
  if (searchQuery.value) {
    return products.value.filter(
      (product) => product.name.includes(searchQuery.value) // 검색 쿼리에 포함된 제품 필터링
    );
  }
  return products.value; // 검색 쿼리가 없을 경우 모든 제품 반환
});

// 전체 페이지 수 계산
const totalPages = computed(() => {
  return Math.ceil(filteredProducts.value.length / itemsPerPage); // 필터링된 제품 수에 따른 페이지 수
});

// 현재 페이지에 표시할 제품 목록 계산
const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage; // 시작 인덱스 계산
  return filteredProducts.value.slice(start, start + itemsPerPage); // 현재 페이지의 제품 목록 반환
});

// 페이지 변경 함수
const changePage = (page) => {
  if (page < 1 || page > totalPages.value) return; // 페이지 범위 확인
  currentPage.value = page; // 현재 페이지 설정
};

// 검색 함수 (검색 시 페이지를 첫 페이지로 초기화)
const searchProducts = () => {
  currentPage.value = 1; // 페이지를 첫 페이지로 초기화
};
</script>

<style scoped>
.container {
  transform: scale(0.9); /* 화면 비율을 줄이기 */
  transform-origin: top left; /* 축소할 기준점을 설정 */
  margin-top: 5px; /* 위쪽 여백을 약간 추가 */
  margin-left: 170px; /* 전체를 오른쪽으로 이동 */
  width: 80%;
  font-family: J3;
}

.outer-box {
  position: relative;
  background-color: #f1f3f8; /* 변경된 배경색 */
  border-radius: 30px;
  padding: 30px;
  width: 100%;
  max-width: 1600px;
}

.input-box {
  position: relative;
  width: 300px; /* 너비를 줄임 */
  height: 400px;
}

.overlay-text {
  position: absolute;
  top: 10px;
  left: 20px;
  color: #438c74;
  font-weight: bold;
  font-size: 20px;
}

.box {
  width: 120px;
  height: 120px;
  background-color: #e0e0e0;
  border-radius: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  color: black;
}

.searchBar {
  width: 70%; /* 검색 바 너비 줄이기 */
  height: 50px;
  margin-left: 10px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 0 10px;
}
.searchBtn {
  height: 50px;
  margin-left: 10px;
  padding: 0 20px;
  border-radius: 5px;
  border: none;
  background-color: #438c74;
  color: white;
  cursor: pointer;
}
.searchBtn:active {
  background-color: lightgrey;
  color: black;
}
.pagination .page-link {
  color: rgba(68, 140, 116, 1);
}
.pagination .active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  border: none;
}
.pagination .page-link:hover {
  color: white;
}
.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}

.isa-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  text-align: center;
}

th {
  padding: 10px;
  border: none; /* 제목 부분의 경계선을 없앰 */
  border-radius: 30px 30px 0 0; /* 제목 부분의 모서리를 둥글게 만듦 */
}

.last-row {
  border-bottom: 1px solid #ccc; /* 마지막 행 아래에 경계선 추가 */
}
</style>
