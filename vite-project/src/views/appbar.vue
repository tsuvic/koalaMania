<script setup lang="ts">
import { ref, watchEffect } from 'vue';

const drawer = ref(false);
const items = [
  {
  title:"トップページ",
  value:"topPage"
  },{
  title:"コアラマニアとは？",
  value:"description"
  },{
  title:"コアラを登録する",
  value:"animalRegister"
  },{
  title:"コアラを閲覧する",
  value:"animalList"
  },{
  title:"写真を投稿する",
  value:"animalPost"
  },{
  title:"問い合わせ",
  value:"QA"
  },
]


</script>

<style>
</style>

<template>
  <v-app-bar color="primary">
    <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
  <v-btn variant="text" icon="mdi-account-lock"></v-btn>
  </v-app-bar>
  <v-navigation-drawer v-model="drawer" bottom temporary>
    <v-list :items="items"></v-list>
  </v-navigation-drawer>
</template>
