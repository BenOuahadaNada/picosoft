package com.picosoft.picosoft.module;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="horaire_jour")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class HoraireJour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomJour;
	private String hDebut1;
	private String hFin1;
	private String hDebut2;
	private String hFin2;
	
	@ManyToOne
	@JsonIgnore
	private Horaire horaire;

}
