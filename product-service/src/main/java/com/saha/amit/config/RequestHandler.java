package com.saha.amit.config;


import com.saha.amit.dto.ProductDto;
import com.saha.amit.service.ProductService;
import com.saha.amit.entity.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestHandler {

    private final ProductService productService;

    Log log = LogFactory.getLog(RequestHandler.class);

    @Autowired
    public RequestHandler(ProductService productService) {
        this.productService = productService;
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.info("Inside RequestHandler save");
        HttpHeaders httpHeaders = serverRequest.headers().asHttpHeaders();
        Integer user = Integer.valueOf(Objects.requireNonNull(httpHeaders.get("userId")).get(0));
        return serverRequest.bodyToMono(ProductDto.class)
                .doOnNext(productDto -> productDto.setUserId(user))
                .flatMap(this.productService::save)
                .flatMap(productDto -> ServerResponse.ok().bodyValue(productDto));

    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        log.info("Inside RequestHandler findById");
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
        return this.productService.findById(id)
                .flatMap(productDto -> ServerResponse.ok().bodyValue(productDto));
    }

    public Mono<ServerResponse> saveAll(ServerRequest serverRequest) {
        Flux<ProductDto> productFlux = serverRequest.bodyToFlux(ProductDto.class);
        List<ProductDto> productList = new ArrayList<>();
        productFlux.doOnNext(productList::add).subscribe();
        Flux<ProductDto> response = productService.saveAll(productList);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(response, Product.class);
    }

    public Mono<ServerResponse> findByCategory(ServerRequest serverRequest) {
        //String category = String.valueOf(serverRequest.queryParam("category"));
        int category = Integer.parseInt(serverRequest.pathVariable("price2"));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findByCategoryId(category), Product.class);
    }

    public Mono<ServerResponse> findByPriceBetween(ServerRequest serverRequest) {
        double price1 = Double.parseDouble(serverRequest.pathVariable("price1"));
        double price2 = Double.parseDouble(serverRequest.pathVariable("price2"));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findByPriceBetween(price1, price2), Product.class);
    }
}
