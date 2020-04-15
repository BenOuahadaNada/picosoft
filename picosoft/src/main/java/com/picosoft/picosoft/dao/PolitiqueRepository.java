package com.picosoft.picosoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.picosoft.picosoft.module.Politique;

@Repository
public interface PolitiqueRepository extends JpaRepository<Politique, Long> {

}
