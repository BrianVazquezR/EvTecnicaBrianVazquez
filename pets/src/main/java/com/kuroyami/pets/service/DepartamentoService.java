package com.kuroyami.pets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuroyami.pets.repository.db2.DepartamentoRepositoryDb2;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepositoryDb2 departamentoRepository;

    @Transactional("db2TransactionManager")
    public void updateNombre(Long deptId, String nuevoNombre) {
        departamentoRepository.updateDepartamentoNombre(deptId, nuevoNombre);
    }
}
