package com.saha.amit.client;

import com.saha.amit.dto.CompanyContactInfoDto;
import com.saha.amit.dto.ProductDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProductClient {

    @Value(("${product.service.url}"))
    public String productEndpoint;

    Log log = LogFactory.getLog(ProductClient.class);

    @Autowired
    private WebClient.Builder webBuilder;

    public CompanyContactInfoDto getContactInfo() {
        log.info("getContactInfo webclient "+productEndpoint + "contact-info");
            return webBuilder.build().
                    get()
                    .uri(productEndpoint + "contact-info")
                    .retrieve()
                    .bodyToMono(CompanyContactInfoDto.class)
                    .block();
    }

    public List<ProductDto> findByUserId(int userId) {
        log.info(productEndpoint + "findByUserId/{userId}");
        try {
            return webBuilder.build().
                    get()
                    .uri(productEndpoint + "findByUserId/{userId}", userId)
                    .retrieve()
                    .bodyToFlux(ProductDto.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            log.error("Error in webclient findByUserId --> " + e);
        }
        return null;
    }

}
