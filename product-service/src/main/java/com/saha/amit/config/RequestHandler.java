package com.saha.amit.config;


import com.saha.amit.dto.ProductDto;
import com.saha.amit.exception.CustomerNotFindException;
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

import java.time.Duration;
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

    public Mono<ServerResponse> saveAll(ServerRequest serverRequest) {
        Flux<ProductDto> productFlux = serverRequest.bodyToFlux(ProductDto.class);
        List<ProductDto> productList = new ArrayList<>();
        productFlux.doOnNext(productList::add).subscribe();
        Flux<ProductDto> response = productService.saveAll(productList);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(response, Product.class);
    }

    public Mono<ServerResponse> deleteCustomerById(ServerRequest serverRequest) {
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
        log.info("Inside RequestHandler deleteCustomerById" + id);
        return this.productService.deleteCustomerById(id)
                .filter(aBoolean -> aBoolean)
                .switchIfEmpty(Mono.error(new CustomerNotFindException(id)))
                .flatMap(aBoolean -> ServerResponse.ok().bodyValue(aBoolean));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
        log.info("Inside RequestHandler findById" + id);
        return this.productService.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFindException(id)))
                .flatMap(productDto -> ServerResponse.ok().bodyValue(productDto));
    }

    public Mono<ServerResponse> findByUserId(ServerRequest serverRequest) {
        var userId = Integer.parseInt(serverRequest.pathVariable("userId"));
        log.info("Inside RequestHandler findByUserId" + userId);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findByUserId(userId).delayElements(Duration.ofMillis(100)), Product.class);
    }


    public Mono<ServerResponse> findByCategory(ServerRequest serverRequest) {
        int category = Integer.parseInt(serverRequest.pathVariable("category"));
        log.info("Inside RequestHandler deleteCustomerById" + category);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findByCategoryId(category).delayElements(Duration.ofMillis(100)), Product.class);
    }

    public Mono<ServerResponse> findByPriceBetween(ServerRequest serverRequest) {
        var var1 = serverRequest.queryParam("price1");
        var var2 = serverRequest.queryParam("price2");
        double price1 = Double.parseDouble(var1.orElse(null));
        double price2 = Double.parseDouble(var2.orElse(null));
        log.info("Inside findByPriceBetween "+ price1 + " "+price2);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findByPriceBetween(price1, price2), Product.class);
    }
}
