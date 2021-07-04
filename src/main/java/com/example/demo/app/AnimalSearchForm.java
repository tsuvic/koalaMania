package com.example.demo.app;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AnimalSearchForm {

	@NotBlank
	private String keyword;
	
}
