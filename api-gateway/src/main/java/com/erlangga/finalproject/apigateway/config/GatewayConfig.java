package com.erlangga.finalproject.apigateway.config;

import com.erlangga.finalproject.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth",r->r.path("/auth/**").filters(f->f.filter(filter)).uri("lb://auth"))
                .route("auth",r->r.path("/users/**").filters(f->f.filter(filter)).uri("lb://auth"))
                .route("category-service",r->r.path("/category/**").filters(f->f.filter(filter)).uri("lb://category-service"))
                .route("post-service",r->r.path("/post/**").filters(f->f.filter(filter)).uri("lb://post-service"))
                .route("log-service",r->r.path("/logs/**").filters(f->f.filter(filter)).uri("lb://log-service"))
                .build();
    }

}
