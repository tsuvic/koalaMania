package com.example.demo.app;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Coara;
import com.example.demo.service.CoaraService;


@Controller
@RequestMapping("/")  
public class CoaraController {
	
//	下記のリクエストで使用するために、CoaraService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final CoaraService coaraService;

	@Autowired
	public CoaraController(CoaraService coaraService) {
		this.coaraService = coaraService;
	}	

//ドメインに対してリクエストが来た際には/indexを返す
	@GetMapping
	public String index() {
		return"index";
	}
	
	// /indexに対してリクエストが来た際には/indexを返す
	@GetMapping("/index")
	public String indexReload() {
		return"index";
	}


	@GetMapping("/search")
	public String displayAllCoara(Model model) {
		List<Coara> list = coaraService.getAll();
		for(Coara coara : list) {
			Date birthDate = (Date) coara.getBirthdate();
			Date deathDate = (Date) coara.getDeathdate();
			coara.setStringBirthDate(disPlayDate(birthDate));
			coara.setStringDeathDate(disPlayDate(deathDate));
		}
		model.addAttribute("coaraList", list);
		model.addAttribute("searchResult","検索結果一覧");
		return "search";
	}
	
	/**
	 * 性別の表示に使用するアイテム
	 */
	final static Map<Integer, String> SEX_ITEMS =
	   (Map<Integer, String>) new LinkedHashMap<Integer, String> (){{
		  put(2, "女性");
	      put(1, "男性");
	      put(0, "不明");
	}};
	   
	/**
	 * 生死の表示に使用するアイテム
	 */
	final static Map<Integer, String> IS_ALIVE_ITEMS =
		   (Map<Integer, String>) new LinkedHashMap<Integer, String> (){{
		      put(1, "生存");
		      put(0, "死亡");
  	}};
	
	@GetMapping("/insert")
	public String getInsert(Model model,@ModelAttribute CoaraInsertForm form) {
		if(model.getAttribute("title")==null) {
			model.addAttribute("title","コアラの登録");
			model.addAttribute("sexItems",SEX_ITEMS);
			model.addAttribute("isAliveItems",IS_ALIVE_ITEMS);
		}
		return "insert";
	}

	@Autowired
	CoaraService service;
	@PostMapping("/insert")
	public String insertCoara(Model model,@Validated CoaraInsertForm form,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return getInsert(model,form);
		}
		if(form.getCoara_id() == 0){
			service.insert(form);
		}else {
			service.update(form);
		}
		return "redirect:/search";
	}
	
	@GetMapping("/detail/{id}")
	public String displayDetailCoara(@PathVariable Long id, Model model){
		Coara coara = coaraService.findById(id);
		model.addAttribute("title","コアラ情報詳細");
		Date birthDate = (Date) coara.getBirthdate();
		Date deathDate = (Date) coara.getDeathdate();
		String stringBirthDate =  disPlayDate(birthDate);
		String stringDeathDate =  disPlayDate(deathDate);
		coara.setStringBirthDate(stringBirthDate);
		coara.setStringDeathDate(stringDeathDate);
		model.addAttribute("detail", coara);
		return "detail";
	}
	
	@GetMapping("/edit/{id}")
	public String editCoara(@PathVariable Long id, Model model,@ModelAttribute CoaraInsertForm form) throws ParseException {
		model.addAttribute("title","コアラ編集画面");
		model.addAttribute("sexItems",SEX_ITEMS);
		model.addAttribute("isAliveItems",IS_ALIVE_ITEMS);
		Coara coara = coaraService.findById(id);
		form.setCoara_id(coara.getCoara_id());
		form.setName(coara.getName());
		form.setIs_alive(coara.getIs_alive());
		form.setSex(coara.getSex());
		String[] birthDate = coara.getBirthdate().toString().split("-");
		form.setBirthYear(birthDate[0]);
		birthDate[1] = zeroCut(birthDate[1],birthDate[0]);
		birthDate[2] = zeroCut(birthDate[2],birthDate[0]);
		form.setBirthMonth(birthDate[1]);
		form.setBirthDay(birthDate[2]);
		String[] deathDate = coara.getDeathdate().toString().split("-");
		form.setDeathYear(deathDate[0]);
		deathDate[1] = zeroCut(deathDate[1],deathDate[0]);
		deathDate[2] = zeroCut(deathDate[2],deathDate[0]);
		form.setDeathMonth(deathDate[1]);
		form.setDeathDay(deathDate[2]);
		form.setZoo(coara.getZoo());
		form.setMother(coara.getMother());
		form.setFather(coara.getFather());
		form.setDetails(coara.getDetails());
		form.setFeature(coara.getFeature());
		return "insert";
	}
	
	@GetMapping("/delete/{coara_id}")
	public String getDelete(@PathVariable int coara_id) {
		service.delete(coara_id);
		return "redirect:/search";
	}
	
	 /**
	   * 0を省いた数字を返す
	   * @param number 月or日の数字
	   * @param year 年
	   * @return 0落ちさせた数字
	   */
	private String zeroCut(String number,String year) {
		if(year.equals("9999")) {
			return "0";
		}else {
			String cutNumber = number.substring(0,1);
			if(cutNumber.equals("0")){
				number = number.substring(1,2);
			}
		}
		return number;
	}
	
	/**
	   * yyyy年m月d日にした文字列を返す
	   * @param Date date
	   * @return yyyy年m月d日の文字列
	   */
	private String disPlayDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		return  sdf.format(date);
	}
}
