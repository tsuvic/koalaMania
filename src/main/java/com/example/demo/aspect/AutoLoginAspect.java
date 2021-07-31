package com.example.demo.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserDao;
import com.example.demo.service.TwitterLoginService;

@Aspect
@Component
public class AutoLoginAspect {
	private final LoginUserDao loginUserDao;
	private final TwitterLoginService twitterLoginService;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserAuthenticationUtil userAuthenticationUtil;
	
	public AutoLoginAspect(LoginUserDao userDao , TwitterLoginService twitterLoginService) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.loginUserDao = userDao;
		this.twitterLoginService = twitterLoginService;
	}
	
	@Before("within(com.example.demo.app.AnimalController) or within(com.example.demo.app.UserController) or within(com.example.demo.app.PostController)")
	public void CheckAutoLoginAspect(){
		javax.servlet.http.Cookie[] cookies =  request.getCookies();
		String autoLogin = null;
		if(cookies != null) {
			for (javax.servlet.http.Cookie cookie : cookies) {
                if ("autoLogin".equals(cookie.getName())) {
                	autoLogin = cookie.getValue();
                    break;
                }
            }
		}
		
		if(userAuthenticationUtil.isUserAuthenticated() == null && autoLogin != null) {
			LoginUser loginUser  = loginUserDao.checkAutoLoginUser(DigestUtils.sha3_256Hex(autoLogin));
			if(loginUser != null) {
				twitterLoginService.setCookie(loginUser, response,request);
				loginUserDao.updateProviderAdressAndLoginDate(loginUser);
			}
		}
	}

}
