import { defineStore } from 'pinia';
import usersApiService from '../service/usersApiService';

export const user = defineStore('user', {
  state: () => {
    return {
      userId: '',
      // name : '',
      // profile: '',
      // twitterLinkFlag: '',
      // address: '',
      // profileImagePath: '',
      // tmpProfileImage: '',
      // userProfileImageUpload: '',
    }
  },
  
  actions: {
    async getUser() {
      await usersApiService.test2()
      .then(res => {
        const user = res.data;
      })
      .catch(err => {
        console.log(err)
        if (JSON.parse(err.response.status) === 404) {
          window.location.href = '/auth';
        }
      });
      
      
      
    }

  }
})