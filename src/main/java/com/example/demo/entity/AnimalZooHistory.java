package com.example.demo.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class AnimalZooHistory {

	public AnimalZooHistory() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public final String TABLE_NAME = "animal_zoo_history";
	
	public final String COLUMN_ANIMAL_ZOO_HISTORY_ID = "animal_zoo_history_id";
	public final String COLUMN_ANIMAL_ID = "animal_id";
	public final String COLUMN_ZOO_ID = "zoo_id";
	public final String COLUMN_ADMISSION_DATE = "admission_date";
	public final String COLUMN_EXIT_DATE = "exit_date";

	private int animal_zoo_history_id;
	private int animal_id;
	private Date admission_date;
	private Date exit_date;
	private Zoo zoo;
}
