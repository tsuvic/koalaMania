package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.app.AnimalFilterForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;
import com.example.demo.entity.AnimalZooHistory;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Prefecture;
import com.example.demo.entity.Zoo;
import com.example.demo.util.CommonSqlUtil;

@Repository
public class AnimalDaoImpl implements AnimalDao {

    private final JdbcTemplate jdbcTemplate;
    public Object searchAnimals;

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
    public AnimalDaoImpl(JdbcTemplate jdbcTemplate) {
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
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsMotherId + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsFatherId + ", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + " ORDER BY " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " DESC";

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
            if (result.get(AsMotherId) != null) {
                motherAnimal.setAnimal_id((int) result.get(AsMotherId));
            }
            if (result.get(AsFatherId) != null) {
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
            List<AnimalZooHistory> animalZooHistoryList = Arrays.asList(animalZooHistory); //現在所属する動物園のみ追加されたリストをanimalにセットする
            animal.setAnimalZooHistoryList(animalZooHistoryList);
            list.add(animal);
        }
        return list;
    }

    @Override
    public List<Animal> getMotherList(int animal_id, Date birthDay) {
        String sql = "SELECT " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "," + ENTITY_ANIMAL.COLUMN_NAME + "," + ENTITY_ANIMAL.COLUMN_BIRTHDATE + "," + ENTITY_ANIMAL.COLUMN_SEX + " FROM " + ENTITY_ANIMAL.TABLE_NAME
                + " WHERE " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " NOT IN (?) AND "
                + ENTITY_ANIMAL.COLUMN_SEX + " = " + woman + " AND (" + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " < ?"
                + " OR " + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " = '9999-01-01')" + "ORDER BY " + ENTITY_ANIMAL.COLUMN_NAME;
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, animal_id, birthDay);
        List<Animal> motherList = new ArrayList<Animal>();
        for (Map<String, Object> result : resultList) {
            Animal animal = new Animal();
            animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
            animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
            animal.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
            animal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
            motherList.add(animal);
        }
        return motherList;
    }

    @Override
    public List<Animal> getFatherList(int animal_id, Date birthDay) {
        String sql = "SELECT " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", " + ENTITY_ANIMAL.COLUMN_NAME + "," + ENTITY_ANIMAL.COLUMN_BIRTHDATE + "," + ENTITY_ANIMAL.COLUMN_SEX + " FROM " + ENTITY_ANIMAL.TABLE_NAME
                + " WHERE " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " NOT IN (?) AND "
                + ENTITY_ANIMAL.COLUMN_SEX + " = " + man + " AND (" + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " < ?"
                + " OR " + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " = '9999-01-01')" + "ORDER BY " + ENTITY_ANIMAL.COLUMN_NAME;
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, animal_id, birthDay);
        List<Animal> fatherList = new ArrayList<Animal>();
        for (Map<String, Object> result : resultList) {
            Animal animal = new Animal();
            animal.setAnimal_id((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
            animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
            animal.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
            animal.setBirthdate((Date) result.get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
            fatherList.add(animal);
        }
        return fatherList;
    }

    @Override
    public List<Animal> findByKeyword(String keyword) {

        String[] splitkeyWord = keyword.replaceAll(" ", "　").split("　", 0);

        String sql = "SELECT "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsMotherId + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsFatherId + ", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "WHERE";

        for (int i = 0; i < splitkeyWord.length; ++i) {
            sql += " (" + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%' OR " + ENTITY_ZOO.COLUMN_ZOO_NAME + " like '%" + splitkeyWord[i]
                    + "%' OR " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%' OR " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i]
                    + "%')";
            if (i < splitkeyWord.length - 1) {
                sql += " AND ";
            }
        }

        sql += " ORDER BY " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " DESC";

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
            if (result.get(AsMotherId) != null) {
                motherAnimal.setAnimal_id((int) result.get(AsMotherId));
            }
            if (result.get(AsFatherId) != null) {
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
    public List<Animal> getAnimalListByZooId(int zoo_id) {
        String sql = "SELECT  DISTINCT " + ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + "" + ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_NAME + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME +
                " INNER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " ON " + ENTITY_ANIMAL.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " = " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID +
                " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID + " = ?";
        // SQL実行結果をMap型リストへ代入
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, zoo_id);

        List<Animal> returnAnimalList = new ArrayList();
        for (Map<String, Object> result : resultList) {
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
                "INSERT INTO " + ENTITY_ANIMAL.TABLE_NAME + "(" + ENTITY_ANIMAL.COLUMN_NAME + ", " + ENTITY_ANIMAL.COLUMN_SEX + ", " + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", " + ENTITY_ANIMAL.COLUMN_IS_ALIVE + ", " + ENTITY_ANIMAL.COLUMN_DEATH_DATE + ", " + ENTITY_ANIMAL.COLUMN_MOTHER + ", " + ENTITY_ANIMAL.COLUMN_FATHER + ", " + ENTITY_ANIMAL.COLUMN_DETAILS + ", " + ENTITY_ANIMAL.COLUMN_FEATURE + ", " + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "",
                animal.getName(), animal.getSex(), animal.getBirthdate(), animal.getIs_alive(), animal.getDeathdate(),
                animal.getMotherAnimal().getAnimal_id(), animal.getFatherAnimal().getAnimal_id(), animal.getDetails(), animal.getFeature(),
                animal.getProfileImagePath());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commonSqlUtil.updateAllCommonColumn(ENTITY_ANIMAL.TABLE_NAME, ENTITY_ANIMAL.COLUMN_ANIMAL_ID, (int) ((LoginUser) principal).getUser_id(), (int) insertId.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

        return (int) insertId.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID);
    }


    @Override
    public Animal findById(int id) {
        String sql = "SELECT " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + ", " +
                AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", " + AsMainAnimal + "." +
                ENTITY_ANIMAL.COLUMN_NAME + ", " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", " +
                AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", " + AsMainAnimal + "." +
                ENTITY_ANIMAL.COLUMN_IS_ALIVE + ", " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_DEATH_DATE + ", " +

                AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsMotherId + " , " +
                AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsFatherId + ", " +
                AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + ", " +
                AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", " +
                AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_DETAILS + ", " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FEATURE + " "

                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " = " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "WHERE  " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " = ?";

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);

        Animal animal = new Animal();
        animal.setProfileImagePath((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
        animal.setAnimal_id((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
        animal.setName((String) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_NAME));
        animal.setSex((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_SEX));
        animal.setBirthdate((Date) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_BIRTHDATE));
        animal.setIs_alive((int) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_IS_ALIVE));
        animal.setDeathdate((Date) resultList.get(0).get(ENTITY_ANIMAL.COLUMN_DEATH_DATE));
        Animal motherAnimal = new Animal();
        Animal fatherAnimal = new Animal();
        if (resultList.get(0).get(AsMotherId) != null) {
            motherAnimal.setAnimal_id((int) resultList.get(0).get(AsMotherId));
        }
        if (resultList.get(0).get(AsFatherId) != null) {
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
                "UPDATE " + ENTITY_ANIMAL.TABLE_NAME + " SET " + ENTITY_ANIMAL.COLUMN_NAME + "=?, " + ENTITY_ANIMAL.COLUMN_SEX + "=?," + ENTITY_ANIMAL.COLUMN_BIRTHDATE + "=?," + ENTITY_ANIMAL.COLUMN_IS_ALIVE + "=?," + ENTITY_ANIMAL.COLUMN_DEATH_DATE + "=?," + ENTITY_ANIMAL.COLUMN_MOTHER + "=?," + ENTITY_ANIMAL.COLUMN_FATHER + "=?," + ENTITY_ANIMAL.COLUMN_DETAILS + "=?," + ENTITY_ANIMAL.COLUMN_FEATURE + "=?, " + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " = ? WHERE " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " = ?",
                animal.getName(), animal.getSex(), animal.getBirthdate(), animal.getIs_alive(), animal.getDeathdate(),
                animal.getMotherAnimal().getAnimal_id(), animal.getFatherAnimal().getAnimal_id(), animal.getDetails(), animal.getFeature(),
                animal.getProfileImagePath(), animal.getAnimal_id());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_ANIMAL.TABLE_NAME, ENTITY_ANIMAL.COLUMN_ANIMAL_ID, (int) ((LoginUser) principal).getUser_id(), animal.getAnimal_id());

		/*jdbcTemplate.update("UPDATE "+ ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME  +" SET "+ ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID +"=? WHERE "+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" = ?", animal.getZoo(),
				animal.getAnimal_id());
		commonSqlUtil.updateOnlyUpdateCommonColumn( ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME,ENTITY_ANIMAL.COLUMN_ANIMAL_ID ,(int) ((LoginUser) principal).getUserId(),animal.getAnimal_id());*/
    }

    @Override
    public void urlUpdate(int animal_id, String url) {
        jdbcTemplate.update("UPDATE " + ENTITY_ANIMAL.TABLE_NAME + " SET " + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " = ? WHERE " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " = ?", url, animal_id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commonSqlUtil.updateOnlyUpdateCommonColumn(ENTITY_ANIMAL.TABLE_NAME, ENTITY_ANIMAL.COLUMN_ANIMAL_ID, (int) ((LoginUser) principal).getUser_id(), animal_id);
    }

    @Override
    public void delete(int animal_id) {
        jdbcTemplate.update("DELETE FROM " + ENTITY_ANIMAL.TABLE_NAME + " WHERE " + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " = ?", animal_id);
    }

    @Override
    public AnimalForTree getAnimalForTree(int id) {
        String sql = "SELECT "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + ", "
//				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , "
//				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "WHERE " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "= ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);
        AnimalForTree animal = new AnimalForTree();
        animal.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
        animal.setId((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
        animal.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
        animal.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
        animal.setMother_id((int) result.get(ENTITY_ANIMAL.COLUMN_MOTHER));
        animal.setFather_id((int) result.get(ENTITY_ANIMAL.COLUMN_FATHER));
        return animal;
    }

    @Override
    public List<AnimalForTree> getBrotherAnimalForTree(int animal_id, int mother_id, int father_id) {
        String sql = "SELECT "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + ", "
//				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , "
//				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "WHERE  " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + " = ? AND " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + " = ? AND NOT " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + "= ? "
                + "ORDER BY " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " ASC";

//		queryForListで実装予定
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, mother_id, father_id, animal_id);
        List<AnimalForTree> brotherList = new ArrayList<AnimalForTree>();

        for (Map<String, Object> result : resultList) {
            AnimalForTree brother = new AnimalForTree();
            brother.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
            brother.setId((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));
            brother.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
            brother.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
            brother.setMother_id((int) result.get(ENTITY_ANIMAL.COLUMN_MOTHER));
            brother.setFather_id((int) result.get(ENTITY_ANIMAL.COLUMN_FATHER));
            brotherList.add(brother);
        }
        return brotherList;
    }

    @Override
    public List<AnimalForTree> getChildrenAnimalForTree(int animal_id, int sex) {
        String sqlAsMother = "SELECT "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + ", "
//				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , "
//				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "WHERE  " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + " = ? "
                + "ORDER BY " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " ASC";


        String sqlAsFather = "SELECT "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER + ", "
//				+ AsMotherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsMotherId +" , "
//				+ AsFatherAnimal +"."+ ENTITY_ANIMAL.COLUMN_ANIMAL_ID +" as "+ AsFatherId +", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "WHERE  " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER + " = ? "
                + "ORDER BY " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " ASC";

//		queryForListで実装予定
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (sex == 2) {
            resultList = jdbcTemplate.queryForList(sqlAsMother, animal_id);
        } else {
            resultList = jdbcTemplate.queryForList(sqlAsFather, animal_id);
        }

        List<AnimalForTree> childrenList = new ArrayList<AnimalForTree>();

        for (Map<String, Object> result : resultList) {
            AnimalForTree children = new AnimalForTree();
            children.setName((String) result.get(ENTITY_ANIMAL.COLUMN_NAME));
            children.setId((int) result.get(ENTITY_ANIMAL.COLUMN_ANIMAL_ID));

            children.setProfileImagePath((String) result.get(ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE));
            children.setSex((int) result.get(ENTITY_ANIMAL.COLUMN_SEX));
            children.setMother_id((int) result.get(ENTITY_ANIMAL.COLUMN_MOTHER));
            children.setFather_id((int) result.get(ENTITY_ANIMAL.COLUMN_FATHER));
            childrenList.add(children);
        }
        return childrenList;
    }

    @Override
    public List<Animal> filter(AnimalFilterForm animalFilterForm) {
        String sql = "SELECT "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + ", "
                + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_NAME + ", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsMotherId + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " as " + AsFatherId + ", "
                + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsMotherName + " , "
                + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " as " + AsFatherName + ", "
                + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_PROFILE_IMAGE_TYPE + " "
                + "FROM " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMainAnimal

                + " LEFT OUTER JOIN "
                + " (SELECT " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + ", " + " MAX (" + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE + ") "
                + "FROM " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + " "
                + "GROUP BY " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID + ")"
                + ENTITY_ANIMAL_ZOO_HISTORY2
                + " ON " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2

                + " LEFT OUTER JOIN " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ANIMAL_ID
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + COLUMN_ANIMAL_ID2
                + " AND " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_EXIT_DATE
                + " = " + ENTITY_ANIMAL_ZOO_HISTORY2 + "." + "MAX" + " "

                + "LEFT OUTER JOIN " + ENTITY_ZOO.TABLE_NAME
                + " ON " + ENTITY_ANIMAL_ZOO_HISTORY.TABLE_NAME + "." + ENTITY_ANIMAL_ZOO_HISTORY.COLUMN_ZOO_ID
                + " = " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_PREFECTURE.TABLE_NAME
                + " ON " + ENTITY_ZOO.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID
                + " = " + ENTITY_PREFECTURE.TABLE_NAME + "." + ENTITY_PREFECTURE.COLUMN_PREFECTURE_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsMotherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_MOTHER
                + "  = " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " "
                + "LEFT OUTER JOIN " + ENTITY_ANIMAL.TABLE_NAME + " AS " + AsFatherAnimal
                + " on " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_FATHER
                + "  = " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_ANIMAL_ID + " ";

        //index画面からの遷移の場合、nullの場合がある
        if (animalFilterForm.getIsMale() != null) {
            if (animalFilterForm.getIsMale() || animalFilterForm.getIsFemale() || animalFilterForm.getIsDead() || animalFilterForm.getIsAlive()) {
                sql += "WHERE ";
                System.out.println("X");
            }

            if (animalFilterForm.getIsMale() && animalFilterForm.getIsFemale()) {
                System.out.println("A");
                sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + " = 1 OR ";
                sql += AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + " = 2 ) ";
            } else if (animalFilterForm.getIsMale()) {
                System.out.println("B");
                sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + " = 1 ) ";
            } else if (animalFilterForm.getIsFemale()) {
                System.out.println("C");
                sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_SEX + " = 2 ) ";
            }

            if (animalFilterForm.getIsMale() || animalFilterForm.getIsFemale()) {
                if (animalFilterForm.getIsAlive() && animalFilterForm.getIsDead()) {
                    System.out.println("D");
                    sql += "AND ( " + AsMainAnimal + "." + ENTITY_ANIMAL.getCOLUMN_IS_ALIVE() + " = 0 OR ";
                    sql += AsMainAnimal + "." + ENTITY_ANIMAL.getCOLUMN_IS_ALIVE() + " = 1 ) ";
                } else if (animalFilterForm.getIsAlive()) {
                    System.out.println("E");
                    sql += "AND ( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_IS_ALIVE + " = 1 ) ";
                } else if (animalFilterForm.getIsDead()) {
                    System.out.println("F");
                    sql += "AND ( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_IS_ALIVE + " = 0 ) ";
                }
            } else {
                if (animalFilterForm.getIsAlive() && animalFilterForm.getIsDead()) {
                    System.out.println("G");
                    sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.getCOLUMN_IS_ALIVE() + " = 0 OR ";
                    sql += AsMainAnimal + "." + ENTITY_ANIMAL.getCOLUMN_IS_ALIVE() + " = 1 ) ";
                } else if (animalFilterForm.getIsAlive()) {
                    System.out.println("H");
                    sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_IS_ALIVE + " = 1 ) ";
                } else if (animalFilterForm.getIsDead()) {
                    System.out.println("I");
                    sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_IS_ALIVE + " = 0 ) ";
                }
            }
        }

        if(!animalFilterForm.getZoo().isEmpty()) {
            if (animalFilterForm.getIsMale() || animalFilterForm.getIsFemale() || animalFilterForm.getIsDead() || animalFilterForm.getIsAlive()) {
                sql += "AND ( ";
                for (int i = 0; i < animalFilterForm.getZoo().size() - 1; i++) {
                    sql += ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " = " + animalFilterForm.getZoo().get(i) + " OR ";
                }
                sql += ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " = " + animalFilterForm.getZoo().get(animalFilterForm.getZoo().size() - 1);
                sql += ") ";
            } else {
                sql += "WHERE ( ";
                for (int i = 0; i < animalFilterForm.getZoo().size() - 1; i++) {
                    sql += ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " = " + animalFilterForm.getZoo().get(i) + " OR ";
                }
                sql += ENTITY_ZOO.TABLE_NAME + "." + ENTITY_ZOO.COLUMN_ZOO_ID + " = " + animalFilterForm.getZoo().get(animalFilterForm.getZoo().size() - 1);
                sql += ") ";
            }
        }

        if(animalFilterForm.getKeyword() != null) {
            String[] splitkeyWord = animalFilterForm.getKeyword().replaceAll(" ", "　").split("　", 0);
            if (animalFilterForm.getIsMale() || animalFilterForm.getIsFemale() || animalFilterForm.getIsDead()
                    || animalFilterForm.getIsAlive() || !animalFilterForm.getZoo().isEmpty()) {
                sql += "AND ( ";
                for (int i = 0; i < splitkeyWord.length; ++i) {
                    sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%' OR " + ENTITY_ZOO.COLUMN_ZOO_NAME + " like '%" + splitkeyWord[i]
                            + "%' OR " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%' OR " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%')";
                    if (i < splitkeyWord.length - 1) {
                        sql += " AND ";
                    }
                }
                sql += ") ";
            } else {
                sql += "WHERE ";
                for (int i = 0; i < splitkeyWord.length; ++i) {
                    sql += "( " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%' OR " + ENTITY_ZOO.COLUMN_ZOO_NAME + " like '%" + splitkeyWord[i]
                            + "%' OR " + AsMotherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%' OR " + AsFatherAnimal + "." + ENTITY_ANIMAL.COLUMN_NAME + " like '%" + splitkeyWord[i] + "%')";
                    if (i < splitkeyWord.length - 1) {
                        sql += " AND ";
                    }
                }
            }
        }

        sql += " ORDER BY " + AsMainAnimal + "." + ENTITY_ANIMAL.COLUMN_BIRTHDATE + " DESC";

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
            if (result.get(AsMotherId) != null) {
                motherAnimal.setAnimal_id((int) result.get(AsMotherId));
            }
            if (result.get(AsFatherId) != null) {
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
    public List<Animal> searchAnimals(Optional<String> keyword, Optional<String> zooId, Optional<String> animalId){
        /**
         * Entityとテーブルの関係性を整理し、SQLとAPの責務を整理する必要がある。
         * 例えば、中間テーブルも含め、Entityをテーブルと同一のデータを保持させ、シンプルにORMで取得し、APで返却用のデータを組み立てる。
         * 現状、中間テーブルも含め、そのほかのテーブルのデータをSQLで全て取得し、1つのanimalインスタンスにボイラーコードで詰め込んでいる。
         * https://www.docswell.com/s/MasatoshiTada/5Q4EMZ-spring-101#p30
         * https://www.docswell.com/s/MasatoshiTada/K7MM75-jdbc-intro
         * https://www.docswell.com/s/MasatoshiTada/596WW5-how-to-choose-java-orm
         */

        //SQLで使用するために、引数の文字列を全て、半角区切りに変換後に分割し、Collectionにする。
        String keywordList []
                = keyword.isPresent()
                ? keyword.get()
                .replaceAll(" ", ",")
                .replaceAll("　", ",")
                .split(",")
                : new String[0];

        String zooIdList []
                = zooId.isPresent()
                ? zooId.get()
                    .replaceAll(" ", ",")
                    .replaceAll("　", ",")
                    .split(",")
                : new String[0];

        String animalIdList []
                = animalId.isPresent()
                ? animalId.get()
                    .replaceAll(" ", ",")
                    .replaceAll("　", ",")
                    .split(",")
                : new String[0];

        String sql = """
            SELECT
                animal.animal_id,
                animal.animal_type_id,
                animal.name,
                animal.sex,
                animal.birthdate,
                animal.is_alive,
                animal.mother,
                animal.father,
                animal.details,
                animal.profile_image_type,
                animal.updated_by,
                animal.updated_date,
                animal_zoo_history.zoo_id,
                animal_zoo_history.admission_date,
                animal_zoo_history.exit_date,
                zoo.zoo_name,
                prefecture.prefecture_name,
                mother.animal_id AS mother_id,
                mother.name AS mother_name,
                father.animal_id AS father_id,
                father.name AS father_name
                
            FROM
                animal

            LEFT JOIN
                (
                    SELECT animal_id, MAX(exit_date)
                    FROM animal_zoo_history
                    GROUP BY animal_id
                )
                AS animal_zoo_history_tmp
                ON animal.animal_id = animal_zoo_history_tmp.animal_id
            
            LEFT JOIN
                animal_zoo_history
                ON animal_zoo_history.animal_id = animal.animal_id
                AND animal_zoo_history.exit_date = animal_zoo_history_tmp.MAX
            
            LEFT JOIN
                zoo ON animal_zoo_history.zoo_id = zoo.zoo_id

            LEFT JOIN
                prefecture ON zoo.prefecture_id = prefecture.prefecture_id
                
            LEFT JOIN
                animal AS mother ON animal.mother = mother.animal_id
                
            LEFT JOIN
                animal AS father ON animal.father = father.animal_id
                                
            """;

        //動的SQLを生成する
        if(keywordList.length > 0 && zooIdList.length > 0 && animalIdList.length > 0){
            sql += "WHERE ";
        }

        if(keywordList.length > 0){
            for(int i = 0; i < keywordList.length; i++){
                sql += """
                ( 
                    animal.name LIKE '%%s%' 
                    OR animal.details LIKE '%%s%'
                    OR zoo.zoo_name LIKE '%%s%'
                    OR mother.name LIKE '%%s%'
                    OR father.name LIKE '%%s%'
                )
                """.replaceAll("%s", keywordList[i]);

            if(i < keywordList.length - 1){
                    sql += " AND ";
                }
            }
        }
        
        if(zooIdList.length > 0 && !zooIdList[0].toString().isEmpty()){
        	if(keywordList.length > 0){
        		 sql += " AND ";
        	}
            for(int i = 0; i < zooIdList.length; i++){
                sql += """
                ( 
                    zoo.zoo_id =  '%s'
                )
                """.replaceAll("%s", zooIdList[i]);

            if(i < zooIdList.length - 1){
                    sql += " OR ";
                }
            }
        }


        // SQL実行結果をMap型リストへ代入
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        System.out.println(sql);
        System.out.println(resultList);
        System.out.println(keyword);
        System.out.println(keywordList);


        // view返却用のリストを生成
        List<Animal> animalsList = new ArrayList<Animal>();

        // MAP型リストからMapを繰り返し出力し、ObjectをAnimalインスタンスにsetする
        for (Map<String, Object> result : resultList) {
            Animal animal = new Animal();
            animal.setAnimal_id((int) result.get("animal_id"));
            animal.setName((String) result.get("name"));
            animal.setSex((int) result.get("sex"));
            animal.setBirthdate((Date) result.get("birthdate"));
            animal.setProfileImagePath((String) result.get("profile_image_type"));

            if (result.get("mother_id") != null) {
                Animal motherAnimal = new Animal();
                motherAnimal.setAnimal_id((int) result.get("mother_id"));
                motherAnimal.setName((String) result.get("mother_name"));
                animal.setMotherAnimal(motherAnimal);
            }

            if (result.get("father_id") != null) {
                Animal fatherAnimal = new Animal();
                fatherAnimal.setAnimal_id((int) result.get("father_id"));
                fatherAnimal.setName((String) result.get("father_name"));
                animal.setFatherAnimal(fatherAnimal);
            }

            if(result.get("zoo_id") != null){
                AnimalZooHistory animalZooHistory = new AnimalZooHistory();
                Zoo zoo = new Zoo();
                zoo.setZoo_id((int) result.get("zoo_id"));
                zoo.setZoo_name((String) result.get("zoo_name"));
                animalZooHistory.setZoo(zoo);
                animal.setAnimalZooHistoryList(Arrays.asList(animalZooHistory));
            }
            animalsList.add(animal);
        }
        return animalsList;
    }
}
