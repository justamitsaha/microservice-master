package com.saha.amit.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouteLocator routingConfigurationBean(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/auth/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.rewritePath("/auth/(?<segment>.*)","/identity/${segment}"))
                        .uri("lb://IDENTITY-SERVICE"))
                .build();
    }
}
