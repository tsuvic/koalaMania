package com.example.demo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Animal;
import com.example.demo.repository.AnimalDao;
import com.example.demo.repository.AnimalZooHistoryDao;
import com.example.demo.repository.ZooDao;

@DisplayName("Test AnimalServiceImpl")
@SpringBootTest
class AnimalServiceImplTest {
	@Autowired
	AnimalDao animalDao;

	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	AnimalZooHistoryDao animalZooHistoryDao;

	@Autowired
	ZooDao zooDao;
	
	@Autowired
	AnimalServiceImpl animalServiceImpl = new AnimalServiceImpl(animalDao, cloudinaryService, animalZooHistoryDao, zooDao);
	
	@BeforeEach
	public void setUp() throws Exception{
	}
	
	@Test
	void getAll_アニマル型のリストを返却() {
		var animalList = new ArrayList<Animal>();
		final List<Animal> actual = animalServiceImpl.getAll();
		assertEquals(actual.getClass(), animalList.getClass());
	}
	
	@Test
	@DisplayName("コアラ名で検索_対象を含む結果を返却")
	void findbyKeyword() {
		final List<Animal> actual = animalServiceImpl.findByKeyword("コタロウ");
		assertTrue(actual.stream().anyMatch(o -> o.getName().equals("コタロウ")));
		assertTrue(actual.stream().anyMatch(o -> o.getFatherAnimal().getName().equals("コタロウ")));
	}	
	
	@DisplayName("動物園名で検索_対象を含む結果を返却")
	@Test
	void findbyKeyword2() {
		final List<Animal> actual = animalServiceImpl.findByKeyword("多摩");
		assertEquals("多摩動物公園", actual.get(0).getAnimalZooHistoryList().get(0).getZoo().getZoo_name());
		assertTrue(actual.stream().allMatch(o -> o.getAnimalZooHistoryList().stream().allMatch(x -> x.getZoo().getZoo_name().equals("多摩動物公園"))));
	}
	
	@DisplayName("コアラ情報登録時の母親リストを返却「コタロウ(ID:52)」")
	@Test
	void getMotherList() throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date testDate = sdf.parse("2015-09-25");
		 Date filterDate = sdf.parse("9999-01-01");
		final List<Animal> motherList = animalServiceImpl.getMotherList(52, "2015", "9", "25");
		motherList.stream().map(x -> x.getSex()).forEach(System.out::println);
		System.out.println(testDate);
		motherList.stream().filter(x -> x.getBirthdate() != filterDate && x.getBirthdate() != null).map(x -> x.getBirthdate()).forEach(System.out::println);
		assertTrue(motherList.stream().allMatch(o -> o.getSex() != 1)); //メスは2であるが、母親不明時にセットするフォーム用データに性別はセットされてないため、!=1とする
		assertTrue(motherList.stream().filter(x -> x.getBirthdate() != filterDate && x.getBirthdate() != null).allMatch(o -> o.getBirthdate().before(testDate))); //誕生日不明の9999年生まれのコアラ、nullを除く
		
	}
	
}
