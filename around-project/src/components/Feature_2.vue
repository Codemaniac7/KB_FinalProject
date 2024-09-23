<template>
  <section class="review-section container pt-5 pb-4 mb-2 my-lg-3 my-xl-4 my-xxl-5">
    <div class="row justify-content-center pt-2 pt-sm-3 pt-md-4 mt-lg-2 pb-lg-2 pb-xl-0">
      <div class="col-sm-11 col-md-9 col-lg-8 col-xl-7 col-xxl-6 text-center">
        <h2 class="review-section__title h1 mb-0">{{ mainTitle }}</h2>
      </div>
    </div>
    
    <ReviewItem 
      v-for="(item, index) in reviewItems" 
      :key="index"
      :item="item"
      :reversed="index % 2 !== 0"
    />
  </section>
</template>

<script setup>
import { ref } from 'vue';
import ReviewItem from './ReviewItem.vue';

const mainTitle = ref('Powerful and highly customizable data analysis tool');

const reviewItems = ref([
  {
    badge: 'Team activities',
    title: 'Key performance indicators of marketing activities',
    description: 'Morbi et massa fames ac scelerisque sit commodo dignissim faucibus vel quisque proin lectus laoreet sem adipiscing sollicitudin erat massa tellus lorem enim aenean phasellus in hendrerit interdum lorem proin pretium dictum urna suspendisse quis risus et.',
    image: {
      light: require('@/assets/img/landing/saas-1/features/02-light.png'),
      dark: require('@/assets/img/landing/saas-1/features/02-dark.png')
    }
  },
  {
    badge: 'Team activities',
    title: 'Key performance indicators of marketing activities',
    listItems: [
      'Segmenting the audience is easy and simple',
      'Functional dashboard for managing your data',
      'Key performance indicators of team interaction'
    ],
    image: {
      light: require('@/assets/img/landing/saas-1/features/03-light.png'),
      dark: require('@/assets/img/landing/saas-1/features/03-dark.png')
    }
  },
  {
    badge: 'Segmentation',
    title: 'Segmentation of the audience using different methods',
    description: 'Facilisi eu neque amet, orci tellus nibh amet tincidunt nunc, feugiat arcu vulputate duis. Quis odio mauris, in sed pharetra velit egestas risus purus tincidunt. Nisl in enim, gravida risus enim risus non et in iaculis non facilisis eget blandit id vulputate.',
    image: {
      light: require('@/assets/img/landing/saas-1/features/04-light.png'),
      dark: require('@/assets/img/landing/saas-1/features/04-dark.png')
    }
  }
]);
</script>

