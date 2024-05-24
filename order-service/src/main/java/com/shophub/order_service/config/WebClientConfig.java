package com.shophub.order_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(value = "inventory-service", configuration = WebClientConfig.class)
public class WebClientConfig {
    @Bean
    //@LoadBalanced
    public WebClient.Builder webClientBuilder(){
        // the WebClient is an HTTP client that can be used to make HTTP requests to external services.
        return WebClient.builder();
    }
}
