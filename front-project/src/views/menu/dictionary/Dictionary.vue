<template>
  <div class="bc">
    <div class="container text-center">
      <br><br>
      <h1 class="text-slide-up">용어 사전</h1><br><br>
      <div class="content" :class="{ 'slide-up': filteredTerms.length > 0 && !loading }">
        <div class="dic text-slide-up">
          <br>
          <table class="text-start">
            <tbody>
              <tr class="firstFilter text-slide-up">
                <td>
                  <h4>금융용어</h4>
                </td>
                <td>
                  <div class="searchBar">
                    <input
                      class="search"
                      placeholder="키워드를 입력해주세요"
                      v-model="searchTerm"
                      @input="filterTerms"
                    />
                    <button type="button" class="searchBtn" @click="filterTerms">검색</button>
                  </div>
                </td>
              </tr>
              <tr class="firstFilter text-slide-up">
                <td style="width: 15%;">
                  <h4>한글 순</h4>
                </td>
                <td>
                  <button
                    v-for="hangul in hangulList"
                    :key="hangul"
                    :class="{ 'activeBtn': selectedHangul === hangul }"
                    class="hangul text-slide-up"
                    @click="filterTermsByHangul(hangul)"
                  >{{ hangul }}</button>
                </td>
              </tr>
              <tr class="firstFilter text-slide-up">
                <td>
                  <h4>알파벳 순</h4>
                </td>
                <td>
                  <button
                    v-for="alphabet in alphabetList"
                    :key="alphabet"
                    :class="{ 'activeBtn': selectedAlphabet === alphabet }"
                    class="hangul text-slide-up"
                    @click="filterTermsByAlphabet(alphabet)"
                  >{{ alphabet }}</button>
                </td>
              </tr>
            </tbody>
          </table>
          <br>
        </div>
        <br>
        <h5 class="text-end text-slide-up" style="width: 93%;">
          <i class="ai-search"></i>"{{ searchTerm }}{{selectedHangul}}{{selectedAlphabet}}" 검색 결과 "{{ filteredTerms.length }}"건의 정보가 검색되었습니다.
        </h5>
        <div class="row">
          <div class="p-2 col-4 scrollbar">
            <ul class="text-start dicSubject">
              <li v-if="filteredTerms.length === 0" class="text-slide-up">
                <h3>검색된 검색어가 없습니다.</h3>
              </li>
              <li v-for="(term, index) in filteredTerms"
                  :key="index"
                  @click="selectTerm(term)"
                  :class="{ 'active': selectedTerm === term }">
                <h3 class="text-slide-up">{{ term.termName }}</h3>
              </li>
            </ul>
          </div>
          <div class="p-2 col-7 text-start">
            <span v-if="filteredTerms.length === 0">
              <h3 class="test-center m-5 text-slide-up">검색된 검색어가 없습니다.</h3>
            </span>
            <h2 v-if="selectedTerm" class="text-slide-up">{{ selectedTerm.termName }}</h2>
            <p v-if="selectedTerm" class="text-slide-up">{{ selectedTerm.termDescription }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    searchQuery: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      terms: [],
      filteredTerms: [],
      selectedTerm: null,
      searchTerm: this.searchQuery,  // URL 쿼리에서 전달된 검색어로 초기값 설정
      selectedHangul: null,
      selectedAlphabet: null,
      error: null,
      loading: true,
      hangulList: ['ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'],
      alphabetList: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
    };
  },
  mounted() {
    this.fetchTerms().then(() => {
      const urlParams = new URLSearchParams(window.location.search);
      const search = urlParams.get('search');
      if (search) {
        this.searchTerm = search;  
        this.filterTerms();       
      }
    });
  },
  methods: {
    async fetchTerms() {
      this.loading = true;
      try {
        const response = await axios.get('/api/terms');
        this.terms = response.data;
        this.filteredTerms = response.data;

        // 중복 제거
        this.filteredTerms = Array.from(new Set(this.filteredTerms.map(term => term.termName)))
          .map(name => this.filteredTerms.find(term => term.termName === name));

        // 용어가 존재하면 첫 번째 용어를 기본 선택
        if (this.filteredTerms.length > 0) {
          this.selectedTerm = this.filteredTerms[0];
        }
      } catch (err) {
        this.error = err;
      } finally {
        this.loading = false;
      }
    },
    selectTerm(term) {
      this.selectedTerm = term; // 선택된 용어 설정
    },
    filterTerms() {
      this.selectedHangul = null;
      this.selectedAlphabet = null;

      const search = this.searchTerm.toLowerCase();
      
      this.filteredTerms = this.terms.filter(term =>
        term.termName.toLowerCase().includes(search)
      );

      // 중복 제거
      this.filteredTerms = Array.from(new Set(this.filteredTerms.map(term => term.termName)))
        .map(name => this.filteredTerms.find(term => term.termName === name));

      this.selectedTerm = this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    filterTermsByHangul(hangul) {
      this.searchTerm = '';
      this.selectedHangul = hangul;
      this.selectedAlphabet = null;
      this.filteredTerms = this.terms.filter(term =>
        this.getInitialConsonant(term.termName[0]) === hangul
      );

      // 중복 제거
      this.filteredTerms = Array.from(new Set(this.filteredTerms.map(term => term.termName)))
        .map(name => this.filteredTerms.find(term => term.termName === name));

      this.selectedTerm = this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    filterTermsByAlphabet(alphabet) {
      this.searchTerm = '';
      this.selectedAlphabet = alphabet;
      this.selectedHangul = null;
      this.filteredTerms = this.terms.filter(term =>
        term.termName[0].toUpperCase() === alphabet
      );

      // 중복 제거
      this.filteredTerms = Array.from(new Set(this.filteredTerms.map(term => term.termName)))
        .map(name => this.filteredTerms.find(term => term.termName === name));

      this.selectedTerm = this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    getInitialConsonant(char) {
  // 한글 초성 리스트
  const initialConsonants = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];

  // 한글의 유니코드 범위: 0xAC00 ~ 0xD7A3
  const unicode = char.charCodeAt(0);
  if (unicode < 0xAC00 || unicode > 0xD7A3) {
    return null; // 한글이 아닌 경우
  }

  // 초성의 인덱스 계산
  const initialIndex = Math.floor((unicode - 0xAC00) / 588);
  return initialConsonants[initialIndex];
}

  }
};
</script>


<style scoped>
.active {
  text-decoration: underline;
}

.scrollbar {
  overflow-y: scroll; /* 세로 스크롤바 */
  scrollbar-width: thin; /* Firefox에서 스크롤바 폭 설정 */
  scrollbar-color: rgba(190, 190, 190, 0.58) transparent; /* 스크롤바 색상 설정 */
  margin-left: 10px; /* 왼쪽으로 이동 */
}

/* 웹킷 기반 브라우저(Chrome, Safari 등)에서 스크롤바 스타일 지정 */
.scrollbar::-webkit-scrollbar {
  width: 8px; /* 스크롤바 폭 */
  background: transparent; /* 스크롤바 배경 투명하게 설정 */
}

.scrollbar::-webkit-scrollbar-thumb {
  background-color: rgba(68, 140, 116, 1); /* 스크롤바 바 색상 */
  border-radius: 50%; /* 스크롤바 바 둥글게 만들기 */
}

.scrollbar::-webkit-scrollbar-track {
  background: transparent; /* 스크롤바 배경 투명하게 설정 */
}

.container {
  font-family: J5;
}

.scrollbar::-webkit-scrollbar-corner {
  background: transparent; /* 코너 배경 투명하게 설정 */
}

.scrollbar::-webkit-scrollbar-button {
  display: none; /* 모든 스크롤바 버튼 숨기기 */
}

.dic {
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-left: 30px;
  padding: 20px;
  width: 91%;
}

.row {
  width: 99%;
  height: 450px;
  padding: 10px;
  margin: 20px;
}

.p-2.col-4 {
  background-color: #F7F9FC;
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-right: 15px;
}

.p-2.col-7 {
  border: 1px solid lightgrey;
  border-radius: 30px;
}

h3{ font-family: J3;}

.searchBtn {
  display: inline-block;
  width: 80px;
  height: 40px;
  color: white;
  border: none;
  border-radius: 20px;
  background-color: rgba(68, 140, 116, 1);
}

.searchBtn:active {
  background-color: lightgrey;
  color: black;
}

table {
  margin-left: 8%;
}

.ai-search {
  margin: 15px;
}

.searchBar {
  display: flex;
  width: 80%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  align-items: center;
  margin-bottom: 20px;
}

h4 {
  margin-right: 2%;
  font-family: J3;
}

.search {
  border: none;
  width: 89%;
  height: 40px;
  margin: 20px;
}

.hangul {
  width: 50px;
  height: 50px;
  margin: 6px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 10px;
}

.activeBtn {
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

template {
  margin: 0 auto;
}

.dicSubject {
  height: 380px;
  margin-right: 15px;
  list-style: none;
  padding: 20px;
}

.dicSubject h3 {
  margin: 20px;
  cursor: pointer;
}

.dicSubject li.active h3 {
  text-decoration: underline;
  color: rgba(68, 140, 116, 1);
}

.col-7 h2 {
  color: rgba(68, 140, 116, 1);
  margin: 20px;
}

.col-7 p {
  font-size: 20px;
  margin: 20px;
}

/* 애니메이션 스타일 */
.slide-up {
  animation: slideUp 0.9s ease forwards;
}

.text-slide-up {
  opacity: 0;
  transform: translateY(20px);
  animation: slideUp 0.9s ease forwards;
}

@keyframes slideUp {
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

</style>
