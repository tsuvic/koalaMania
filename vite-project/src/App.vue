<script setup lang="ts">
import { computed, ref } from 'vue'
import appbar from './views/appbar.vue';
import home from './views/home.vue';
</script>

<template>
  <v-app>
    <v-main>
      <appbar></appbar>
      <home></home>
      <v-btn color="something">ボタン</v-btn>
      <v-btn color="secondary">ボタン</v-btn>
      <v-btn color="error">ボタン</v-btn>
</v-main>
  </v-app>
</template>