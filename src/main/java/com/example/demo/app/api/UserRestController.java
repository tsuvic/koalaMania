//SPAテスト用に実装したが、本番では不要
package com.example.demo.app.api;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.app.UserForm;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
	private final UserAuthenticationUtil userAuthenticationUtil;
	private final UserService userService;
	private final PostService postService;

	@Autowired
	public UserRestController(UserAuthenticationUtil userAuthenticationUtil,
							  UserService userService,
							  PostService postService) {
		this.userAuthenticationUtil = userAuthenticationUtil;
		this.userService = userService;
		this.postService = postService;
	}

	@RequestMapping("/checkAuthenticated")
	@CrossOrigin(
			origins = {"http://127.0.0.1:8080","http://localhost:8080","https://koalamania.herokuapp.com"},
			allowCredentials = "true"
	)
	String checkAuthenticated(@ModelAttribute UserForm form) throws Exception {
		LoginUser loginUser = userAuthenticationUtil.isUserAuthenticated();
		if (loginUser != null) {
			form.setUser_id(loginUser.getUser_id());
			form.setName(loginUser.getUserName());
			form.setProfile(loginUser.getProfile());
			form.setProfileImagePath(loginUser.getProfileImagePath());
			form.setTwitterLinkFlag(loginUser.isTwitterLinkFlag());
			System.out.println("HI");
			return new ObjectMapper().writeValueAsString(form);
		} else {
			System.out.println("null");
			return null;
		}
	}


	@GetMapping("/{userId}")
	@CrossOrigin(
			origins = {"http://127.0.0.1:8080","http://localhost:8080","https://koalamania.herokuapp.com"},
			allowCredentials = "true"
	)
	String getUser(@PathVariable int userId, @ModelAttribute UserForm form, Model model) throws Exception {
		LoginUser loginUser = userAuthenticationUtil.isUserAuthenticated();
		if (loginUser != null && loginUser.getUser_id() == userId) {
			form.setUser_id(userId);
			form.setName(loginUser.getUserName());
			form.setProfile(loginUser.getProfile());
			form.setTwitterLinkFlag(loginUser.isTwitterLinkFlag());
			form.setAdress(loginUser.getProvider_adress());
			form.setProfileImagePath(loginUser.getProfileImagePath());
			model.addAttribute("editFlag", true);
		} else {
			LoginUser otherUser = userService.findById(userId);
			form.setName(otherUser.getUserName());
			form.setProfile(otherUser.getProfile());
			form.setTwitterLinkFlag(otherUser.isTwitterLinkFlag());
			form.setAdress(otherUser.getProvider_adress());
			form.setProfileImagePath(otherUser.getProfileImagePath());
			model.addAttribute("editFlag", false);
		}

		if (form.getProfileImagePath() == null) {
			form.setProfileImagePath("/images/users/profile/defaultUser.png");
		}
		return new ObjectMapper().writeValueAsString(form);
	}

	@GetMapping("/{userId}/posts")
	@CrossOrigin(
			origins = {"http://127.0.0.1:8080","http://localhost:8080","https://koalamania.herokuapp.com"},
			allowCredentials = "true"
	)
	String getPosts(@PathVariable int userId, @ModelAttribute UserForm form, Model model) throws Exception {
		List<Post> postList = postService.getPostByUserId(userId);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objectMapper.writeValueAsString(postList);
	}
}