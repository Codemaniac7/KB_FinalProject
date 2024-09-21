<script setup>

</script>

<template>
  <div class="container text-center">
    <h1 class="d-inline">적금 </h1><p class="d-inline">당신의 꿈을 모아서</p>
    <br><br>
    <div class="savingDetailBox">
      <br>
      <ul>
        <li><h2>상품명</h2></li>
        <li><h5>부제목</h5></li>
      </ul>
      <hr>
      <br>
      <table>
        <thead>
          <tr>
            <th><h3>기간</h3></th>
            <th><h3>금액</h3></th>
            <th><h3>금리</h3></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><h3></h3></td>
            <td><h3></h3></td>
            <td><h3></h3></td>
          </tr>
        </tbody>
      </table>
      <br>
      <hr>
      <br>
      <div class="savingsDetailContent">
        상세설명
      </div>
      <br>
      <button class="calculator"><a href="#">적금 계산기</a></button>
      <button class="detailMove"><a href="#">상품 페이지로 이동</a></button>
    </div>

  </div>
</template>

<style scoped>
table{
  margin-right:auto;
  width: 70%;
}

hr{
  width: 90%;
  margin: 0px auto;
}

.savingDetailBox{
  border-radius: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

ul{
  list-style:none;
}
.calculator{
  border: none;
  background-color: rgba(217, 217, 217, 1);
  width: 200px;
  height: 50px;
  color: black;
  font-size: 20px;
}
.detailMove{
  border: none;
  background-color: rgba(67, 140, 116, 1);
  width: 200px;
  height: 50px;
  color: white;
  font-size: 20px;
}

button{
  border-radius: 20px;
  margin: 20px;
}

a {
  color: inherit;
  text-decoration: none;
}

</style>