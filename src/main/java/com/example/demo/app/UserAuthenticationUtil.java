package com.example.demo.app;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.LoginUser;

@Component
public class UserAuthenticationUtil {
	
	
	
	/**
	 * ユーザーが認証されているか確認するためのメソッド
	 * 認証されている場合LoginUserを返し、されていない場合nullを返す
	 * @return LoginUser or null
	 */
	public LoginUser isUserAuthenticated() {
		if(!(SecurityContextHolder.getContext().getAuthentication() 
				instanceof AnonymousAuthenticationToken)) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return ((LoginUser) principal);
		}else {
			return null;
		}
	}

}
