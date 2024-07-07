package com.saha.amit.dto;

import java.util.List;

public class UserProductList {

    private  UserDto userDto;
    private List<ProductDto> productDtoList;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }

}
