package com.gitlab.config;

import com.gitlab.filters.AuthenticationFilter;
import com.gitlab.filters.RouteValidator;
import com.gitlab.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class SpringCloudConfig {

    private final AuthenticationFilter filter;
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/bank-card/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/example/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/passport/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/personal_address/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/pickup_point/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/postomat/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/images/**")
//                        .uri("http://localhost:8080/"))
//                .route(r -> r.path("/api/product/**")
//                        .uri("http://localhost:8080/"))
//                .build();
//    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("gazon-service", r -> r.path("/api/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8080/api/"))
                .route("security-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083/"))
                .build();
    }
}
