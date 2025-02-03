package com.saha.amit;

import com.saha.amit.dto.ProductDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class ProductTest {
    public static final Log log = LogFactory.getLog(ProductTest.class);

    @Autowired
    private WebTestClient webTestClient;

    //@Test
    public void getAllProductPage() {
        this.webTestClient.get()
                .uri("/product/getAllProductPage?page=1&size=10")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ProductDto.class)
                .value(productDto -> {
                    log.info("getAllProductPage result "+productDto.toString());
                })
                .hasSize(10);

    }

    //@Test
    public void getAllProductPageJsonPath() {
        this.webTestClient.get()
                .uri("/product/getAllProductPage?page=1&size=10")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(entityExchangeResult -> log.info(new String(entityExchangeResult.getResponseBody())))
                .jsonPath("$.length()").isEqualTo(10)
                .jsonPath("$[0].productId").isEqualTo(1);

    }

    //@Test
    public void findById() {
        this.webTestClient.get()
                .uri("/product/findById/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductDto.class)
                .value(productDto -> log.info(productDto.toString()));
        //https://github.com/vinsguru/spring-webflux-course/blob/master/02-webflux-playground/src/test/java/com/vinsguru/playground/tests/sec03/CustomerServiceTest.java

    }

}
