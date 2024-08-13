package com.saha.amit.functions;

import com.saha.amit.dto.ProductDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    public static final Log log = LogFactory.getLog(MessageFunctions.class);

    @Bean
    public Function<ProductDto, ProductDto> email(){
        return productDto -> {
            log.info("Sending mail "+ productDto.toString());
            return productDto;
        };
    }

    @Bean
    public Function<ProductDto, String> sms(){
        return productDto -> {
            log.info("Sending sms "+ productDto.toString());
            return productDto.productDescription();
        };
    }
}
