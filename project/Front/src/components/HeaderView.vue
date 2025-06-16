<template>
  <header class="w-full py-4 px-6 bg-white shadow-sm flex justify-between items-center">
    <div class="text-primary text-2xl font-['Pacifico']">NutriTrack</div>
    <div class="flex items-center gap-6">
      <a href="#" class="text-gray-600 hover:text-primary flex items-center gap-1">
        <div class="w-5 h-5 flex items-center justify-center">
          <i class="ri-history-line"></i>
        </div>
        <span>History</span>
      </a>
      <router-link to="/SearchFood">testFood</router-link>

      <a href="#" class="text-gray-600 hover:text-primary flex items-center gap-1">
        <div class="w-5 h-5 flex items-center justify-center">
          <i class="ri-user-line"></i>
        </div>
        <span>Profile</span>
      </a>
    </div>
  </header>
</template>

<style>
:where([class^='ri-'])::before {
  content: '\f3c2';
}
body {
  background-color: #f9fafb;
  color: black;
}
input:focus {
  outline: none;
}
input[type='number']::-webkit-inner-spin-button,
input[type='number']::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>
