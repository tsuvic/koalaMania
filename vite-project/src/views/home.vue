<script setup>
  import { computed } from 'vue'
  import { useStore } from 'vuex'
  import { createVuetify } from 'vuetify'
  import * as components from 'vuetify/components'
  import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})

const store = useStore()
const name = computed(() => {
    return store.state.user.name
})

</script>

<style>
</style>

<template>
  <p class="">
    Virtual DOM : {{name}}
  </p>

<router-link to="/users">Axios</router-link>

  
</template>

