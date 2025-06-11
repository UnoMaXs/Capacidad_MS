package com.capacidad_ms.application;

import com.capacidad_ms.domain.api.ICapacityServicePort;
import com.capacidad_ms.domain.spi.ICapacityPersistencePort;
import com.capacidad_ms.domain.usecase.CapacityUseCase;
import com.capacidad_ms.infrastructure.adapters.client.TechnologyWebClient;
import com.capacidad_ms.infrastructure.adapters.mapper.ICapacityMapper;
import com.capacidad_ms.infrastructure.adapters.persistence.CapacityAdapter;
import com.capacidad_ms.infrastructure.adapters.repository.ICapacityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

    private final ICapacityRepository capacityRepository;
    private final ICapacityMapper capacityMapper;
    private final TechnologyWebClient technologyWebClient;

    @Bean
    public ICapacityPersistencePort capacityPersistencePort(){
        return new CapacityAdapter(capacityRepository, capacityMapper);
    }

    @Bean
    public ICapacityServicePort capacityServicePort(ICapacityPersistencePort capacityPersistencePort) {
        return new CapacityUseCase(capacityPersistencePort, technologyWebClient);
    }

}
