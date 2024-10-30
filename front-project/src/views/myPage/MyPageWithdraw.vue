<template>
  <div>
    <br><br><br><br>
    <h1>회원탈퇴</h1><br><br>
    <div class="profileBox">
      <i class="d-inline ai-trash"></i>
      <h2 class="d-inline"> 계정 삭제</h2>
      <br> <br>
      <div class="accountDelete">
        <i class="d-inline ai-triangle-alert"></i>
        <p class="d-inline"> 계정을 삭제하면 공개 프로필이 즉시 비활성화됩니다.</p> <p class="d-inline more">더보기</p>
      </div>
      <div class="form-check">
        <input class="form-check-input" type="checkbox" id="ex-check-1">
        <label class="form-check-label" for="ex-check-1">동의 합니다</label>
      </div>
      <br><br>
      <div class="d-flex justify-content-end">
        <button class="accountDeleteBtn">삭제합니다</button>
      </div>
    </div>
  </div>
</template>
<style scoped>
.accountDeleteBtn{
  width: 250px;
  height: 40px;
  border-radius: 10px;
  color: white;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: rgb(211, 7, 7);
  font-size:20px;
}

.form-check-label{
  color: black;
  font-size: 14px;
}

.more{
  color:rgba(68, 140, 116, 1);
}
.ai-triangle-alert{
  font-size: 27px;
  color: rgba(236, 196, 52, 1);
}
.form-check{
  margin: 10px;
}
.accountDelete{
  border-radius: 20px;
  background-color: rgba(253, 250, 238, 1);
  width: 480px;
  height: 50px;
  font-size:15px;
  font-weight: 700;
  padding: 15px;
  margin: 10px;
}
.form-check-input:checked{
  background-color: rgba(68, 140, 116, 1);
  border-color:rgba(68, 140, 116, 1);
}
h1{
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 40px;
}
.ai-trash{
  font-size:30px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}


</style>

<script setup>

</script>