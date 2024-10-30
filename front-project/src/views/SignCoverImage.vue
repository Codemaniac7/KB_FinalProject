<script setup>

import coverImage from "@/assets/img/account/cover.jpg";
</script>

<template>
  <div class="w-50 bg-size-cover bg-repeat-0 bg-position-center" :style="{ backgroundImage: `url(${coverImage})` }"></div>
</template>

<style scoped>
.w-50 {
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
</style>