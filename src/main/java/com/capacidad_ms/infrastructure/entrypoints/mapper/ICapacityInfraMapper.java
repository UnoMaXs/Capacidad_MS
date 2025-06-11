package com.capacidad_ms.infrastructure.entrypoints.mapper;

import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.infrastructure.entrypoints.dto.CapacityDTO;

public interface ICapacityInfraMapper {

    Capacity toCapacity(CapacityDTO capacityDTO);
}
