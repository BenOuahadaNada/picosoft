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

import com.picosoft.picosoft.dao.RelationAppPoliRepository;
import com.picosoft.picosoft.module.RelationAppPolitique;

@RestController
@Transactional
@RequestMapping(value="api/user")
public class RelationAppPoliController {

	@Autowired
	RelationAppPoliRepository relationRepository;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allAppPoli")
    public List<RelationAppPolitique> getAllAppPoli(){
        return relationRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/appPoli/{id}")
    public ResponseEntity<RelationAppPolitique> getAppPoliById(@PathVariable Long id) throws Exception{
		RelationAppPolitique apphor = relationRepository.findById(id).orElseThrow(()->new Exception("Application Horaire n'existe pas"));
        return ResponseEntity.ok().body(apphor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterAppPoli")
    public RelationAppPolitique AjouterAppPoli(@Valid @RequestBody RelationAppPolitique apphor){
        return relationRepository.save(apphor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateAppPoli/{id}")
    public ResponseEntity<RelationAppPolitique> updateAppPoli(@PathVariable Long id, @Valid @RequestBody RelationAppPolitique appHorDetails) throws Exception{
		RelationAppPolitique appHor = relationRepository.findById(id).orElseThrow(()->new Exception("Application Horaire n'existe pas"));
        appHor.setAppHoraire(appHorDetails.getAppHoraire());
        appHor.setPolitique(appHorDetails.getPolitique());
        relationRepository.save(appHor);
        return ResponseEntity.ok(appHor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteAppPoli/{id}")
    public Map<String,Boolean> deleteAppPoli(@PathVariable Long id) throws Exception{
		RelationAppPolitique apphor = relationRepository.findById(id).orElseThrow(()->new Exception("Application Horaire n'existe pas"));
        relationRepository.delete(apphor);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Application Horaire est supprimée!",Boolean.TRUE);
        return response;
    }

}
