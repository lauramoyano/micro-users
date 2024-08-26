package com.pragma.userscrud.infrastructure.output.jpa.adapter;

import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.domain.spi.IRolPersistencePort;
import com.pragma.userscrud.infrastructure.output.jpa.mapper.IRolEntityMapper;
import com.pragma.userscrud.infrastructure.output.jpa.repository.IRolRespository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RolAdapter implements IRolPersistencePort {
    private final IRolRespository rolRepo;
    private final IRolEntityMapper rolMapper;
    @Override
    public Rol findRolByName(String name) {
        return rolMapper.mapRolEntityToRol(rolRepo.findByName(name));
    }

    @Override
    public Rol findRolById(Long id) {
        return rolMapper.mapRolEntityToRol(rolRepo.findById(id).orElse(null));
    }
}
