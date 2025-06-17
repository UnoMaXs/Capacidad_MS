package com.capacidad_ms.infrastructure.adapters.repository;

import com.capacidad_ms.infrastructure.adapters.entity.CapacityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ICapacityRepositoryCustomImpl implements ICapacityRepositoryCustom {

    private final DatabaseClient databaseClient;

    @Override
    public Flux<CapacityEntity> findAllPageAndSort(int page, int size, String sortBy, String direction) {
        String query = String.format("""
            SELECT * FROM capacities 
            ORDER BY %s %s 
            LIMIT %d OFFSET %d
        """, sortBy, direction.toUpperCase(), size, page * size);

        return databaseClient.sql(query)
                .map((row, metadata) -> {
                    Long[] techIdsArray = row.get("technology_ids", Long[].class);
                    List<Long> technologyIds = techIdsArray != null
                            ? Arrays.asList(techIdsArray)
                            : Collections.emptyList();

                    return CapacityEntity.builder()
                            .id(row.get("id", Long.class))
                            .name(row.get("name", String.class))
                            .description(row.get("description", String.class))
                            .technologyIds(technologyIds)
                            .build();
                })
                .all();
    }

    @Override
    public Mono<Boolean> existsByExactTechnologyIds(List<Long> technologyIds) {

        Long[] array = technologyIds.toArray(new Long[0]);
        String arrayLiteral = "ARRAY[" + Arrays.stream(array)
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";

        String sql = """
        SELECT EXISTS (
            SELECT 1
            FROM capacities
            WHERE array_length(technology_ids, 1) = array_length(%s, 1)
              AND (
                  SELECT COUNT(*)
                  FROM unnest(technology_ids) t1
                  JOIN unnest(%s) t2 ON t1 = t2
              ) = array_length(%s, 1)
        )
        """.formatted(arrayLiteral, arrayLiteral, arrayLiteral);

        return databaseClient.sql(sql)
                .map(row -> row.get(0, Boolean.class))
                .one();
    }

}
