package com.example.demo.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

	public PostController() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@GetMapping("/{id}")
	public String getNewParentPost(@PathVariable int id, Model model) {
		model.addAttribute("zoo", null);
		
		return "post/insert";
	}

}
