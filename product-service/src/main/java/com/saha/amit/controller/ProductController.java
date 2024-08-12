package com.saha.amit.controller;

import com.saha.amit.dto.ProductDto;
import com.saha.amit.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;


@RestController
@RequestMapping("product")
public class ProductController {

    Log log = LogFactory.getLog(ProductController.class);

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("save")
    public ResponseEntity<Mono<ProductDto>> save(@RequestBody ProductDto productDto, @RequestHeader("userId") String userId) {
        productDto.setUserId(Integer.parseInt(userId));
        log.info("Inside ProductController save " +productDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }

    @PostMapping("save/private")
    public ResponseEntity<Mono<ProductDto>> privateSave(@RequestBody ProductDto productDto, @RequestHeader("userId") String userId) {
        productDto.setUserId(Integer.parseInt(userId));
        log.info("Inside ProductController save " +productDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }

    @PostMapping(value = "saveAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> saveAll(List<ProductDto> productDtoList, @RequestHeader("userId") String userId) {
        productDtoList.forEach(productDto ->
                productDto.setProductId(Integer.parseInt(userId)));
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveAll(productDtoList));
    }

    @GetMapping(value = "findById/{id}")
    public ResponseEntity<Mono<ProductDto>> findById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findById(id));
    }

    @GetMapping(value = "findByUserId/{userId}")
    public ResponseEntity<Flux<ProductDto>> findByUserId(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByUserId(userId));
    }

    @GetMapping(value = "search/{category}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> findByCategory(@PathVariable String category) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findByCategory(category).delayElements(Duration.ofMillis(500)));
    }

    @GetMapping(value = "search/{price1}/{price2}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> findByPriceBetween(@PathVariable Double price1, @PathVariable Double price2) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findByPriceBetween(price1, price2).delayElements(Duration.ofMillis(500)));
    }

    @GetMapping(value = "getAllProduct", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> getAllProduct() {
        log.info("Inside getAllProduct");
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProduct().delayElements(Duration.ofMillis(500)));
    }
}
