<script setup lang="ts">
import { computed, ref } from 'vue'
import Appbar from './views/appbar.vue';
import Toppage from './views/toppage.vue';
</script>

<template>
  <v-app>
    <v-main>
      <Appbar></Appbar>
      <Toppage></Toppage>
</v-main>
  </v-app>
</template>