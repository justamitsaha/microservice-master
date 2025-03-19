package com.saha.amit.controller;

import com.saha.amit.dto.CompanyContactInfoDto;
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

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/pod-info")
    public ResponseEntity<Map<String, String>> getPodInfo() {
        Map<String, String> podInfo = new HashMap<>();

        try {
            // Get hostname
            String hostname = InetAddress.getLocalHost().getHostName();
            podInfo.put("hostname", hostname);

            // Get IP address
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            podInfo.put("ipAddress", ipAddress);

            // If running in Kubernetes, you can also expose the pod name from environment variable
            String podName = System.getenv("HOSTNAME");
            if (podName != null) {
                podInfo.put("podName", podName);
            }

            return ResponseEntity.ok(podInfo);
        } catch (Exception e) {
            podInfo.put("error", "Failed to retrieve pod information: " + e.getMessage());
            return ResponseEntity.internalServerError().body(podInfo);
        }
    }
}
