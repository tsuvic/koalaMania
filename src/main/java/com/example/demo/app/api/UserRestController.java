//SPAテスト用に実装したが、本番では不要
package com.example.demo.app.api;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.app.UserForm;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserRestController {
	private final UserAuthenticationUtil userAuthenticationUtil;
	private final UserService userService;
	private final PostService postService;
	static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public UserRestController(UserAuthenticationUtil userAuthenticationUtil, UserService userService, PostService postService) {
		this.userAuthenticationUtil = userAuthenticationUtil;
		this.userService = userService;
		this.postService = postService;
	}

	/**
	 *　Spring Securityにて認証有無を確認する
	 * @param form ユーザーフォーム
	 * @return 認証済みの場合、ユーザー情報
	 * @throws Exception
	 */
	@RequestMapping("/checkAuthenticated")
	String checkAuthenticated(@ModelAttribute UserForm form) throws JsonProcessingException {
		LoginUser loginUser = userAuthenticationUtil.isUserAuthenticated();
		if (loginUser != null) {
			return objectMapper.writeValueAsString(loginUser);
		} else {
			return null;
		}
	}

	@GetMapping("/{userId}")
	String getUser(@PathVariable int userId, @ModelAttribute UserForm form, Model model) throws JsonProcessingException {
		LoginUser user = userAuthenticationUtil.isUserAuthenticated();
		if (user != null && user.getUser_id() == userId) {
			model.addAttribute("editFlag", true);
		} else {
			user = userService.findById(userId);
			model.addAttribute("editFlag", false);
		}

		if (user.getProfileImagePath() == null) {
			user.setProfileImagePath("/images/users/profile/defaultUser.png");
		}
		return objectMapper.writeValueAsString(user);
	}

	@GetMapping("/{userId}/posts")
	String getPosts(@PathVariable int userId) throws JsonProcessingException {
		List<Post> postList = postService.getPostByUserId(userId);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objectMapper.writeValueAsString(postList);
	}

	@PostMapping("/posts")
	@ResponseStatus(HttpStatus.CREATED)
	String postPosts(@ModelAttribute Post post) throws JsonProcessingException {

		System.out.println(post);
		System.out.println(post.getPost_id());
		return null;
	}
}