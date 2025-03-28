package com.saha.amit.controller;

import com.saha.amit.record.CompanyContactInfoDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("configuration")
public class ConfigurationController {

    private final Log log = LogFactory.getLog(ConfigurationController.class);

    @Value("${api.info}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CompanyContactInfoDto companyContactInfoDto;


    @GetMapping("/api-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    @GetMapping("/env-variables")
    public ResponseEntity<String> getEnvironmentValues() {
        log.info("POTASH " + System.getenv("POTASH"));
        String response = environment.getProperty("POTASH");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CompanyContactInfoDto> getContactInfo() {
        log.info("Inside getContactInfo");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyContactInfoDto);
    }


    @GetMapping("/retry")
    public int getProbabilisticResponse() {
        log.info("Inside retry pattern");
        Random random = new Random();
        // Generate a random number between 0 and 2
        int randomValue = random.nextInt(3);

        // 1/3 chance of returning an integer (when randomValue is 0)
        if (randomValue == 0) {
            return 42;
        } else {
            // 2/3 chance of throwing an exception
            throw new RuntimeException("Random exception occurred");
        }
    }

    @GetMapping("/retryWithFallback")
    @Retry(name = "retryConfig",fallbackMethod = "retryFallback")
    public int getProbabilisticResponse2() throws TimeoutException {

        Random random = new Random();
        int randomValue = random.nextInt(3);
        log.info("Inside retryWithFallback pattern " +randomValue);
        if (randomValue == 0) {
            return 42;
        } else if(randomValue ==1){
            throw new NullPointerException("Null exception occurred");
        }else {
            throw new NumberFormatException("Number format exception occurred");
        }
    }

    public int retryFallback() {
        return  99;
    }
}
