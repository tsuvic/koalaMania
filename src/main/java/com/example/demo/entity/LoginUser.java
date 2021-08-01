package com.example.demo.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class LoginUser extends org.springframework.security.core.userdetails.User {

	public final String TABLE_NAME = "login_user";
	
	public final String COLUMN_USER_ID = "user_id";
	public final String COLUMN_PROVIDER = "provider";
	public final String COLUMN_PROVIDER_ID = "provider_id";
	public final String COLUMN_PROVIDER_ADRESS = "provider_adress";
	public final String COLUMN_USER_NAME = "user_name";
	public final String COLUMN_PROFILE = "profile";
	public final String COLUMN_ROLE = "role";
	public final String COLUMN_STATUS = "status";
	public final String COLUMN_PROFILE_IMAGE_PATH = "profileimagepath";
	public final String COLUMN_TWITTER_LINK_FLAG = "twitterlinkflag";
	public final String COLUMN_AUTO_LOGIN = "auto_login";
	public final String COLUMN_LOGIN_DATE = "login_date";
	
	private int user_id;
	private String provider;
	private long provider_id;
	private String userName;
	private String provider_adress;
	private String profile;
	private String role;
	private String profileImagePath;
	private boolean twitterLinkFlag;
	private int status;
	private Date loginDate;
	
	private static final String dummyPassword = "password";
	private static final String ROLE_USER = "ROLE_USER";
	
	public LoginUser(String userName, String password, String authorities,int user_id,
			String provider,long provider_id,String provider_adress,String profile,
			int status,String profileImagePath, boolean twitterLinkFlag) {
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
		this.profileImagePath = profileImagePath;
		this.twitterLinkFlag = twitterLinkFlag;
	}

	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public LoginUser() {
		super(dummyPassword, dummyPassword, AuthorityUtils.createAuthorityList(ROLE_USER));
	}

}
