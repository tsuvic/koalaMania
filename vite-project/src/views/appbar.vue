<script setup lang="ts">
import { ref, watchEffect } from 'vue';

const drawer = ref(false);
const items = [
  {
  title:"トップページ",
  value:"topPage",
  to:"/",
  },{
  title:"コアラマニアとは？",
  value:"description",
  to:"/description",
  },{
  title:"コアラを登録する",
  value:"animalRegister",
  to:"/animalRegister",
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
  <v-app-bar color="teal-darken-4">
    <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
  <v-btn variant="text" icon="mdi-account-lock"></v-btn>
  </v-app-bar>
  <v-navigation-drawer v-model="drawer" bottom temporary>
    <v-list>
      <v-list-item v-for="item in items" color="teal" :key="item.value" :to="item.to" :exact="true">
        <v-list-item-title>{{item.title}}</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>
