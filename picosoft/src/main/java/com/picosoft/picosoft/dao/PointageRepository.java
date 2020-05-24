package com.picosoft.picosoft.dao;

import java.sql.Time;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.picosoft.picosoft.module.Pointage;
import com.picosoft.picosoft.module.PointageResponse;

@Repository
public interface PointageRepository extends JpaRepository<Pointage, Long> {
	/*@Query("select u.email , p.checkTime  from pointage p join p.user u where p.user.email=u.email and u.email=:email and p.checkDate=:date and p.verifyCode=101")
	public List<Object> findCheckOut(@Param ("date") String date , @Param("email") String email);
	
	@Query("select u.email , p.checkTime from pointage p join p.user u where p.user.email=u.email and u.email=:email and p.checkDate=:date and p.verifyCode=1")
	public List<Object> findCheckIn(@Param ("date") String date , @Param("email") String email);*/
	
	@Query("select p from pointage p where p.user.email=:email")
	public List<Pointage> findAllByEmail(@Param("email") String email);
	
	@Query("select count(*) from user u where u.role.id=2")
	public int countEmployee();
	
	@Query ("select count(*) from user u")
	public int countAll();
	
	@Query("select count(*) from user u where u.role.id=3")
	public int countManager();
	
	@Query ("select count(*) from user u where u.role.id=4")
	public int countRh();
	
	@Query("SELECT p FROM pointage p join p.user u WHERE p.user.idUser=u.idUser AND u.email=:email "
			+ " and p.checkDate BETWEEN :dateDebut AND :dateFin ORDER BY p.checkDate DESC , p.checkTime DESC")
	public List<Pointage> findPointageByUser(@Param("email") String email , @Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin);

}
