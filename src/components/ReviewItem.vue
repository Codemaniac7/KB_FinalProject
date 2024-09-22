<template>
  <div class="review-item row align-items-center py-4 py-lg-5 my-sm-2 my-md-4 my-lg-0 my-xl-2 my-xxl-4">
    <div :class="[
      'col-md-6',
      'col-xl-5',
      { 'offset-xl-1': !reversed, 'order-md-2': reversed },
      'pb-3 pb-md-0 mb-3 mb-sm-4 mb-md-0'
    ]">
      <img :src="item.image.light" :alt="item.title" class="img-fluid d-dark-mode-none">
      <img :src="item.image.dark" :alt="item.title" class="img-fluid d-none d-dark-mode-block">
    </div>
    <div :class="[
      'col-md-6',
      'col-xl-5',
      { 'offset-xl-1': reversed, 'order-md-1': reversed }
    ]">
      <div :class="{ 'ps-md-3 ps-xl-0': !reversed, 'pe-md-3 pe-xl-0': reversed }">
        <span class="badge bg-faded-primary text-primary fs-sm mb-3 mb-lg-4">{{ item.badge }}</span>
        <h3 class="h2 mb-lg-4">{{ item.title }}</h3>
        <p v-if="item.description" class="mb-0">{{ item.description }}</p>
        <ul v-if="item.listItems" class="list-unstyled mb-0">
          <li v-for="(listItem, index) in item.listItems" :key="index" class="d-flex pt-1 mt-2">
            <i class="ai-check-alt text-primary fs-4 mt-n1 me-2"></i>
            {{ listItem }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  item: {
    type: Object,
    required: true
  },
  reversed: {
    type: Boolean,
    default: false
  }
});
</script>

<style lang="scss" scoped>
.review-item {
  @include media-breakpoint-up(lg) {
    margin-top: 2rem;
    margin-bottom: 2rem;
  }

  .badge {
    font-size: 0.875rem;
  }

  h3 {
    font-size: 1.75rem;
    margin-bottom: 1rem;

    @include media-breakpoint-up(lg) {
      font-size: 2rem;
      margin-bottom: 1.5rem;
    }
  }

  .list-unstyled {
    li {
      display: flex;
      align-items: flex-start;
      margin-bottom: 0.5rem;

      i {
        color: $primary;
        margin-right: 0.5rem;
        flex-shrink: 0;
      }
    }
  }
}
</style>
<style lang="scss" scoped>
.review-item {
  @include media-breakpoint-up(lg) {
    margin-top: 2rem;
    margin-bottom: 2rem;
  }

  // 기타 스타일...
}
</style>