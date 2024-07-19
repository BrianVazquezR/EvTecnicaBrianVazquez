package com.kuroyami.pets.repository.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kuroyami.pets.model.postgres.Empleado;

import jakarta.transaction.Transactional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query(value = "SELECT * FROM get_empleados_by_departamento(:departamentoId)", nativeQuery = true)
    @Transactional
    public List<Empleado> findEmpleadosByDepartamentoId(Long departamentoId);

}
