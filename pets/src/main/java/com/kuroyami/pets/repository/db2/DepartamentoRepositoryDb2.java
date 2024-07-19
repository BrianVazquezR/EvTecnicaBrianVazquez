package com.kuroyami.pets.repository.db2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kuroyami.pets.model.db2.DepartamentoDb2;

@Repository
public interface DepartamentoRepositoryDb2 extends JpaRepository<DepartamentoDb2, Long>{

    @Procedure(name = "update_department_name")
    void updateDepartamentoNombre(@Param("dept_id") Long deptId, @Param("new_name") String nuevoNombre);

}
