package com.capacidad_ms.infrastructure.adapters.repository;

import com.capacidad_ms.infrastructure.adapters.entity.CapacityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacityRepository extends ReactiveCrudRepository <CapacityEntity, Long> {

    @Query("""
    SELECT EXISTS (
        SELECT 1
        FROM capacities
        WHERE ARRAY(SELECT unnest(technology_ids) ORDER BY 1)
            = ARRAY(SELECT unnest(:technologyIds) ORDER BY 1)
    )
""")
    Mono<Boolean> existsByExactTechnologyIds(@Param("technologyIds") List<Long> technologyIds);


}
