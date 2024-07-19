package com.kuroyami.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.kuroyami.pets.model.postgres.Empleado;
import com.kuroyami.pets.service.DepartamentoService;
import com.kuroyami.pets.service.EmpleadoService;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/{id}/empleados")
    public List<Empleado> getEmpleadosByDepartamentoId(@PathVariable Long id) {
        List<Empleado> empleados = empleadoService.findEmpleadosByDepartamentoId(id);
        // Inicializar el departamento para cada empleado
        empleados.forEach(empleado -> {
            if (empleado.getDepartamento() != null) {
                empleado.getDepartamento().getNombre(); // Forzar la inicialización
            }
        });
        return empleados;
    }


    @PutMapping("/{id}/nombre")
    public ResponseEntity<Void> updateDepartamentoNombre(@PathVariable("id") Long id, @RequestParam("nombre") String nombre) {
        departamentoService.updateNombre(id, nombre);
        return ResponseEntity.ok().build();
    }
}
