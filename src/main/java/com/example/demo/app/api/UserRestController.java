package com.example.demo.app.api;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.app.UserForm;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.service.*;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserService userService;

	private final PostService postService;

	private final PostImageService postImageService;

	private final PostFavoriteService postFavoriteService;

	private final PostImageFavoriteService postImageFavoriteService;

	private final UserAuthenticationUtil userAuthenticationUtil;

	private final DateUtil dateUtil;

	@Autowired
	public UserRestController(UserService userService, PostFavoriteService postFavoriteService , UserAuthenticationUtil userAuthenticationUtil,
                              PostService postService, PostImageService postImageService, PostImageFavoriteService postImageFavoriteService, DateUtil dateUtil) {
		this.userService = userService;
		this.userAuthenticationUtil = userAuthenticationUtil;
		this.postService = postService;
		this.postFavoriteService = postFavoriteService;
		this.postImageService = postImageService;
		this.postImageFavoriteService = postImageFavoriteService;
		this.dateUtil = dateUtil;
	}

//	@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:8080"})
	@GetMapping("/{userId}")
	public String getUsers(@PathVariable int userId, Model model, @ModelAttribute UserForm form) throws Exception{
		LoginUser principal = userAuthenticationUtil.isUserAuthenticated();


		form.setUser_id(userId);
		String json = new ObjectMapper().writeValueAsString(form);
		return json;
	}

	@GetMapping("/mypage/{user_id}")
	public String getMyPage(@PathVariable int user_id, Model model,
			@ModelAttribute UserForm form) {

		return "redirect:/user/mypage/" + user_id + "/" + 1;
	}

	@GetMapping("/mypage/{user_id}/{tabType}")
	public String getMyPageTabType(@PathVariable int user_id, @PathVariable int tabType, Model model,
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

		setDefaultUserProfileImage(form);

		switch (tabType) {
			case 1:
				List<Post> postList = postService.getPostByUserId(user_id);
				postList.stream()
						.forEach(post -> setDefaultUserProfileImage(post));
				setDiffTime(postList);
				model.addAttribute("postList", postList);
				break;
			case 2:
				List<Post> postFavoriteList = postFavoriteService.getPostFavoirteByUserId(user_id);
				 postFavoriteList.stream()
						.forEach(post -> setDefaultUserProfileImage(post));
				 setDiffTime(postFavoriteList);
				model.addAttribute("postList", postFavoriteList);
				break;
			case 3:
				model.addAttribute("postImageList", postImageService.getPostImageListByUserId(user_id));
				break;
			case 4:
				List<Post> postCommnetList = postService.getCommentByUserId(user_id);
				postCommnetList.stream()
						.forEach(post -> setDefaultUserProfileImage(post));
				 setDiffTime(postCommnetList);
				model.addAttribute("postList", postCommnetList);
				break;
			case 5:
				model.addAttribute("postImageList", postImageFavoriteService.getPostImageFavoirteByUserId(user_id));
		}

		model.addAttribute("tabType", tabType);

		return "user/mypage";
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

			return "user/edit";
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

		return "redirect:/user/mypage/" + form.getUser_id();
	}

	/**
	 * プロフィール画像がセットされていない場合、デフォルトの画像をセットする
	 * 
	 * @param UserForm form
	*/
	private void setDefaultUserProfileImage(UserForm form) {
		if (form.getProfileImagePath() == null) {
			form.setProfileImagePath("/images/user/profile/defaultUser.png");
		}
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
	
	private List<Post> setDiffTime(List<Post> postList) {
	
		for(Post post : postList) {

			dateUtil.setPostDiffTime(post);
		}
		
		return postList;
	}

}
