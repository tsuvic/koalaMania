<script setup lang="ts">
import axios from 'axios';
import { validate } from 'json-schema';
import { defineComponent, ref, onMounted, computed, watch } from 'vue';

const valid = ref< boolean | undefined >(false);
const mailRules = [
  (v: string) => !!v || 'メールアドレスを入力してください',
  (v: string) =>
    /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(v) ||
    'メールアドレスを正しく入力してください',
];
const pwRules = [
  (v: string) => !!v || 'パスワードを入力してください',
  (v: string) => v.length >= 6 || 'パスワードは6文字以上です',
];

const form = ref<{ validate: () => boolean }>()
valid.value = form.value?.validate();

</script>

<template>
  <v-card class="d-flex flex-column mx-auto flat outlined" width="374" height="650" color="#fff">
    <v-card-title class="d-flex justify-center mt-6"
      >ログイン</v-card-title
    >
    <v-card-text class="d-flex flex-column">
      <v-btn
        class="fill-width text-capitalize caption mx-4 m-2"
        rounded
        color="#00ACEE"
        dark
        depressed
        height="48px"
        link="true"
        href="/oauth/twitter/auth"
      >
        <img
          class="button-logo-img mr-4"
          src="../assets/202  1-Twitter-logo-white.png"
          style="height: 20px"
        />
        twitterでログイン
      </v-btn>
      <v-btn
        class="fill-width text-capitalize caption mx-4 m-2"
        rounded
        height="48px"
        outlined
        style="border-color: #979797"
        @click=""
      >
        <img
          class="button-logo-img mr-4"
          src="https://madeby.google.com/static/images/google_g_logo.svg"
          style="height: 24px"
        />
        Googleでログイン
      </v-btn>
      
      <p class="text-center mt-6 text-subtitle-1">
        メールアドレスでログイン
      </p>
      <v-form class="m-2" ref="form" v-model="valid">
        <v-text-field
          label="メールアドレス"
          :rules="mailRules"
          dense
          hide-details="auto"
        ></v-text-field>
        <v-text-field
          label="パスワード"
          :rules="pwRules"
          dense
          hide-details="auto"
        ></v-text-field>
        <div class="text-center m-4">
          <v-btn large primary block :disabled="!valid">ログイン</v-btn>
          <p class=" m-3" @click="">パスワードを忘れた方</p>
        </div>
      </v-form>
    </v-card-text>
  </v-card>
</template>