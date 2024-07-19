package com.kuroyami.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import com.kuroyami.pets.dto.EmpleadoDto;
import com.kuroyami.pets.exceptions.ResourceNotFoundException;
import com.kuroyami.pets.model.postgres.Empleado;
import com.kuroyami.pets.repository.postgres.EmpleadoRepository;
import com.kuroyami.pets.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;  // Asumiendo que EmpleadoService maneja la lógica de negocio

    @GetMapping
    public List<EmpleadoDto> getAllEmpleados() {
        return empleadoService.findAllEmpleados();
    }

    @PostMapping
    public EmpleadoDto createEmpleado(@RequestBody EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoService.createEmpleado(empleadoDto);
        return EmpleadoService.toDTO(empleado); 
    }

    @GetMapping("/{id}")
    public EmpleadoDto getEmpleadoById(@PathVariable Long id) {
        return empleadoService.findEmpleadoById(id);
    }


    @PutMapping("/{id}")
    public EmpleadoDto updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoDto empleadoDto) {
        Empleado updatedEmpleado = empleadoService.updateEmpleado(id, empleadoDto);
        return EmpleadoService.toDTO(updatedEmpleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.ok().build();
    }


    //Servicio para consulta por Procedicmiento Postgres
    //@GetMapping("/departamento/{id}")
    //public List<Empleado> getEmpleadosByDepartamento(@PathVariable long id) {
    //    return empleadoService.getEmpleadosByDepartamentoId(id);
    //}

}
