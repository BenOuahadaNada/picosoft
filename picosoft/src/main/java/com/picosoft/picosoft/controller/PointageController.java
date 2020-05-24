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
import com.picosoft.picosoft.module.CountResponse;
import com.picosoft.picosoft.module.ListStat;
import com.picosoft.picosoft.module.Pointage;
import com.picosoft.picosoft.module.PointageResponse;
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
	
	/*@PreAuthorize("hasAuthority('responsable_rh')")
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
	}*/
	
	@PreAuthorize("hasAnyAuthority('responsable_rh', 'manager')")
	@GetMapping(value="/{email}/{datedeb}/{datefin}")
	public List<Pointage> getPointageByWeek(@PathVariable String email , @PathVariable String datedeb, @PathVariable String datefin){
		return pointage.findPointageByUser(email, datedeb, datefin);
	}
	
	/*@PreAuthorize("hasAnyAuthority('responsable_rh', 'manager')")
	@GetMapping(value="/nbheures")
	public Map<List<ListStat>, List<Double>> getNbHeureByWeek(@PathVariable String email , @PathVariable String dateDebut, @PathVariable String dateFin){
		List<Pointage> Tab= pointage.findPointageByUser(email, dateDebut, dateFin);
		List<Double> percent= new ArrayList<Double>();
		List<name, List<ListStat>> dataset= new ArrayList<name, List<ListStat>>();
		List<ListStat> series= new ArrayList<ListStat>();
		Double numberOfHours=(double) 0;
		for(int i=0; i<Tab.size()-1; i=2) {
			numberOfHours += Tab.get(i-1).getChecktime()-Tab.get(i).getChecktime();
			if(i<Tab.size()-1 && Tab.get(i).getCheckdate()!=Tab.get(i+1).getCheckdate()) {
				series.add(ListStat.this.setName(Tab.get(i).getCheckdate()););
				percent.add(numberOfHours*100/8.5);
				numberOfHours=(double) 0;
			}
		}
		percent.add(numberOfHours*100/8.5);
		series.add(Tab.get(Tab.get(index)))
	}*/ 
	@PreAuthorize("hasAnyAuthority('responsable_rh', 'manager')")
	@GetMapping(value="/nbheures/{email}/{dateDebut}/{dateFin}")
	public List<PointageResponse> getNbHeureByWeek(@PathVariable("email") String email , @PathVariable("dateDebut")  String dateDebut, @PathVariable("dateFin")  String dateFin){
		List<PointageResponse> mp = new ArrayList<>();
		List<Pointage> pointages = pointage.findPointageByUser(email, dateDebut, dateFin);
		String date =  pointages.get(pointages.size()-2).getCheckDate();
		Double sum =(double)0;
		  for(int i=pointages.size()-2;i>=0;i-=2) {
			  if(!date.equals(pointages.get(i).getCheckDate())) {
				 mp.add(new PointageResponse(date, sum/3600000));
				  sum =  (double)pointages.get(i).getCheckTime().getTime()-pointages.get(i+1).getCheckTime().getTime();
				  date = pointages.get(i).getCheckDate();continue;
			  }
			  if(date.equals(pointages.get(i).getCheckDate())) {
				  sum += (double)pointages.get(i).getCheckTime().getTime()-pointages.get(i+1).getCheckTime().getTime();
			  }
			  if(i<= 0) {
				  mp.add(new PointageResponse(date, sum/3600000));
			  }
		  }
		  return mp;
	}
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/count")
	public List<CountResponse> getNbTotal(){
        List<CountResponse> mp= new ArrayList<>();
        int nbMember = pointage.countAll();
        int nbEmp = pointage.countEmployee();
        int nbManager = pointage.countManager();
        int nbRh = pointage.countRh();
        mp.add(new CountResponse("member",nbMember));
        mp.add(new CountResponse("employe",nbEmp));
        mp.add(new CountResponse("manager",nbManager));
        mp.add(new CountResponse("responsable rh", nbRh));
        return mp;
    }
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
