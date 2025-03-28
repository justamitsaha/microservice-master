package com.saha.amit.controller;

import com.saha.amit.dto.ErrorResponseDto;
import com.saha.amit.dto.UserDto;
import com.saha.amit.dto.UserCustomerContactInfo;
import com.saha.amit.dto.UserPreferenceDto;
import com.saha.amit.service.UserService;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {

    Log log = LogFactory.getLog(UserController.class);

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("create")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(userDto));
    }

    @PostMapping("setUpData")
    public ResponseEntity<String> setUpData(@Valid @RequestBody UserDto userDto) {
        this.userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @Operation(
            summary = "Fetch User Details REST API",
            description = "REST API to fetch User details based on user id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("findById/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("private/findById/{id}")
    public ResponseEntity<UserDto> privateFindById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("getUserContactInfo/{id}")
    public ResponseEntity<UserCustomerContactInfo> getUserContactInfo(@PathVariable String id) {
        log.info("Inside getUserContactInfo " + id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserContactInfo(id));
    }


    @Operation(
            summary = "Fetch User Details REST API",
            description = "REST API to fetch User details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("findByPhoneNumberContaining")
    public ResponseEntity<List<UserDto>> findByPhoneNumberLike(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{5,10})", message = "At least enter 6 digits to search")
                                                               String phoneNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByPhoneNumberContaining(phoneNumber));
    }


    @Operation(
            summary = "Fetch User Details REST API",
            description = "REST API to fetch User details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @Retry(name = "findByEmailContaining", fallbackMethod = "findByEmailContainingFallback")
    @GetMapping("findByEmailContaining/{email}")
    public ResponseEntity<List<UserDto>> findByEmailContaining(@PathVariable
                                                               @Email(message = "Please provide valid Email")
                                                               String email) {
        log.info("Inside findByEmailContaining ");
        throw new RuntimeException();
    }

    @GetMapping("findUsersWithMailPreference/{email}")
    public ResponseEntity<List<UserPreferenceDto>> findUsersWithMailPreference(@PathVariable String email) {
        log.info("Inside findUsersWithMailPreference  " + email);
        List<UserPreferenceDto> userDtoList = new ArrayList<>();
        userService.findUsersWithMailPreference(Boolean.valueOf(email))
                .forEach(user -> userDtoList.add(new UserPreferenceDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getRole(),
                        user.getUserPreferenceReference().isOkToPush(),
                        user.getUserPreferenceReference().isOkToMail(),
                        user.getUserPreferenceReference().isOkToSms(),
                        user.getUserPreferenceReference().getTimeStamp()
                )));
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

    public ResponseEntity<List<UserDto>> findByEmailContainingFallback(String email, Throwable throwable) {
        log.info("Inside findByEmailContainingFallback  ");
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByEmailContaining(email));
    }




}
