import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vuetify from '@vuetify/vite-plugin'

const path = require('path')

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vite-plugin
    vuetify({
      autoImport: true,
    }),
  ],

  define: { 'process.env': {} },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  
  build: {
    outDir: '../src/main/resources/static',
    assetsDir: './assets',
    rollupOptions: {
      external: [
      ],
    },
  },

  server: {
    host: '127.0.0.1',
    port: 3002,
    proxy: {
      '/api': { 
        target: 'http://127.0.0.1:8080',
        changeOrigin: true,
        secure: false,
      },
      
    }
  },

  /* remove the need to specify .vue files https://vitejs.dev/config/#resolve-extensions
  resolve: {
    extensions: [
      '.js',
      '.json',
      '.jsx',
      '.mjs',
      '.ts',
      '.tsx',
      '.vue',
    ]
  },
  */
})
