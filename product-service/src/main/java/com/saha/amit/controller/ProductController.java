package com.saha.amit.controller;

import com.saha.amit.dto.ProductDto;
import com.saha.amit.exception.CustomerNotFindException;
import com.saha.amit.service.ProductService;
import com.saha.amit.util.Mapper;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;




   /*
   https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/responseentity.html
    ResponseEntity<Mono<T>> or ResponseEntity<Flux<T>> make the response status and headers known immediately while the body is provided asynchronously at a later point.
    Use Mono if the body consists of 0..1 values or Flux if it can produce multiple values.
    Mono<ResponseEntity<T>> provides all three — response status, headers, and body, asynchronously at a later point.
    This allows the response status and headers to vary depending on the outcome of asynchronous request handling.
    Mono<ResponseEntity<Mono<T>>> or Mono<ResponseEntity<Flux<T>>> are yet another possible, albeit less common alternative.
    They provide the response status and headers asynchronously first and then the response body, also asynchronously, second.
*/

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    Log log = LogFactory.getLog(ProductController.class);

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("save")
    public ResponseEntity<Mono<ProductDto>> save(@Valid @RequestBody ProductDto productDto, @RequestHeader("userId") String userId) {
        productDto.setUserId(Integer.parseInt(userId));
        log.info("Inside ProductController save " + productDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }

    @PostMapping("save/private")
    public ResponseEntity<Mono<ProductDto>> privateSave(@Valid @RequestBody ProductDto productDto, @RequestHeader("userId") String userId) {
        productDto.setUserId(Integer.parseInt(userId));
        log.info("Inside ProductController save " + productDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }

    @PostMapping(value = "saveAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> saveAll(List<ProductDto> productDtoList, @RequestHeader("userId") String userId) {
        productDtoList.forEach(productDto ->
                productDto.setProductId(Integer.parseInt(userId)));
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveAll(productDtoList));
    }

    @GetMapping(value = "findById/{id}")
    public Mono<ResponseEntity<ProductDto>> findById(@PathVariable int id) {
        //return ResponseEntity.status(HttpStatus.FOUND).body(productService.findById(id));
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new CustomerNotFindException(id)));
                //.defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>>  deleteCustomerById(@PathVariable Integer id){
            return this.productService.deleteCustomerById(id)
                    .filter(aBoolean -> aBoolean)
                    .map(aBoolean -> ResponseEntity.ok().<Void>build())
                    .switchIfEmpty(Mono.error(new CustomerNotFindException(id)));
                    //.defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "findByUserId/{userId}")
    public ResponseEntity<Flux<ProductDto>> findByUserId(@PathVariable int userId) {
        log.info("Inside findByUserId " +userId);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByUserId(userId));
    }

    @GetMapping(value = "search/{category}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> findByCategoryId(@PathVariable int category) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findByCategoryId(category).delayElements(Duration.ofMillis(500)));
    }

    @GetMapping(value = "search/{price1}/{price2}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> findByPriceBetween(@PathVariable Double price1, @PathVariable Double price2) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findByPriceBetween(price1, price2).delayElements(Duration.ofMillis(500)));
    }

    @GetMapping(value = "getAllProduct", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ProductDto>> getAllProduct() {
        log.info("Inside getAllProduct");
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProduct().delayElements(Duration.ofMillis(500)));
    }

    @GetMapping(value = "getAllProductPage")
    public Mono<List<ProductDto>> getAllProductPage(@RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(defaultValue = "10") Integer size) {
        return productService.getAllProductPage(page,size).collectList();
        //return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProductPage(page, size).delayElements(Duration.ofMillis(500)));
    }
}
