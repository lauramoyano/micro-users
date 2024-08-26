package com.pragma.userscrud.domain.api;

import com.pragma.userscrud.domain.models.Rol;


public interface IRolServicePort {
    Rol findRolByName(String name);
    Rol findRolById(Long id);
}
