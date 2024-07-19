package com.kuroyami.pets.repository.db2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kuroyami.pets.model.db2.EmpleadoDb2;

@Repository
public interface EmpleadoRepositoryDb2 extends JpaRepository<EmpleadoDb2, Long>{

}
