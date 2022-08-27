package com.example.demo.app;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class UserForm {
	
	private int user_id;

	@NotBlank
	private String name;
	
	private String profile;
	
	private boolean twitterLinkFlag;

	private long favoriteZooId = 5;
	
	private String adress;
	
	private String profileImagePath;
	
	private String tmpProfileImage;
	
	private MultipartFile userProfileImageUpload;

}
