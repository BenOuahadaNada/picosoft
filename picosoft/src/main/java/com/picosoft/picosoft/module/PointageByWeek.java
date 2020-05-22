package com.picosoft.picosoft.module;

import java.sql.Time;

import javax.persistence.Entity;

import lombok.Data;
@Data
public class PointageByWeek {
	
	private String email;
	private String checktype;
	private Time checktime;
	private String checkdate;
	
	PointageByWeek(String email, String checktype ,Time checktime , String checkdate){
		this.email=email;
		this.checktype=checktype;
		this.checktime=checktime;
		this.checkdate=checkdate;
	}
	PointageByWeek(){
		
	}
}
