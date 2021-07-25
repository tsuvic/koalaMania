package com.example.demo.app;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Zoo;
import com.example.demo.service.PostService;


@Controller
@RequestMapping("/post")
public class PostController {
	private PostService postService;
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/{zoo_id}")
	public String getNewParentPost(@PathVariable int zoo_id, Model model,@ModelAttribute PostInsertForm postInsertForm) {
		Zoo zoo = postService.getZooById(zoo_id);
		List<Animal> animalList = postService.getAnimalListByZooId(zoo_id);
		model.addAttribute("zoo",zoo);
		model.addAttribute("title", zoo.getZoo_name() + "に投稿");
		model.addAttribute("animalList", animalList);
		postInsertForm.setParent_id(0);
		postInsertForm.setZoo_id(zoo_id);
		
		return "post/postInsert";
	}
	
	@PostMapping("/insert")
	public String postNewParentPost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.insertNewPost(postInsertForm);
		
		return "redirect:/zoo/detail/" + postInsertForm.getZoo_id();
	}

}
