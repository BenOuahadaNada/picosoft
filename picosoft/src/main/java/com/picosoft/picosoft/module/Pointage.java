package com.picosoft.picosoft.module;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="pointage") 
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Pointage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Time checkTime;
	private Date CheckDate;
	private String CheckType;

	private int VerifyCode;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
}