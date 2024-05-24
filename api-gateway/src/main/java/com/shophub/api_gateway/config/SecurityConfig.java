package com.shophub.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        // Disable CSRF protection
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // Configure authorization rules for request exchanges
                .authorizeExchange(exchange ->
                        // Allow unrestricted access to any endpoint under "/eureka/"
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                // Require authentication for all other endpoints
                                .anyExchange()
                                .authenticated())
                // Configure OAuth2 resource server to use JWT tokens
                .oauth2ResourceServer(spec -> spec.jwt(Customizer.withDefaults()));

        // Build and return the configured security filter chain
        return serverHttpSecurity.build();
    }
}
