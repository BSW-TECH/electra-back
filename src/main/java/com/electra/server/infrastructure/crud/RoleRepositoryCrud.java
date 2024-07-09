package com.electra.server.infrastructure.crud;

import com.electra.server.infrastructure.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepositoryCrud extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}

