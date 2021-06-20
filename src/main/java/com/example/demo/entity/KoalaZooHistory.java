package com.example.demo.entity;

import org.springframework.stereotype.Component;

@Component
public class KoalaZooHistory {

	public KoalaZooHistory() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public final String TABLE_NAME = "koala_zoo_history";
	
	public final String COLUMN_KOALA_ZOO_HISTORY_ID = "koala_zoo_history_id";
	public final String COLUMN_KOALA_ID = "koala_id";
	public final String COLUMN_ZOO_ID = "zoo_id";
	public final String COLUMN_ADMISSION_DATE = "admission_date";
	public final String COLUMN_EXIT_DATE = "exit_date";

}
