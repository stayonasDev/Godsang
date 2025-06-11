<template>
  <transition name="toast-fade">
    <div v-if="isVisible" class="toast" :class="toastClass">
      <span class="toast-message">{{ message }}</span>
    </div>
  </transition>
</template>

<script setup>
import { defineProps, computed } from 'vue';

const props = defineProps({
  isVisible: {
    type: Boolean,
    default: false
  },
  message: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'success', // 'success', 'error', 'warning'
    validator: (value) => ['success', 'error', 'warning'].includes(value)
  }
});

// 토스트 유형에 따른 Tailwind CSS 클래스를 동적으로 생성
const toastClass = computed(() => {
  return {
    'bg-green-500': props.type === 'success',
    'bg-red-500': props.type === 'error',
    'bg-yellow-500': props.type === 'warning',
    'text-white': true, // 항상 흰색 텍스트
  };
});
</script>

<style scoped>
/* Toast Notification Styles */
.toast {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  padding: 12px 24px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 2000; /* 다른 모달 위에 있도록 가장 높은 z-index */
  display: flex;
  align-items: center;
  justify-content: center;
  white-space: nowrap; /* 내용이 한 줄로 유지되도록 */
  color: white; /* 텍스트 색상을 props에서 오는 bg-color에 따라 변경될 수 있으므로 기본을 흰색으로 */
}

/* Vue Transition Styles */
.toast-fade-enter-active, .toast-fade-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}
.toast-fade-enter-from, .toast-fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-20px); /* 위에서 아래로 나타나고 사라지도록 */
}
.toast-fade-enter-to, .toast-fade-leave-from {
  opacity: 1;
  transform: translateX(-50%) translateY(0);
}
</style>