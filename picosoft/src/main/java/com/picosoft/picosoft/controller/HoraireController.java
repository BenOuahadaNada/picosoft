package com.picosoft.picosoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picosoft.picosoft.dao.HoraireRepository;
import com.picosoft.picosoft.module.Horaire;

@RestController
@Transactional
@RequestMapping(value = "api/user")
public class HoraireController {
	
	@Autowired
	HoraireRepository horRepository;
	
	@PreAuthorize("hasAnyAuthority('responsable_rh','manager','employe')")
	@GetMapping(value="/allHoraire")
    public List<Horaire> getAllHoraire(){
        return horRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/horaire/{id}")
    public ResponseEntity<Horaire> getHoraireById(@PathVariable Long id) throws Exception{
       Horaire hor = horRepository.findById(id).orElseThrow(()->new Exception("Horaire n'existe pas"));
        return ResponseEntity.ok().body(hor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterHoraire")
    public Horaire AjouterHoraire(@Valid @RequestBody Horaire hor){
        return horRepository.save(hor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateHoraire/{id}")
    public ResponseEntity<Horaire> updateHoraire(@PathVariable Long id, @Valid @RequestBody Horaire horDetails) throws Exception{
        Horaire hor = horRepository.findById(id).orElseThrow(()->new Exception("Horaire n'existe pas"));
        hor.setNom(horDetails.getNom());
        hor.setHoraireJour(horDetails.getHoraireJour());
        horRepository.save(hor);
        return ResponseEntity.ok(hor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteHoraire/{id}")
    public Map<String,Boolean> deleteHoraire(@PathVariable Long id) throws Exception{
        Horaire hor = horRepository.findById(id).orElseThrow(()->new Exception("Horaire n'existe pas"));
        horRepository.delete(hor);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Horaire est supprimée!",Boolean.TRUE);
        return response;
    }

}
