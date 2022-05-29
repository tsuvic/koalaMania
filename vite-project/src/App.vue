<template>
  <v-app>
    <v-main>
      <div id="nav">
        <router-link to="/">Home</router-link> |
        <router-link to="/param/1/2">param</router-link> |
        <router-link to="/auth">auth</router-link> |
        <router-link to="/inbox">Inbox</router-link> |
        <router-link to="/adfnsdofjna">ERROR</router-link>
      </div>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'App',

  data () {
    return {
      msg: 'Hello Vue!'
    }
  },
})
</script>
