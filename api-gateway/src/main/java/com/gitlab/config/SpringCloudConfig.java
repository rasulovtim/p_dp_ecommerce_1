package com.gitlab.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/bank-card/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/example/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/passport/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/personal_address/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/pickup_point/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/postomat/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/images/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/api/product/**")
                        .uri("http://localhost:8080/"))
                .build();
    }
}
