package com.example.demo.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Post;

@Component
public class DateUtil {
	
	
	/**
	 * 現在の時間と投稿時間の差をセットするメゾッド
	 * @param post
	 */
	public void setPostDiffTime(Post post) {
		
		Date now = new Date();
		long nowtime = now.getTime();
		
		Date cteate = post.getCreatedDate();
		long createtime = cteate.getTime();
		long difftime  =  nowtime - createtime;
		
		if(difftime/1000/60 < 59) {
			post.setDisplayDiffTime(difftime/1000/60  + "分前");
		}else if(difftime/1000/60/60 < 24) {
			post.setDisplayDiffTime(difftime/1000/60/60  + "時間前");
		}else if(difftime/1000/60/60/24 < 30) {
			post.setDisplayDiffTime(difftime/1000/60/60/24  + "日前");
		}else if(difftime/1000/60/60/24/30 < 12) {
			post.setDisplayDiffTime(difftime/1000/60/60/24/30  + "ヶ月前");
		}else {
			post.setDisplayDiffTime(difftime/1000/60/60/24/30/12  + "年前");
		}
	}

}
