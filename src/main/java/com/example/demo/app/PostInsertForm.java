package com.example.demo.app;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PostInsertForm {
	
	private int postId;
	
	private int zooId = -1;
	
	private int parentId;
	
	private int userId;
	
	private int tabType;
	
	private String visitDate;

	@NotBlank
	private String contents;
	
	private List<Integer> tagList;
	
	private List<MultipartFile> imageList;
}
