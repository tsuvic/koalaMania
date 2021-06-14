package com.example.demo.entity;

import lombok.Data;

@Data
public class RelationForTree {
	
	public RelationForTree() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private KoalaForTree source;
	private KoalaForTree target;
	
}