<script setup lang="ts">
import { ref, watchEffect } from 'vue';

const drawer = ref(false);
const items = [
  {
  title:"home",
  value:"ホーム画面"
  },{
  title:"users",
  value:"ユーザー一覧"
  },
]


</script>

<style>
</style>

<template>
  <v-app-bar color="primary">
    <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
    <v-btn variant="text" icon="mdi-dots-vertical"></v-btn>
  </v-app-bar>      
  <v-navigation-drawer v-model="drawer" bottom temporary>
    <v-list :items="items"></v-list>
  </v-navigation-drawer>
</template>
