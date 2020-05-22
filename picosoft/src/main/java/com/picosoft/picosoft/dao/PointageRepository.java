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
import com.picosoft.picosoft.module.PointageByWeek;

@Repository
public interface PointageRepository extends JpaRepository<Pointage, Long> {
	
	/*@Query(value="SELECT convert(check_time, char), user_id_user FROM pointage WHERE verify_code=101 and user_id_user=:id and check_date=:checkDate",
			nativeQuery=true)
	public List<OutRepository> findCheckTimeOut (@Param("checkDate") String checkDate , @Param ("id") Long id);
	
	@Query(value="SELECT convert(check_time , char), user_id_user FROM pointage WHERE verify_code=1 and user_id_user=:id and check_date=:date",
			nativeQuery=true)
	public List<OutRepository> findCheckTimeIn (@Param("date") String date, @Param("id") Long id);*/ 
	
	//select p.id, p.checkktime from pointage p where (verfrycdeo=1 or verfiey code =0) and date=x groupe by p.id
	
	@Query("select u.email , p.checkTime  from pointage p join p.user u where p.user.email=u.email and u.email=:email and p.CheckDate=:date and p.VerifyCode=101")
	public List<Object> findCheckOut(@Param ("date") String date , @Param("email") String email);
	
	@Query("select u.email , p.checkTime from pointage p join p.user u where p.user.email=u.email and u.email=:email and p.CheckDate=:date and p.VerifyCode=1")
	public List<Object> findCheckIn(@Param ("date") String date , @Param("email") String email);
	
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
	
	@Query("SELECT new com.picosoft.picosoft.module.PointageByWeek(u.email, p.checkType , p.checkTime, p.checkDate) FROM pointage p join p.user u WHERE p.user.email=u.email AND u.email=:email"
			+ "and p.checkDate BETWEEN :dateDebut AND :dateFin ORDER BY p.checkDate DESC , p.checkTime DESC")
	public List<PointageByWeek> findPointageByWeek(@Param("email") String email , @Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin);

}
