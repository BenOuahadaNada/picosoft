package com.picosoft.picosoft.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.picosoft.picosoft.module.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByNom(String nom);

	public User findByEmail(String email);

	public List<User> findByPrenom(String prenom);
	

}
