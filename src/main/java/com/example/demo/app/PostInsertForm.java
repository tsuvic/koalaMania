package com.example.demo.app;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostInsertForm {
	
	private int zoo_id;
	
	private int parent_id;
	
	private String visitdate;
	@NotBlank
	private String contents;
	
	private List<Integer> tagList;
	
	private List<MultipartFile> imageList;
	
	private List<Integer> deletePostImageIds;
}
