package com.capacidad_ms.infrastructure.adapters.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "capacities")
public class CapacityEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private List<Long> technologyIds;

}
