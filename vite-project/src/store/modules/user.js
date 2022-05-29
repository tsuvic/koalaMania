export default{
  state: () => ({
    name : 'tsuvi'
  }),
  getters: {},
  mutations: {
    SET_NAME(state, payload){
      state.name = payload
    }
  },
  actions:{
    setName({commit}, data){
      commit('SET_NAME', data)
    },
  },
}