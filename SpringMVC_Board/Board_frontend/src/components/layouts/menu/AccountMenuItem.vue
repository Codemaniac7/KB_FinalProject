<script setup>
const props = defineProps({ id: String });

const avatar = `/api/member/${props.id}/avatar`;
</script>

<template>
  <li class="nav-item">
    <router-link class="nav-link" to="/auth/profile">
      <img :src="avatar" class="avatar avatar-sm" />
      {{ id }}
    </router-link>
  </li>
</template>
<style></style>
