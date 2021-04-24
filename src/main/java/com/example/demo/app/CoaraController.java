package com.example.demo.app;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		return"/index";
	}
	
	// /indexに対してリクエストが来た際には/indexを返す
	@GetMapping("/index")
	public String indexReload() {
		return"/index";
	}


	@GetMapping("/search")
	public String displayAllCoara(Model model) {
		List<Coara> list = coaraService.getAll();
		model.addAttribute("coaraList", list);
		model.addAttribute("searchResult","検索結果一覧");
		
		return "/search";
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
	 * 性別の表示に使用するアイテム
	 */
	final static Map<Integer, String> IS_ALIVE_ITEMS =
		   (Map<Integer, String>) new LinkedHashMap<Integer, String> (){{
		      put(1, "生存");
		      put(0, "死亡");
  	}};
	
	@GetMapping("/insert")
	public String insert(Model model) {
		model.addAttribute("title","コアラ追加");
		model.addAttribute("sexItems",SEX_ITEMS);
		model.addAttribute("isAliveItems",IS_ALIVE_ITEMS);
		return "/insert";
	}
	
	@Autowired
	CoaraService service;
	@PostMapping("/insert")
	public String insertCoara(Model model,CoaraInsertForm form) {
		service.insert(form);
		List<Coara> list = coaraService.getAll();
		model.addAttribute("coaraList", list);
		model.addAttribute("title","コアラ一覧");
		return "/search";
	}
	
	@GetMapping("/detail/{id}")
	public String displayDetailCoara(@PathVariable Long id, Model model) {
		Coara coara = coaraService.findById(id);
		model.addAttribute("detail", coara);
		return "/detail";
	}
	
}
