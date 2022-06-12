export default{
  key: 'user',

  state: () => ({
    userId: '',
    name : '',
    profile: '',
    twitterLinkFlag: '',
    address: '',
    profileImagePath: '',
    tmpProfileImage: '',
    userProfileImageUpload: '',
  }),

  getters: {
    setName(){
      return 
    }
  },
  
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