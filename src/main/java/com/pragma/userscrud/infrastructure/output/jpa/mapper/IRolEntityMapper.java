package com.pragma.userscrud.infrastructure.output.jpa.mapper;


import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.infrastructure.output.jpa.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRolEntityMapper {
    Rol mapRolEntityToRol(RolEntity rolEntity);
}
