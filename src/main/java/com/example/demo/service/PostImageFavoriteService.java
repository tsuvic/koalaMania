package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.PostImage;

public interface PostImageFavoriteService {
	Map<String,Object> checkPostImageFavoriteByPostImageId(int postImage_id);
	
	Map<String,Object> insertPostImageFavorite(int postImage_id);
	
	Map<String,Object> deletePostImageFavorite(int postImage_id);
	
	List<PostImage> getPostImageFavoirteByUserId(int user_id);
}
