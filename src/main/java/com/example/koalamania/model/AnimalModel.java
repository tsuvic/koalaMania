package com.example.koalamania.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "animal")
public class AnimalModel {

    @Id
    @Column(name = "animal_id")
    private int animal_id;

    @Column(name = "name")
	private String name;

    @Column(name = "sex")
	private int sex;

    @Column(name = "birth_date")
	private LocalDateTime birthDate;

    @Column(name = "is_alive")
	private int isAlive;

    @Column(name = "death_date")
	private LocalDateTime deathDate;

    @Column(name = "details")
	private String details;

    @Column(name = "feature")
	private String feature;

    @Column(name = "created_by")
	private int createdBy;

    @Column(name = "updated_by")
	private int updatedBy;

    @Column(name = "created_date")
	private LocalDateTime createdDate;

    @Column(name = "updated_date")
	private LocalDateTime updatedDate;

    @Column(name = "animal_type_id")
	private int animalTypeId;

    @Column(name = "profile_image_type")
	private String profileImageType;
}
