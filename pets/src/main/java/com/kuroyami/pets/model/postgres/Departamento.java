package com.kuroyami.pets.model.postgres;

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
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    // Relación Uno a Muchos con Empleados
    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Empleado> empleados = new ArrayList<>();

    // Constructor por defecto
    public Departamento() {
    }

    // Constructor con parámetros
    public Departamento(String nombre) {
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

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
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
