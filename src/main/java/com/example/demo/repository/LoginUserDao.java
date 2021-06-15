package com.example.demo.repository;

import com.example.demo.entity.LoginUser;

public interface LoginUserDao {
	LoginUser checkUser(twitter4j.User twitterUser);
	
	void insertUser(LoginUser insertUser);
	
	void updateProviderAdressAndLoginDate(LoginUser updateUser);
	
	void updateAutoLoginKey(String AutoLoginKey , LoginUser updateUser);
	
	LoginUser checkAutoLoginUser(String AutoLoginKey);
	
	void setLoginDate(LoginUser updateUser);
}
