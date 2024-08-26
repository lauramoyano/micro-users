package com.pragma.userscrud.domain.spi;

import com.pragma.userscrud.domain.models.Rol;

public interface IRolPersistencePort {
    Rol findRolByName(String name);
    Rol findRolById(Long id);
}
