package com.example.demo.app;

import java.util.Date;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/post")
public class PostController {
	private final PostService postService;
	private final PostFavoriteService postFavoriteService;
	private final PostImageFavoriteService postImageFavoriteService;
	private final AnimalService animalSearvice;
	
	@Autowired
	public PostController(PostService postService,PostFavoriteService postFavoriteService ,PostImageFavoriteService postImageFavoriteService, AnimalService animalService) {
		this.postService = postService;
		this.postFavoriteService = postFavoriteService;
		this.postImageFavoriteService = postImageFavoriteService;
		this.animalSearvice = animalService;
	}
	
	@GetMapping
	public String getNewPost(Model model,@ModelAttribute PostInsertForm postInsertForm) {
		var zooList = animalSearvice.getZooList();
		model.addAttribute("zooList", zooList);		
		postInsertForm.setParent_id(0);
		return "post/postInsert2";
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
	
	@GetMapping("/animalList")
	@ResponseBody
	public String getAnimalList(@RequestParam(required = false, name = "zoo_id") int zoo_id,Model model) throws JsonProcessingException {
		Zoo zoo = postService.getZooById(zoo_id);
		List<Animal> animalList = postService.getAnimalListByZooId(zoo_id);
		model.addAttribute("zoo", zoo);
		model.addAttribute("animalList", animalList);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(animalList);
		System.out.println(json);
		return json;
	}
		

	
	@GetMapping("/postDetail/{post_id}")
	public String getPostDetail(@PathVariable int post_id, Model model,
			@ModelAttribute PostInsertForm postInsertForm) {
		
		Post post = postService.getPostByPostId(post_id);
		Date now = new Date();
		long nowtime = now.getTime();
		
		Date cteate = post.getCreatedDate();
		long createtime = cteate.getTime();
		long difftime  =  nowtime - createtime;
		
		if(difftime/1000/60 < 59) {
			post.setDisplayDiffTime(difftime/1000/60  + "分前");
		}else if(difftime/1000/60/60 < 24) {
			post.setDisplayDiffTime(difftime/1000/60/60  + "時間前");
		}else if(difftime/1000/60/60/24 < 31) {
			post.setDisplayDiffTime(difftime/1000/60/60/24  + "日前");
		}else if(difftime/1000/60/60/24/30 < 1) {
			post.setDisplayDiffTime(difftime/1000/60/60/24/30  + "ヶ月前");
		}else {
			post.setDisplayDiffTime(difftime/1000/60/60/24/30  + "年前");
		}
		
		setDefaultUserProfileImage(post);
		
		if(post.getChildrenPost() != null) {
			
			for(Post childPost : post.getChildrenPost()) {
				Date cteate2 = childPost.getCreatedDate();
				long createtime2 = cteate2.getTime();
				long difftime2  =  nowtime - createtime2;
				
				if(difftime2/1000/60 < 59) {
					childPost.setDisplayDiffTime(difftime2/1000/60  + "分前");
				}else if(difftime2/1000/60/60 < 24) {
					childPost.setDisplayDiffTime(difftime2/1000/60/60  + "時間前");
				}else if(difftime2/1000/60/60/24 < 31) {
					childPost.setDisplayDiffTime(difftime2/1000/60/60/24  + "日前");
				}else if(difftime2/1000/60/60/24/30 < 1) {
					childPost.setDisplayDiffTime(difftime2/1000/60/60/24/30  + "ヶ月前");
				}else {
					childPost.setDisplayDiffTime(difftime2/1000/60/60/24/30  + "年前");
				}
				setDefaultUserProfileImage(childPost);
			}
		}
		
		postInsertForm.setParent_id(post_id);
		
		model.addAttribute("post", post);
		
		return "post/postDetail";
	}
	
	@PostMapping("/insertParentPost")
	public String postNewParentPost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.insertNewPost(postInsertForm);
		
		return "redirect:/zoo/detail/" + postInsertForm.getZoo_id();
	}
	
	@PostMapping("/insertChildPost")
	public String postNewChildPost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.insertNewPost(postInsertForm);
		
		return "redirect:/post/postDetail/" + postInsertForm.getParent_id();
	}
	
	@PostMapping("/delete")
	public String deletePost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.deletePost(postInsertForm);
		
		return "redirect:/zoo/detail/" + postInsertForm.getZoo_id();
	}
	
	@PostMapping("/deleteChild")
	public String deleteChildPost(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.deletePost(postInsertForm);
		
		return "redirect:/post/postDetail/" + postInsertForm.getParent_id();
	}
	
	@PostMapping("/deleteFromMypage")
	public String deleteFromMypage(@ModelAttribute PostInsertForm postInsertForm) {
		
		postService.deletePost(postInsertForm);
		
		return "redirect:/user/mypage/" + postInsertForm.getUser_id() + "/" + postInsertForm.getTabType();
	}
	
	@GetMapping("/insertPostFavorite")
	@ResponseBody
	public String getInsertPostFavorite(@RequestParam(required = true, name = "post_id") int post_id) throws Exception {
		Map<String, Object> map  = postFavoriteService.insertPostFavoirte(post_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	@GetMapping("/deletePostFavorite")
	@ResponseBody
	public String getDeletePostFavorite(@RequestParam(required = true, name = "post_id") int post_id) throws Exception {
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
		if (post.getLoginUser().getProfileImagePath() == null) {
			post.getLoginUser().setProfileImagePath("/images/user/profile/defaultUser.png");
		}
	}

}
