package com.capacidad_ms.infrastructure.entrypoints.handler;

import com.capacidad_ms.domain.api.ICapacityServicePort;
import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.infrastructure.entrypoints.dto.CapacityDTO;
import com.capacidad_ms.infrastructure.entrypoints.mapper.ICapacityInfraMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CapacityHandler {

    private final ICapacityServicePort capacityServicePort;
    private final ICapacityInfraMapper capacityInfraMapper;

    public Mono<ServerResponse> saveCapacity(ServerRequest request) {
        return request.bodyToMono(CapacityDTO.class)
                .flatMap(dto -> {
                    Capacity capacity = capacityInfraMapper.toCapacity(dto);
                    return capacityServicePort.saveCapacity(capacity);
                })
                .flatMap(savedCapacity ->
                        ServerResponse.ok()
                                .bodyValue(savedCapacity)
                );
    }

    public Mono<ServerResponse> listCapacities(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String sortBy = request.queryParam("sortBy").orElse("name");
        String direction = request.queryParam("direction").orElse("asc");

        return capacityServicePort.findAll(page, size, sortBy, direction)
                .flatMap(capacityList -> ServerResponse.ok().bodyValue(capacityList));
    }

    public Mono<ServerResponse> getCapacityById(ServerRequest request) {
            List<Long> ids = Arrays.stream(request.queryParam("ids")
                            .orElse("")
                            .split(","))
                    .filter(id -> !id.isBlank())
                    .map(Long::valueOf)
                    .toList();

            return capacityServicePort.getCapacitiesByIds(ids)
                    .map(list ->{
                        List<Long> foundIds = list.stream().map(Capacity::getId).toList();
                        List<Long> missing = ids.stream()
                                .filter(id -> !foundIds.contains(id))
                                .toList();
                        return Map.of(
                                "valid", missing.isEmpty(),
                                "missingIds", missing
                        );
                    })
                    .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> getCapacitySummaries(ServerRequest request) {
        List<Long> ids = Arrays.stream(request.queryParam("ids")
                        .orElse("")
                        .split(","))
                .filter(id -> !id.isBlank())
                .map(Long::valueOf)
                .toList();

        return capacityServicePort.getCapacitiesSummaryByIds(ids)
                .flatMap(summaries -> ServerResponse.ok().bodyValue(summaries));
    }



}
