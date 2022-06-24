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
    <p>Spring Securityで/loginを返すように設定し、エラーとする。</p>
    <p>必要に応じてフロント側のAuthページを返すように変更する。</p>
    <p class="">{{userId}}</p>
    <v-btn @click="store.getUser" class="m-2">Pinia Axios</v-btn>

    <br><br>
    <p>【テスト】Axios </p>
    <v-btn class="m-2">
      <router-link to="/users">Axios</router-link>
    </v-btn>
    <br>
  </div>
</template>