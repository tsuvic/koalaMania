import http from '../service/http';

class usersApiService {
  getUser(count :any): Promise<any> {
    return http.get('/api/users/' + count.value);
  }
}

export default new usersApiService();