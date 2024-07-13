package com.saha.amit.dto;

public class UserCustomerContactInfo {

    private  UserDto userDto;

    private CompanyContactInfoDto companyContactInfoDto;

    public CompanyContactInfoDto getCompanyContactInfoDto() {
        return companyContactInfoDto;
    }

    public void setCompanyContactInfoDto(CompanyContactInfoDto companyContactInfoDto) {
        this.companyContactInfoDto = companyContactInfoDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

}
