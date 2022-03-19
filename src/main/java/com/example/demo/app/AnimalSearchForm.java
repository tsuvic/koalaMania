package com.example.demo.app;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AnimalSearchForm {

	@NotBlank
	private String keyword;


}
