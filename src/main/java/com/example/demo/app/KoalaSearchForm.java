package com.example.demo.app;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class KoalaSearchForm {

	@NotBlank
	private String keyword;
	
}
