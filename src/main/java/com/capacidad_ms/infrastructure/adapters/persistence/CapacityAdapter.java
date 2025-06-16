package com.capacidad_ms.infrastructure.adapters.persistence;

import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.domain.spi.ICapacityPersistencePort;
import com.capacidad_ms.infrastructure.adapters.mapper.ICapacityMapper;
import com.capacidad_ms.infrastructure.adapters.repository.ICapacityRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Override
    public Mono<Boolean> existsByTechnologyIds(List<Long> technologyIds) {
        return capacityRepository.existsByExactTechnologyIds(technologyIds);
    }

    @Override
    public Mono<List<Capacity>> findAll(int page, int size, String sortBy, String direction) {
        return capacityRepository.findAllPageAndSort(page, size, sortBy, direction)
                .map(capacityMapper::toCapacity)
                .collectList();
    }

    @Override
    public Mono<List<Capacity>> getCapacitiesByIds(List<Long> capacityIds) {
        return Mono.just(capacityIds)
                .flatMapMany(capacityRepository::findAllById)
                .map(capacityMapper::toCapacity)
                .collectList();
    }

}
