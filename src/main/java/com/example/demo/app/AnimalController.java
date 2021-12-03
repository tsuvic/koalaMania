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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalZooHistory;
import com.example.demo.entity.Zoo;
import com.example.demo.service.AnimalService;
import com.example.demo.service.PostImageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class AnimalController {
	private final AnimalService animalService;
	private final PostImageService postImageService;
	private final ZooHistoryValidator zooHistoryValidator;
	
	@Autowired
	public AnimalController(AnimalService animalService,PostImageService postImageService, ZooHistoryValidator zooHistoryValidator) {
		this.animalService = animalService;
		this.postImageService = postImageService;
		this.zooHistoryValidator = zooHistoryValidator;
	}

    @InitBinder("animalInsertForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(zooHistoryValidator);
    }
	
	@Autowired
	@Qualifier("com.cloudinary.image.url")
	String cloudinaryImageUrl;

	@GetMapping("/")
	public String indexDisplay(Model model) {
		var zooList = animalService.getZooList();
		zooList.remove(0);
		model.addAttribute("zooList", zooList);
		return "index";
	}

	@GetMapping("/search")
	public String displaySearchedKoara(Model model, @RequestParam(required = false, name = "keyword") String keyword,
			@ModelAttribute AnimalSearchForm animalSearchForm, BindingResult bindingResult) {
		List<Animal> list = new ArrayList<Animal>();
		if (keyword == null || keyword.replaceAll(" ", "　").split("　", 0).length == 0) {
			list = animalService.getAll();
			model.addAttribute("searchResult", "検索結果一覧");
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
			put(2, "メス");
			put(1, "オス");
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
	public String getInsert(Model model, @ModelAttribute AnimalInsertForm form) {
		model.addAttribute("title", "コアラ情報登録");
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("isAliveItems", IS_ALIVE_ITEMS);
		List<Zoo> zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);
		List<Animal> motherList = animalService.getMotherList(form.getAnimal_id(), form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("motherList", motherList);
		List<Animal> fatherList = animalService.getFatherList(form.getAnimal_id(), form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("fatherList", fatherList);
		
		if (form.getInsertZoo() == null ) {
			List<Integer> insertZoo = new ArrayList<Integer>();
			insertZoo.add(-1);
			form.setInsertZoo(insertZoo);
		}
		
		if(form.getAdmissionYear() == null) {
			List<String> admissionYear = new ArrayList<String>();
			admissionYear.add("9999");
			form.setAdmissionYear(admissionYear);
		}

		if(form.getAdmissionMonth() == null) {
			List<String> admissionMonth = new ArrayList<String>();
			admissionMonth.add("0");
			form.setAdmissionMonth(admissionMonth);
		}
		
		if(form.getAdmissionDay() == null) {
			List<String> admissionDay = new ArrayList<String>();
			admissionDay.add("0");
			form.setAdmissionDay(admissionDay);
		}
		
		if(form.getExitYear() == null) {
			List<String> exitYear = new ArrayList<String>();
			exitYear.add("9999");
			form.setExitYear(exitYear);
		}

		if(form.getExitMonth() == null) {
			List<String> exitMonth = new ArrayList<String>();
			exitMonth.add("0");
			form.setExitMonth(exitMonth);
		}
		
		if(form.getExitDay() == null) {
			List<String> exitDay = new ArrayList<String>();
			exitDay.add("0");
			form.setExitDay(exitDay);
		}
		
		if (form.getProfileImagePath() == null) {
			form.setProfileImagePath("/images/defaultAnimal.png");
		}
		
		return "insert";
	}

	@PostMapping("/insert")
	public String insertAnimal(Model model, @Validated AnimalInsertForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			if (form.getAnimal_id() == 0) {
				getInsert(model, form);
			} else {
				editAnimal(form.getAnimal_id(), model, form);
			}
			System.out.println(bindingResult);
				return "insert";
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
		Animal animal = animalService.findById(id);
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
		model.addAttribute("postImageList", postImageService.getPostImageListByAnimalId(id));
		return "detail";
	}

	@GetMapping("/edit/{id}")
	public String editAnimal(@PathVariable int id, Model model, @ModelAttribute AnimalInsertForm form){
		model.addAttribute("title", "コアラ情報編集");
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("isAliveItems", IS_ALIVE_ITEMS);
		model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		List<Zoo> zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);
		Animal animal = animalService.findById(id);
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
		form.setDetails(animal.getDetails());
		form.setFeature(animal.getFeature());
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
		
//		既存のコアラ情報修正の画面表示で、動物園履歴が取得できない。
//		なぜならformにanimalZooHistroyを詰めることになっているから。
//		formに年月日の配列を詰めるように修正する。メソッドの切り出し等のリファクタリングは後回し。
//		controllerが肥大化してる。entityからformにセットするのはcontrollerの責務。serviceの責務にすべき箇所か。
//		animalエンティティと併せて、zooHistoryエンティティのリストをserviceからcontrollerに渡す等。
		 List<String> admissionYear = new ArrayList<String>();
		 List<String> admissionMonth= new ArrayList<String>();
		 List<String> admissionDay= new ArrayList<String>();
		 List<String> exitYear= new ArrayList<String>();
		 List<String> exitMonth= new ArrayList<String>();
		 List<String> exitDay= new ArrayList<String>();
		 List<Integer> insertZoo= new ArrayList<Integer>(); 
		 List<AnimalZooHistory> animalZooHistoryList = animal.getAnimalZooHistoryList();
		
		SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat getMonthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat getDateFormat = new SimpleDateFormat("dd");
		
		for (AnimalZooHistory animalZooHistory : animalZooHistoryList) {
			admissionYear.add(getYearFormat.format(animalZooHistory.getAdmission_date()).toString());
			admissionMonth.add(getMonthFormat.format(animalZooHistory.getAdmission_date()).toString());
			admissionDay.add(getDateFormat.format(animalZooHistory.getAdmission_date()).toString());
			exitYear.add(getYearFormat.format(animalZooHistory.getExit_date()).toString());
			exitMonth.add(getMonthFormat.format(animalZooHistory.getExit_date()).toString());
			exitDay.add(getDateFormat.format(animalZooHistory.getExit_date()).toString());
			insertZoo.add(animalZooHistory.getZoo().getZoo_id());
		}
		
		form.setAnimalZooHistory(animal.getAnimalZooHistoryList()); //詳細情報表示画面用にanimalのanimalzooHistoryListは残す
		form.setAdmissionYear(admissionYear);
		form.setAdmissionMonth(admissionMonth);
		form.setAdmissionDay(admissionDay);
		form.setExitYear(exitYear);
		form.setExitMonth(exitMonth);
		form.setExitDay(exitDay);
		form.setInsertZoo(insertZoo);
		
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
	
	@GetMapping("/discription")
	public String discriptionDisplay(Model model) {
		model.addAttribute("searchResult", "コアラマニアとは");
        return "discription";
	}
	
}
