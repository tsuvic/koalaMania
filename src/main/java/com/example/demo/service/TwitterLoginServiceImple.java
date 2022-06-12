package com.example.demo.service;

import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import twitter4j.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TwitterLoginServiceImple implements TwitterLoginService {
	
	private final LoginUserDao loginUserDao;
	
	@Autowired
    @Qualifier("spring.social.twitter.autoLoginKey")
	private String autoLoginKey;

	public TwitterLoginServiceImple(LoginUserDao userDao) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.loginUserDao = userDao;
	}

	@Override
	public LoginUser userLogin(User twitterUser ,HttpServletResponse response,HttpServletRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		LoginUser loginUser = loginUserDao.checkUser(twitterUser);
		
		setCookie(loginUser,response,request);
	    
		return loginUser;
	}
	
	@Override
	public void setCookie(LoginUser loginUser,HttpServletResponse response,HttpServletRequest request) {
		
		String[] hashedKeys = getHashedKeys(loginUser);
		
		
		Cookie cookie = new Cookie("autoLogin", hashedKeys[0]); // Cookieの作成
	    cookie.setMaxAge(1 * 24 * 60 * 60); // Cookieの残存期間（秒数）30日に設定
	    cookie.setPath("/");
	    if(request.getScheme().equals("https")) {
	    	cookie.setSecure(true);
	    }
	    cookie.setHttpOnly(true);
	    response.addCookie(cookie);
	    
	    loginUserDao.updateAutoLoginKey(hashedKeys[1],loginUser);
	    
	    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, null,
				AuthorityUtils.createAuthorityList(loginUser.getRole()));
		SecurityContextHolder.getContext().setAuthentication(token);
	}
	
	@Override
	public String[] getHashedKeys(LoginUser loginUser) {
		String[] hashedKeys = new String[2];		
		
		hashedKeys[0] = DigestUtils.sha3_256Hex(autoLoginKey + loginUser.getProvider_adress() + loginUser.getLoginDate());
		hashedKeys[1] = DigestUtils.sha3_256Hex(hashedKeys[0]);
		
		return hashedKeys;
	}
}
