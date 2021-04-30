package com.example.demo.app;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.CoaraImage;

import lombok.Data;

@Data
public class CoaraInsertForm {
	
	private int coara_id;
	@NotBlank
	private String name;
	@Max(1)
	private int is_alive = 1;
	
	private int sex = 2;

	@NotBlank
	private String birthYear = "2000";
	@NotBlank
	private String birthMonth = "1";
	@NotBlank
	private String birthDay = "1";
	@NotBlank
	private String deathYear = "0";
	@NotBlank
	private String deathMonth = "0";
	@NotBlank
	private String deathDay= "0";
	@NotBlank
	private String zoo;
	@NotBlank
	private String mother;
	@NotBlank
	private String father;
	@NotBlank
	@Length(min = 1,max = 50)
	private String details;
	@NotBlank
	@Length(min = 1,max = 50)
	private String feature;
	
	private List<MultipartFile> coaraImage;
	
	private List<CoaraImage> coaraImageList;
	
	private String deleteCoaraImageFiles;
}
