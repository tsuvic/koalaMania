<script setup lang="ts">
import { computed, ref } from 'vue'
import appbar from './views/appbar.vue';
import navigationdrawer from './views/navigationdrawer.vue';
import home from './views/home.vue';

</script>

<template>
  <v-app>
    <v-main>
      <appbar></appbar>
      <home></home>
    </v-main>
  </v-app>
</template>