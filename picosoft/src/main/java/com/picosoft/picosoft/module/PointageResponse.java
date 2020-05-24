package com.picosoft.picosoft.module;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data @AllArgsConstructor
public class PointageResponse {
	
	private String name;
	private Double value;
}
