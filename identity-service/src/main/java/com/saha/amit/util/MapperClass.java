package com.saha.amit.util;

import com.saha.amit.dto.UserDto;
import com.saha.amit.dto.UserPreferenceDto;
import com.saha.amit.entity.User;
import com.saha.amit.entity.UserPreference;

import java.time.Instant;
import java.util.Random;

public class MapperClass {

    public static User getUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());
        //If this is creation of user then user id will be 0 by default hence preference is set as false
        if (userDto.getId() == 0) {
            UserPreference userPreference =new UserPreference(false, false, false, Instant.now());
            userPreference.setUserMaster(user);
            user.setUserPreferenceReference(userPreference);
        }
        return user;
    }

    public static UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public static UserPreference createUserPreferenceDto(User user) {
        Random random = new Random();
        return new UserPreference(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), Instant.now());
    }
}
