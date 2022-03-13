package com.example.demo.app;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class AnimalSearchForm {

	@NotBlank
	private String keyword;

	private Boolean isMale;
	private int isFemale;

	private int isAlive;
	private int isDead;

}
