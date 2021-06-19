package com.example.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginUser;

@Repository
public class LoginUserDaoImple implements LoginUserDao {

	private final JdbcTemplate jdbcTemplate;
	private final String PROVIDER_TWITTER = "twitter"; //有効
	private final int STATUS_OK = 0; //有効
	private final int STATUS_NG = 1; //無効
	private final String dummyPassword = "password";
	private final String ROLE_USER = "ROLE_USER";
	private final String ROLE_ADMIN = "ROLE_ADMIN";
	
	@Autowired
	public LoginUserDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public LoginUser checkUser(twitter4j.User twitterUser) {
		String sql = "SELECT * FROM  login_user WHERE provider = ? AND provider_id = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,PROVIDER_TWITTER,twitterUser.getId());
		if(resultList.size() < 1) {
			LoginUser loginUser = new LoginUser(twitterUser.getName(), dummyPassword, ROLE_USER, 0, null, 0, null, null, 0);
			loginUser.setProvider(PROVIDER_TWITTER);
			loginUser.setProvider_id(twitterUser.getId());
			loginUser.setProfile(null);
			loginUser.setProvider_adress(twitterUser.getScreenName());
			loginUser.setStatus(STATUS_OK);
			insertUser(loginUser);
			
			setLoginDate(loginUser);
			
			return loginUser;
		} else {
			Map<String, Object> getUserMap = resultList.get(0);
			LoginUser loginUser = new LoginUser((String) getUserMap.get("user_name"), dummyPassword, (String) getUserMap.get("role"), (int) getUserMap.get("user_id"), (String) getUserMap.get("provider"), (long) getUserMap.get("provider_id"), twitterUser.getScreenName(), (String) getUserMap.get("profile"), (int) getUserMap.get("user_id"));
			updateProviderAdressAndLoginDate(loginUser);
			
			return loginUser;
		}	
	}
	
	@Override
	public void insertUser(LoginUser insertUser) {
		Map<String, Object> insertId = jdbcTemplate.queryForMap(
				"INSERT INTO login_user(provider, provider_id, provider_adress, user_name, role, status, login_date, created_date, updated_date) VALUES(?, ?, ?, ?, ? , ?, now(), now(), now()) RETURNING user_id",
				PROVIDER_TWITTER, insertUser.getProvider_id(), insertUser.getProvider_adress(), insertUser.getUserName(),  insertUser.getRole(),insertUser.getStatus());
				insertUser.setUser_id((int) insertId.get("user_id"));
	};
	
	@Override
	public void updateProviderAdressAndLoginDate(LoginUser updateUser) {
		jdbcTemplate.update(
				"UPDATE login_user SET provider_adress=?,login_date=now() WHERE user_id = ?",
				updateUser.getProvider_adress(), updateUser.getUser_id());
		
		setLoginDate(updateUser);
	}
	
	@Override
	public void updateAutoLoginKey(String AutoLoginKey ,  LoginUser updateUser) {
		jdbcTemplate.update(
				"UPDATE login_user SET auto_login = ? , updated_date=now() WHERE user_id = ?",
				AutoLoginKey , updateUser.getUser_id());
	}
	
	@Override
	public LoginUser checkAutoLoginUser(String AutoLoginKey) {
		String sql = "SELECT * FROM  login_user WHERE auto_login = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,AutoLoginKey);
		if(resultList.size() < 1) {
			
			return null;
		} else {
			Map<String, Object> getUserMap = resultList.get(0);
			LoginUser loginUser = new LoginUser((String) getUserMap.get("user_name"), dummyPassword, (String) getUserMap.get("role"), (int) getUserMap.get("user_id"), (String) getUserMap.get("provider"), (long) getUserMap.get("provider_id"), (String) getUserMap.get("provider_adress"), (String) getUserMap.get("profile"), (int) getUserMap.get("user_id"));
			setLoginDate(loginUser);
			
			return loginUser;
		}	
	}
	
	@Override
	public void setLoginDate(LoginUser updateUser) {
		String sql = "SELECT login_date FROM login_user WHERE user_id = ?";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql,updateUser.getUser_id());
		Date loginDate = (Date) resultMap.get("login_date");
		updateUser.setLoginDate(loginDate);
	}

}
