package com.example.demo.app;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CoaraInsertForm {
	@NotBlank
	private String name;
	@Max(1)
	private int is_alive = 1;
	
	private int sex=2;

  
	private String birthYear = "2000";
	@NotBlank
	private String birthMonth;
	@NotBlank
	private String birthDay;
	@NotBlank
	private String deathYear;
	@NotBlank
	private String deathMonth;
	@NotBlank
	private String deathDay;
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
}
