package com.capacidad_ms.domain.api;

import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.infrastructure.entrypoints.dto.CapacityResponseDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacityServicePort {

    Mono<Capacity> saveCapacity(Capacity capacity);
    Mono<List<CapacityResponseDTO>> findAll(int page, int size, String sortBy, String direction);
    Mono<List<Capacity>> getCapacitiesByIds(List<Long> capacityIds);
    Mono<List<CapacityResponseDTO>> getCapacitiesSummaryByIds(List<Long> capacityIds);

}
