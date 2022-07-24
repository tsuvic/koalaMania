package com.example.demo.app;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import com.example.demo.service.AnimalService;
import com.example.demo.service.PostFavoriteService;
import com.example.demo.service.PostImageFavoriteService;
import com.example.demo.service.PostService;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/post")
public class PostController {
	private final PostService postService;
	private final PostFavoriteService postFavoriteService;
	private final PostImageFavoriteService postImageFavoriteService;
	private final AnimalService animalSearvice;
	private final DateUtil dateUtil;
	
	@Autowired
	public PostController(PostService postService,PostFavoriteService postFavoriteService ,PostImageFavoriteService postImageFavoriteService, AnimalService animalService , DateUtil dateUtil) {
		this.postService = postService;
		this.postFavoriteService = postFavoriteService;
		this.postImageFavoriteService = postImageFavoriteService;
		this.animalSearvice = animalService;
		this.dateUtil = dateUtil;
	}
	
	@GetMapping
	public String getNewPost(Model model,@ModelAttribute PostInsertForm postInsertForm) {
		var zooList = animalSearvice.getZooList();
		model.addAttribute("zooList", zooList);		
		postInsertForm.setParentId(0);
		return "post/postInsert2";
	}
	
	@GetMapping("/postInsert//{zooId}")
	public String getNewParentPost(@PathVariable int zooId, Model model,@ModelAttribute PostInsertForm postInsertForm) {
		Zoo zoo = postService.getZooById(zooId);
		List<Animal> animalList = postService.getAnimalListByZooId(zooId);
		model.addAttribute("zoo",zoo);
		model.addAttribute("title", zoo.getZoo_name() + "に投稿");
		model.addAttribute("animalList", animalList);
		postInsertForm.setParentId(0);
		postInsertForm.setZooId(zooId);
		return "post/postInsert";
	}
	
	@GetMapping("/animalList")
	@ResponseBody
	public String getAnimalList(@RequestParam(required = false, name = "zooId") int zooId,Model model) throws JsonProcessingException {
		Zoo zoo = postService.getZooById(zooId);
		List<Animal> animalList = postService.getAnimalListByZooId(zooId);
		model.addAttribute("zoo", zoo);
		model.addAttribute("animalList", animalList);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(animalList);
		System.out.println(json);
		return json;
	}
		

	
	@GetMapping("/postDetail/{postId}")
	public String getPostDetail(@PathVariable int postId, Model model,
			@ModelAttribute PostInsertForm postInsertForm) {
		
		Post post = postService.getPostByPostId(postId);
		
		dateUtil.setPostDiffTime(post);
		
		setDefaultUserProfileImage(post);
		
		if(post.getChildrenPost() != null) {
			
			
			for(Post childPost : post.getChildrenPost()) {
				
				dateUtil.setPostDiffTime(childPost);
				
				setDefaultUserProfileImage(childPost);
			}
		}
		
		postInsertForm.setParentId(postId);
		
		postInsertForm.setZooId(post.getZoo().getZoo_id());
		
		model.addAttribute("post", post);
		
		return "post/postDetail";
	}
	
	@PostMapping("/insertParentPost")
	public String postNewParentPost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.insertNewPost(postInsertForm);
		
		return "redirect:/zoo/detail/" + postInsertForm.getZooId();
	}
	
	@PostMapping("/insertChildPost")
	public String postNewChildPost(@ModelAttribute PostInsertForm postInsertForm) {
				postService.insertNewPost(postInsertForm);
		
		return "redirect:/post/postDetail/" + postInsertForm.getParentId();
	}
	
	@PostMapping("/delete")
	public String deletePost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.deletePost(postInsertForm);
		
		return "redirect:/zoo/detail/" + postInsertForm.getZooId();
	}
	
	@PostMapping("/deleteChild")
	public String deleteChildPost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.deletePost(postInsertForm);
		
		return "redirect:/post/postDetail/" + postInsertForm.getParentId();
	}
	
	@PostMapping("/deleteFromMypage")
	public String deleteFromMypage(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.deletePost(postInsertForm);
		
		return "redirect:/users/" + postInsertForm.getUserId();
	}
	
	@GetMapping("/insertPostFavorite")
	@ResponseBody
	public String getInsertPostFavorite(@RequestParam(required = true, name = "postId") int post_id) throws Exception {
		Map<String, Object> map  = postFavoriteService.insertPostFavoirte(post_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	@GetMapping("/deletePostFavorite")
	@ResponseBody
	public String getDeletePostFavorite(@RequestParam(required = true, name = "postId") int post_id) throws Exception {
		Map<String, Object> map =  postFavoriteService.deletePostFavoirte(post_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	@GetMapping("/checkPostImageFavorite")
	@ResponseBody
	public String getCheckPostImageFavorite(@RequestParam(required = true, name = "postImage_id") int postImage_id) throws Exception {
		Map<String, Object> resultMap =  postImageFavoriteService.checkPostImageFavoriteByPostImageId(postImage_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(resultMap);
		System.out.println(json);
		return json;
	}
	
	@GetMapping("/insertPostImageFavorite")
	@ResponseBody
	public String getInsertPostImageFavorite(@RequestParam(required = true, name = "postImage_id") int postImage_id) throws Exception {
		Map<String, Object> resultMap =  postImageFavoriteService.insertPostImageFavorite(postImage_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(resultMap);
		return json;
	}
	
	@GetMapping("/deletePostImageFavorite")
	@ResponseBody
	public String getDeletePostImageFavorite(@RequestParam(required = true, name = "postImage_id") int postImage_id) throws Exception {
		Map<String, Object> resultMap =  postImageFavoriteService.deletePostImageFavorite(postImage_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(resultMap);
		return json;
	}
	
	/**
	 * プロフィール画像がセットされていない場合、デフォルトの画像をセットする
	 * 
	 * @param UserForm form
	*/
	private void setDefaultUserProfileImage(Post post) {
		if (post.getUser().getProfileImagePath() == null) {
			post.getUser().setProfileImagePath("/images/users/profile/defaultUser.png");
		}
	}

}
