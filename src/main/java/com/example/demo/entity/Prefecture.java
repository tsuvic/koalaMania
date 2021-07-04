package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Prefecture {

	public final String TABLE_NAME = "prefecture";
	
	public final String COLUMN_PREFECTURE_ID = "prefecture_id";
	public final String COLUMN_PREFECTURE_NAME = "prefecture_name";
	public final String COLUMN_PREFECTURE_NAME_KANA = "prefecture_name_kana";
	
	private int prefecture_id;
	private String name;;

}
