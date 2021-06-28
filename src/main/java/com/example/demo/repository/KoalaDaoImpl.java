package com.example.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaForTree;
import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.KoalaZooHistory;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Prefecture;
import com.example.demo.entity.Zoo;

@Repository
public class KoalaDaoImpl implements KoalaDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Koala ENTITY_KOALA;
	
	@Autowired
	private KoalaImage ENTITY_KOALA_IMAGE;
	
	@Autowired
	private KoalaZooHistory ENTITY_KOALA_ZOO_HISTORY;
	
	@Autowired
	private Zoo ENTITY_ZOO;
	
	@Autowired
	private Prefecture ENTITY_PREFECTURE;
	
	@Autowired
	private CommonSqlUtil commonSqlUtil;
	
	@Autowired
	public KoalaDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	String AsMainKoala = "mainKoala";
	String AsMotherKoala = "motherKoala";
	String AsFatherKoala = "fatherKoala";
	String AsMotherName = "mother_name";
	String AsFatherName = "father_name";
	String AsMotherId = "mother_id";
	String AsFatherId = "father_id";
	String dummyDate = "9999-01-01";
	String man = "1";
	String woman = "2";

	@Override
	public List<Koala> getAll() {
		String sql = "SELECT "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_NAME +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_SEX +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +" , "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMainKoala +" LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' "
				+ "ORDER BY "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" ASC";

		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		// view返却用のリストを生成
		List<Koala> list = new ArrayList<Koala>();
		// MAP型リストからMapを繰り返し出力し、MapのバリューObjectをKoalaインスタンスに詰め込む
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));

			// Koalaインスタンスをview返却用のリストに詰め込んでいく
			koala.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
			koala.setSex((int) result.get(ENTITY_KOALA.COLUMN_SEX));
			koala.setBirthdate((Date) result.get(ENTITY_KOALA.COLUMN_BIRTHDATE));
			koala.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			koala.setMother((String) result.get(AsMotherName));
			koala.setFather((String) result.get(AsFatherName));
			koala.setProfileImagePath((String)result.get(ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE));
			list.add(koala);
		}
		return list;
	}

	@Override
	public List<Koala> getMotherList(int koala_id, Date birthDay) {
		String sql = "SELECT "+ ENTITY_KOALA.COLUMN_KOALA_ID +","+ ENTITY_KOALA.COLUMN_NAME +" FROM "+ ENTITY_KOALA.TABLE_NAME +" WHERE "+ ENTITY_KOALA.COLUMN_KOALA_ID +" NOT IN (?) AND "+ ENTITY_KOALA.COLUMN_SEX +" = "+ woman +"  AND "+ ENTITY_KOALA.COLUMN_BIRTHDATE +" < ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, koala_id, birthDay);
		List<Koala> motherList = new ArrayList<Koala>();
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));
			koala.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
			motherList.add(koala);
		}
		return motherList;
	}

	@Override
	public List<Koala> getFatherList(int koala_id, Date birthDay) {
		String sql = "SELECT "+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ ENTITY_KOALA.COLUMN_NAME +" FROM "+ ENTITY_KOALA.TABLE_NAME +" WHERE "+ ENTITY_KOALA.COLUMN_KOALA_ID +" NOT IN (?) AND "+ ENTITY_KOALA.COLUMN_SEX +" = "+ man + " AND "+ ENTITY_KOALA.COLUMN_BIRTHDATE +" < ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, koala_id, birthDay);
		List<Koala> fatherList = new ArrayList<Koala>();
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));
			koala.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
			fatherList.add(koala);
		}
		return fatherList;
	}

	@Override
	public List<Koala> findByKeyword(String keyword) {

		String[] splitkeyWord = keyword.replaceAll(" ", "　").split("　", 0);

		String sql = "SELECT "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_NAME +", "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_SEX +","+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +" , "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +" ON "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' ";

		for (int i = 0; i < splitkeyWord.length; ++i) {
			sql += "AND ("+ ENTITY_KOALA.TABLE_NAME + "."+ ENTITY_KOALA.COLUMN_NAME +" like '%" + splitkeyWord[i] + "%' OR "+ ENTITY_ZOO.COLUMN_ZOO_NAME +" like '%" + splitkeyWord[i]
					+ "%' OR "+ ENTITY_KOALA.COLUMN_MOTHER +"."+ ENTITY_KOALA.COLUMN_NAME +" like '%" + splitkeyWord[i] + "%' OR "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" like '%" + splitkeyWord[i]
					+ "%')";
		}

		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		// view返却用のリストを生成
		List<Koala> list = new ArrayList<Koala>();
		// MAP型リストからMapを繰り返し出力し、MapのバリューObjectをKoalaインスタンスに詰め込む
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));

			// Koalaインスタンスをview返却用のリストに詰め込んでいく
			koala.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
			koala.setSex((int) result.get(ENTITY_KOALA.COLUMN_SEX));
			koala.setBirthdate((Date) result.get(ENTITY_KOALA.COLUMN_BIRTHDATE));
			koala.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			koala.setMother((String) result.get(AsMotherName));
			koala.setFather((String) result.get(AsFatherName));
			koala.setProfileImagePath((String) result.get(ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE));
			list.add(koala);
		}
		return list;
	}

	@Override
	public List<Zoo> getZooList() {
		String sql = "SELECT "+ ENTITY_ZOO.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +" FROM "+ ENTITY_ZOO.TABLE_NAME +" ORDER BY "+ ENTITY_ZOO.COLUMN_ZOO_ID +" ASC";
		List<Map<String, Object>> resultZooList = jdbcTemplate.queryForList(sql);
		List<Zoo> zooList = new ArrayList<Zoo>();
		for (Map<String, Object> result : resultZooList) {
			Zoo zoo = new Zoo();
			zoo.setZoo_id((int) result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
			zoo.setZoo_name((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			zooList.add(zoo);
		}
		return zooList;
	}

	@Override
	public int insert(Koala koala) {
		Map<String, Object> insertId = jdbcTemplate.queryForMap(
				"INSERT INTO "+ ENTITY_KOALA.TABLE_NAME +"("+ ENTITY_KOALA.COLUMN_NAME +", "+ ENTITY_KOALA.COLUMN_SEX +", "+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ ENTITY_KOALA.COLUMN_IS_ALIVE +", "+ ENTITY_KOALA.COLUMN_DEATHDATE +", "+ ENTITY_KOALA.COLUMN_MOTHER +", "+ ENTITY_KOALA.COLUMN_FATHER +", "+ ENTITY_KOALA.COLUMN_DETAILS +", "+ ENTITY_KOALA.COLUMN_FEATURE +", "+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING "+ ENTITY_KOALA.COLUMN_KOALA_ID +"",
				koala.getName(), koala.getSex(), koala.getBirthdate(), koala.getIs_alive(), koala.getDeathdate(),
				koala.getMother_id(), koala.getFather_id(), koala.getDetails(), koala.getFeature(),
				koala.getProfileImagePath());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = dateFormat.parse(dummyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateAllCommonColumn(ENTITY_KOALA.TABLE_NAME,ENTITY_KOALA.COLUMN_KOALA_ID ,(int) ((LoginUser) principal).getUser_id() ,(int) insertId.get(ENTITY_KOALA.COLUMN_KOALA_ID));

		Map<String, Object> insertKoala_zoo_Id = jdbcTemplate.queryForMap(
				"INSERT INTO "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"("+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ADMISSION_DATE +", "+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +") VALUES(?, ?, ?, ?) RETURNING "+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_KOALA_ZOO_HISTORY_ID + "",
				(int) insertId.get(ENTITY_KOALA.COLUMN_KOALA_ID), koala.getZoo(), date, date);
		
		commonSqlUtil.updateAllCommonColumn(ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME,ENTITY_KOALA_ZOO_HISTORY.COLUMN_KOALA_ZOO_HISTORY_ID ,(int) ((LoginUser) principal).getUser_id(),(int) insertKoala_zoo_Id.get(ENTITY_KOALA_ZOO_HISTORY.COLUMN_KOALA_ZOO_HISTORY_ID));

		// インサートしたコアラidを取得
		return (int) insertId.get(ENTITY_KOALA.COLUMN_KOALA_ID);

	}

	@Override
	public Koala findById(int id) {
		String sql = "SELECT "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_NAME +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_SEX +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_IS_ALIVE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DEATHDATE +", "
				+ ""+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DETAILS +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FEATURE +" , "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID +" ,"+ ENTITY_KOALA_IMAGE.COLUMN_FILETYPE +", "
				+ ""+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMainKoala +" LEFT OUTER JOIN "+ ENTITY_KOALA_IMAGE.TABLE_NAME +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_IMAGE.TABLE_NAME +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE  "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = ?";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);

		Koala koala = new Koala();
		koala.setProfileImagePath((String) resultList.get(0).get(ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE));
		koala.setKoala_id((int) resultList.get(0).get(ENTITY_KOALA.COLUMN_KOALA_ID));
		koala.setName((String) resultList.get(0).get(ENTITY_KOALA.COLUMN_NAME));
		koala.setSex((int) resultList.get(0).get(ENTITY_KOALA.COLUMN_SEX));
		koala.setBirthdate((Date) resultList.get(0).get(ENTITY_KOALA.COLUMN_BIRTHDATE));
		koala.setIs_alive((int) resultList.get(0).get(ENTITY_KOALA.COLUMN_IS_ALIVE));
		koala.setDeathdate((Date) resultList.get(0).get(ENTITY_KOALA.COLUMN_DEATHDATE));
		koala.setZoo((int) resultList.get(0).get(ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID));
		koala.setZooName((String) resultList.get(0).get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		koala.setMother((String) resultList.get(0).get(AsMotherName));
		koala.setFather((String) resultList.get(0).get(AsFatherName));
		koala.setDetails((String) resultList.get(0).get(ENTITY_KOALA.COLUMN_DETAILS));
		koala.setFeature((String) resultList.get(0).get(ENTITY_KOALA.COLUMN_FEATURE));
		koala.setMother_id((int) resultList.get(0).get(ENTITY_KOALA.COLUMN_MOTHER ));
		koala.setFather_id((int) resultList.get(0).get(ENTITY_KOALA.COLUMN_FATHER ));


		if (resultList.get(0).get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID) != null) {

			List<KoalaImage> koalaImageList = new ArrayList<KoalaImage>();
			for (Map<String, Object> result : resultList) {
				KoalaImage koalaImage = new KoalaImage();
				koalaImage.setKoalaimage_id((int) result.get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID));
				koalaImage.setKoala_id((int) resultList.get(0).get(ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID));
				koalaImage.setFiletype((String) result.get(ENTITY_KOALA_IMAGE.COLUMN_FILETYPE));
				koalaImageList.add(koalaImage);
				koalaImage = null;
			}
			koala.setKoalaImageList(koalaImageList);
		}
		
		return koala;
	}

	@Override
	public void update(Koala koala) {
		jdbcTemplate.update(
				"UPDATE "+ ENTITY_KOALA.TABLE_NAME +" SET "+ ENTITY_KOALA.COLUMN_NAME +"=?, "+ ENTITY_KOALA.COLUMN_SEX +"=?,"+ ENTITY_KOALA.COLUMN_BIRTHDATE +"=?,"+ ENTITY_KOALA.COLUMN_IS_ALIVE +"=?,"+ ENTITY_KOALA.COLUMN_DEATHDATE +"=?,"+ ENTITY_KOALA.COLUMN_MOTHER +"=?,"+ ENTITY_KOALA.COLUMN_FATHER +"=?,"+ ENTITY_KOALA.COLUMN_DETAILS +"=?,"+ ENTITY_KOALA.COLUMN_FEATURE +"=?, "+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +" = ? WHERE "+ ENTITY_KOALA.COLUMN_KOALA_ID +" = ?",
				koala.getName(), koala.getSex(), koala.getBirthdate(), koala.getIs_alive(), koala.getDeathdate(),
				koala.getMother_id(), koala.getFather_id(), koala.getDetails(), koala.getFeature(),
				koala.getProfileImagePath(), koala.getKoala_id());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_KOALA.TABLE_NAME,ENTITY_KOALA.COLUMN_KOALA_ID ,(int) ((LoginUser) principal).getUser_id(),koala.getKoala_id());

		jdbcTemplate.update("UPDATE "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +" SET "+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +"=? WHERE "+ ENTITY_KOALA.COLUMN_KOALA_ID +" = ?", koala.getZoo(),
				koala.getKoala_id());
	}

	@Override
	public void urlUpdate(int koala_id, String url) {
		jdbcTemplate.update("UPDATE "+ ENTITY_KOALA.TABLE_NAME +" SET "+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +" = ? WHERE "+ ENTITY_KOALA.COLUMN_KOALA_ID +" = ?", url, koala_id);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_KOALA.TABLE_NAME,ENTITY_KOALA.COLUMN_KOALA_ID,(int) ((LoginUser) principal).getUser_id() ,koala_id);
	}

	@Override
	public void delete(int koala_id) {
		jdbcTemplate.update("DELETE FROM "+ ENTITY_KOALA.TABLE_NAME +" WHERE "+ ENTITY_KOALA.COLUMN_KOALA_ID +" = ?", koala_id);
	}

	@Override
	public KoalaForTree getKoalaForTree(int id) {
		String sql = "SELECT "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_NAME +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_SEX +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_IS_ALIVE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DEATHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +", "
				+ ""+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DETAILS +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMainKoala +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE  "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = ?";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);
		KoalaForTree koala = new KoalaForTree();
		koala.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
		koala.setId((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));

	koala.setProfileImagePath((String) result.get(ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE));
//		mainKoala.setKoala_id((int) resultList.get(ENTITY_KOALA.COLUMN_KOALA_ID));
		koala.setSex((int) result.get(ENTITY_KOALA.COLUMN_SEX));
//		mainKoala.setBirthdate((Date) result.get(ENTITY_KOALA.COLUMN_BIRTHDATE));
//		mainKoala.setIs_alive((int) result.get(ENTITY_KOALA.COLUMN_IS_ALIVE));
//		mainKoala.setDeathdate((Date) result.get(ENTITY_KOALA.COLUMN_DEATHDATE));
//		mainKoala.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		koala.setMother_id((int)result.get( ENTITY_KOALA.COLUMN_MOTHER));
		koala.setFather_id((int)result.get(ENTITY_KOALA.COLUMN_FATHER));

		return koala;
	}
	@Override
	public 	List<KoalaForTree> getBrotherKoalaForTree(int koala_id, int mother_id, int father_id){
		String sql = "SELECT "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_NAME +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_SEX +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_IS_ALIVE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DEATHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +", "
				+ ""+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DETAILS +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FEATURE +" , "+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_IMAGE_ID +" ,"+ ENTITY_KOALA_IMAGE.COLUMN_FILETYPE +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMainKoala +" LEFT OUTER JOIN "+ ENTITY_KOALA_IMAGE.TABLE_NAME +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_IMAGE.TABLE_NAME +"."+ ENTITY_KOALA_IMAGE.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE  "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +" = ? AND "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +" = ? AND NOT " + AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID + "= ? "
				+ "ORDER BY "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +" ASC";
		
//		queryForListで実装予定
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, mother_id, father_id, koala_id );
		List<KoalaForTree> brotherList = new ArrayList<KoalaForTree>();
		
		for (Map<String, Object> result : resultList) {
			KoalaForTree brother = new KoalaForTree();
		brother.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
		brother.setId((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));

		brother.setProfileImagePath((String) result.get(ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE));
//		mainKoala.setKoala_id((int) resultList.get(""+ ENTITY_KOALA.COLUMN_KOALA_ID +""));
		brother.setSex((int) result.get(ENTITY_KOALA.COLUMN_SEX));
//		mainKoala.setBirthdate((Date) result.get(ENTITY_KOALA.COLUMN_BIRTHDATE));
//		mainKoala.setIs_alive((int) result.get(ENTITY_KOALA.COLUMN_IS_ALIVE));
//		mainKoala.setDeathdate((Date) result.get(ENTITY_KOALA.COLUMN_DEATHDATE));
//		mainKoala.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		brother.setMother_id((int)result.get( ENTITY_KOALA.COLUMN_MOTHER));
		brother.setFather_id((int)result.get(ENTITY_KOALA.COLUMN_FATHER));
		brotherList.add(brother);
		}
		return brotherList;
	}
	
	@Override
	public 	List<KoalaForTree> getChildrenKoalaForTree(int koala_id, int sex){
		String sqlAsMother = "SELECT "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_NAME +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_SEX +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_IS_ALIVE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DEATHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +", "
				+ ""+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DETAILS +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMainKoala +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE  "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +" = ? "
        + "ORDER BY "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +" ASC";
		

		String sqlAsFather = "SELECT "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_NAME +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_SEX +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_IS_ALIVE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DEATHDATE +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +", "
				+ ""+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_DETAILS +", "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMainKoala +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME +" ON "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" = "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsMotherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_MOTHER +"  = "+ AsMotherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_KOALA.TABLE_NAME +" AS "+ AsFatherKoala +" on "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +"  = "+ AsFatherKoala +"."+ ENTITY_KOALA.COLUMN_KOALA_ID +" "
				+ "WHERE  "+ ENTITY_KOALA_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_KOALA_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_FATHER +" = ? "
        + "ORDER BY "+ AsMainKoala +"."+ ENTITY_KOALA.COLUMN_BIRTHDATE +" ASC";

		
		
//		queryForListで実装予定
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if	(sex == 2) {
		 resultList= jdbcTemplate.queryForList(sqlAsMother, koala_id);
		} else {
			resultList = jdbcTemplate.queryForList(sqlAsFather, koala_id);
		}
		
		List<KoalaForTree> childrenList = new ArrayList<KoalaForTree>();

		for (Map<String, Object> result : resultList) {
			KoalaForTree children = new KoalaForTree();
			children.setName((String) result.get(ENTITY_KOALA.COLUMN_NAME));
			children.setId((int) result.get(ENTITY_KOALA.COLUMN_KOALA_ID));

			children.setProfileImagePath((String) result.get(ENTITY_KOALA.COLUMN_PROFILE_IMAGE_TYPE));
//			mainKoala.setKoala_id((int) resultList.get(""+ ENTITY_KOALA.COLUMN_KOALA_ID +""));
			children.setSex((int) result.get(ENTITY_KOALA.COLUMN_SEX));
//			mainKoala.setBirthdate((Date) result.get(ENTITY_KOALA.COLUMN_BIRTHDATE));
//			mainKoala.setIs_alive((int) result.get(ENTITY_KOALA.COLUMN_IS_ALIVE));
//			mainKoala.setDeathdate((Date) result.get(ENTITY_KOALA.COLUMN_DEATHDATE));
//			mainKoala.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			children.setMother_id((int)result.get( ENTITY_KOALA.COLUMN_MOTHER));
			children.setFather_id((int)result.get(ENTITY_KOALA.COLUMN_FATHER));
		childrenList.add(children);
		}
		return childrenList;
	}
	
}
