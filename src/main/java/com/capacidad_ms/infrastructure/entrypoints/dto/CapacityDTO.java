package com.capacidad_ms.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CapacityDTO {

    @Size(max = 50)
    private String name;
    @Size(max = 90)
    private String description;
    private List<Long> technologyIds;

}
