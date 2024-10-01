package com.saha.amit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    RequestHandler requestHandler;

    @Autowired
    public RouterConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .POST("product/route/save", requestHandler::save)
                .POST("product/route/saveAll", request -> requestHandler.saveAll(request))
                .GET("product/route/findById/{id}", request -> requestHandler.findById(request))
                .GET("product/route/findByCategory", request -> requestHandler.findByCategory(request))
                .GET("product/route/findByPrice", request -> requestHandler.findByPriceBetween(request))
                .build();
    }


}
