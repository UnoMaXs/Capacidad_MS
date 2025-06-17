package com.capacidad_ms.infrastructure.adapters.repository;

import com.capacidad_ms.infrastructure.adapters.entity.CapacityEntity;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ICapacityRepositoryCustom {
    Flux<CapacityEntity> findAllPageAndSort(int page, int size, String sortBy, String direction);
    Mono<Boolean> existsByExactTechnologyIds(@Param("technologyIds") List<Long> technologyIds);
}
