package com.example.demo.app;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.AnimalZooHistory;

import lombok.Data;

@Data
public class AnimalInsertForm {
	
	private int animal_id;
	@NotBlank
	private String name;
	@Max(1)
	private int is_alive = 1;
	
	private int sex = 2;
	
	//動物園履歴表示用
	private List<AnimalZooHistory> animalZooHistory;
	
	//動物園履歴送信用
	private List<String> admissionYear;
	private List<String> admissionMonth;
	private List<String> admissionDay;
	private List<String> exitYear;
	private List<String> exitMonth;
	private List<String> exitDay;
	private List<Integer> insertZoo;
	
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
	private int mother_id;
	@Min(0)
	private int father_id;
	@NotBlank
	@Length(min = 1,max = 300)
	private String details = "未入力";
	@NotBlank
	@Length(min = 1,max = 300)
	private String feature = "未入力";
	
	private MultipartFile animalProfileImageUpload;
	
	private String profileImagePath;
}
