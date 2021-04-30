package com.example.demo.entity;


import lombok.Data;

@Data
public class CoaraImage {
	private int coaraimage_id;	
	private int coara_id;
	private String filetype;
	
	public CoaraImage(int coaraimage_id,int coara_id,String filetype) {
		this.coaraimage_id = coaraimage_id;
		this.coara_id = coara_id;
		this.filetype = filetype;
	}

	public CoaraImage() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}
