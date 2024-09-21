<script setup>

</script>

<template>

  <div class="container text-center">
    <h1 class="d-inline">적금 </h1><p class="d-inline">당신의 꿈을 모아서</p>
    <br><br>
    <div class="savingBest">
      <div class="text-start">
        <h2>ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품</h2>
        <h4>가장 많이 사랑 받은 적금 상품</h4><br><br>
      </div>
      <div class="itemBoxDiv row g-3 gap-3">
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
      </div>
    </div><br><br>
    <div>
      <h2 class="d-inline search">상품 검색</h2>
      <input class="searchBar" type="text" placeholder="검색어를 입력해주세요"/>
      <button class="searchBtn" type="submit">검색</button>
    </div>
    <br><br>
    <div class="savingsContent">
      <div class="text-start">
        <h2 class="d-inline">ooo가 고객님에게 추천한 </h2><h2 class="d-inline themeItem">테마상품</h2>
        <br><br><br>
      </div>
      <div class="itemBoxDiv row g-lg-3 gap-3">
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
      </div><br>
      <div class="row g-3 gap-3">
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
      </div><br>
    </div>
  </div>
</template>

<style scoped>
.savingBest{
  background-color: rgba(67, 140, 116, 0.06);
  border-radius: 30px;
  padding: 50px;
}

.itemBox{
  border: 1px solid rgba(231, 236, 243, 1);
  border-radius: 30px;
  display: inline-block;
  width: 350px;
  height: 350px;
  background-color: white;
}

.savingsContent{
  background-color: rgba(247, 249, 252, 1);
  border-radius: 30px;
  padding: 50px;
}

.themeItem{
  font-weight: 700;
}

.searchBar{
  width: 50%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 20px;
}

.searchBtn{
  width: 80px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
}
.searchBtn:active{
  background-color: lightgrey;
  color: black;
}

.search{
  margin: 30px;
}
</style>