package com.saha.amit.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouteLocator routingConfigurationBean(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/auth/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/auth/(?<segment>.*)", "/identity/${segment}")
                                .circuitBreaker(config -> config.setName("identity-circuit-breaker")))
//                                .circuitBreaker(config -> config.setName("identity-circuit-breaker")
//                                        .setFallbackUri("forward:/auth/configuration/contact-info"))
//                                .retry(retryConfig -> retryConfig.setRetries(3)
//                                        .setMethods(HttpMethod.GET)
//                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://IDENTITY-SERVICE"))
                .route(p -> p
                        .path("/product/configuration/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/product/configuration/(?<segment>.*)", "/configuration/${segment}")
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://PRODUCT-SERVICE"))
                .route(p -> p
                        .path("/product/actuator/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/product/actuator/(?<segment>.*)", "/actuator/${segment}")
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://PRODUCT-SERVICE"))
                .build();
    }
}
