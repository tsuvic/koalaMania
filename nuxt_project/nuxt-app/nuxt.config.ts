import { defineNuxtConfig } from 'nuxt'

// https://v3.nuxtjs.org/api/configuration/nuxt.config
export default defineNuxtConfig({
    app: {
        head: {
          meta: [
            // <meta name="viewport" content="width=device-width, initial-scale=1">
            { name: 'viewport', content: 'width=device-width, initial-scale=1' }
          ],
          script: [
            // <script src="https://myawesome-lib.js"></script>
            { src: '/assets/js/jquery-slim.min.js' },
            { src: '/assets/js/popper.min.js' },
            { src: '/assets/js/bootstrap.min.js' }
          ],
          link: [
            // <link rel="stylesheet" href="https://myawesome-lib.css">
            { rel: 'stylesheet', href: '/assets/css/bootstrap.min.css' },
            { rel: 'stylesheet', href: '/assets/css/starter-template.css' },
            { rel: 'stylesheet', href: '/assets/css/background.css' },
            { rel: 'stylesheet', href: '/assets/css/common.css' },
            { rel: 'stylesheet', href: '/assets/css/index.css' },
            { rel: 'stylesheet', href: '/assets/css/searchResult.css' }
          ]
      }
    }
})
