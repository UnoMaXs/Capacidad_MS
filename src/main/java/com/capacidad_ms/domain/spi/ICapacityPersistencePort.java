package com.capacidad_ms.domain.spi;

import com.capacidad_ms.domain.model.Capacity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacityPersistencePort {

    Mono<Capacity> saveCapacity(Capacity capacity);
    Mono<Boolean> existsByTechnologyIds(List<Long> technologyIds);

}
