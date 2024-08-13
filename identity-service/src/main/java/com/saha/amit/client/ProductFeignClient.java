package com.saha.amit.client;

import com.saha.amit.dto.CompanyContactInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@FeignClient(name = "${product.service.feign.configuration.url}", fallback = ProductContactInfoFallback.class)
public interface ProductFeignClient {

    @GetMapping("/contact-info")
    public ResponseEntity<CompanyContactInfoDto> getContactInfo();
}
