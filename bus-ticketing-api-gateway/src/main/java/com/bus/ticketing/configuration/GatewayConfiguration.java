package com.bus.ticketing.configuration;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Principal;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfiguration {

    private static final String HTTP_HEADER_AUTH_USER_ID = "X-Auth-Id";
    private static final String UNAUTHORIZED_USER_NAME = "SYSTEM USER";

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> exchange.getPrincipal().map(Principal::getName).defaultIfEmpty(UNAUTHORIZED_USER_NAME).map(principal -> {
            // adds header to proxied request
            exchange.getRequest().mutate()
                    .header(HTTP_HEADER_AUTH_USER_ID, principal)
                    .build();
            return exchange;
        }).flatMap(chain::filter).then(Mono.fromRunnable(() -> {
            // Any post-processing logic here
        }));
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Management Service
                .route("user-service", r -> r.path("/user/**")
                        .uri("lb://bus-ticketing-user-service"))

                // Fleet & Route Management Service
                .route("fleet-service", r -> r.path("/fleet/**")
                        .uri("lb://bus-ticketing-fleet-service"))
                .route("route-service", r -> r.path("/route/**")
                        .uri("lb://bus-ticketing-route-service"))

                // Booking & Ticketing Service
                .route("booking-service", r -> r.path("/booking/**")
                        .uri("lb://bus-ticketing-booking-service"))
                .route("ticket-service", r -> r.path("/ticket/**")
                        .uri("lb://bus-ticketing-ticket-service"))

                // Payment Processing Service
                .route("payment-service", r -> r.path("/payment/**")
                        .uri("lb://bus-ticketing-payment-service"))

                // Live Trip Updates Service (WebSockets)
                .route("trip-updates-service", r -> r.path("/trip-updates/**")
                        .uri("lb://bus-ticketing-trip-updates-service"))

                // Monitoring endpoints
                .route("monitoring", r -> r.path("/monitoring/**")
                        .uri("lb://bus-ticketing-monitoring-service"))

                .build();
    }
}