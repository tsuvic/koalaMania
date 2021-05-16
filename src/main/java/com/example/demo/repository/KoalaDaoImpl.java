package com.example.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.KoalaProfileImage;
import com.example.demo.entity.Zoo;

@Repository
public class KoalaDaoImpl implements KoalaDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public KoalaDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Koala> getAll() {
		String sql = "SELECT koalakoala.koala_id, koalakoala.name, koalakoala.sex, koalakoala.birthdate, zoo.zoo_name, mother.name as mother_name , father.name as father_name "
				+ "FROM koala AS koalakoala LEFT OUTER JOIN koala_zoo_history ON koalakoala.koala_id = koala_zoo_history.koala_id "
				+ "LEFT OUTER JOIN zoo ON koala_zoo_history.zoo_id = zoo.zoo_id "
				+ "LEFT OUTER JOIN prefecture ON zoo.prefecture_id = prefecture.prefecture_id "
				+ "LEFT OUTER JOIN koala AS mother on koalakoala.mother  = mother.koala_id "
				+ "LEFT OUTER JOIN koala AS father on koalakoala.father  = father.koala_id "
				+ "WHERE koala_zoo_history.exit_date = '9999-01-01'";

		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		// view返却用のリストを生成
		List<Koala> list = new ArrayList<Koala>();
		// MAP型リストからMapを繰り返し出力し、MapのバリューObjectをKoalaインスタンスに詰め込む
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get("koala_id"));

			// Koalaインスタンスをview返却用のリストに詰め込んでいく
			koala.setName((String) result.get("name"));
			koala.setSex((int) result.get("sex"));
			koala.setBirthdate((Date) result.get("birthdate"));
			koala.setZooName((String) result.get("zoo_name"));
			koala.setMother((String) result.get("mother_name"));
			koala.setFather((String) result.get("father_name"));

			list.add(koala);
		}
		return list;
	}

	@Override
	public List<Koala> getMotherList(int koala_id, Date birthDay) {
		String sql = "SELECT koala_id, name FROM koala WHERE koala_id NOT IN (?) AND sex = 2  AND birthdate < ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, koala_id, birthDay);
		List<Koala> motherList = new ArrayList<Koala>();
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get("koala_id"));
			koala.setName((String) result.get("name"));
			motherList.add(koala);
		}
		return motherList;
	}

	@Override
	public List<Koala> getFatherList(int koala_id, Date birthDay) {
		String sql = "SELECT koala_id, name FROM koala WHERE koala_id NOT IN (?) AND sex = 1 AND birthdate < ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, koala_id, birthDay);
		List<Koala> fatherList = new ArrayList<Koala>();
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get("koala_id"));
			koala.setName((String) result.get("name"));
			fatherList.add(koala);
		}
		return fatherList;
	}

	@Override
	public List<Koala> findByKeyword(String keyword) {

		String[] splitkeyWord = keyword.replaceAll(" ", "　").split("　", 0);

		String sql = "SELECT koalakoala.koala_id, koalakoala.name, koalakoala.sex, koalakoala.birthdate, zoo.zoo_name, mother.name as mother_name , father.name as father_name "
				+ "FROM koala AS koalakoala LEFT OUTER JOIN koala_zoo_history ON koalakoala.koala_id = koala_zoo_history.koala_id "
				+ "LEFT OUTER JOIN zoo ON koala_zoo_history.zoo_id = zoo.zoo_id "
				+ "LEFT OUTER JOIN prefecture ON zoo.prefecture_id = prefecture.prefecture_id "
				+ "LEFT OUTER JOIN koala AS mother on koalakoala.mother  = mother.koala_id "
				+ "LEFT OUTER JOIN koala AS father on koalakoala.father  = father.koala_id "
				+ "WHERE koala_zoo_history.exit_date = '9999-01-01' AND ";

		for (int i = 0; i < splitkeyWord.length; ++i) {
			if (i != 0) {
				sql += " AND ";
			}
			sql += "(koalakoala.name like '%" + splitkeyWord[i] + "%' OR zoo_name like '%" + splitkeyWord[i]
					+ "%' OR mother.name like '%" + splitkeyWord[i] + "%' OR father.name like '%" + splitkeyWord[i]
					+ "%')";
		}

		// SQL実行結果をMap型リストへ代入
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		// view返却用のリストを生成
		List<Koala> list = new ArrayList<Koala>();
		// MAP型リストからMapを繰り返し出力し、MapのバリューObjectをKoalaインスタンスに詰め込む
		for (Map<String, Object> result : resultList) {
			Koala koala = new Koala();
			koala.setKoala_id((int) result.get("koala_id"));

			// Koalaインスタンスをview返却用のリストに詰め込んでいく
			koala.setName((String) result.get("name"));
			koala.setSex((int) result.get("sex"));
			koala.setBirthdate((Date) result.get("birthdate"));
			koala.setZooName((String) result.get("zoo_name"));
			koala.setMother((String) result.get("mother_name"));
			koala.setFather((String) result.get("father_name"));
			list.add(koala);
		}
		return list;
	}

	@Override
	public List<Zoo> getZooList() {
		String sql = "SELECT zoo_id, zoo_name FROM zoo ORDER BY zoo_id ASC";
		List<Map<String, Object>> resultZooList = jdbcTemplate.queryForList(sql);
		List<Zoo> zooList = new ArrayList<Zoo>();
		for (Map<String, Object> result : resultZooList) {
			Zoo zoo = new Zoo();
			zoo.setZoo_id((int) result.get("zoo_id"));
			zoo.setZoo_name((String) result.get("zoo_name"));
			zooList.add(zoo);
		}
		return zooList;
	}

	@Override
	public int insert(Koala koala) {
		Map<String, Object> insertId = jdbcTemplate.queryForMap(
				"INSERT INTO koala(name, sex, birthdate, is_alive, deathdate, mother, father, details, feature, profile_image_type) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING koala_id",
				koala.getName(), koala.getSex(), koala.getBirthdate(), koala.getIs_alive(), koala.getDeathdate(),
				koala.getMother_id(), koala.getFather_id(), koala.getDetails(), koala.getFeature(), koala.getProfileImageType());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = dateFormat.parse("9999-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		jdbcTemplate.update(
				"INSERT INTO koala_zoo_history(koala_id, zoo_id, admission_date, exit_date) VALUES(?, ?, ?, ?)",
				(int) insertId.get("koala_id"), koala.getZoo(), date, date);

		// インサートしたコアラidを取得
		return (int) insertId.get("koala_id");

	}

	@Override
	public Koala findById(int id) {
		String sql = "SELECT koalakoala.profile_image_type, koalakoala.koala_id, koalakoala.name, koalakoala.sex, koalakoala.birthdate, koalakoala.is_alive, koalakoala.deathdate, "
				+ "koala_zoo_history.zoo_id, zoo_name, mother.name as mother_name, father.name as father_name, koalakoala.details, koalakoala.feature , koalaimage_id ,koalaImage.filetype, koalaProfileImage_id, koalaProfileImage.filetype as profilefiletype "
				+ "FROM koala AS koalakoala LEFT OUTER JOIN koalaimage ON koalakoala.koala_id = koalaimage.koala_id "
				+ "LEFT OUTER JOIN koala_zoo_history ON koalakoala.koala_id = koala_zoo_history.koala_id "
				+ "LEFT OUTER JOIN zoo ON koala_zoo_history.zoo_id = zoo.zoo_id "
				+ "LEFT OUTER JOIN prefecture ON zoo.prefecture_id = prefecture.prefecture_id "
				+ "LEFT OUTER JOIN koala AS mother on koalakoala.mother  = mother.koala_id "
				+ "LEFT OUTER JOIN koala AS father on koalakoala.father  = father.koala_id "
				+ "LEFT OUTER JOIN koalaProfileImage on koalakoala.koala_id = koalaProfileImage.koala_id "
				+ "WHERE  koala_zoo_history.exit_date = '9999-01-01' AND koalakoala.koala_id = ?";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);

		Koala koala = new Koala();
		koala.setProfileImageType((String) resultList.get(0).get("profile_image_type"));
		koala.setKoala_id((int) resultList.get(0).get("koala_id"));
		koala.setName((String) resultList.get(0).get("name"));
		koala.setSex((int) resultList.get(0).get("sex"));
		koala.setBirthdate((Date) resultList.get(0).get("birthdate"));
		koala.setIs_alive((int) resultList.get(0).get("is_alive"));
		koala.setDeathdate((Date) resultList.get(0).get("deathdate"));
		koala.setZoo((int) resultList.get(0).get("zoo_id"));
		koala.setZooName((String) resultList.get(0).get("zoo_name"));
		koala.setMother((String) resultList.get(0).get("mother_name"));
		koala.setFather((String) resultList.get(0).get("father_name"));
		koala.setDetails((String) resultList.get(0).get("details"));
		koala.setFeature((String) resultList.get(0).get("feature"));

		if (resultList.get(0).get("koalaimage_id") != null) {

			List<KoalaImage> koalaImageList = new ArrayList<KoalaImage>();
			for (Map<String, Object> result : resultList) {
				KoalaImage koalaImage = new KoalaImage();
				koalaImage.setKoalaimage_id((int) result.get("koalaImage_id"));
				koalaImage.setKoala_id((int) resultList.get(0).get("koala_id"));
				koalaImage.setFiletype((String) result.get("filetype"));
				koalaImageList.add(koalaImage);
				koalaImage = null;
			}
			koala.setKoalaImageList(koalaImageList);
		}
		
		if (resultList.get(0).get("koalaProfileImage_id") != null) {
			KoalaProfileImage koalaProfileImage = new KoalaProfileImage();
			koalaProfileImage.setKoala_id((int) resultList.get(0).get("koala_id"));
			koalaProfileImage.setKoalaProfileImage_id((int) resultList.get(0).get("koalaProfileImage_id"));
			koalaProfileImage.setFiletype((String) resultList.get(0).get("profilefiletype"));
			koala.setKoalaProfileImage(koalaProfileImage);
		}
		return koala;
	}

	@Override
	public void update(Koala koala) {
		jdbcTemplate.update(
				"UPDATE koala SET name=?, sex=?,birthdate=?,is_alive=?,deathdate=?,mother=?,father=?,details=?,feature=?, profile_image_type = ? WHERE koala_id = ?",
				koala.getName(), koala.getSex(), koala.getBirthdate(), koala.getIs_alive(), koala.getDeathdate(),
				koala.getMother_id(), koala.getFather_id(), koala.getDetails(), koala.getFeature(), koala.getProfileImageType(),
				koala.getKoala_id());

		jdbcTemplate.update("UPDATE koala_zoo_history SET zoo_id=? WHERE koala_id = ?", koala.getZoo(),
				koala.getKoala_id());
	}

	@Override
	public void delete(int koala_id) {
		jdbcTemplate.update("DELETE FROM koala WHERE koala_id = ?", koala_id);
	}
}
