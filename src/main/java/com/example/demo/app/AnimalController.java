package com.example.demo.app;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalImage;
import com.example.demo.entity.AnimalZooHistory;
import com.example.demo.entity.Zoo;
import com.example.demo.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class AnimalController {

	//下記のリクエストで使用するために、AnimalService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final AnimalService animalService;

	@Autowired
	public AnimalController(AnimalService animalService) {
		this.animalService = animalService;
	}

	@Autowired
	@Qualifier("com.cloudinary.image.url")
	String cloudinaryImageUrl;

	//ドメインに対してリクエストが来た際には/indexを返す
	@GetMapping
	public String index() {
		return "index";
	}

	// /indexに対してリクエストが来た際には/indexを返す
	@GetMapping("/index")
	public String indexReload() {
		return "index";
	}


	/*@GetMapping("/search")
	public String displayAllAnimal(Model model, @ModelAttribute AnimalSearchForm animalSearchForm,
			BindingResult bindingResult) {
		List<Animal> list = animalService.getAll();
		for (Animal animal : list) {
			Date birthDate = (Date) animal.getBirthdate();
			animal.setStringBirthDate(disPlayDate(birthDate));
	
		}
		model.addAttribute("animalList", list);
		model.addAttribute("searchResult", "コアラ一覧");
		return "search";
	}*/

	@GetMapping("/search")
	public String displaySearchedKoara(Model model, @RequestParam(required = false, name = "keyword") String keyword,
			@ModelAttribute AnimalSearchForm animalSearchForm, BindingResult bindingResult) {
		List<Animal> list = new ArrayList<Animal>();
		if (keyword == null || keyword.replaceAll(" ", "　").split("　", 0).length == 0) {
			list = animalService.getAll();
			model.addAttribute("searchResult", "コアラ一覧");
		} else {
			list = animalService.findByKeyword(keyword);
			model.addAttribute("searchResult", "検索結果");
		}
		model.addAttribute("animalList", list);
		for (Animal animal : list) {
			Date birthDate = (Date) animal.getBirthdate();
			animal.setStringBirthDate(disPlayDate(birthDate));
			if(animal.getProfileImagePath() == null) {
				animal.setProfileImagePath("/images/defaultAnimal.png");
			}
		}
		return "search";

	}

	/**
	 * 性別の表示に使用するアイテム
	 */
	final static Map<Integer, String> SEX_ITEMS = new LinkedHashMap<Integer, String>() {
		{
			put(2, "女性");
			put(1, "男性");
			put(0, "不明");
		}
	};

	/**
	 * 生死の表示に使用するアイテム
	 */
	final static Map<Integer, String> IS_ALIVE_ITEMS = new LinkedHashMap<Integer, String>() {
		{
			put(1, "生存");
			put(0, "死亡");
		}
	};

	@GetMapping("/insert")
	public String getInsert(Model model, @ModelAttribute AnimalInsertForm form, boolean updateFlag) {
		// 新しいコアラ登録画面からか、編集画面のバリデーションでエラーが出て戻ってきたか判定
		if (updateFlag) {
			model.addAttribute("title", "コアラ編集画面");
			model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		} else {
			model.addAttribute("title", "コアラの登録");
			if(form.getAnimalZooHistory() == null) {
				Date dummyDate =  animalService.getDate("9999", "01", "01");
				AnimalZooHistory dummyHistory = new AnimalZooHistory();
				Zoo zoo = new Zoo();
				zoo.setZoo_id(0);
				dummyHistory.setZoo(zoo);
				dummyHistory.setAdmission_date(dummyDate);
				dummyHistory.setExit_date(dummyDate);
				form.setAnimalZooHistory(Arrays.asList(dummyHistory));		}
		}
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("isAliveItems", IS_ALIVE_ITEMS);
		List<Zoo> zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);

		List<Animal> motherList = animalService.getMotherList(form.getAnimal_id(), form.getBirthYear(),
				form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("motherList", motherList);
		List<Animal> fatherList = animalService.getFatherList(form.getAnimal_id(), form.getBirthYear(),
				form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("fatherList", fatherList);
		if (form.getProfileImagePath() == null) {
			form.setProfileImagePath("/images/defaultAnimal.png");
		}

		return "insert";
	}

	@PostMapping("/insert")
	public String insertAnimal(Model model, @Validated AnimalInsertForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// エラーになった場合、コアラの写真情報を引き継ぐための処理
			List<AnimalImage> animalImageList = animalService.findAnimalImageById(form.getAnimal_id());
			// 削除したい写真がある場合、表示しない
			if (form.getDeleteAnimalImageFiles() != null) {
				// 拡張子の前のanimalImageIdを取得
				String[] animalImageFiles = form.getDeleteAnimalImageFiles().split(",");
				// 削除したいanimalImageIdと表示しようとしているanimalImageIdが一致していたら表示させない
				for (int i = 0; i < animalImageFiles.length; ++i) {
					String animalImageId = animalImageFiles[i].split("\\.")[0];
					for (int index = 0; index < animalImageList.size(); ++index) {
						AnimalImage animalImage = animalImageList.get(index);
						if (animalImage.getAnimalimage_id() == Integer.parseInt(animalImageId)) {
							animalImageList.remove(index);
							break;
						}
					}
				}
			}
			
			form.setAnimalImageList(animalImageList);
			return getInsert(model, form, true);
		}

		if (form.getAnimal_id() == 0) {
			animalService.insert(form);
		} else {
			animalService.update(form);
		}
		return "redirect:/search";
	}

	@GetMapping("/detail/{id}")
	public String displayDetailAnimal(@PathVariable int id, Model model) {
		Animal animal = animalService.findById(id , true);
		model.addAttribute("title", "コアラ情報詳細");
		model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		Date birthDate = (Date) animal.getBirthdate();
		Date deathDate = (Date) animal.getDeathdate();
		String stringBirthDate = disPlayDate(birthDate);
		String stringDeathDate = disPlayDate(deathDate);
		animal.setStringBirthDate(stringBirthDate);
		animal.setStringDeathDate(stringDeathDate);
		setDefaultAnimalProfileImage(animal);
		model.addAttribute("detail", animal);	
		return "detail";
	}

	@GetMapping("/edit/{id}")
	public String editAnimal(@PathVariable int id, Model model, @ModelAttribute AnimalInsertForm form)
			throws ParseException {
		model.addAttribute("title", "コアラ編集画面");
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("isAliveItems", IS_ALIVE_ITEMS);
		model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		List<Zoo> zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);
		Animal animal = animalService.findById(id,false);
		form.setAnimal_id(animal.getAnimal_id());
		form.setName(animal.getName());
		form.setIs_alive(animal.getIs_alive());
		form.setSex(animal.getSex());
		String[] birthDate = animal.getBirthdate().toString().split("-");
		form.setBirthYear(birthDate[0]);
		birthDate[1] = zeroCut(birthDate[1], birthDate[0]);
		birthDate[2] = zeroCut(birthDate[2], birthDate[0]);
		form.setBirthMonth(birthDate[1]);
		form.setBirthDay(birthDate[2]);
		String[] deathDate = animal.getDeathdate().toString().split("-");
		form.setDeathYear(deathDate[0]);
		deathDate[1] = zeroCut(deathDate[1], deathDate[0]);
		deathDate[2] = zeroCut(deathDate[2], deathDate[0]);
		form.setDeathMonth(deathDate[1]);
		form.setDeathDay(deathDate[2]);
//		form.setZoo(animal.getZoo());
		form.setDetails(animal.getDetails());
		form.setFeature(animal.getFeature());
		form.setAnimalImageList(animal.getAnimalImageList());
		List<Animal> motherList = animalService.getMotherList(form.getAnimal_id(), form.getBirthYear(),
				form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("motherList", motherList);
		List<Animal> fatherList = animalService.getFatherList(form.getAnimal_id(), form.getBirthYear(),
				form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("fatherList", fatherList);
		form.setMother_id(animal.getMotherAnimal().getAnimal_id());
		form.setFather_id(animal.getFatherAnimal().getAnimal_id());
		setDefaultAnimalProfileImage(animal);
		form.setProfileImagePath(animal.getProfileImagePath());
		form.setAnimalZooHistory(animal.getAnimalZooHistoryList());
		return "insert";
	}

	@GetMapping("/delete/{animal_id}")
	public String getDelete(@PathVariable int animal_id) {
		animalService.delete(animal_id);
		return "redirect:/search";
	}

	/**
	 * 0を省いた数字を返す
	 * 
	 * @param number 月or日の数字
	 * @param year   年
	 * @return 0落ちさせた数字
	 */
	private String zeroCut(String number, String year) {
		if (year.equals("9999")) {
			return "0";
		} else {
			String cutNumber = number.substring(0, 1);
			if (cutNumber.equals("0")) {
				number = number.substring(1, 2);
			}
		}
		return number;
	}

	/**
	 * yyyy年m月d日にした文字列を返す
	 * 
	 * @param Date date
	 * @return yyyy年m月d日の文字列
	 */
	private String disPlayDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		return sdf.format(date);
	}

	/**
	 * コアラにプロフィール画像がセットされていない場合、デフォルトの画像をセットする
	 * 
	 * @param Animal animal
	*/
	private void setDefaultAnimalProfileImage(Animal animal) {
		if (animal.getProfileImagePath() == null) {
			animal.setProfileImagePath("/images/defaultAnimal.png");
		}
	}
	
	@GetMapping("/familytree")
	public String familytreeDisplay(@RequestParam(required = false, name = "id") int id, Model model) throws Exception {
		model.addAttribute("id", id);
        return "familytree";
	}
	
	@GetMapping("/familytreeAnimal")
	@ResponseBody
	public String getAnimalForTree(@RequestParam(required = false, name = "id") int id) throws Exception {
		Map<String, Object> animalForTree = animalService.getAnimalForTree(id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(animalForTree);
		return json;
	}
	
}
