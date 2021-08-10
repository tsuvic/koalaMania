package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PostImage;
import com.example.demo.entity.PostImageFavorite;
import com.example.demo.repository.PostImageDao;
import com.example.demo.repository.PostImageFavoriteDao;

@Service
public class PostImageFavoriteServiceImpl implements PostImageFavoriteService {
	
	private PostImageFavoriteDao postImageFavoriteDao;
	
	private PostImageDao postImageDao;

	public PostImageFavoriteServiceImpl(PostImageFavoriteDao postImageFavoriteDao,PostImageDao postImageDao) {
		this.postImageFavoriteDao = postImageFavoriteDao;
		this.postImageDao = postImageDao;
	}

	@Override
	public Map<String, Object> checkPostImageFavoriteByPostImageId(int postImage_id) {
		
		PostImageFavorite postImageFavorite = postImageFavoriteDao.checkPostImageFavoriteByPostImageId(postImage_id);
		if(postImageFavorite.getPostImage() == null) {
			postImageFavorite.setPostImage(postImageDao.getPostImageByPostImageId(postImage_id));
		}
		
		Map<String, Object> resultMap =  new HashMap<String,Object>(){{put("status", "ok");}};
		resultMap.put("resourse", postImageFavorite);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> insertPostImageFavorite(int postImage_id) {
		int postImageFavoriteId = postImageFavoriteDao.insertPostImageFavorite(postImage_id);
		Map<String, Object> resultMap =  new HashMap<String,Object>(){{put("status", "ok");}};
		resultMap.put("postImageFavoriteId", postImageFavoriteId);
		return resultMap;
	}

	@Override
	public Map<String, Object> deletePostImageFavorite(int postImage_id) {
		postImageFavoriteDao.deletePostImageFavorite(postImage_id);
		Map<String, Object> resultMap =  new HashMap<String,Object>(){{put("status", "ok");}};
		return resultMap;
	}
	
	@Override
	public List<PostImage> getPostImageFavoirteByUserId(int user_id) {
		return postImageFavoriteDao.getPostImageFavoirteByUserId(user_id);
	}

}
