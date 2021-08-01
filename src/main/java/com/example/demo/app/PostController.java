package com.example.demo.app;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Post;
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
	
	@GetMapping("/postDetail/{post_id}")
	public String getPostDetail(@PathVariable int post_id, Model model,
			@ModelAttribute PostInsertForm postInsertForm) {
		
		Post post = postService.getPostByPostId(post_id);
		
		setDefaultUserProfileImage(post);
		
		if(post.getChildrenPost() != null) {
		
			Date now = new Date();
			long nowtime = now.getTime();
			
			for(Post childPost : post.getChildrenPost()) {
				Date cteate = post.getCreatedDate();
				long createtime = cteate.getTime();
				long difftime  =  nowtime - createtime;
				
				if(difftime/1000/60 < 59) {
					childPost.setDisplayDiffTime(difftime/1000/60  + "分前");
				}else if(difftime/1000/60/60 < 24) {
					childPost.setDisplayDiffTime(difftime/1000/60/60  + "時間前");
				}else if(difftime/1000/60/60/24 < 31) {
					childPost.setDisplayDiffTime(difftime/1000/60/60/24  + "日前");
				}else if(difftime/1000/60/60/24/30 < 1) {
					childPost.setDisplayDiffTime(difftime/1000/60/60/24/30  + "ヶ月前");
				}else {
					childPost.setDisplayDiffTime(difftime/1000/60/60/24/30  + "年前");
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
