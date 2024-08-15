package com.saha.amit.controller;

import com.saha.amit.record.CompanyContactInfoDto;
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

@RestController
@RequestMapping("configuration")
public class ConfigurationController {

    private Log log = LogFactory.getLog(ConfigurationController.class);

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
}
