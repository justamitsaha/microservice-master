package com.saha.amit.service;

import com.saha.amit.client.UserClient;
import com.saha.amit.dto.ProductDto;
import com.saha.amit.dto.UserDto;
import com.saha.amit.entity.Product;
import com.saha.amit.repository.ProductRepository;
import com.saha.amit.util.Mapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class ProductService {

    ProductRepository productRepository;
    UserClient userClient;

    StreamBridge streamBridge;

    Log log = LogFactory.getLog(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository, UserClient userClient, StreamBridge streamBridge) {
        this.productRepository = productRepository;
        this.userClient = userClient;
        this.streamBridge = streamBridge;
    }

    public Mono<ProductDto> save(ProductDto productDto) {
        return getUser(productDto.getUserId()).map(userDto -> {
            if (null == userDto) throw new RuntimeException();
            else {
                Product product = Mapper.getProduct(productDto);
                product.setUserId(userDto.getId());
                product.setUserName(userDto.getName());
                sendCommunication(productDto);
                return productRepository.save(product);
            }
        }).flatMap(productMono -> productMono.map(Mapper::getProductDto));
    }

    private void sendCommunication(ProductDto productDto) {
        log.info("Sending Communication request for the details: {} " + productDto);
        var result = streamBridge.send("sendCommunication-out-0", productDto);
        log.info("Is the Communication request successfully triggered ? : {} " + result);
    }

//    public Mono<ProductDto> save(ProductDto productDto){
//        UserDto userDto = getUser(productDto.getUserId()).block();
//        if (null == userDto)
//            throw new RuntimeException();
//        log.info("Inside Save Product fetch user details for user with id -->" + productDto.getUserId() +":: "+ userDto.toString());
//        Product product = Mapper.getProduct(productDto);
//        product.setUserId(userDto.getId());
//        product.setUserName(userDto.getName());
//        log.info("Saving product with details -->" + product.toString());
//        return  productRepository.save(product)
//                .map(Mapper::getProductDto);
//    }

    public Flux<ProductDto> saveAll(List<ProductDto> productDtoList) {
        List<Product> products = new ArrayList<>();
        productDtoList.forEach(productDto -> products.add(Mapper.getProduct(productDto)));
        return productRepository.saveAll(products).map(Mapper::getProductDto);
    }

    public Mono<ProductDto> findById(int id) {
        return productRepository.findById(id).map(Mapper::getProductDto);
    }

    public Flux<ProductDto> findByUserId(int id) {
        return productRepository.findByUserId(id).map(Mapper::getProductDto);
    }

    public Flux<ProductDto> findByCategory(String category) {
        return productRepository.findByCategory(category).map(Mapper::getProductDto);
    }

    public Flux<ProductDto> findByPriceBetween(Double price1, Double price2) {
        return productRepository.findByPriceBetween(price1, price2).map(Mapper::getProductDto);
    }

    public Flux<ProductDto> getAllProduct() {
        return productRepository.findAll().map(Mapper::getProductDto);
    }

    public Mono<UserDto> getUser(int id) {
        return userClient.getUserById(id);
    }
}

