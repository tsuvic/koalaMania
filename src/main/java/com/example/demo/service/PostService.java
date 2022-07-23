package com.example.demo.service;

import com.example.demo.app.PostInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
	List<Post> getPostsByUserId(int userId);
	List<Post> getCommentsByUserId(int userId);
	List<Post> getImagesByUserId(int userId);
	List<Post> getFavoritesByUserId(int userId);

	Zoo getZooById(int zoo_id);
	
	List<Animal> getAnimalListByZooId(int zoo_id);
	
	void insertNewPost(PostInsertForm postInsertForm);
	
	void deletePost(PostInsertForm postInsertForm);
	
	String insertPostImage(int post_id, MultipartFile postImageUpload ,int post_image_id);
	
	Post getPostByPostId(int post_id);
}
