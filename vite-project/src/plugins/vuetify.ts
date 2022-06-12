// Vuetify
import { createVuetify } from 'vuetify'
import colors from 'vuetify/lib/util/colors'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: 'myCustomTheme1',
    themes: {
      myCustomTheme1: {
        dark: false,
        colors: {
          something: '#00ff00'
        },
      }
    },
  },
})
