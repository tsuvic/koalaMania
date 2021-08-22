package com.example.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;
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
	String ENTITY_ANIMAL_ZOO_HISTORY2 = "animal_zoo_history_2";
	String COLUMN_ANIMAL_ID2 = "animal_id";
	
	@Override
	public List<Animal> getAll() {
		String sql = "SELECT "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "
				+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +", "
				+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "
				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , "
				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", "
				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +" , "
				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "
				+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal 
				
				+" LEFT OUTER JOIN " 
				+" (SELECT "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID 
				+ ", " + " MAX (" +ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
				+"FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
				+"GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." +ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
				+ ENTITY_ANIMAL_ZOO_HISTORY2
				+ " ON " +  AsMainAnimal + "." +ENTITY_ANIMAL.COLUMN_ANIMAL_ID 
				+" = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
				
				+" LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
				+" ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." +ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
				+" = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
				+" AND " +  ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
				+" = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "
				
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
				+" ORDER BY "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" DESC";
		
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
			Animal motherAnimal = new Animal();
			Animal fatherAnimal = new Animal();
			if(result.get(AsMotherId) != null) {
				motherAnimal.setAnimal_id((int) result.get(AsMotherId));
			}
			if(result.get(AsFatherId) != null) {
				fatherAnimal.setAnimal_id((int) result.get(AsFatherId));
			}
			motherAnimal.setName((String) result.get(AsMotherName));
			fatherAnimal.setName((String) result.get(AsFatherName));
			animal.setMotherAnimal(motherAnimal);
			animal.setFatherAnimal(fatherAnimal);
			animal.setProfileImagePath((String)result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
			AnimalZooHistory animalZooHistory = new AnimalZooHistory();
			Zoo zoo = new Zoo();
			zoo.setZoo_id((int) result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
			zoo.setZoo_name((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			animalZooHistory.setZoo(zoo);
			List<AnimalZooHistory> animalZooHistoryList = Arrays.asList(animalZooHistory);
			animal.setAnimalZooHistoryList(animalZooHistoryList);
			list.add(animal);
		}
		return list;
	}

	@Override
	public List<Animal> getMotherList(int animal_id, Date birthDay) {
		String sql = "SELECT "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +","+ ENTITY_ANIMAL.COLUMN_NAME +" FROM "+ ENTITY_ANIMAL.TABLE_NAME 
							+" WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" NOT IN (?) AND "
							+ ENTITY_ANIMAL.COLUMN_SEX +" = "+ woman +" AND ("+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" < ?"
							+" OR "+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" = '9999-01-01')" + "ORDER BY " + ENTITY_ANIMAL.COLUMN_NAME ;
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
		String sql = "SELECT "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ ENTITY_ANIMAL.COLUMN_NAME +" FROM "+ ENTITY_ANIMAL.TABLE_NAME 
						+" WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" NOT IN (?) AND "
						+ ENTITY_ANIMAL.COLUMN_SEX +" = "+ man + " AND ("+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" < ?"
						+" OR "+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +" = '9999-01-01')" + "ORDER BY " + ENTITY_ANIMAL.COLUMN_NAME ;
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

		String sql = "SELECT "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "
				+ ""+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_NAME +", "
				+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_SEX +","+ 
				ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ 
				ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID + "," + 
				ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+
				AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , " +
				AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", " +
				AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +" , "+ 
				AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ 
				ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" "
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
			Animal motherAnimal = new Animal();
			Animal fatherAnimal = new Animal();
			if(result.get(AsMotherId) != null) {
				motherAnimal.setAnimal_id((int) result.get(AsMotherId));
			}
			if(result.get(AsFatherId) != null) {
				fatherAnimal.setAnimal_id((int) result.get(AsFatherId));
			}
			motherAnimal.setName((String) result.get(AsMotherName));
			fatherAnimal.setName((String) result.get(AsFatherName));
			animal.setMotherAnimal(motherAnimal);
			animal.setFatherAnimal(fatherAnimal);
			animal.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
			AnimalZooHistory animalZooHistory = new AnimalZooHistory();
			Zoo zoo = new Zoo();
			zoo.setZoo_id((int) result.get(ENTITY_ZOO.COLUMN_ZOO_ID));
			zoo.setZoo_name((String) result.get(ENTITY_ZOO.COLUMN_ZOO_NAME));
			animalZooHistory.setZoo(zoo);
			List<AnimalZooHistory> animalZooHistoryList = Arrays.asList(animalZooHistory);
			animal.setAnimalZooHistoryList(animalZooHistoryList);
			
			list.add(animal);
		}
		return list;
	}
	
	@Override
	public List<Animal> getAnimalListByZooId(int zoo_id){
		String sql = "SELECT  DISTINCT "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "
				+ ""+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_NAME +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +
				" INNER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" ON "+ ENTITY_ANIMAL.TABLE_NAME + "."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID +
				" AND " +  ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID + " = ?";
		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,zoo_id);
		
		List<Animal> returnAnimalList = new ArrayList();
		for(Map<String, Object> result:resultList) {
			Animal animal = new Animal();
			animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
			animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
			returnAnimalList.add(animal);
		}
		
		return returnAnimalList;
		
	}

	@Override
	public int insert(Animal animal) {
		Map<String, Object> insertId = jdbcTemplate.queryForMap(
				"INSERT INTO "+ ENTITY_ANIMAL.TABLE_NAME +"("+ ENTITY_ANIMAL.COLUMN_NAME +", "+ ENTITY_ANIMAL.COLUMN_SEX +", "+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", "+ ENTITY_ANIMAL.COLUMN_MOTHER +", "+ ENTITY_ANIMAL.COLUMN_FATHER +", "+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ ENTITY_ANIMAL.COLUMN_FEATURE +", "+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +"",
				animal.getName(), animal.getSex(), animal.getBirthdate(), animal.getIs_alive(), animal.getDeathdate(),
				animal.getMotherAnimal().getAnimal_id(), animal.getFatherAnimal().getAnimal_id(), animal.getDetails(), animal.getFeature(),
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
		String sql = "SELECT "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +", "+ 
						AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +", "+ AsMainAnimal +"."+ 
						ENTITY_ANIMAL.COLUMN_NAME +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_SEX +", "+ 
						AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +", "+ AsMainAnimal +"."+ 
						ENTITY_ANIMAL.COLUMN_IS_ALIVE +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DEATHDATE +", " +

						AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , " +
						AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", " +
						AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ 
						AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ 
						AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" "
						
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" ON "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ZOO.TABLE_NAME +" ON "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +" = "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_ZOO.COLUMN_ZOO_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_PREFECTURE.TABLE_NAME +" ON "+ ENTITY_ZOO.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" = "+ ENTITY_PREFECTURE.TABLE_NAME +"."+ ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMotherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_MOTHER +"  = "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "LEFT OUTER JOIN "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsFatherAnimal +" on "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FATHER +"  = "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" "
				+ "WHERE  " + AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);

		Animal animal = new Animal();
		animal.setProfileImagePath((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
		animal.setAnimal_id((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
		animal.setName((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_NAME));
		animal.setSex((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_SEX));
		animal.setBirthdate((Date) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
		animal.setIs_alive((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_IS_ALIVE));
		animal.setDeathdate((Date) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_DEATHDATE));
		Animal motherAnimal = new Animal();
		Animal fatherAnimal = new Animal();
		if(resultList.get(0).get(AsMotherId) != null) {
			motherAnimal.setAnimal_id((int) resultList.get(0).get(AsMotherId));
		}
		if(resultList.get(0).get(AsFatherId) != null) {
			fatherAnimal.setAnimal_id((int) resultList.get(0).get(AsFatherId));
		}
		motherAnimal.setName((String) resultList.get(0).get(AsMotherName));
		fatherAnimal.setName((String) resultList.get(0).get(AsFatherName));
		animal.setMotherAnimal(motherAnimal);
		animal.setFatherAnimal(fatherAnimal);
		animal.setDetails((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_DETAILS));
		animal.setFeature((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_FEATURE));
		return animal;
	}

	@Override
	public void update(Animal animal) {
		jdbcTemplate.update(
				"UPDATE "+ ENTITY_ANIMAL.TABLE_NAME +" SET "+ ENTITY_ANIMAL.COLUMN_NAME +"=?, "+ ENTITY_ANIMAL.COLUMN_SEX +"=?,"+ ENTITY_ANIMAL.COLUMN_BIRTHDATE +"=?,"+ ENTITY_ANIMAL.COLUMN_IS_ALIVE +"=?,"+ ENTITY_ANIMAL.COLUMN_DEATHDATE +"=?,"+ ENTITY_ANIMAL.COLUMN_MOTHER +"=?,"+ ENTITY_ANIMAL.COLUMN_FATHER +"=?,"+ ENTITY_ANIMAL.COLUMN_DETAILS +"=?,"+ ENTITY_ANIMAL.COLUMN_FEATURE +"=?, "+ ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE +" = ? WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?",
				animal.getName(), animal.getSex(), animal.getBirthdate(), animal.getIs_alive(), animal.getDeathdate(),
				animal.getMotherAnimal().getAnimal_id(), animal.getFatherAnimal().getAnimal_id(), animal.getDetails(), animal.getFeature(),
				animal.getProfileImagePath(), animal.getAnimal_id());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_ANIMAL.TABLE_NAME,ENTITY_ANIMAL.COLUMN_ANIMAL_ID ,(int) ((LoginUser) principal).getUser_id(),animal.getAnimal_id());

		/*jdbcTemplate.update("UPDATE "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" SET "+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +"=? WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?", animal.getZoo(),
				animal.getAnimal_id());
		commonSqlUtil.updateOnlyUpdateCommonColumn( ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME,ENTITY_ANIMAL.COLUMN_ANIMAL_ID ,(int) ((LoginUser) principal).getUser_id(),animal.getAnimal_id());*/
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
				+ ""+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +"."+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +", "+ ENTITY_ZOO.COLUMN_ZOO_NAME +", "+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsMotherName +", "+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_NAME +" as "+ AsFatherName +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_DETAILS +", "+ AsMainAnimal +"."+ ENTITY_ANIMAL.COLUMN_FEATURE +" "
				+ "FROM "+ ENTITY_ANIMAL.TABLE_NAME +" AS "+ AsMainAnimal +" "
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
