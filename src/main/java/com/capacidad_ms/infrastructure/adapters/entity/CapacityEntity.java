package com.capacidad_ms.infrastructure.adapters.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "capacities")
public class CapacityEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private List<Long> technologyIds;

}
