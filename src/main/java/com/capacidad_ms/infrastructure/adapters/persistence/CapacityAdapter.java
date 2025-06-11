package com.capacidad_ms.infrastructure.adapters.persistence;

import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.domain.spi.ICapacityPersistencePort;
import com.capacidad_ms.infrastructure.adapters.mapper.ICapacityMapper;
import com.capacidad_ms.infrastructure.adapters.repository.ICapacityRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CapacityAdapter implements ICapacityPersistencePort {

    private final ICapacityRepository capacityRepository;
    private final ICapacityMapper capacityMapper;


    @Override
    public Mono<Capacity> saveCapacity(Capacity capacity) {
        return Mono.just(capacity).
                map(capacityMapper ::toCapacityEntity).
                flatMap(capacityRepository::save).
                map(capacityMapper::toCapacity);
    }

}
