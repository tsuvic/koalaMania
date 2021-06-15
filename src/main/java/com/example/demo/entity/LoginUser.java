package com.example.demo.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUser extends org.springframework.security.core.userdetails.User {
	
	private int user_id;
	private String provider;
	private long provider_id;
	private String userName;
	private String provider_adress;
	private String profile;
	private String role;
	private int status;
	private Date loginDate;
	
	public LoginUser(String userName, String password, String authorities,int user_id,String provider,long provider_id,String provider_adress,String profile,int status) {
		super(userName, password, AuthorityUtils.createAuthorityList(authorities));
		// TODO 自動生成されたコンストラクター・スタブ
		this.user_id = user_id;
		this.provider = provider;
		this.provider_id = provider_id;
		this.userName = userName;
		this.provider_adress = provider_adress;
		this.profile = profile;
		this.role = authorities;
		this.status = status;
	}

	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO 自動生成されたコンストラクター・スタブ
	}

}
