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

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;
import com.example.demo.entity.AnimalImage;
import com.example.demo.entity.AnimalZooHistory;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Prefecture;
import com.example.demo.entity.Zoo;

@Repository
public class AnimalDaoImple implements AnimalDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Animal ENTITY_ANIMAL;
	
	@Autowired
	private AnimalImage ENTITY_ANIMAL_IMAGE;
	
	@Autowired
	private AnimalZooHistory ENTITY_ANIMAL_ZOO_HISTORY;
	
	@Autowired
	private Zoo ENTITY_ZOO;
	
	@Autowired
	private Prefecture ENTITY_PREFECTURE;
	
	@Autowired
	private CommonSqlUtil commonSqlUtil;
	
	@Autowired
	public AnimalDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	String AsMainAnimal = "mainAnimal";
	String AsMotherAnimal = "motherAnimal";
	String AsFatherAnimal = "fatherAnimal";
	String AsMotherName = "mother_name";
	String AsFatherName = "father_name";
	String AsMotherId = "mother_id";
	String AsFatherId = "father_id";
	String dummyDate = "9999-01-01";
	String man = "1";
	String woman = "2";

	@Override
	public List<Animal> getAll() {
		String sql = "SELECT "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "
				+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "
				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +" , "
				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal 
				+" LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  
				+" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID 
				+" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME 
				+" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID 
				+" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME 
				+" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID 
				+" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal 
				+" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER 
				+"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal 
				+" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER 
				+"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE 
				+" = '"+ dummyDate +"' "
				+ "ORDER BY "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" ASC";
		
		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		// view返却用のリストを生成
		List<Animal> list = new ArrayList<Animal>();
		// MAP型リストからMapを繰り返し出力し、MapのバリューObjectをAnimalインスタンスに詰め込む
		for (Map<String, Object> result : resultList) {
			Animal animal = new Animal();
			animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

			// Animalインスタンスをview返却用のリストに詰め込んでいく
			animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
			animal.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
			animal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
			animal.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			animal.setMother((String) result.get(AsMotherName));
			animal.setFather((String) result.get(AsFatherName));
			animal.setProfileImagePath((String)result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
			list.add(animal);
		}
		return list;
	}

	@Override
	public List<Animal> getMotherList(int animal_id, Date birthDay) {
		String sql = "SELECT "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +","+ ENTITY_ANIMAL.COLUMN_NAME +" FROM "+ ENTITY_ANIMAL.TABLE_NAME +" WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" NOT IN (?) AND "+ ENTITY_ANIMAL.COLUMN_SEX +" = "+ woman +"  AND "+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" < ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, animal_id, birthDay);
		List<Animal> motherList = new ArrayList<Animal>();
		for (Map<String, Object> result : resultList) {
			Animal animal = new Animal();
			animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
			animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
			motherList.add(animal);
		}
		return motherList;
	}

	@Override
	public List<Animal> getFatherList(int animal_id, Date birthDay) {
		String sql = "SELECT "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ ENTITY_ANIMAL.COLUMN_NAME +" FROM "+ ENTITY_ANIMAL.TABLE_NAME +" WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" NOT IN (?) AND "+ ENTITY_ANIMAL.COLUMN_SEX +" = "+ man + " AND "+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" < ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, animal_id, birthDay);
		List<Animal> fatherList = new ArrayList<Animal>();
		for (Map<String, Object> result : resultList) {
			Animal animal = new Animal();
			animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
			animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
			fatherList.add(animal);
		}
		return fatherList;
	}

	@Override
	public List<Animal> findByKeyword(String keyword) {

		String[] splitkeyWord = keyword.replaceAll(" ", "　").split("　", 0);

		String sql = "SELECT "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_NAME +", "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_SEX +","+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID + "," + ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +" , "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" ON "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' ";

		for (int i = 0; i < splitkeyWord.length; ++i) {
			sql += "AND ("+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_NAME +" like '%" + splitkeyWord[i] + "%' OR "+ ENTITY_ZOO.COLUMN_ZOO_NAME +" like '%" + splitkeyWord[i]
					+ "%' OR "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" like '%" + splitkeyWord[i] + "%' OR "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" like '%" + splitkeyWord[i]
					+ "%')";
		}

		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		// view返却用のリストを生成
		List<Animal> list = new ArrayList<Animal>();
		// MAP型リストからMapを繰り返し出力し、MapのバリューObjectをAnimalインスタンスに詰め込む
		for (Map<String, Object> result : resultList) {
			Animal animal = new Animal();
			animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

			// Animalインスタンスをview返却用のリストに詰め込んでいく
			animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
			animal.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
			animal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
			animal.setZoo((int) result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
			animal.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			animal.setMother((String) result.get(AsMotherName));
			animal.setFather((String) result.get(AsFatherName));
			animal.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
			list.add(animal);
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
	public int insert(Animal animal) {
		Map<String, Object> insertId = jdbcTemplate.queryForMap(
				"INSERT INTO "+ ENTITY_ANIMAL.TABLE_NAME +"("+ ENTITY_ANIMAL.COLUMN_NAME +", "+ ENTITY_ANIMAL.COLUMN_SEX +", "+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ ENTITY_ANIMAL.COLUMN_FATHER +", "+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ ENTITY_ANIMAL.COLUMN_FEATURE +", "+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +"",
				animal.getName(), animal.getSex(), animal.getBirthdate(), animal.getIs_alive(), animal.getDeathdate(),
				animal.getMother_id(), animal.getFather_id(), animal.getDetails(), animal.getFeature(),
				animal.getProfileImagePath());

		//削除対象？？
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = dateFormat.parse(dummyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateAllCommonColumn(ENTITY_ANIMAL.TABLE_NAME,ENTITY_ANIMAL.COLUMN_ANIMAL_ID ,(int) ((LoginUser) principal).getUser_id() ,(int) insertId.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

		return (int) insertId.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID);
	}
	

	@Override
	public Animal findById(int id) {
		String sql = "SELECT "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "
				+ ""+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" , "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID +" ,"+ ENTITY_ANIMAL_IMAGE.COLUMN_FILETYPE +", "
				+ ""+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" LEFT OUTER JOIN "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE  "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);

		Animal animal = new Animal();
		animal.setProfileImagePath((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
		animal.setAnimal_id((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
		animal.setName((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_NAME));
		animal.setSex((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_SEX));
		animal.setBirthdate((Date) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
		animal.setIs_alive((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_IS_ALIVE));
		animal.setDeathdate((Date) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_DEATHDATE));
		animal.setZoo((int) resultList.get(0).get(ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID));
		animal.setZooName((String) resultList.get(0).get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		animal.setMother((String) resultList.get(0).get(AsMotherName));
		animal.setFather((String) resultList.get(0).get(AsFatherName));
		animal.setDetails((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_DETAILS));
		animal.setFeature((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_FEATURE));
		animal.setMother_id((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_MOTHER ));
		animal.setFather_id((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_FATHER ));

		//アニマル写真（いずれ不要になる）
		if (resultList.get(0).get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID) != null) {

			List<AnimalImage> animalImageList = new ArrayList<AnimalImage>();
			for (Map<String, Object> result : resultList) {
				AnimalImage animalImage = new AnimalImage();
				animalImage.setAnimalimage_id((int) result.get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID));
				animalImage.setAnimal_id((int) resultList.get(0).get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID));
				animalImage.setFiletype((String) result.get(ENTITY_ANIMAL_IMAGE.COLUMN_FILETYPE));
				animalImageList.add(animalImage);
				animalImage = null;
			}
			animal.setAnimalImageList(animalImageList);
		}		
		return animal;
	}

	@Override
	public void update(Animal animal) {
		jdbcTemplate.update(
				"UPDATE "+ ENTITY_ANIMAL.TABLE_NAME +" SET "+ ENTITY_ANIMAL.COLUMN_NAME +"=?, "+ ENTITY_ANIMAL.COLUMN_SEX +"=?,"+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +"=?,"+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +"=?,"+ ENTITY_ANIMAL.COLUMN_DEATHDATE +"=?,"+ ENTITY_ANIMAL.COLUMN_MOTHER +"=?,"+ ENTITY_ANIMAL.COLUMN_FATHER +"=?,"+ ENTITY_ANIMAL.COLUMN_DETAILS +"=?,"+ ENTITY_ANIMAL.COLUMN_FEATURE +"=?, "+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" = ? WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?",
				animal.getName(), animal.getSex(), animal.getBirthdate(), animal.getIs_alive(), animal.getDeathdate(),
				animal.getMother_id(), animal.getFather_id(), animal.getDetails(), animal.getFeature(),
				animal.getProfileImagePath(), animal.getAnimal_id());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_ANIMAL.TABLE_NAME,ENTITY_ANIMAL.COLUMN_ANIMAL_ID ,(int) ((LoginUser) principal).getUser_id(),animal.getAnimal_id());

		jdbcTemplate.update("UPDATE "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" SET "+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +"=? WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?", animal.getZoo(),
				animal.getAnimal_id());
	}

	@Override
	public void urlUpdate(int animal_id, String url) {
		jdbcTemplate.update("UPDATE "+ ENTITY_ANIMAL.TABLE_NAME +" SET "+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" = ? WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?", url, animal_id);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_ANIMAL.TABLE_NAME,ENTITY_ANIMAL.COLUMN_ANIMAL_ID,(int) ((LoginUser) principal).getUser_id() ,animal_id);
	}

	@Override
	public void delete(int animal_id) {
		jdbcTemplate.update("DELETE FROM "+ ENTITY_ANIMAL.TABLE_NAME +" WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?", animal_id);
	}

	@Override
	public AnimalForTree getAnimalForTree(int id) {
		String sql = "SELECT "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +", "
				+ ""+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE  "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);
		AnimalForTree animal = new AnimalForTree();
		animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
		animal.setId((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

	animal.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
//		mainAnimal.setAnimal_id((int) resultList.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
		animal.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
//		mainAnimal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
//		mainAnimal.setIs_alive((int) result.get(ENTITY_ANIMAL.COLUMN_IS_ALIVE));
//		mainAnimal.setDeathdate((Date) result.get(ENTITY_ANIMAL.COLUMN_DEATHDATE));
//		mainAnimal.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		animal.setMother_id((int)result.get( ENTITY_ANIMAL.COLUMN_MOTHER));
		animal.setFather_id((int)result.get(ENTITY_ANIMAL.COLUMN_FATHER));

		return animal;
	}
	@Override
	public 	List<AnimalForTree> getBrotherAnimalForTree(int animal_id, int mother_id, int father_id){
		String sql = "SELECT "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +", "
				+ ""+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" , "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID +" ,"+ ENTITY_ANIMAL_IMAGE.COLUMN_FILETYPE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" LEFT OUTER JOIN "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +"."+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE  "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +" = ? AND "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +" = ? AND NOT " + AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "= ? "
				+ "ORDER BY "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" ASC";
		
//		queryForListで実装予定
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, mother_id, father_id, animal_id );
		List<AnimalForTree> brotherList = new ArrayList<AnimalForTree>();
		
		for (Map<String, Object> result : resultList) {
			AnimalForTree brother = new AnimalForTree();
		brother.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
		brother.setId((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

		brother.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
//		mainAnimal.setAnimal_id((int) resultList.get(""+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +""));
		brother.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
//		mainAnimal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
//		mainAnimal.setIs_alive((int) result.get(ENTITY_ANIMAL.COLUMN_IS_ALIVE));
//		mainAnimal.setDeathdate((Date) result.get(ENTITY_ANIMAL.COLUMN_DEATHDATE));
//		mainAnimal.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
		brother.setMother_id((int)result.get( ENTITY_ANIMAL.COLUMN_MOTHER));
		brother.setFather_id((int)result.get(ENTITY_ANIMAL.COLUMN_FATHER));
		brotherList.add(brother);
		}
		return brotherList;
	}
	
	@Override
	public 	List<AnimalForTree> getChildrenAnimalForTree(int animal_id, int sex){
		String sqlAsMother = "SELECT "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +", "
				+ ""+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE  "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +" = ? "
        + "ORDER BY "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" ASC";
		

		String sqlAsFather = "SELECT "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +", "
				+ ""+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE  "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE +" = '"+ dummyDate +"' AND "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +" = ? "
        + "ORDER BY "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" ASC";

		
		
//		queryForListで実装予定
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if	(sex == 2) {
		 resultList= jdbcTemplate.queryForList(sqlAsMother, animal_id);
		} else {
			resultList = jdbcTemplate.queryForList(sqlAsFather, animal_id);
		}
		
		List<AnimalForTree> childrenList = new ArrayList<AnimalForTree>();

		for (Map<String, Object> result : resultList) {
			AnimalForTree children = new AnimalForTree();
			children.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
			children.setId((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

			children.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
//			mainAnimal.setAnimal_id((int) resultList.get(""+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +""));
			children.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
//			mainAnimal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
//			mainAnimal.setIs_alive((int) result.get(ENTITY_ANIMAL.COLUMN_IS_ALIVE));
//			mainAnimal.setDeathdate((Date) result.get(ENTITY_ANIMAL.COLUMN_DEATHDATE));
//			mainAnimal.setZooName((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			children.setMother_id((int)result.get( ENTITY_ANIMAL.COLUMN_MOTHER));
			children.setFather_id((int)result.get(ENTITY_ANIMAL.COLUMN_FATHER));
		childrenList.add(children);
		}
		return childrenList;
	}
}
