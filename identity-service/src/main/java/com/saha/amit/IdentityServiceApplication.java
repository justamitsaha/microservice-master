package com.saha.amit;

import com.saha.amit.dto.UserDto;
import com.saha.amit.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@EnableFeignClients
public class IdentityServiceApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;
    private final Log log = LogFactory.getLog(IdentityServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("H2 console URL http://localhost:8090/identity/h2-console/login.do");
        UserDto userDto = new UserDto("Amit Saha","amit007@mailinator.com", "9999999999", "SELLER", "pwd");
        UserDto userDto1 = new UserDto("Shamit Saha","shamit007@mailinator.com", "9999999998", "SELLER", "pwd");
        UserDto userDto2 = new UserDto("Shivangi Saha","shivang007i@mailinator.com", "9999999997", "SELLER", "pwd");
        UserDto userDto3 = new UserDto("Anju Saha","anju007@mailinator.com", "9999999996", "SELLER", "pwd");
        userService.save(userDto);
        userService.save(userDto1);
        userService.save(userDto2);
        userService.save(userDto3);
    }
}