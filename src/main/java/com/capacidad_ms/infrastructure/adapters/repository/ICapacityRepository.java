package com.capacidad_ms.infrastructure.adapters.repository;

import com.capacidad_ms.infrastructure.adapters.entity.CapacityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICapacityRepository extends ReactiveCrudRepository <CapacityEntity, Long> , ICapacityRepositoryCustom {

}
