package com.kuroyami.pets.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.kuroyami.pets.dto.DepartamentoDto;
import com.kuroyami.pets.dto.EmpleadoDto;
import com.kuroyami.pets.exceptions.ResourceNotFoundException;
import com.kuroyami.pets.model.postgres.Departamento;
import com.kuroyami.pets.model.postgres.Empleado;
import com.kuroyami.pets.repository.postgres.DepartamentoRepository;
import com.kuroyami.pets.repository.postgres.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    // Metodo para convertir Entidad a DTO
    public static EmpleadoDto toDTO(Empleado empleado) {
        EmpleadoDto dto = new EmpleadoDto();
        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setPuesto(empleado.getPuesto());
        if (empleado.getDepartamento() != null) {
            dto.setNombreDepartamento(empleado.getDepartamento().getNombre());
            dto.setDepartamentoId(empleado.getDepartamento().getId());
        }
        return dto;
    }

    public static DepartamentoDto depToDTO(Departamento departamento) {
        DepartamentoDto dto = new DepartamentoDto();
        dto.setId(departamento.getId());
        dto.setNombre(departamento.getNombre());
        return dto;
    }

    public Departamento depFromDTO(DepartamentoDto dto){
        Departamento departamento = new Departamento();
        departamento.setId(dto.getId());
        departamento.setNombre(dto.getNombre());
        return departamento;
    }

    // Convertir DTO a Entidad (Suponiendo que existen métodos para manejar la conversión)
    private Empleado fromDTO(EmpleadoDto dto) {
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setPuesto(dto.getPuesto());
        empleado.setDepartamento(depFromDTO(findDepartamentoById(dto.getDepartamentoId())));
        return empleado;
    }

    @Transactional(readOnly = true)
    public List<EmpleadoDto> findAllEmpleados() {
        return empleadoRepository.findAll().stream()
                                 .map(EmpleadoService::toDTO)
                                 .collect(Collectors.toList());
    }

    @Transactional
    public EmpleadoDto findEmpleadoById(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        Hibernate.initialize(empleado.getDepartamento());
        return toDTO(empleado);
    }

    @Transactional
    public DepartamentoDto findDepartamentoById(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));
        Hibernate.initialize(departamento.getNombre());
        return depToDTO(departamento);
    }

    @Transactional
    public Empleado createEmpleado(EmpleadoDto dto) {
        Empleado empleado = fromDTO(dto);
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public Empleado updateEmpleado(Long id, EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        
        empleado.setNombre(empleadoDto.getNombre());
        empleado.setPuesto(empleadoDto.getPuesto());
        
        if (empleadoDto.getNombreDepartamento() != null) {
            Departamento departamento = departamentoRepository.findById(empleadoDto.getDepartamentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));
            empleado.setDepartamento(departamento);
        }

        return empleadoRepository.save(empleado);
    }


    @Transactional
    public void deleteEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        empleadoRepository.delete(empleado);
    }


    //Consulta a traves de ejecución de Store Procedure Postcres
    @Transactional(readOnly = true)
    public List<Empleado> findEmpleadosByDepartamentoId(Long departamentoId) {
        List<Empleado> empleados = empleadoRepository.findEmpleadosByDepartamentoId(departamentoId);
        empleados.forEach(empleado -> {
            if (empleado.getDepartamento() != null) {
                empleado.getDepartamento().getNombre(); // Forzar la inicialización
            }
        });
        return empleados;
    }
    
    
}
