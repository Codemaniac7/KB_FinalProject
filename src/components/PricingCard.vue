<template>
  <div class="col" style="min-width: 19rem;">
    <div :class="['card', 'h-100', 'py-lg-3', cardClass]">
      <div class="card-body w-100 mx-auto" style="max-width: 21rem;">
        <div class="d-flex align-items-center border-bottom pb-4 mb-4">
          <div :class="['bg-' + cardColor, 'rounded', 'flex-shrink-0', 'p-2']">
            <img class="d-block m-1" :src="plan.imgSrc" width="44" alt="Icon">
          </div>
          <div class="ps-3">
            <h3 :class="['h4', 'text-' + cardColor, 'mb-0']">{{ plan.name }}</h3>
            <div class="d-flex align-items-end">
              <span class="h5 mb-1 me-1">$</span>
              <span class="h2 mb-0">{{ plan.monthlyPrice }}</span>
            </div>
          </div>
        </div>
        <ul class="list-unstyled mb-1 pb-4">
          <li v-for="(feature, index) in plan.features" :key="index" class="d-flex pb-1 mb-2">
            <i class="ai-check fs-xl mt-1 me-2"></i>{{ feature }}
          </li>
        </ul>
        <button :class="['btn', 'w-100', 'btn-' + plan.buttonType]" type="button">Get this plan now</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    plan: Object
  },
  computed: {
    cardClass() {
      return this.plan.buttonType === 'primary' ? 'border-primary' : 'border-0';
    },
    cardColor() {
      return this.plan.buttonType === 'primary' ? 'primary' : 'info';
    }
  }
}
</script>

<style scoped>
/* Add your scoped CSS here */
</style>
