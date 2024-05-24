package com.shophub.order_service.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

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
