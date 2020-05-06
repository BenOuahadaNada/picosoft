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
	
	@Query(value="SELECT convert(check_time , char) FROM pointage p , user u WHERE verify_code=101 and p.user_id_user=u.id_user and check_date=:checkDate",
			nativeQuery=true)
	public List<String> findCheckTimeOut (@Param("checkDate") String checkDate);
	
	@Query(value="SELECT convert(check_time , char) FROM pointage p , user u WHERE verify_code=1 and p.user_id_user=u.id_user and check_date=:date",
			nativeQuery=true)
	public List<String> findCheckTimeIn (@Param("date") String date);
	

}
