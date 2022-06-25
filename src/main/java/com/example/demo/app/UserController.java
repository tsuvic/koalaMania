package com.example.demo.app;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.service.*;
import com.example.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	private final PostService postService;
	
	private final PostImageService postImageService;
	
	private final PostFavoriteService postFavoriteService;
	
	private final PostImageFavoriteService postImageFavoriteService; 

	private final UserAuthenticationUtil userAuthenticationUtil;
	
	private final DateUtil dateUtil;

	@Autowired
	public UserController(UserService userService, PostFavoriteService postFavoriteService ,UserAuthenticationUtil userAuthenticationUtil,
			PostService postService,PostImageService postImageService,PostImageFavoriteService postImageFavoriteService,DateUtil dateUtil) {
		this.userService = userService;
		this.userAuthenticationUtil = userAuthenticationUtil;
		this.postService = postService;
		this.postFavoriteService = postFavoriteService;
		this.postImageService = postImageService;
		this.postImageFavoriteService = postImageFavoriteService;
		this.dateUtil = dateUtil;
	}

	@GetMapping("/{user_id}")
	public String getMyPageTabType(@PathVariable int user_id, Model model, @ModelAttribute UserForm form) {

		LoginUser principal = userAuthenticationUtil.isUserAuthenticated();
		if (principal != null
				&& principal.getUser_id() == user_id) {
			form.setUser_id(user_id);
			form.setName(((LoginUser) principal).getUserName());
			form.setProfile(((LoginUser) principal).getProfile());
			form.setTwitterLinkFlag(((LoginUser) principal).isTwitterLinkFlag());
			form.setAdress(((LoginUser) principal).getProvider_adress());
			form.setProfileImagePath(((LoginUser) principal).getProfileImagePath());
			model.addAttribute("editFlag", true);
		} else {
			LoginUser user = userService.findById(user_id);
			form.setName(user.getUserName());
			form.setProfile(user.getProfile());
			form.setTwitterLinkFlag(user.isTwitterLinkFlag());
			form.setAdress(user.getProvider_adress());
			form.setProfileImagePath(user.getProfileImagePath());
			model.addAttribute("editFlag", false);
		}

		return "users/mypage";
	}

	@GetMapping("/edit/{user_id}")
	public String getEditMypage(@PathVariable int user_id, Model model,
			@ModelAttribute UserForm form) {

		LoginUser principal = userAuthenticationUtil.isUserAuthenticated();
		if (principal != null
				&& principal.getUser_id() == user_id) {
			form.setUser_id(user_id);
			form.setName(((LoginUser) principal).getUserName());
			form.setProfile(((LoginUser) principal).getProfile());
			form.setTwitterLinkFlag(((LoginUser) principal).isTwitterLinkFlag());
			form.setAdress(((LoginUser) principal).getProvider_adress());
			form.setProfileImagePath(((LoginUser) principal).getProfileImagePath());

			setDefaultUserProfileImage(form);

			return "users/edit";
		} else {

			return "redirect:/ ";
		}
	}

	@PostMapping("/edit")
	public String insertAnimal(Model model, @Validated UserForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return getEditMypage(form.getUser_id(), model, form);
		}

		userService.updateMyPage(form);

		return "redirect:/users/" + form.getUser_id();
	}

	/**
	 * プロフィール画像がセットされていない場合、デフォルトの画像をセットする
	 * 
	 * @param UserForm form
	*/
	private void setDefaultUserProfileImage(UserForm form) {
		if (form.getProfileImagePath() == null) {
			form.setProfileImagePath("/images/users/profile/defaultUser.png");
		}
	}
	
	/**
	 * プロフィール画像がセットされていない場合、デフォルトの画像をセットする
	 * 
	 * @param UserForm form
	*/
	private void setDefaultUserProfileImage(Post post) {
		if (post.getLoginUser().getProfileImagePath() == null) {
			post.getLoginUser().setProfileImagePath("/images/users/profile/defaultUser.png");
		}
	}
	
	private List<Post> setDiffTime(List<Post> postList) {
	
		for(Post post : postList) {

			dateUtil.setPostDiffTime(post);
		}
		
		return postList;
	}

}
