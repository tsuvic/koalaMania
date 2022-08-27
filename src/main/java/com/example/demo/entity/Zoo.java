package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Zoo {
	public final String TABLE_NAME = "zoo";
	public final String COLUMN_ZOO_ID = "zoo_id";
	public final String COLUMN_ZOO_NAME = "zoo_name";
	public final String COLUMN_PREFECTURE_ID = "prefecture_id";

	private int zoo_id;
	private String zoo_name;
	private Prefecture prefecture;
}
