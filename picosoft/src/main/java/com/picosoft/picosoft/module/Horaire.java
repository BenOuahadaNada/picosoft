package com.picosoft.picosoft.module;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Horaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	
	@OneToMany(mappedBy = "horaire" ,cascade = CascadeType.ALL)
	@JsonIgnore
	private List<HoraireJour> horaireJour;
	
	@OneToMany(mappedBy = "horaire", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ApplicationHoraire> appHoraire;
	

}
