package com.saha.amit.service;

import com.saha.amit.dto.UserDto;
import com.saha.amit.entity.User;
import com.saha.amit.exception.CustomException;
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

@Service
public class UserService {
    UserRepository userRepository;

    Log log = LogFactory.getLog(UserService.class);
    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto save(@Valid UserDto userDto){
        log.info("Inside Save "+userDto.toString());
        User user = MapperClass.getUser(userDto);
        log.info("user -->"+user.toString());
        return MapperClass.getUserDto(userRepository.save(user));
    }

    public List<UserDto> findAll(){
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            UserDto userDto = MapperClass.getUserDto(user);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }

    public UserDto findById(String id){
        var opt =userRepository.findById(Integer.parseInt(id));
        User user = opt.orElseThrow(() ->new UserNoFoundException("User not Found"));
        UserDto userDto = MapperClass.getUserDto(user);
        log.info("findById --> User details fetched from DB -->"+user.toString());
        log.info("findById --> User details response object -->"+userDto.toString());
        return userDto;
    }

    public List<UserDto> findByPhoneNumberContaining(String phoneNumber){
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findByPhoneNumberContaining(phoneNumber).forEach(user -> {
            UserDto userDto = MapperClass.getUserDto(user);
            userDtoList.add(userDto);
        });
        if (userDtoList.size() ==0)
            throw new UserNoFoundException("User not Found");
        return  userDtoList;
    }

    public List<UserDto> findByEmailContaining(String email){
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findByEmailContaining(email).forEach(user -> {
            UserDto userDto = MapperClass.getUserDto(user);
            userDtoList.add(userDto);
        });
        if (userDtoList.size() ==0)
            throw new UserNoFoundException("User not Found");
        return  userDtoList;
    }
}
