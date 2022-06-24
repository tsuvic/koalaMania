package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class LoginUser extends org.springframework.security.core.userdetails.User {

	@JsonIgnore
	public final String TABLE_NAME = "login_user";

	@JsonIgnore
	public final String COLUMN_USER_ID = "user_id";

	@JsonIgnore
	public final String COLUMN_PROVIDER = "provider";

	@JsonIgnore
	public final String COLUMN_PROVIDER_ID = "provider_id";

	@JsonIgnore
	public final String COLUMN_PROVIDER_ADRESS = "provider_adress";

	@JsonIgnore
	public final String COLUMN_USER_NAME = "user_name";

	@JsonIgnore
	public final String COLUMN_PROFILE = "profile";

	@JsonIgnore
	public final String COLUMN_ROLE = "role";

	@JsonIgnore
	public final String COLUMN_STATUS = "status";

	@JsonIgnore
	public final String COLUMN_PROFILE_IMAGE_PATH = "profileimagepath";

	@JsonIgnore
	public final String COLUMN_TWITTER_LINK_FLAG = "twitterlinkflag";

	@JsonIgnore
	public final String COLUMN_AUTO_LOGIN = "auto_login";

	@JsonIgnore
	public final String COLUMN_LOGIN_DATE = "login_date";

	@JsonIgnore
	private static final String dummyPassword = "password";

	@JsonIgnore
	private static final String dummyUser = "user";

	@JsonIgnore
	private static final String ROLE_USER = "ROLE_USER";
	
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

	public LoginUser(String userName, String password, String authorities,int user_id,
			String provider,long provider_id,String provider_adress,String profile,
			int status,String profileImagePath, boolean twitterLinkFlag) {
		super(userName, password, AuthorityUtils.createAuthorityList(authorities));
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
	public LoginUser() {
		super(dummyUser, dummyPassword, AuthorityUtils.createAuthorityList(ROLE_USER));
	}
}
