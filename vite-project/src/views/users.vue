<script setup lang="ts">
  import { computed, ref } from 'vue'
  import { useStore } from 'vuex'
  import { defineComponent } from 'vue'
  import { createVuetify } from 'vuetify'
  import * as components from 'vuetify/components'
  import * as directives from 'vuetify/directives'
  import usersApiService from '../service/usersApiService';
  
  const vuetify = createVuetify({
    components,
    directives,
  })

  const store = useStore()
  const name = computed(() => {
      return store.state.user.name
  })

  const count = ref(0);
  const increment = () => {
    count.value++;
  }
  const decrement = () => {
    count.value--;
  }


  const posts = ref([]);
  const axios = () => {
    usersApiService.getUser(count)
      .then(res => {posts.value = res.data;})
      .catch(err => {console.log(err);});
  }


</script>

<template>
    <br>
    <v-btn @click="increment" class="m-2">Increment</v-btn>
    <v-btn @click="decrement" class="m-2">Decrement</v-btn>
    <v-btn @click="axios" class="m-2">Axsios</v-btn>
    <br>
    <h1 class="m-2">{{count}}</h1>
    <br>
    <p class="m-2"> axios結果 : {{posts}}</p>
    <br>



</template>

