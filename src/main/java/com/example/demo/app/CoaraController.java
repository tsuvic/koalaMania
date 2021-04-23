package com.example.demo.app;

import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/detail/{id}")
	public String displayDetailCoara(@PathVariable Long id, Model model) {
		Coara coara = coaraService.findById(id);
		model.addAttribute("detail", coara);
		return "/detail";
	}
	
}
