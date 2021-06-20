package com.example.demo.entity;


import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class KoalaImage {
	
	public final String TABLE_NAME = "koalaimage";
	
	public final String COLUMN_KOALA_IMAGE_ID = "koalaimage_id";
	public final String COLUMN_KOALA_ID = "koala_id";
	public final String COLUMN_FILETYPE = "filetype";
	
	
	private int koalaimage_id;	
	private int koala_id;
	private String filetype;
	
	public KoalaImage(int koalaimage_id,int koala_id,String filetype) {
		this.koalaimage_id = koalaimage_id;
		this.koala_id = koala_id;
		this.filetype = filetype;
	}

	public KoalaImage() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}
