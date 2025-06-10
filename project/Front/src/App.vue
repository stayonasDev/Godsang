<template>
  <div class="app-layout-vertical">
    <header class="app-header">
      <HeaderView />
    </header>
    <main class="main-content-vertical">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import HeaderView from './components/HeaderView.vue'
</script>

<style scoped>
.body {
  width: auto;
  height: auto;
}

.app-layout-vertical {
  width: 1300px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.main-content-vertical {
  flex-grow: 1;
  overflow-y: auto;
  padding: 20px;
  box-sizing: border-box;
}
</style>
