package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AnimalImage;
import com.example.demo.entity.LoginUser;

@Repository
public class AnimalImageDaoImple implements AnimalImageDao {

private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AnimalImage ENTITY_ANIMAL_IMAGE;
	
	@Autowired
	private CommonSqlUtil commonSqlUtil;
	
	@Autowired
	public AnimalImageDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(AnimalImage animalImage) {
		/*
		jdbcTemplate.update("INSERT INTO animalImage("+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID +",filetype) VALUES(?,?)",
			animalImage.getAnimal_id(),animalImage.getFiletype());
		int insertAnimalImage_id = jdbcTemplate.queryForObject("SELECT LASTVAL()", Integer.class);
		*/
		Map<String, Object> insertId = jdbcTemplate.queryForMap("INSERT INTO "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +"("+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID +","+ ENTITY_ANIMAL_IMAGE.COLUMN_FILETYPE +") VALUES(?,?) RETURNING "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID +"",
				animalImage.getAnimal_id(),animalImage.getFiletype());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commonSqlUtil.updateAllCommonColumn(ENTITY_ANIMAL_IMAGE.TABLE_NAME,ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID,(int) ((LoginUser) principal).getUser_id() ,(int)insertId.get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID));
		return (int)insertId.get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID);
	}
	
	@Override
	public void delete(List<String> animalImage_idList) {
		String sql = "DELETE FROM "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +" WHERE "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID +" = ";
		for (int i = 0; i < animalImage_idList.size(); i++) {
			if (i != 0) {
				sql += " OR "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID +" = ";
			}
			sql += animalImage_idList.get(i).toString();
		}
		jdbcTemplate.execute(sql);
	}
	
	@Override
	public List<AnimalImage> findByAnimal_id(int id){
		String sql = "SELECT  "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID +" ,"+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID +","+ ENTITY_ANIMAL_IMAGE.COLUMN_FILETYPE +" FROM  "+ ENTITY_ANIMAL_IMAGE.TABLE_NAME +" WHERE "+ ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID +" = ?";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);
		
		List<AnimalImage> animalImageList = new ArrayList<AnimalImage>();
		for(Map<String, Object> result : resultList) {
			AnimalImage animalImage = new AnimalImage();
			animalImage.setAnimalimage_id((int)result.get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_IMAGE_ID));
			animalImage.setAnimal_id((int)resultList.get(0).get(ENTITY_ANIMAL_IMAGE.COLUMN_ANIMAL_ID));
			animalImage.setFiletype((String)result.get(ENTITY_ANIMAL_IMAGE.COLUMN_FILETYPE));
			animalImageList.add(animalImage);
			animalImage = null;
		}
		return animalImageList;
	}

}
