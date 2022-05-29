import { createStore } from 'vuex'
//@ts-ignore
import user from './modules/user'

const store = createStore({
  modules: {
    user,
  }
})

export default store;