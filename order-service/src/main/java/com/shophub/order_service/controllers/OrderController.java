package com.shophub.order_service.controllers;

import com.shophub.order_service.dto.OrderRequest;
import com.shophub.order_service.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        /*
        *
        * CompletableFuture.supplyAsync enables asynchronous execution of a task or computation,
        * allowing your application to perform non-blocking operations and utilize the benefits of concurrent execution.
        * It is particularly useful when dealing with time-consuming or resource-intensive tasks
        * */
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }
    //this method will be invoked when the circuit breaker is in 'open' state
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong, please order after some time :(");
    }

}
