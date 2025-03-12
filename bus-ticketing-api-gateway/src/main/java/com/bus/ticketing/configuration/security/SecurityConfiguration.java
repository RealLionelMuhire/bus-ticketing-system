package com.bus.ticketing.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> {
                    // Public endpoints
                    exchanges.pathMatchers("/user/api/v1/users/register").permitAll();
                    exchanges.pathMatchers("/user/api/v1/users/login").permitAll();

                    // Public routes for viewing available buses and routes
                    exchanges.pathMatchers("/fleet/api/v1/buses/available").permitAll();
                    exchanges.pathMatchers("/route/api/v1/routes/search").permitAll();

                    // Public booking info
                    exchanges.pathMatchers("/booking/api/v1/info/**").permitAll();

                    // WebSocket connections for live updates
                    exchanges.pathMatchers("/trip-updates/ws/**").permitAll();

                    // Actuator endpoints for all services
                    exchanges.pathMatchers("/actuator/**").permitAll();
                    exchanges.pathMatchers("/user/actuator/**").permitAll();
                    exchanges.pathMatchers("/fleet/actuator/**").permitAll();
                    exchanges.pathMatchers("/route/actuator/**").permitAll();
                    exchanges.pathMatchers("/booking/actuator/**").permitAll();
                    exchanges.pathMatchers("/ticket/actuator/**").permitAll();
                    exchanges.pathMatchers("/payment/actuator/**").permitAll();
                    exchanges.pathMatchers("/trip-updates/actuator/**").permitAll();

                    // Require authentication for all other routes
                    exchanges.anyExchange().authenticated();
                })
                .csrf(csrf -> csrf.disable())  // New style
                .oauth2Login(oauth2Login -> {})  // Empty configuration means use defaults
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))  // Configure JWT resource server
                .build();
    }
}