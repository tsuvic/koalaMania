import http from '../service/http';

class usersApiService {
  getUser(count :any): Promise<any> {
    return http.get('/api/users/' + count.value);
  }

  test2 () {
    return http.get('/api/users/checkAuthenticated');
  }
}

export default new usersApiService();