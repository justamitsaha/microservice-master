package com.saha.amit.service;

import com.saha.amit.client.ProductClient;
import com.saha.amit.client.ProductFeignClient;
import com.saha.amit.dto.CompanyContactInfoDto;
import com.saha.amit.dto.UserDto;
import com.saha.amit.dto.UserCustomerContactInfo;
import com.saha.amit.entity.User;
import com.saha.amit.exception.UserNoFoundException;
import com.saha.amit.repository.UserRepository;
import com.saha.amit.util.MapperClass;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    UserRepository userRepository;

    ProductClient productClient;

    ProductFeignClient productFeignClient;


    Log log = LogFactory.getLog(UserService.class);

    @Autowired
    UserService(UserRepository userRepository, ProductClient productClient, ProductFeignClient productFeignClient) {
        this.userRepository = userRepository;
        this.productClient = productClient;
        this.productFeignClient = productFeignClient;
    }

    public UserDto save(@Valid UserDto userDto) {
        User user = MapperClass.getUser(userDto);
        log.info("user -->" + user.toString());
        return MapperClass.getUserDto(userRepository.save(user));
    }

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            UserDto userDto = MapperClass.getUserDto(user);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }

    public UserDto findById(String id) {
        var opt = userRepository.findById(Integer.parseInt(id));
        User user = opt.orElseThrow(() -> new UserNoFoundException("User not Found"));
        UserDto userDto = MapperClass.getUserDto(user);
        log.info("findById --> User details fetched from DB -->" + user.toString());
        log.info("findById --> User details response object -->" + userDto.toString());
        return userDto;
    }

    public UserCustomerContactInfo getUserContactInfo(String id) {
        log.info("Inside getUserContactInfo service" + id);
        UserCustomerContactInfo userCustomerContactInfo = new UserCustomerContactInfo();
        var opt = userRepository.findById(Integer.parseInt(id));
        User user = opt.orElseThrow(() -> new UserNoFoundException("User not Found"));
        UserDto userDto = MapperClass.getUserDto(user);
        log.info("User details received" + userDto.toString());

        userCustomerContactInfo.setUserDto(userDto);
        CompanyContactInfoDto companyContactInfoDto = new CompanyContactInfoDto();
        companyContactInfoDto = getContactInfo();
        userCustomerContactInfo.setCompanyContactInfoDto(companyContactInfoDto);
        return userCustomerContactInfo;
    }

    public List<UserDto> findByPhoneNumberContaining(String phoneNumber) {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findByPhoneNumberContaining(phoneNumber).forEach(user -> {
            UserDto userDto = MapperClass.getUserDto(user);
            userDtoList.add(userDto);
        });
        if (userDtoList.size() == 0)
            throw new UserNoFoundException("User not Found");
        return userDtoList;
    }

    public List<UserDto> findByEmailContaining(String email) {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findByEmailContaining(email).forEach(user -> {
            UserDto userDto = MapperClass.getUserDto(user);
            userDtoList.add(userDto);
        });
        if (userDtoList.size() == 0)
            throw new UserNoFoundException("User not Found");
        return userDtoList;
    }

    public CompanyContactInfoDto getContactInfo() {
        Random random = new Random();
        if (random.nextBoolean()) {
            log.info("From Feign client");
            var response = productFeignClient.getContactInfo();
            if (null == response)
                return null;
            else
                return productFeignClient.getContactInfo().getBody();
        } else {
            log.info("From web client");
            return productClient.getContactInfo();
        }
    }
}
