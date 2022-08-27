//SPAテスト用に実装したが、本番では不要
package com.example.demo.app.api;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.app.UserForm;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import com.example.demo.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * ユーザーページ向けのREST APIを提供するクラス
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {
	private final UserAuthenticationUtil userAuthenticationUtil;
	private final UserService userService;
	private final PostService postService;
	private final PostServiceImpl postServiceImpl;
	private final ZooService zooService;
	private final TwitterLoginService twitterLoginService;
	static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public UserRestController(UserAuthenticationUtil userAuthenticationUtil, UserService userService,
							  PostService postService, PostServiceImpl postServiceImp, TwitterLoginService twitterLoginService,
							  ZooService zooService) {
		this.userAuthenticationUtil = userAuthenticationUtil;
		this.userService = userService;
		this.postService = postService;
		this.postServiceImpl = postServiceImp;
		this.twitterLoginService = twitterLoginService;
		this.zooService = zooService;
	}

	/**
	 *　Spring Securityにて認証有無を確認する
	 * @param form ユーザーフォーム
	 * @return 認証済みの場合:ユーザー情報, 未認証の場合:null
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

	/**
	 * ユーザー情報を取得する
	 * @param userId
	 * @param form
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
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

	@GetMapping("/{userId}/favoriteZoo")
	String getFavoriteZoo(@PathVariable int userId) throws JsonProcessingException {
		Zoo favoriteZoo = zooService.getFavoriteZoo(userId);
		return objectMapper.writeValueAsString(favoriteZoo);
	}

	@GetMapping("/{userId}/posts")
	String getPosts(@PathVariable int userId) throws JsonProcessingException {
		List<Post> postList = postService.getPostsByUserId(userId);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objectMapper.writeValueAsString(postList);
	}

	@GetMapping("/{userId}/comments")
	String getComments(@PathVariable int userId) throws JsonProcessingException {
		List<Post> postList = postService.getCommentsByUserId(userId);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objectMapper.writeValueAsString(postList);
	}

	@GetMapping("/{userId}/images")
	String getImages(@PathVariable int userId) throws JsonProcessingException {
		List<Post> postList = postService.getImagesByUserId(userId);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objectMapper.writeValueAsString(postList);
	}

	@GetMapping("/{userId}/favorites")
	String getFavorites(@PathVariable int userId) throws JsonProcessingException {
		List<Post> postList = postService.getFavoritesByUserId(userId);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objectMapper.writeValueAsString(postList);
	}

	@PostMapping("/posts")
	@ResponseStatus(HttpStatus.CREATED)
	public String postPosts(@RequestBody String jsonPost, @AuthenticationPrincipal LoginUser user) throws JsonProcessingException {
		Optional<LoginUser> nullableUser = Optional.ofNullable(user);
		Post post = objectMapper.readValue(jsonPost, Post.class);

		//TODO 引数としてjsonを受け取るか、postのエンティティとして受け取るか確認。バリデーションもここで実装する
		/* https://qiita.com/rubytomato@github/items/92ac7944c830e54aa03d */
		/* nullableUser.ifPresentOrElse(u -> {}, () -> {}); */
		if (nullableUser.isPresent()) {
			Post savedPost = postServiceImpl.save(nullableUser.get(), post);
			return objectMapper.writeValueAsString(savedPost);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ログインしてください");
		}
	}

	/**
	 *　Spring Securityにて認証有無を確認する
	 * @param twitterId
	 * @return 認証済みの場合:ユーザー情報, 未認証の場合:null
	 * @throws Exception
	 */
	@RequestMapping("/checkLogin")
	String checkLogin(@RequestParam(required = true, defaultValue = "", name = "twitterId") String twitterId ,HttpServletResponse response,HttpServletRequest request) throws JsonProcessingException {
		LoginUser loginUser = twitterLoginService.checkUserApi(Long.parseLong(twitterId),response,request);
		if (loginUser != null) {
			return objectMapper.writeValueAsString(loginUser);
		} else {
			return null;
		}
	}
}