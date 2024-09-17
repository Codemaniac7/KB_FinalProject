<template>
  <section class="bg-secondary pt-2 py-sm-3 py-md-4 py-lg-5">
    <div class="container py-5 mt-lg-3 mt-xl-4 mt-xxl-5 mb-lg-2 mb-xl-4">
      <h2 class="h1 text-center mt-lg-n3 mb-2 mb-sm-0">Pricing</h2>
      <div class="price-switch-wrapper mt-sm-n4">
        <div class="text-center py-3 mb-3">
          <div class="form-check form-check-inline pe-4">
            <label class="form-check-label text-dark fs-base fw-medium" for="monthly">Monthly payment</label>
          </div>
          <div class="form-check d-inline-flex align-items-end me-sm-n5">
            <div class="d-inline-flex">
              <label class="form-check-label text-dark fs-base fw-medium" for="annual">Annual payment</label>
            </div>
            <div class="d-inline-block text-center ps-3 ms-sm-4">
              <div class="text-danger fs-sm fw-bold mb-sm-1">Save 20%</div>
              <svg class="text-danger ms-n4 d-none d-sm-block" width="52" height="38" viewBox="0 0 52 38" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M50.5002 1C50.3336 5.33333 48.5 13 44.0002 20.5C39.5005 28 29.0002 32.5 28.5002 23C28.0002 13.5 39.5002 7 36.5002 14.5C33.5002 22 18.5 35.5 4 30" stroke="currentColor" stroke-width="1.2" stroke-linecap="round"></path>
                <path d="M3.83039 29.8565C6.35946 29.7566 8.93618 29.122 11.2178 28.1747L11.2178 28.1748L11.2196 28.1739C11.5476 28.0299 11.7182 27.6503 11.5867 27.3359C11.448 26.9871 11.0624 26.8028 10.7213 26.9517C7.91141 28.0931 4.64686 28.7829 1.62704 28.4553L1.62706 28.4551L1.62094 28.4549C1.27015 28.4384 0.944127 28.6914 0.902297 29.0451L0.902167 29.045L0.901827 29.0501C0.896722 29.1266 0.902407 29.2007 0.917743 29.2707C0.94057 29.4967 1.07932 29.7124 1.30195 29.8067C4.07984 31.0931 5.87728 34.0754 7.0096 36.882L7.00959 36.882L7.01015 36.8833C7.13957 37.1912 7.5283 37.3687 7.86577 37.2206L7.86579 37.2205C8.19919 37.0741 8.36065 36.6916 8.22031 36.3385L8.22006 36.3379C7.2736 34.0027 5.84935 31.53 3.83039 29.8565Z" fill="currentColor" stroke="currentColor" stroke-width="0.2"></path>
              </svg>
            </div>
          </div>
        </div>
        <div class="row row-cols-3 flex-nowrap overflow-auto pb-4">
          <PricingCard
            v-for="(plan, index) in pricingPlans"
            :key="index"
            :plan="plan"
          />
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import PricingCard from './PricingCard.vue';

export default {
  components: {
    PricingCard
  },
  data() {
    return {
      pricingPlans: [
        {
          name: 'Starter',
          monthlyPrice: 15.00,
          annualPrice: 12.00,
          imgSrc: 'assets/img/landing/saas-1/pricing/starter.svg',
          features: [
            'In lorem eget amet sit',
            'Sodales sit velit ornare',
            'Tristique suspendisse proin',
            'Nunc nullam in dignissim',
            'Imperdiet amet suspendisse',
            'Tristique nibh erat et quis',
          ],
          buttonType: 'outline-primary'
        },
        {
          name: 'Business',
          monthlyPrice: 35.00,
          annualPrice: 28.00,
          imgSrc: 'assets/img/landing/saas-1/pricing/business.svg',
          features: [
            'In lorem eget amet sit',
            'Sodales sit velit ornare',
            'Tristique suspendisse proin',
            'Nunc nullam in dignissim',
            'Imperdiet amet suspendisse',
            'Tristique nibh erat et quis',
          ],
          buttonType: 'primary'
        },
        {
          name: 'Premium',
          monthlyPrice: 50.00,
          annualPrice: 40.00,
          imgSrc: 'assets/img/landing/saas-1/pricing/premium.svg',
          features: [
            'In lorem eget amet sit',
            'Sodales sit velit ornare',
            'Tristique suspendisse proin',
            'Nunc nullam in dignissim',
            'Imperdiet amet suspendisse',
            'Tristique nibh erat et quis',
          ],
          buttonType: 'outline-primary'
        }
      ]
    }
  }
}
</script>

<style scoped>
section.bg-secondary.pt-2.py-sm-3.py-md-4.py-lg-5 {
        background-color: #F6F9FC !important;
}
</style>