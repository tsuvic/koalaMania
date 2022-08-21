package com.example.demo.service;

import java.time.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserDao;

import twitter4j.User;

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
	public LoginUser checkUserApi(long twitterId, HttpServletResponse response,HttpServletRequest request) {
		
		LoginUser loginUser = loginUserDao.checkUserApi(twitterId);
		
		setCookie(loginUser,response,request);
	    
		return loginUser;
	}
	
	@Override
	public void setCookie(LoginUser loginUser,HttpServletResponse response,HttpServletRequest request) {
		
		String[] hashedKeys = getHashedKeys(loginUser);

		/* https://qiita.com/nannou/items/fc86d052e356e095fcbf */
		/* https://blog.cybozu.io/entry/2020/05/07/080000 */
		ResponseCookie responseCookie = ResponseCookie
				.from("autoLogin", hashedKeys[0])
				.domain(request.getServerName())
				.maxAge(Duration.ofDays(30))
				.path("/")
				.httpOnly(true)
				.secure(true)
				.sameSite("Lax").build();
		response.addHeader("Set-Cookie", responseCookie.toString());
	    
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
