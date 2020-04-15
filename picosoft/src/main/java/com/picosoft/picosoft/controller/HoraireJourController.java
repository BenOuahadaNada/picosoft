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

import com.picosoft.picosoft.dao.HoraireJourRepository;
import com.picosoft.picosoft.module.HoraireJour;

@RestController
@Transactional
@RequestMapping(value = "api/user")
public class HoraireJourController {
	@Autowired
	HoraireJourRepository horjourRepository;

	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allHoraireJour")
    public List<HoraireJour> getAllHoraireJour(){
        return horjourRepository.findAll();
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/horaireJour/{id}")
    public ResponseEntity<HoraireJour> getHoraireJourById(@PathVariable Long id) throws Exception{
       HoraireJour horj = horjourRepository.findById(id).orElseThrow(()->new Exception("Horaire Jour n'existe pas"));
        return ResponseEntity.ok().body(horj);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterHoraireJour")
    public HoraireJour AjouterHoraireJour(@Valid @RequestBody HoraireJour horj){
        return horjourRepository.save(horj);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateHoraireJour/{id}")
    public ResponseEntity<HoraireJour> updateHoraireJour(@PathVariable Long id, @Valid @RequestBody HoraireJour horjDetails) throws Exception{
        HoraireJour horj = horjourRepository.findById(id).orElseThrow(()->new Exception("Horaire Jour n'existe pas"));
        horj.setNomJour(horjDetails.getNomJour());
        horj.setHDebut1(horjDetails.getHDebut1());
        horj.setHFin1(horjDetails.getHFin1());
        horj.setHDebut2(horjDetails.getHDebut2());
        horj.setHFin2(horjDetails.getHFin2());
        horj.setHoraire(horjDetails.getHoraire());
        horjourRepository.save(horj);
        return ResponseEntity.ok(horj);
    }
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteHoraireJour/{id}")
    public Map<String,Boolean> deleteHoraireJour(@PathVariable Long id) throws Exception{
        HoraireJour horj = horjourRepository.findById(id).orElseThrow(()->new Exception("Horaire Jour n'existe pas"));
        horjourRepository.delete(horj);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Horaire Jour est supprimée!",Boolean.TRUE);
        return response;
    }
}
