package com.picosoft.picosoft.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picosoft.picosoft.dao.PointageRepository;

@Transactional
@RestController
@RequestMapping(value="api/user")
public class PointageController {
	@Autowired
	PointageRepository pointage;
	
	public List<String> out= new ArrayList<String>();
	
	public List<String> in= new ArrayList<String>();
	
	@GetMapping(value="/checktimeout/{checkDate}")
	public List<String> getCheckTimeOut(@PathVariable String checkDate) {
		out= pointage.findCheckTimeOut(checkDate);
		return out;
	}
	
	@GetMapping(value="/check/{date}")
	public List<String> getCheckTimeIn(@PathVariable String date) {
		in=pointage.findCheckTimeIn(date);
		return in;
	}
	
	@GetMapping(value="/diff/{checkDate}")
	public List<Long> getDifference(@PathVariable String checkDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		List<Long> diff= new ArrayList<>();
		//List<String> diffS=new ArrayList<>(diff.size());
		List<String> out= pointage.findCheckTimeOut(checkDate);
		List<String> in= pointage.findCheckTimeIn(checkDate);
		for(int i=0; i<=out.size()-1 ; i++) {
				Date O= sdf.parse(out.get(i));
				Date I=sdf.parse(in.get(i));
			    diff.add((O.getTime()- I.getTime())/3600000);
		}
		return diff;
    }

}
