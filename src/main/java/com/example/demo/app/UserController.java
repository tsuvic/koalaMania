package com.example.demo.app;

import java.util.Date;
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

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.service.PostFavoriteService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	private final PostService postService;
	
	private final PostFavoriteService postFavoriteService;

	private UserAuthenticationUtil userAuthenticationUtil;

	@Autowired
	public UserController(UserService userService, PostFavoriteService postFavoriteService ,UserAuthenticationUtil userAuthenticationUtil,
			PostService postService) {
		this.userService = userService;
		this.userAuthenticationUtil = userAuthenticationUtil;
		this.postService = postService;
		this.postFavoriteService = postFavoriteService;
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
	

	/**
	 * プロフィール画像がセットされていない場合、デフォルトの画像をセットする
	 * 
	 * @param UserForm form
	*/
	private List<Post> setDiffTime(List<Post> postList) {
		
		Date now = new Date();
		long nowtime = now.getTime();
	
		for(Post post : postList) {
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
		}
		
		return postList;
	}

}
