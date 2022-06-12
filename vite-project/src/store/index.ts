import { createPinia } from "pinia"
import user from './modules/user'

const store = createPinia({
  modules: {
    user,
  }
})

export default store;