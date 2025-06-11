<template>
  <div v-if="isVisible" class="confirm-dialog-overlay" @click.self="$emit('cancel')">
    <div class="confirm-content p-6 text-center">
      <h4 class="text-xl font-semibold mb-4">{{ title }}</h4>
      <p class="mb-6">{{ message }}</p>
      <div class="flex justify-center space-x-4">
        <button
          @click="$emit('cancel')"
          class="px-5 py-2 rounded-lg bg-gray-200 text-gray-800 hover:bg-gray-300"
        >
          {{ cancelText }}
        </button>
        <button
          @click="$emit('confirm')"
          class="px-5 py-2 rounded-lg bg-red-600 text-white hover:bg-red-700"
        >
          {{ confirmText }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

defineProps({
  isVisible: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: 'Confirm Action' // 기본 제목
  },
  message: {
    type: String,
    default: 'Are you sure you want to proceed?' // 기본 메시지
  },
  cancelText: {
    type: String,
    default: 'Cancel'
  },
  confirmText: {
    type: String,
    default: 'Confirm'
  }
});

defineEmits(['confirm', 'cancel']);
</script>

<style scoped>
.confirm-dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* 모달과 동일하거나 높은 z-index */
}

.confirm-content {
  background: white;
  padding: 1.5rem;
  border-radius: 0.5rem; /* rounded-lg */
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06); /* shadow-md */
  width: 90%;
  max-width: 400px; /* 적당한 최대 너비 */
  animation: fadeInScale 0.3s ease-out; /* AddEditFoodModal과 동일한 애니메이션 사용 */
}

@keyframes fadeInScale {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>