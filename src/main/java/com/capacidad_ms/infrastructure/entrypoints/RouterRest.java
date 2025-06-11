package com.capacidad_ms.infrastructure.entrypoints;

import com.capacidad_ms.infrastructure.entrypoints.handler.CapacityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(CapacityHandler capcityHandler) {
        return route(POST("/capacity"), capcityHandler::saveCapacity);
    }

}
