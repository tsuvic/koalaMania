package com.example.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
//	下記のリクエストで使用するために、KoalaService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/mypage/{user_id}")
	public String getMyPage(@PathVariable int user_id, Model model, 
			@ModelAttribute UserForm form) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!(SecurityContextHolder.getContext().getAuthentication() 
				instanceof AnonymousAuthenticationToken) 
				&& ((LoginUser) principal).getUser_id() == user_id) {
			form.setUser_id(user_id);
			form.setName(((LoginUser) principal).getUserName());
			form.setProfile(((LoginUser) principal).getProfile());
			form.setTwitterLinkFlag(((LoginUser) principal).isTwitterLinkFlag());
			form.setAdress(((LoginUser) principal).getProvider_adress());
			form.setProfileImagePath(((LoginUser) principal).getProfileImagePath());
			model.addAttribute("editFlag", true);
		}else {
			LoginUser user = userService.findById(user_id);
			form.setName(user.getUserName());
			form.setProfile(user.getProfile());
			form.setTwitterLinkFlag(user.isTwitterLinkFlag());
			form.setAdress(user.getProvider_adress());
			form.setProfileImagePath(user.getProfileImagePath());
			model.addAttribute("editFlag", false);
		}
		
		setDefaultUserProfileImage(form);
		
		return "user/mypage";
	}
	
	@GetMapping("/edit/{user_id}")
	public String getEditMypage(@PathVariable int user_id, Model model, 
			@ModelAttribute UserForm form) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!(SecurityContextHolder.getContext().getAuthentication() 
				instanceof AnonymousAuthenticationToken) 
				&& ((LoginUser) principal).getUser_id() == user_id) {
			form.setUser_id(user_id);
			form.setName(((LoginUser) principal).getUserName());
			form.setProfile(((LoginUser) principal).getProfile());
			form.setTwitterLinkFlag(((LoginUser) principal).isTwitterLinkFlag());
			form.setAdress(((LoginUser) principal).getProvider_adress());
			form.setProfileImagePath(((LoginUser) principal).getProfileImagePath());
			
			setDefaultUserProfileImage(form);
			
			return "user/edit";
		}else {
			
			return "redirect:/ ";
		}
	}
	
	@PostMapping("/edit")
	public String insertKoala(Model model, @Validated UserForm form, BindingResult bindingResult) {
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

}
