<template>
  <button
    @click="$emit('click')"
    class="fixed bottom-6 right-6 bg-primary text-white p-4 rounded-full shadow-lg hover:bg-secondary focus:outline-none focus:ring-2 focus:ring-primary focus:ring-opacity-50 transition-all duration-300"
  >
    <i class="ri-add-line text-2xl"></i>
  </button>
</template>

<script setup>
import { defineEmits } from 'vue';

// 'click' 이벤트를 부모 컴포넌트로 내보냅니다.
defineEmits(['click']);
</script>

<style scoped>
/* 이 컴포넌트에만 고유한 스타일이 필요하다면 여기에 추가합니다. */
/* 현재 HTML에서 제공된 스타일은 Tailwind 클래스이므로, */
/* 여기서는 특별히 추가할 것이 없습니다. */
</style>