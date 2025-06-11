package com.capacidad_ms.domain.api;

import com.capacidad_ms.domain.model.Capacity;
import reactor.core.publisher.Mono;

public interface ICapacityServicePort {

    Mono<Capacity> saveCapacity(Capacity capacity);

}
