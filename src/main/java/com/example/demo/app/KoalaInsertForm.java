package com.example.demo.app;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.KoalaImage;

import lombok.Data;

@Data
public class KoalaInsertForm {
	
	private int koala_id;
	@NotBlank
	private String name;
	@Max(1)
	private int is_alive = 1;
	
	private int sex = 2;

	@NotBlank
	private String birthYear = "2021";
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
	@Min(0)
	private int zoo=-1;
	@Min(0)
	private int mother_id;
	@Min(0)
	private int father_id;
	@NotBlank
	@Length(min = 1,max = 50)
	private String details;
	@NotBlank
	@Length(min = 1,max = 50)
	private String feature;
	
	private List<MultipartFile> koalaImage;
	
	private List<KoalaImage> koalaImageList;
	
	private String deleteKoalaImageFiles;
}
