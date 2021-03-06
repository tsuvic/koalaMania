package com.example.demo.app;

import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalZooHistory;
import com.example.demo.entity.Zoo;
import com.example.demo.service.AnimalService;
import com.example.demo.service.PostImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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

	@GetMapping({"/", "/index"})
	public String indexDisplay(Model model, @ModelAttribute AnimalFilterForm animalSearchForm, BindingResult bindingResult) {
		var zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);
		return "index";
	}
	@GetMapping("/static/index")
	public String displaySpaTestPage(){
		return "../static/index";
	}

	@GetMapping("/search")
	public String displaySearchedKoala(Model model, @RequestParam(required = false, name = "keyword") String keyword,
									   @ModelAttribute AnimalFilterForm animalFilterForm, @ModelAttribute AnimalSearchForm animalSearchForm, BindingResult bindingResult) {
		List<Animal> list = new ArrayList<Animal>();
		if (keyword == null || keyword.replaceAll(" ", "???").split("???", 0).length == 0) {
			list = animalService.getAll();
		} else {
			list = animalService.findByKeyword(keyword);
		}

		for (Animal animal : list) {
			Date birthDate = (Date) animal.getBirthdate();
			animal.setStringBirthDate(disPlayDate(birthDate));
			if (animal.getProfileImagePath() == null) {
				animal.setProfileImagePath("/images/defaultAnimal.png");
			}
		}

		var zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);
		model.addAttribute("animalList", list);
		model.addAttribute("searchResult", "??????????????????");
		return "search";
	}

	@GetMapping("/filter")
	public String displayFilteredKoala(Model model, @ModelAttribute AnimalFilterForm animalFilterForm, BindingResult bindingResult) {

		System.out.println(animalFilterForm);
//		???????????????????????????????????????????????????????????????????????????????????????
//		animalSearchForm?????????Service??????????????????????????????????????????
//		?????????search?????????????????????filetr?????????????????????????????????????????????

		List<Animal> animalList = new ArrayList<>();
		animalList = animalService.animalFilter(animalFilterForm);

		var zooList = animalService.getZooList();
		model.addAttribute("zooList", zooList);
		model.addAttribute("searchResult", "??????????????????");
		model.addAttribute("animalList", animalList);

		for (Animal animal : animalList) {
			Date birthDate = animal.getBirthdate();
			animal.setStringBirthDate(disPlayDate(birthDate));
			if(animal.getProfileImagePath() == null) {
				animal.setProfileImagePath("/images/defaultAnimal.png");
			}
		}
		return "search";
	}

	/**
	 * ??????????????????????????????????????????
	 */
	final static Map<Integer, String> SEX_ITEMS = new LinkedHashMap<Integer, String>() {
		{
			put(2, "??????");
			put(1, "??????");
			put(0, "??????");
		}
	};

	/**
	 * ??????????????????????????????????????????
	 */
	final static Map<Integer, String> IS_ALIVE_ITEMS = new LinkedHashMap<Integer, String>() {
		{
			put(1, "??????");
			put(0, "??????");
		}
	};
	
	@GetMapping("/insert") 
	public String getInsert(Model model, @ModelAttribute AnimalInsertForm form) {
		model.addAttribute("title", "?????????????????????");
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
		model.addAttribute("title", "?????????????????????");
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
		model.addAttribute("title", "?????????????????????");
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
		
//		??????????????????????????????????????????????????????????????????????????????????????????
//		????????????form???animalZooHistroy?????????????????????????????????????????????
//		form?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//		controller????????????????????????entity??????form????????????????????????controller????????????service?????????????????????????????????
//		animal?????????????????????????????????zooHistory?????????????????????????????????service??????controller???????????????
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
		
		form.setAnimalZooHistory(animal.getAnimalZooHistoryList()); //??????????????????????????????animal???animalzooHistoryList?????????
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
	 * 0???????????????????????????
	 * 
	 * @param number ???or????????????
	 * @param year   ???
	 * @return 0?????????????????????
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
	 * yyyy???m???d??????????????????????????????
	 * 
	 * @param date
	 * @return yyyy???m???d???????????????
	 */
	private String disPlayDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy???M???d???");
		return sdf.format(date);
	}

	/**
	 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param animal
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
	
	@GetMapping("/description")
	public String descriptionDisplay(Model model) {
		model.addAttribute("searchResult", "????????????????????????");
        return "description";
	}
	
}
