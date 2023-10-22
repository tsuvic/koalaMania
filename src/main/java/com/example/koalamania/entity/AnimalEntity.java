package com.example.koalamania.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class AnimalEntity {
    private int animal_id;
	private String name;
	private int sex;
	private Date birthdate;
	private int is_alive;
	private Date deathdate;
	private String details;
	private String feature;
	private String stringBirthDate;
	private String stringDeathDate;
	private String profileImagePath;
}
