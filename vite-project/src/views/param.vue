<script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { user } from '../store/user';

  const store = user()
  const { userId } = storeToRefs(store)
  
</script>

<template>
  <div class="param">
    <br>
    <p>【テスト】動的ルーティング</p>
    <p>{{ $route.params.param1 }}</p>
    <p>{{ $route.params.param2 }}</p>

    <br>
    <p>【テスト】Pinia</p>
    <p class="">{{userId}}</p>
    <v-btn @click="store.getUser" class="m-2">Pinia Axios</v-btn>

    <br>
    <p>【テスト】Axios</p>
    <router-link to="/users">Axios</router-link>
  </div>
</template>