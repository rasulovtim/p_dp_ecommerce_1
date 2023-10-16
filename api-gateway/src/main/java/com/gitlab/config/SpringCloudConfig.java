package com.gitlab.config;

import com.gitlab.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringCloudConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/")

                        .uri("http://localhost:8083/"))
                .route("security-service", r -> r.path("/auth/**")

                        .uri("http://localhost:8080/"))
                .route("gazon-service", r -> r.path("/api/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8080/api/"))
                .route("security-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083/auth/"))

                .build();
    }
}
