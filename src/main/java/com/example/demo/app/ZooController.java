package com.example.demo.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Post;
import com.example.demo.service.ZooService;
import com.example.demo.util.DateUtil;

@Controller
@RequestMapping("/zoo")
public class ZooController {
	
	//下記のリクエストで使用するために、AnimalService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final ZooService zooService;
	
	private final DateUtil dateUtil;

	@Autowired
	public ZooController(ZooService zooService,DateUtil dateUtil) {
		this.zooService = zooService;
		this.dateUtil = dateUtil;
	}
	
	@GetMapping("/detail/{zooId}")
	public String detail(@PathVariable int zoo_id, Model model){
		
		model.addAttribute("zoo", zooService.findById(zoo_id));
		
		List<Post> postList = zooService.getPostListByZooId(zoo_id);
		
		for(Post post : postList) {
			
			dateUtil.setPostDiffTime(post);
			
			setDefaultUserProfileImage(post);
		}
		
		model.addAttribute("postList", postList);
		
		return "zoo/detail";
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
