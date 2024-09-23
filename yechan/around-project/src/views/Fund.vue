<template>
  <div class="fund">
    <h2 class="text-center">펀드</h2>
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
.fund {
  margin-top: 60px;
  text-align: center;
}

.input-boxes {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-top: 1rem;
}

.input-field {
  width: 300px;
  height: 300px;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
  font-size: 16px;
}
</style>
