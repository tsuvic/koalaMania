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
	private CommonSqlUtil commonSqlUtil;
	
	private final  LoginUser ENTITY_LOGIN_USER = new LoginUser(dummyPassword, dummyPassword, ROLE_USER, 0, null, 0, null, null, 0 ,null,true);
	
	@Autowired
	public LoginUserDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public LoginUser checkUser(twitter4j.User twitterUser) {
		String sql = "SELECT * FROM  " + ENTITY_LOGIN_USER.TABLE_NAME + " WHERE " + ENTITY_LOGIN_USER.COLUMN_PROVIDER + " = ? AND " + ENTITY_LOGIN_USER.COLUMN_PROVIDER_ID + " = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,PROVIDER_TWITTER,twitterUser.getId());
		if(resultList.size() < 1) {
			LoginUser checkLoginUser = new LoginUser(twitterUser.getName(), dummyPassword, ROLE_USER, 0, null, 0, null, null, 0 , null ,true);
			checkLoginUser.setProvider(PROVIDER_TWITTER);
			checkLoginUser.setProvider_id(twitterUser.getId());
			checkLoginUser.setProfile(null);
			checkLoginUser.setProvider_adress(twitterUser.getScreenName());
			checkLoginUser.setStatus(STATUS_OK);
			insertUser(checkLoginUser);
			
			setLoginDate(checkLoginUser);
			
			return checkLoginUser;
		} else {
			LoginUser checkLoginUser = getLoginUser(resultList.get(0));
			updateProviderAdressAndLoginDate(checkLoginUser);
			
			return checkLoginUser;
		}	
	}
	
	@Override
	public void insertUser(LoginUser insertUser) {
		Map<String, Object> insertId = jdbcTemplate.queryForMap(
				"INSERT INTO " + ENTITY_LOGIN_USER.TABLE_NAME + "(" + ENTITY_LOGIN_USER.COLUMN_PROVIDER + "," + ENTITY_LOGIN_USER.COLUMN_PROVIDER_ID + "," +  ENTITY_LOGIN_USER.COLUMN_PROVIDER_ADRESS + ", " +  ENTITY_LOGIN_USER.COLUMN_USER_NAME + ", " +  ENTITY_LOGIN_USER.COLUMN_ROLE + ", " +  ENTITY_LOGIN_USER.COLUMN_STATUS + ", " +  ENTITY_LOGIN_USER.COLUMN_LOGIN_DATE + ") VALUES(?, ?, ?, ?, ? , ?, now()) RETURNING " +  ENTITY_LOGIN_USER.COLUMN_USER_ID,
				PROVIDER_TWITTER, insertUser.getProvider_id(), insertUser.getProvider_adress(), insertUser.getUserName(),  insertUser.getRole(),insertUser.getStatus());
				insertUser.setUser_id((int) insertId.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
				commonSqlUtil.updateAllCommonColumn(ENTITY_LOGIN_USER.TABLE_NAME,ENTITY_LOGIN_USER.COLUMN_USER_ID ,(int) insertId.get(ENTITY_LOGIN_USER.COLUMN_USER_ID),(int) insertId.get(ENTITY_LOGIN_USER.COLUMN_USER_ID));
	}
	
	@Override
	public void updateProviderAdressAndLoginDate(LoginUser updateUser) {
		jdbcTemplate.update(
				"UPDATE " + ENTITY_LOGIN_USER.TABLE_NAME + " SET " +  ENTITY_LOGIN_USER.COLUMN_PROVIDER_ADRESS + "=?," +  ENTITY_LOGIN_USER.COLUMN_LOGIN_DATE + "=now() WHERE " +  ENTITY_LOGIN_USER.COLUMN_USER_ID + " = ?",
				updateUser.getProvider_adress(), updateUser.getUser_id());
		
		setLoginDate(updateUser);
	}
	
	@Override
	public void updateAutoLoginKey(String AutoLoginKey ,  LoginUser updateUser) {
		jdbcTemplate.update(
				"UPDATE " + ENTITY_LOGIN_USER.TABLE_NAME + " SET " +  ENTITY_LOGIN_USER.COLUMN_AUTO_LOGIN + " = ?  WHERE " +  ENTITY_LOGIN_USER.COLUMN_USER_ID + " = ?",
				AutoLoginKey , updateUser.getUser_id());
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_LOGIN_USER.TABLE_NAME , ENTITY_LOGIN_USER.COLUMN_USER_ID , updateUser.getUser_id(),updateUser.getUser_id());
	}
	
	@Override
	public LoginUser checkAutoLoginUser(String AutoLoginKey) {
		String sql = "SELECT * FROM  " + ENTITY_LOGIN_USER.TABLE_NAME + " WHERE " +  ENTITY_LOGIN_USER.COLUMN_AUTO_LOGIN + " = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,AutoLoginKey);
		if(resultList.size() < 1) {
			
			return null;
		} else {
			LoginUser loginUser = getLoginUser(resultList.get(0));
			
			setLoginDate(loginUser);
			
			return loginUser;
		}	
	}
	
	@Override
	public void setLoginDate(LoginUser updateUser) {
		String sql = "SELECT " + ENTITY_LOGIN_USER.COLUMN_LOGIN_DATE + " FROM " + ENTITY_LOGIN_USER.TABLE_NAME + " WHERE " + ENTITY_LOGIN_USER.COLUMN_USER_ID + " = ?";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql,updateUser.getUser_id());
		Date loginDate = (Date) resultMap.get(ENTITY_LOGIN_USER.COLUMN_LOGIN_DATE);
		updateUser.setLoginDate(loginDate);
	}
	
	@Override
	public LoginUser findById(int user_id) {
		String sql = "SELECT * FROM  " + ENTITY_LOGIN_USER.TABLE_NAME + " WHERE " +  ENTITY_LOGIN_USER.COLUMN_USER_ID + " = ?";
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql,user_id);
		
		return getLoginUser(resultMap);
	}
	
	@Override
	public LoginUser updateMyPage(LoginUser loginUser) {
		jdbcTemplate.update(
				"UPDATE " + ENTITY_LOGIN_USER.TABLE_NAME + " SET " +  ENTITY_LOGIN_USER.COLUMN_USER_NAME + "=?," +  ENTITY_LOGIN_USER.COLUMN_PROFILE + "=?," +  ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH + "=? ," +  ENTITY_LOGIN_USER.COLUMN_TWITTER_LINK_FLAG + "=? WHERE " +  ENTITY_LOGIN_USER.COLUMN_USER_ID + " = ?",
				loginUser.getUserName(), loginUser.getProfile(), loginUser.getProfileImagePath() , loginUser.isTwitterLinkFlag() ,loginUser.getUser_id());
		
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_LOGIN_USER.TABLE_NAME , ENTITY_LOGIN_USER.COLUMN_USER_ID , loginUser.getUser_id(),loginUser.getUser_id());
		
		return findById(loginUser.getUser_id());
	}
	
	public LoginUser getLoginUser(Map<String,Object>getUserMap) {
		
		LoginUser loginUser = new LoginUser((String) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_USER_NAME), 
				dummyPassword, (String) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_ROLE), 
				(int) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_USER_ID), 
				(String) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_PROVIDER), 
				(long) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_PROVIDER_ID), 
				(String) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_PROVIDER_ADRESS), 
				(String) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_PROFILE), 
				(int) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_STATUS),
				(String) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_PROFILE_IMAGE_PATH),
				(boolean) getUserMap.get(ENTITY_LOGIN_USER.COLUMN_TWITTER_LINK_FLAG));
		
		return loginUser;
	}

}
