<script setup>

</script>

<template>
  <div class="container text-center">

    <h1>용어 사전</h1><br><br>
    <div class="dic" style="width: 93%;">
      <div>

        <h5>금융용어</h5> <input placeholder="키워드를 입력해주세요">
        <button type="button" class="btn btn-success">검색</button>
      </div>
    </div>
    <br>
    <h5 class="text-end" style="width: 93%;">"ㄱ" 검색 결과 "152"건의 정보가 검색되었습니다.</h5>
    <br><br>
    <div class="row">
      <div class="p-2 col-4">
        검색결과
      </div>
      <div class="p-2 col-7">
        검색내용
      </div>
    </div>

  </div>
</template>

<style scoped>

.dic{
  border: 1px solid lightgrey;
  border-radius: 30px;
}

.btn-success{
  color:white;
}
.btn-success:hover{
  color:white;
  background: gray;
}

.row{
  height: 770px;
  padding: 10px;
}

.p-2.col-4{
  background-color: #F7F9FC;
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-right: 15px;
}

.p-2.col-7{
  border: 1px solid lightgrey;
  border-radius: 30px;
}
</style>