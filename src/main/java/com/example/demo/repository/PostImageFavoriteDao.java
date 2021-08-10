package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.PostImage;
import com.example.demo.entity.PostImageFavorite;

public interface PostImageFavoriteDao {
	PostImageFavorite checkPostImageFavoriteByPostImageId(int postImage_id);
	
	int insertPostImageFavorite(int postImage_id);
	
	void deletePostImageFavorite(int postImage_id);
	
	List<PostImage> getPostImageFavoirteByUserId(int user_id);
}
