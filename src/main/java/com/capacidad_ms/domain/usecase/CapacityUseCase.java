package com.capacidad_ms.domain.usecase;

import com.capacidad_ms.domain.api.ICapacityServicePort;
import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.domain.spi.ICapacityPersistencePort;
import com.capacidad_ms.infrastructure.adapters.client.TechnologyWebClient;
import com.capacidad_ms.infrastructure.entrypoints.dto.CapacityResponseDTO;
import com.capacidad_ms.infrastructure.entrypoints.dto.TechnologySummaryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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

    @Override
    public Mono<List<CapacityResponseDTO>> findAll(int page, int size, String sortBy, String direction) {
        return capacityPersistencePort.findAll(page, size, sortBy, direction)
                .flatMap(capacityList -> {
                    List<Long> allTechnologyIds = capacityList.stream()
                            .flatMap(cap -> cap.getTechnologyIds().stream())
                            .distinct()
                            .toList();

                    return technologyWebClient.getTechnologySummariesByIds(allTechnologyIds)
                            .collectMap(TechnologySummaryDTO::getId, Function.identity())
                            .map(techMap -> capacityList.stream().map(cap -> {
                                List<TechnologySummaryDTO> techs = cap.getTechnologyIds().stream()
                                        .map(techMap::get)
                                        .filter(Objects::nonNull)
                                        .toList();

                                return new CapacityResponseDTO(
                                        cap.getId(),
                                        cap.getName(),
                                        cap.getDescription(),
                                        techs
                                );
                            }).toList());
                });
    }

    @Override
    public Mono<List<Capacity>> getCapacitiesByIds(List<Long> capacityIds) {
        return capacityPersistencePort.getCapacitiesByIds(capacityIds);
    }

    @Override
    public Mono<List<CapacityResponseDTO>> getCapacitiesSummaryByIds(List<Long> capacityIds) {
        return capacityPersistencePort.getCapacitiesByIds(capacityIds)
                .flatMap(capacityList -> {
                    List<Long> allTechnologyIds = capacityList.stream()
                            .flatMap(cap -> cap.getTechnologyIds().stream())
                            .distinct()
                            .toList();

                    return technologyWebClient.getTechnologySummariesByIds(allTechnologyIds)
                            .collectMap(tech -> tech.getId(), Function.identity())
                            .map(techMap -> capacityList.stream().map(cap -> {
                                List<TechnologySummaryDTO> techs = cap.getTechnologyIds().stream()
                                        .map(techMap::get)
                                        .filter(Objects::nonNull)
                                        .toList();

                                return new CapacityResponseDTO(
                                        cap.getId(),
                                        cap.getName(),
                                        cap.getDescription(),
                                        techs
                                );
                            }).toList());
                });
    }

    @Override
    public Flux<Long> getTechnologyIdsUsedByOtherCapacities(List<Long> excludedCapacityIds) {
        return capacityPersistencePort.getTechnologyIdsUsedByOtherCapacities(excludedCapacityIds);
    }

    @Override
    public Mono<Void> deleteCapacitiesByIds(List<Long> capacityIds) {
        return capacityPersistencePort.deleteCapacitiesByIds(capacityIds);
    }

}
