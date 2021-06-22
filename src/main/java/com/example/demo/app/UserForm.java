package com.example.demo.app;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserForm {
	
	private int user_id;

	@NotBlank
	private String name;
	
	private String profile;
	
	private boolean twitterLinkFlag;
	
	private String adress;
	
	private String profileImagePath;
	
	private String tmpProfileImage;
	
	private MultipartFile userProfileImageUpload;

}
