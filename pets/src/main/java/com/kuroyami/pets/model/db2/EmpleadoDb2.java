package com.kuroyami.pets.model.db2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Empleados")
public class EmpleadoDb2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "puesto")
    private String puesto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private DepartamentoDb2 departamento;

    // Constructor por defecto
    public EmpleadoDb2() {
    }

    // Constructor con parámetros
    public EmpleadoDb2(String nombre, String puesto, DepartamentoDb2 departamento) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.departamento = departamento;
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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public DepartamentoDb2 getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDb2 departamento) {
        this.departamento = departamento;
    }

    // Método toString
    @Override
    public String toString() {
        return "Empleado{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", puesto='" + puesto + '\'' +
               ", departamento=" + (departamento != null ? departamento.getNombre() : "null") +
               '}';
    }
}