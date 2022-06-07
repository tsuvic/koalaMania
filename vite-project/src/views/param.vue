<script setup lang="ts">
import { ref, watchEffect, computed } from 'vue';
import { useStore } from 'vuex';

const store = useStore()
const name = computed(() => {
    return store.state.user.name
})
</script>

<template>
  <div class="param">
    <h1>test</h1>
    <p>{{ $route.params.param1 }}</p>
    <p>{{ $route.params.param2 }}</p>
    <p class="">Virtual DOM : {{name}}</p>
    <router-link to="/users">Axios</router-link>
  </div>
</template>