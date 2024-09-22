<template>
  <div class="home">
    <Feature_1 />
    <Feature_2 />
    <Feature_3 />
    <!-- <Feature_4 /> -->
  </div>
</template>

<script>
import Feature_1 from '@/components/Feature_1.vue';
import Feature_2 from '@/components/Feature_2.vue';
import Feature_3 from '@/components/Feature_3.vue';
// import Feature_4 from '@/components/Feature_4.vue';

export default {
  name: 'Home',
  components: {
    Feature_1,
    Feature_2,
    Feature_3,
    // Feature_4
  }
}
</script>