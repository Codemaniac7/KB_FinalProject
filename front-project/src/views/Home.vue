<template>
  <div class="home">
    <Feature_1 v-if="feature1Visible" class="animated-item" />
    <Feature_2 v-if="feature2Visible" class="animated-item" />
    <br><br>
    <Feature_3 v-if="feature3Visible" class="animated-item" />
    <br><br>
    <Feature_4 v-if="feature4Visible" class="animated-item" />
    <br><br>
    <Feature_5 v-if="feature5Visible" class="animated-item" />
    <br><br>
    <Feature_6 v-if="feature6Visible" class="animated-item" />
    <br><br>
    <Feature_7 v-if="feature7Visible" class="animated-item" />
    <br><br>
    <Feature_8 v-if="feature8Visible" class="animated-item" />
  </div>
</template>

<script>
import Feature_1 from '@/components/Feature_1.vue';
import Feature_2 from '@/components/Feature_2.vue';
import Feature_3 from '@/components/Feature_3.vue';
import Feature_4 from '@/components/Feature_4.vue';
import Feature_5 from '@/components/Feature_5.vue';
import Feature_6 from '@/components/Feature_6.vue';
import Feature_7 from '@/components/Feature_7.vue';
import Feature_8 from '@/components/Feature_8.vue';

export default {
  name: 'Home',
  components: {
    Feature_1,
    Feature_2,
    Feature_3,
    Feature_4,
    Feature_5,
    Feature_6,
    Feature_7,
    Feature_8
  },
  data() {
    return {
      feature1Visible: false,
      feature2Visible: false,
      feature3Visible: false,
      feature4Visible: false,
      feature5Visible: false,
      feature6Visible: false,
      feature7Visible: false,
      feature8Visible: false,
    };
  },
  mounted() {
    this.animateFeatures();
  },
  methods: {
    animateFeatures() {
      setTimeout(() => { this.feature1Visible = true; }, 0);
      setTimeout(() => { this.feature2Visible = true; }, 300);
      setTimeout(() => { this.feature3Visible = true; }, 600);
      setTimeout(() => { this.feature4Visible = true; }, 900);
      setTimeout(() => { this.feature5Visible = true; }, 1200);
      setTimeout(() => { this.feature6Visible = true; }, 1500);
      setTimeout(() => { this.feature7Visible = true; }, 1800);
      setTimeout(() => { this.feature8Visible = true; }, 2100);
    }
  }
}
</script>

<style scoped>
@keyframes slideIn {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.animated-item {
  animation: slideIn 0.5s ease-out forwards;  
  transition: opacity 0.5s ease;
}
</style>
