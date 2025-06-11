package com.capacidad_ms.infrastructure.adapters.mapper;

import com.capacidad_ms.domain.model.Capacity;
import com.capacidad_ms.infrastructure.adapters.entity.CapacityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICapacityMapper {

    Capacity toCapacity(CapacityEntity capacityEntity);
    CapacityEntity toCapacityEntity(Capacity capacity);

}
