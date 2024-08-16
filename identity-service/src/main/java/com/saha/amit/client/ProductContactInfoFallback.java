package com.saha.amit.client;

import com.saha.amit.dto.CompanyContactInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

    @Component
    public class ProductContactInfoFallback implements ProductFeignClient {
        @Override
        public ResponseEntity<CompanyContactInfoDto> getContactInfo() {
            return null;
        }
    }
