package com.asid.apigateway.config;

import com.asid.apigateway.handler.CartHandler;
import com.asid.apigateway.handler.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(CartHandler cartHandler, OrderHandler orderHandler) {
        return RouterFunctions
            .route(RequestPredicates.POST("/api/cart/add"), cartHandler::addBookToCart)
            .andRoute(RequestPredicates.GET("/api/order/details/{orderId}"), orderHandler::getOrderDetails);
    }
}
