package com.saha.amit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(
        name = "User",
        description = "Schema to hold Account information"
)
public class UserDto {

    @Schema(
            description = "User Id of user"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotEmpty(message = "Please provide a name")
    @Schema(
            description = "Name of customer", example = "Guest"
    )
    private String name;

    @NotEmpty(message = "Please provide email")
    @Email(message = "Please provide a valid Email")
    @Schema(
            description = "Email  of user", example = "helloDev@mailinator.com"
    )
    private String email;
    @NotEmpty(message = "Please provide email")
    @Pattern(regexp = "\\d{10}" , message = "Please enter valid phone no")
    @Schema(
            description = "User Phone no", example = "9999999911"
    )
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String role;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Please enter password")
    @Schema(
            description = "User password", example = "hellBoy"
    )
    private String password;

    public UserDto(String name, String email, String phoneNumber, String role, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
    }

    public UserDto(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
