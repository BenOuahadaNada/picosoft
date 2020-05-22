package com.picosoft.picosoft.controller;

import java.sql.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picosoft.picosoft.dao.PointageRepository;
import com.picosoft.picosoft.dao.UserRepository;
import com.picosoft.picosoft.module.ListStat;
import com.picosoft.picosoft.module.Pointage;
import com.picosoft.picosoft.module.PointageByWeek;
import com.picosoft.picosoft.module.User;

@Transactional
@RestController
@RequestMapping(value="api/user")
public class PointageController {
	@Autowired
	PointageRepository pointage;
	
	@Autowired
	UserRepository userRepo;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allPointage/{email}")
	public List<Pointage> getAllPointage(@PathVariable String email){
		return pointage.findAllByEmail(email);
	}
	
	@PreAuthorize("hasAnyAuthority('responsable_rh','admin', 'manager', 'employe')")
	@GetMapping(value="/allpointage")
	public List<Pointage> getAll(){
		return pointage.findAll();
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterPointage")
	public ResponseEntity<?> AjouterPointage(@Valid @RequestBody Pointage p) {
		if(p.getCheckDate()==null||p.getCheckTime()==null||p.getCheckType()==null||p.getUser().getIdUser()==null || p.getVerifyCode()==0)
		{
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		User u=userRepo.getOne(p.getUser().getIdUser());
		p.setUser(u);
		return  new ResponseEntity<>(pointage.save(p),HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyAuthority('responsable_rh','admin', 'manager', 'employe')")
	@GetMapping(value="/checkin/{date}/{email}")
	public List<Object> getCheckTimeIn(@PathVariable String date , @PathVariable String email) {
		return  pointage.findCheckIn(date , email);
	
	}
	
	@PreAuthorize("hasAnyAuthority('responsable_rh','admin', 'manager', 'employe')")
	@GetMapping(value="/checkout/{date}/{email}")
	public List<Object> getCheckTimeOut(@PathVariable("date") String date , @PathVariable("email") String email) {
		return  pointage.findCheckOut(date , email);
	
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/countEmp")
	public int getNbEmp() {
		return pointage.countEmployee();
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/countMember")
	public int getNbMember() {
		return pointage.countAll();
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/countManager")
	public int getNbManager() {
		return pointage.countManager();
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/countRh")
	public int getNbRh() {
		return pointage.countRh();
	}
	
	@PreAuthorize("hasAnyAuthority('responsable_rh', 'manager')")
	@GetMapping(value="/{email}/{datedeb}/{datefin}")
	public List<PointageByWeek> getPointageByWeek(@PathVariable String email , @PathVariable String datedeb, @PathVariable String datefin){
		return pointage.findPointageByWeek(email, datedeb, datefin);
	}
	
	@PreAuthorize("hasAnyAuthority('responsable_rh', 'manager')")
	@GetMapping(value="/nbheures")
	public Map<List<ListStat>, List<Double>> getNbHeureByWeek(@PathVariable String email , @PathVariable String dateDebut, @PathVariable String dateFin){
		List<PointageByWeek> Tab= pointage.findPointageByWeek(email, dateDebut, dateFin);
		List<Double> percent= new ArrayList<Double>();
		List<name, List<ListStat>> dataset= new ArrayList<name, List<ListStat>>();
		List<ListStat> series= new ArrayList<ListStat>();
		Double numberOfHours=(double) 0;
		for(int i=0; i<Tab.size()-1; i=2) {
			numberOfHours += Tab.get(i-1).getChecktime()-Tab.get(i).getChecktime();
			if(i<Tab.size()-1 && Tab.get(i).getCheckdate()!=Tab.get(i+1).getCheckdate()) {
				series.add(ListStat.this.setName(Tab.get(i).getCheckdate()););
				percent.
			}
		}
	}
	/*@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/count")
	public Map<String, Integer> getNbTotal(){
        Map<String,Integer> mp= new HashMap<>();
        int nbMember = pointage.countAll();
        int nbEmp = pointage.countEmployee();
        int nbManager = pointage.countManager();
        int nbRh = pointage.countRh();
        mp.put("member",nbMember);
        mp.put("employe",nbEmp);
        mp.put("manager",nbManager);
        mp.put("responsable rh", nbRh);
        return mp;
    }*/
	/*@GetMapping(value="/diff/{checkDate}/{id}")
	public List<Long> getDifference(@PathVariable String checkDate, @PathVariable Long id) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		List<Long> diff= new ArrayList<>();
		//List<String> diffS=diff.stream().map(Object::toString).collect(Collectors.toList());
		List<Out> out= pointage.findCheckTimeOut(checkDate, id);
		List<Out> in= pointage.findCheckTimeIn(checkDate, id);
		/*for(Object objet: out) {
			diffS.add(Objects.toString(objet, null));
		}
		for(int i=0; i<=out.size()-1; i++) {
			if(out.indexOf(i).get(0))
				Date O= sdf.parse((out[0]).toString());
				Date I=sdf.parse(in.get(0).toString());
			    diff.add((O.getTime()- I.getTime())/3600000);
		}
		return diff;
    }*/

}
