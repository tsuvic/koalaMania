package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.entity.LoginUser;

import twitter4j.User;

public interface TwitterLoginService {
	LoginUser userLogin(User twitterUser,HttpServletResponse response,HttpServletRequest request);
	
	void setCookie(LoginUser loginUser,HttpServletResponse response,HttpServletRequest request);
	
	String[] getHashedKeys(LoginUser loginUser);
}
