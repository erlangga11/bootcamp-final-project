package com.erlangga.finalproject.apigateway.filter;

import com.erlangga.finalproject.apigateway.exception.JwtTokenMalformedException;
import com.erlangga.finalproject.apigateway.exception.JwtTokenMissingException;
import com.erlangga.finalproject.apigateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        final List<String> apiEndpoints = List.of("/register","/token");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri->r.getURI().getPath().contains(uri));
        if (isApiSecured.test(request)){
            if (!request.getHeaders().containsKey("Authorization")){
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

//            final String token = request.getHeaders().getOrEmpty("Authorization").toString().substring(7);
            final String token = getJWTFromRequest(request);

            try{
                jwtUtil.validateToken(token);
            }catch (JwtTokenMalformedException | JwtTokenMissingException e){
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }
            Claims claims = jwtUtil.getClaims(token);
            exchange.getRequest().mutate().header("id",String.valueOf(claims.get("id"))).build();
        }
        return chain.filter(exchange);
    }
    private String getJWTFromRequest(ServerHttpRequest  request){
        String bearerToken  = request.getHeaders().getOrEmpty("Authorization").get(0);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
