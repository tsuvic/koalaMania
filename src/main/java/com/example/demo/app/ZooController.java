package com.example.demo.app;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Post;
import com.example.demo.service.ZooService;

@Controller
@RequestMapping("/zoo")
public class ZooController {
	
	//下記のリクエストで使用するために、AnimalService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final ZooService zooService;

	@Autowired
	public ZooController(ZooService zooService) {
		this.zooService = zooService;
	}
	
	@GetMapping("/detail/{zoo_id}")
	public String detail(@PathVariable int zoo_id, Model model){
		
		model.addAttribute("zoo", zooService.findById(zoo_id));
		
		List<Post> postList = zooService.getPostListByZooId(zoo_id);
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
		if (post.getLoginUser().getProfileImagePath() == null) {
			post.getLoginUser().setProfileImagePath("/images/user/profile/defaultUser.png");
		}
	}

}
