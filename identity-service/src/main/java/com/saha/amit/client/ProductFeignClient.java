package com.saha.amit.client;

import com.saha.amit.dto.CompanyContactInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@FeignClient("${product.service.feign.configuration.url}")
public interface ProductFeignClient {

    @GetMapping("/contact-info")
    public ResponseEntity<CompanyContactInfoDto> getContactInfo();
}
