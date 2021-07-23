package com.example.demo.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Zoo;
import com.example.demo.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {
	private PostService postService;
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/{id}")
	public String getNewParentPost(@PathVariable int id, Model model) {
		Zoo zoo = postService.getZooById(id);
		model.addAttribute("zoo",zoo);
		model.addAttribute("title", zoo.getZoo_name() + "に投稿");
		
		return "post/postInsert";
	}

}
