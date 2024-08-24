package com.saha.amit;

import com.saha.amit.dto.UserDto;
import com.saha.amit.service.UserService;
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

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        UserDto userDto = new UserDto("Amit Saha","amit007@mailinator.com", "9999999999", "SELLER", "pwd");
//        UserDto userDto1 = new UserDto("Shamit Saha","shamit007@mailinator.com", "9999999998", "SELLER", "pwd");
//        UserDto userDto2 = new UserDto("Shivangi Saha","shivang007i@mailinator.com", "9999999997", "SELLER", "pwd");
//        UserDto userDto3 = new UserDto("Anju Saha","anju007@mailinator.com", "9999999996", "SELLER", "pwd");
//        userService.save(userDto);
//        userService.save(userDto1);
//        userService.save(userDto2);
//        userService.save(userDto3);
    }
}