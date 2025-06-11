package com.capacidad_ms.domain.usecase;

import com.capacidad_ms.domain.api.ICapacityServicePort;
import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.domain.spi.ICapacityPersistencePort;
import com.capacidad_ms.infrastructure.adapters.client.TechnologyWebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class CapacityUseCase implements ICapacityServicePort {

    private final ICapacityPersistencePort capacityPersistencePort;
    private final TechnologyWebClient technologyWebClient;

    public CapacityUseCase(ICapacityPersistencePort capacityPersistencePort, TechnologyWebClient technologyWebClient) {
        this.capacityPersistencePort = capacityPersistencePort;
        this.technologyWebClient = technologyWebClient;
    }

    @Override
    public Mono<Capacity> saveCapacity(Capacity capacity) {
        List<Long> technologyIds = capacity.getTechnologyIds();

        return technologyWebClient.validateTechnologyIds(technologyIds)
                .filter(result -> Boolean.TRUE.equals(result.get("valid")))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("IDs inválidos")))
                .flatMap(valid -> Mono.just(technologyIds))
                .filter(ids -> ids.size() >= 3 && ids.size() <= 20)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Debe tener entre 3 y 20 tecnologías")))
                .filter(ids -> ids.stream().distinct().count() == ids.size())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tecnologías repetidas")))
                .flatMap(ids -> capacityPersistencePort.existsByTechnologyIds(ids)
                        .filter(exists -> !exists)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Ya existe una capacidad con estos IDs")))
                )
                .then(capacityPersistencePort.saveCapacity(capacity));
    }

}
