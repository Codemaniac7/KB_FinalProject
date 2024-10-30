<template>
  <div class="modal-overlay" v-if="isOpen">
    <div class="modal-content">
      <button class="exitModal" @click="closeModal"><h3>x</h3></button>
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
import { defineEmits } from 'vue';

// eslint-disable-next-line no-unused-vars
const props = defineProps({
  isOpen: {
    type: Boolean,

    required: true
  }

});
const emit = defineEmits(['close']);

const closeModal = () => {
  emit('close');
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-content {
  background: white;
  border-radius: 30px;
  padding: 20px;
  max-width: 500px;
  width: 90%;
  position: relative;
}
.exitModal {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
