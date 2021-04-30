package com.example.demo.entity;


import lombok.Data;

@Data
public class KoalaImage {
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
