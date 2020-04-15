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

import com.picosoft.picosoft.dao.AppHoraireRepository;
import com.picosoft.picosoft.module.ApplicationHoraire;


@RestController
@Transactional
@RequestMapping(value="api/user")
public class AppHoraireController {
	@Autowired
	AppHoraireRepository apphoraireRepository;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allAppHoraire")
    public List<ApplicationHoraire> getAllAppHoraire(){
        return apphoraireRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/appHoraire/{id}")
    public ResponseEntity<ApplicationHoraire> getAppHoraireById(@PathVariable Long id) throws Exception{
       ApplicationHoraire apphor = apphoraireRepository.findById(id).orElseThrow(()->new Exception("Application Horaire n'existe pas"));
        return ResponseEntity.ok().body(apphor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterAppHoraire")
    public ApplicationHoraire AjouterAppHoraire(@Valid @RequestBody ApplicationHoraire apphor){
        return apphoraireRepository.save(apphor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateAppHoraire/{id}")
    public ResponseEntity<ApplicationHoraire> updateAppHoraire(@PathVariable Long id, @Valid @RequestBody ApplicationHoraire appHorDetails) throws Exception{
        ApplicationHoraire appHor = apphoraireRepository.findById(id).orElseThrow(()->new Exception("Application Horaire n'existe pas"));
        appHor.setDate(appHorDetails.getDate());
        appHor.setHoraire(appHorDetails.getHoraire());
        appHor.setPolitique(appHorDetails.getPolitique());
        apphoraireRepository.save(appHor);
        return ResponseEntity.ok(appHor);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteAppHoraire/{id}")
    public Map<String,Boolean> deleteAppHoraire(@PathVariable Long id) throws Exception{
        ApplicationHoraire apphor = apphoraireRepository.findById(id).orElseThrow(()->new Exception("Application Horaire n'existe pas"));
        apphoraireRepository.delete(apphor);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Application Horaire est supprimée!",Boolean.TRUE);
        return response;
    }

}
