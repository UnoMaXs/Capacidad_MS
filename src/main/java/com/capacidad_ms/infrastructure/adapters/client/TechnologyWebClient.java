package com.capacidad_ms.infrastructure.adapters.client;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TechnologyWebClient {

    private final WebClient webClient;

    @Value("${app.technology.url}")
    private String technologyServiceUrl;

    public Mono<Map<String, Object>> validateTechnologyIds(List<Long> ids) {
        String queryParam = String.join(",", ids.stream().map(String::valueOf).toList());

        return webClient.get()
                .uri(technologyServiceUrl + "/technology/ids?ids=" + queryParam)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

}
