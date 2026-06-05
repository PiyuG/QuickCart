package com.quickcart.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CustomGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Global filter: request intercepted"+exchange.getRequest().mutate());
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            System.out.println("Global filter: Response Completed");
        }));
    }
}
