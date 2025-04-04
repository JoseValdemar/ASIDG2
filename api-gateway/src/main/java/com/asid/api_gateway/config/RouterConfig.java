package com.asid.api_gateway.config;

import com.asid.api_gateway.handler.CartHandler;
import com.asid.api_gateway.handler.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(CartHandler cartHandler, OrderHandler orderHandler) {
        return RouterFunctions
            .route(RequestPredicates.POST("/api/cart/add"), cartHandler::addBookToCart)
            .andRoute(RequestPredicates.GET("/api/orderdetails/{orderId}"), orderHandler::getOrderDetails);
    }
}
