package com.electra.server.infrastructure.crud;

import com.electra.server.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryCrud extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
