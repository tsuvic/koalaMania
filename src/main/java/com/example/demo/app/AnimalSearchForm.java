package com.example.demo.app;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.demo.entity.Zoo;
import lombok.Data;

import java.util.List;

@Data
public class AnimalSearchForm {

	@NotBlank
	private String keyword;
	private List<Integer> zoo;
	private Boolean isMale;
	private Boolean isFemale;
	private Boolean isAlive;
	private Boolean isDead;

}
