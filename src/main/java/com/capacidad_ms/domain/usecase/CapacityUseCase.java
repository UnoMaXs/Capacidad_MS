package com.capacidad_ms.domain.usecase;

import com.capacidad_ms.domain.api.ICapacityServicePort;
import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.domain.spi.ICapacityPersistencePort;
import reactor.core.publisher.Mono;

public class CapacityUseCase implements ICapacityServicePort {

    private final ICapacityPersistencePort capacityPersistencePort;

    public CapacityUseCase(ICapacityPersistencePort capacityPersistencePort) {
        this.capacityPersistencePort = capacityPersistencePort;
    }

    @Override
    public Mono<Capacity> saveCapacity(Capacity capacity) {
    return capacityPersistencePort.saveCapacity(capacity);
    }

}
