package com.picosoft.picosoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
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

import com.picosoft.picosoft.dao.JourFerieRepository;
import com.picosoft.picosoft.module.JourFerie;
import com.picosoft.picosoft.module.Politique;

@RestController
@Transactional
@RequestMapping(value = "api/user")
public class JourFerieController {
	@Autowired
	JourFerieRepository jourFerieRepository;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allJour")
    public List<JourFerie> getAllJour(){
        return jourFerieRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/jourferie/{id}")
    public ResponseEntity<JourFerie> getJourById(@PathVariable Long id) throws Exception{
        JourFerie jour = jourFerieRepository.findById(id).orElseThrow(()->new Exception("Jour Férié n'existe pas"));
        return ResponseEntity.ok().body(jour);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterJour")
    public JourFerie AjouterJour(@Valid @RequestBody JourFerie jour){
        return jourFerieRepository.save(jour);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateJour/{id}")
    public ResponseEntity<JourFerie> updateJour(@PathVariable Long id, @Valid @RequestBody JourFerie JourDetails) throws Exception{
        JourFerie jour = jourFerieRepository.findById(id).orElseThrow(()->new Exception("Jour Férié n'existe pas"));
        jour.setNom(JourDetails.getNom());
        jourFerieRepository.save(jour);
        return ResponseEntity.ok(jour);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteJour/{id}")
    public Map<String,Boolean> deleteJour(@PathVariable Long id) throws Exception{
        JourFerie jour = jourFerieRepository.findById(id).orElseThrow(()->new Exception("Jour férié n'existe pas"));
        jourFerieRepository.delete(jour);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Jour férié est supprimé!",Boolean.TRUE);
        return response;
    }

}
