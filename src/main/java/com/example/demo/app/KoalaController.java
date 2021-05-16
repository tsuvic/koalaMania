package com.example.demo.app;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.Zoo;
import com.example.demo.service.KoalaService;

@Controller
@RequestMapping("/")
public class KoalaController {

//	下記のリクエストで使用するために、KoalaService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final KoalaService koalaService;

	@Autowired
	public KoalaController(KoalaService koalaService) {
		this.koalaService = koalaService;
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
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/search")
	public String displayAllKoala(Model model, @ModelAttribute KoalaSearchForm koalaSearchForm,
			BindingResult bindingResult) {
		List<Koala> list = koalaService.getAll();
		for (Koala koala : list) {
			Date birthDate = (Date) koala.getBirthdate();
			koala.setStringBirthDate(disPlayDate(birthDate));

		}
		model.addAttribute("koalaList", list);
		model.addAttribute("searchResult", "コアラ一覧");
		return "search";
	}

	@PostMapping("/search")
	public String displaySearchedKoara(Model model, @RequestParam(required = false, name = "keyword") String keyword,
			@ModelAttribute KoalaSearchForm koalaSearchForm, BindingResult bindingResult) {
		List<Koala> list = new ArrayList<Koala>();
		if(keyword==null || keyword.replaceAll(" ", "　").split("　",0).length == 0) {
			list = koalaService.getAll();
		}else {
			list = koalaService.findByKeyword(keyword);
		}
		model.addAttribute("koalaList", list);
		model.addAttribute("searchResult", "検索結果");
		for (Koala koala : list) {
			Date birthDate = (Date) koala.getBirthdate();
			koala.setStringBirthDate(disPlayDate(birthDate));
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
	public String getInsert(Model model, @ModelAttribute KoalaInsertForm form, boolean updateFlag) {
		// 新しいコアラ登録画面からか、編集画面のバリデーションでエラーが出て戻ってきたか判定
		if (updateFlag) {
			model.addAttribute("title", "コアラ編集画面");
			model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		} else {
			model.addAttribute("title", "コアラの登録");
		}
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("isAliveItems", IS_ALIVE_ITEMS);
		List<Zoo> zooList = koalaService.getZooList();
		model.addAttribute("zooList", zooList);

		List<Koala> motherList = koalaService.getMotherList(form.getKoala_id(), form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("motherList", motherList);
		List<Koala> fatherList = koalaService.getFatherList(form.getKoala_id(), form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("fatherList", fatherList);
		
		return "insert";
	}

	@PostMapping("/insert")
	public String insertKoala(Model model, @Validated KoalaInsertForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// エラーになった場合、コアラの写真情報を引き継ぐための処理
			List<KoalaImage> koalaImageList = koalaService.findKoalaImageById(form.getKoala_id());
			// 削除したい写真がある場合、表示しない
			if (form.getDeleteKoalaImageFiles() != null) {
				// 拡張子の前のkoalaImageIdを取得
				String[] koalaImageFiles = form.getDeleteKoalaImageFiles().split(",");
				// 削除したいkoalaImageIdと表示しようとしているkoalaImageIdが一致していたら表示させない
				for (int i = 0; i < koalaImageFiles.length; ++i) {
					String koalaImageId = koalaImageFiles[i].split("\\.")[0];
					for (int index = 0; index < koalaImageList.size(); ++index) {
						KoalaImage koalaImage = koalaImageList.get(index);
						if (koalaImage.getKoalaimage_id() == Integer.parseInt(koalaImageId)) {
							koalaImageList.remove(index);
							break;
						}
					}
				}
			}
			form.setKoalaImageList(koalaImageList);
			return getInsert(model, form, true);
		}
		
		if (form.getKoala_id() == 0) {
			koalaService.insert(form);
		} else {
			koalaService.update(form);
		}
		return "redirect:/search";
	}

	@GetMapping("/detail/{id}")
	public String displayDetailKoala(@PathVariable int id, Model model) {
		Koala koala = koalaService.findById(id);
		model.addAttribute("title", "コアラ情報詳細");
		model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		Date birthDate = (Date) koala.getBirthdate();
		Date deathDate = (Date) koala.getDeathdate();
		String stringBirthDate = disPlayDate(birthDate);
		String stringDeathDate = disPlayDate(deathDate);
		koala.setStringBirthDate(stringBirthDate);
		koala.setStringDeathDate(stringDeathDate);
		model.addAttribute("detail", koala);
		return "detail";
	}

	@GetMapping("/edit/{id}")
	public String editKoala(@PathVariable int id, Model model, @ModelAttribute KoalaInsertForm form)
			throws ParseException {
		model.addAttribute("title", "コアラ編集画面");
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("isAliveItems", IS_ALIVE_ITEMS);
		model.addAttribute("cloudinaryImageUrl", cloudinaryImageUrl);
		List<Zoo> zooList = koalaService.getZooList();
		model.addAttribute("zooList", zooList);
		Koala koala = koalaService.findById(id);
		model.addAttribute("koalaProfileImage", koala.getKoalaProfileImage());
		form.setKoala_id(koala.getKoala_id());
		form.setName(koala.getName());
		form.setIs_alive(koala.getIs_alive());
		form.setSex(koala.getSex());
		String[] birthDate = koala.getBirthdate().toString().split("-");
		form.setBirthYear(birthDate[0]);
		birthDate[1] = zeroCut(birthDate[1], birthDate[0]);
		birthDate[2] = zeroCut(birthDate[2], birthDate[0]);
		form.setBirthMonth(birthDate[1]);
		form.setBirthDay(birthDate[2]);
		String[] deathDate = koala.getDeathdate().toString().split("-");
		form.setDeathYear(deathDate[0]);
		deathDate[1] = zeroCut(deathDate[1], deathDate[0]);
		deathDate[2] = zeroCut(deathDate[2], deathDate[0]);
		form.setDeathMonth(deathDate[1]);
		form.setDeathDay(deathDate[2]);
		form.setZoo(koala.getZoo());
		form.setDetails(koala.getDetails());
		form.setFeature(koala.getFeature());
		form.setKoalaImageList(koala.getKoalaImageList());
		List<Koala> motherList = koalaService.getMotherList(form.getKoala_id(), form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("motherList", motherList);
		List<Koala> fatherList = koalaService.getFatherList(form.getKoala_id(), form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		model.addAttribute("fatherList", fatherList);
		form.setMother_id(koala.getMother_id());
		form.setFather_id(koala.getFather_id());
		return "insert";
	}

	@GetMapping("/delete/{koala_id}")
	public String getDelete(@PathVariable int koala_id) {
		koalaService.delete(koala_id);
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
}
