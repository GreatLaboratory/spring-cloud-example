package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        // path에 있는 패턴으로 api-gateway에 클라이언트의 요청이 들어오면
        // uri에 있는 ip address와 port number인 micro service에 요청을 전달(route)한다.
        // 그 중간에 filter가 요청과 응답을 가로채서 header에 특정값을 설정해놓을 수 있다.
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                            .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                            .addResponseHeader("first-response", "first-response-header"))
                            .uri("http://localhost:9001"))
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:9002"))
                .build();
    }
}