package com.picosoft.picosoft.dao;

import java.sql.Time;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.picosoft.picosoft.module.Pointage;

@Repository
public interface PointageRepository extends JpaRepository<Pointage, Long> {
	
	/*@Query("select p.check_time from pointage p , user u where p.user=u.id and verify_code=101 and check_date=:date")
	public Time findCheckTypeOut(@Param("date") Date date);*/
	
	/*@Query(value="SELECT convert(check_time, char), user_id_user FROM pointage WHERE verify_code=101 and user_id_user=:id and check_date=:checkDate",
			nativeQuery=true)
	public List<OutRepository> findCheckTimeOut (@Param("checkDate") String checkDate , @Param ("id") Long id);
	
	@Query(value="SELECT convert(check_time , char), user_id_user FROM pointage WHERE verify_code=1 and user_id_user=:id and check_date=:date",
			nativeQuery=true)
	public List<OutRepository> findCheckTimeIn (@Param("date") String date, @Param("id") Long id);*/ 
	
/*	@Query("SELECT convert(check_time, char) FROM pointage p, user u WHERE ((p.verify_code=1 OR p.verify_code=101) and (p.user_id_user=u.id_user and u.email=:email and check_date=:checkdate)) GROUP BY u.id_user",
			nativeQuery=true)
	public List<Object> findChecktime(@Param("checkdate") String checkdate , @Param("email") String email);*/
	//select p.id, p.checkktime from pointage p where (verfrycdeo=1 or verfiey code =0) and date=x groupe by p.id
	
	@Query("select u.email , p.checkTime from pointage p join p.user u where p.user.email=u.email and u.email=:email and p.CheckDate=:date ")
	public List<Object> findChecktime(@Param ("date") String date , @Param("email") String email);
	
	@Query("select p from pointage p where p.user.email=:email")
	public List<Pointage> findAllByEmail(@Param("email") String email);
	

}
