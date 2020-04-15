package com.picosoft.picosoft.module;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Politique {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nom;
	
	
	@OneToMany(mappedBy="politique", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<User> user;
	
	@OneToMany(mappedBy = "politique", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RelationPoli_JF> jourFerie;
	
	@OneToMany(mappedBy = "politique", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RelationAppPolitique> appHoraire;
}