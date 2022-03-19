package com.example.demo.app;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;

@Data
public class AnimalFilterForm {

	@NotBlank
	private String keyword;
	private List<Integer> zoo;
	private Boolean isMale;
	private Boolean isFemale;
	private Boolean isAlive;
	private Boolean isDead;

}
