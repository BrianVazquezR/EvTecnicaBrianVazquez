package com.kuroyami.pets.soap;

import com.kuroyami.pets.departamentos.DepartamentosLista;
import com.kuroyami.pets.departamentos.GetAllDepartamentosRequest;
import com.kuroyami.pets.departamentos.GetAllDepartamentosResponse;
import com.kuroyami.pets.departamentos.GetDepartamentoRequest;
import com.kuroyami.pets.departamentos.GetDepartamentoResponse;
import com.kuroyami.pets.model.postgres.Departamento;
import com.kuroyami.pets.repository.postgres.DepartamentoRepository;

import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.slf4j.Logger;

@Endpoint
public class DepartamentoEndpoint {

    private static final String NAMESPACE_URI = "http://kuroyami.com/departamentos";
    private static final Logger logger = LoggerFactory.getLogger(DepartamentoEndpoint.class);

    private final DepartamentoRepository departamentoRepository;

    //@Autowired
    public DepartamentoEndpoint(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDepartamentoRequest")
    @ResponsePayload
    @Transactional
    public GetDepartamentoResponse getDepartamento(@RequestPayload GetDepartamentoRequest request) {
        logger.info("Byaku Log - Solicitud recibida para el ID: " + request.getId());
        GetDepartamentoResponse response = new GetDepartamentoResponse();

        System.out.println("Byaku 1: "+request.getId()); 
        Departamento departamento = departamentoRepository.findById(request.getId()).orElse(null);
        System.out.println("Byaku 2: departamento= "+departamento);
        
        if (departamento != null) {
            logger.info("Byaku Log - Departamento encontrado: " + departamento.getNombre());
            Hibernate.initialize(departamento.getEmpleados());
            com.kuroyami.pets.departamentos.Departamento responseDepartamento = new com.kuroyami.pets.departamentos.Departamento();
            responseDepartamento.setId(departamento.getId());
            responseDepartamento.setNombre(departamento.getNombre());
            response.setDepartamento(responseDepartamento);
        } else {
            logger.warn("Byaku Log - Departamento no encontrado para el ID: " + request.getId());
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllDepartamentosRequest")
    @ResponsePayload
    public GetAllDepartamentosResponse getAllDepartamentos(@RequestPayload GetAllDepartamentosRequest request) {
        logger.info("Byaku Log - Solicitud recibida para obtener todos los departamentos");
        GetAllDepartamentosResponse response = new GetAllDepartamentosResponse();
        List<Departamento> departamentos = departamentoRepository.findAll();
        List<com.kuroyami.pets.departamentos.Departamento> responseDepartamentos = departamentos.stream().map(dep -> {
            com.kuroyami.pets.departamentos.Departamento responseDepartamento = new com.kuroyami.pets.departamentos.Departamento();
            responseDepartamento.setId(dep.getId());
            responseDepartamento.setNombre(dep.getNombre());
            return responseDepartamento;
        }).collect(Collectors.toList());

        DepartamentosLista departamentosLista = new DepartamentosLista();
        departamentosLista.getDepartamento().addAll(responseDepartamentos);
        response.setDepartamentos(departamentosLista);

        return response;
    }

}

