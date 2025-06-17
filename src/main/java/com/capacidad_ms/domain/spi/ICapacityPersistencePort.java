package com.capacidad_ms.domain.spi;

import com.capacidad_ms.domain.model.Capacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacityPersistencePort {

    Mono<Capacity> saveCapacity(Capacity capacity);
    Mono<Boolean> existsByTechnologyIds(List<Long> technologyIds);
    Mono<List<Capacity>> findAll(int page, int size, String sortBy, String direction);
    Mono<List<Capacity>> getCapacitiesByIds(List<Long> capacityIds);
    Flux<Long> getTechnologyIdsUsedByOtherCapacities(List<Long> excludedCapacityIds);
    Mono<Void> deleteCapacitiesByIds(List<Long> capacityIds);



}
