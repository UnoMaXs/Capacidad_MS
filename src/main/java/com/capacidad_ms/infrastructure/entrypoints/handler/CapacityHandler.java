package com.capacidad_ms.infrastructure.entrypoints.handler;

import com.capacidad_ms.domain.api.ICapacityServicePort;
import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.infrastructure.entrypoints.dto.CapacityDTO;
import com.capacidad_ms.infrastructure.entrypoints.mapper.ICapacityInfraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
}
