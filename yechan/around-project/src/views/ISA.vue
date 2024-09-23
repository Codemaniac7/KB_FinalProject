<template>
  <div class="isa">
    <h2 class="text-center">ISA</h2>
    <!-- 제목 추가 -->
    <div class="input-boxes">
      <input
        type="text"
        placeholder="상품명, 이자율, 예치금액"
        class="input-field"
      />
      <input
        type="text"
        placeholder="상품명, 이자율, 예치금액"
        class="input-field"
      />
      <input
        type="text"
        placeholder="상품명, 이자율, 예치금액"
        class="input-field"
      />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.isa {
  margin-top: 60px; /* 페이지 상단 여백 */
  text-align: center;
}

.input-boxes {
  display: flex;
  justify-content: center;
  gap: 1rem; /* 박스 간격 */
  margin-top: 1rem;
}

.input-field {
  width: 300px; /* 박스 너비 */
  height: 300px; /* 박스 높이로 정사각형 유지 */
  padding: 1rem; /* 내부 여백 */
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center; /* 텍스트 중앙 정렬 */
  font-size: 16px; /* 폰트 크기 조정 */
}
</style>
