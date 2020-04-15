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

import com.picosoft.picosoft.dao.PolitiqueRepository;
import com.picosoft.picosoft.module.Politique;

@RestController
@Transactional
@RequestMapping(value="api/user")
public class PolitiqueController {
	@Autowired
	PolitiqueRepository politiqueRepository;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allPolitique")
    public List<Politique> getAllPolitique(){
        return politiqueRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/politique/{id}")
    public ResponseEntity<Politique> getPolitiqueById(@PathVariable Long id) throws Exception{
        Politique politique = politiqueRepository.findById(id).orElseThrow(()->new Exception("La politique de travail n'existe pas"));
        return ResponseEntity.ok().body(politique);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterPolitique")
    public Politique AjouterPolitique(@Valid @RequestBody Politique politique){
        return politiqueRepository.save(politique);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updatePolitique/{id}")
    public ResponseEntity<Politique> updatePolitique(@PathVariable Long id, @Valid @RequestBody Politique politiqueDetails) throws Exception{
        Politique politique = politiqueRepository.findById(id).orElseThrow(()->new Exception("La politique de travail n'existe pas"));
        politique.setNom(politiqueDetails.getNom());
        politiqueRepository.save(politique);
        return ResponseEntity.ok(politique);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deletePolitique/{id}")
    public Map<String,Boolean> deletePolitique(@PathVariable Long id) throws Exception{
        Politique politique = politiqueRepository.findById(id).orElseThrow(()->new Exception("La politique de travail n'existe pas"));
        politiqueRepository.delete(politique);
        Map<String,Boolean> response = new HashMap<>();
        response.put("La politique de travail est supprimée!",Boolean.TRUE);
        return response;
    }


}
