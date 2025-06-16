package com.capacidad_ms.infrastructure.entrypoints.mapper;

import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.infrastructure.entrypoints.dto.CapacityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICapacityInfraMapper {

    Capacity toCapacity(CapacityDTO capacityDTO);

}
