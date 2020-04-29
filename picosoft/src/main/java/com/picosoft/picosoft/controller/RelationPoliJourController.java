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

import com.picosoft.picosoft.dao.RelationPoliJourRepository;
import com.picosoft.picosoft.module.RelationPoli_JF;

@RestController
@Transactional
@RequestMapping(value="api/user")
public class RelationPoliJourController {

	@Autowired
	RelationPoliJourRepository relaRepository;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allJourPoli")
    public List<RelationPoli_JF> getAllJourPoli(){
        return relaRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/jourPoli/{id}")
    public ResponseEntity<RelationPoli_JF> getJourPoliById(@PathVariable Long id) throws Exception{
		RelationPoli_JF jourp = relaRepository.findById(id).orElseThrow(()->new Exception("Jour Férié n'existe pas"));
        return ResponseEntity.ok().body(jourp);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterJourPoli")
    public RelationPoli_JF AjouterJourPoli(@Valid @RequestBody RelationPoli_JF jourp){
        return relaRepository.save(jourp);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateJourPoli/{id}")
    public ResponseEntity<RelationPoli_JF> updateJourPoli(@PathVariable Long id, @Valid @RequestBody RelationPoli_JF jourpDetails) throws Exception{
		RelationPoli_JF jour = relaRepository.findById(id).orElseThrow(()->new Exception("Jour Férié n'existe pas"));
        jour.setJourferie(jourpDetails.getJourferie());
        jour.setPolitique(jourpDetails.getPolitique());
        relaRepository.save(jour);
        return ResponseEntity.ok(jour);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteJourPoli/{id}")
    public Map<String,Boolean> deleteJourPoli(@PathVariable Long id) throws Exception{
		RelationPoli_JF jour = relaRepository.findById(id).orElseThrow(()->new Exception("Jour Férié n'existe pas"));
        relaRepository.delete(jour);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Jour Férié est supprimé!",Boolean.TRUE);
        return response;
    }

}
