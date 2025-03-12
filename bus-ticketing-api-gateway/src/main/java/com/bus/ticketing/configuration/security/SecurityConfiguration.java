package com.bus.ticketing.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        ServerHttpSecurity httpSecurity = http
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
                });

        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        httpSecurity.oauth2ResourceServer(oAuth2ResourceServer ->
                oAuth2ResourceServer.jwt(jwt -> jwt.jwkSetUri(jwkUri)));

        return httpSecurity.build();
    }
}