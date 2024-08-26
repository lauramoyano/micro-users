package com.pragma.userscrud.infrastructure.output.jpa.repository;

import com.pragma.userscrud.infrastructure.output.jpa.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRolRespository extends JpaRepository<RolEntity, Long> {
    RolEntity findByName(String name);

}
