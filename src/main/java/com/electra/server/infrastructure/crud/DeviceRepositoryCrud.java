package com.electra.server.infrastructure.crud;

import com.electra.server.infrastructure.entity.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepositoryCrud extends CrudRepository<DeviceEntity, Long> {

    Optional<DeviceEntity> findByKey(String key);

}
