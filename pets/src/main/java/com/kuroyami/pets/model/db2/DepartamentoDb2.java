package com.kuroyami.pets.model.db2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Departamentos")
public class DepartamentoDb2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    // Relación Uno a Muchos con Empleados
    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<EmpleadoDb2> empleados = new ArrayList<>();

    // Constructor por defecto
    public DepartamentoDb2() {
    }

    // Constructor con parámetros
    public DepartamentoDb2(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<EmpleadoDb2> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoDb2> empleados) {
        this.empleados = empleados;
    }

    // Método toString
    @Override
    public String toString() {
        return "Departamento{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", empleados=" + empleados.size() +
               '}';
    }
}
