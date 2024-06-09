package com.saha.amit.util;

import com.saha.amit.dto.product.ProductDto;
import com.saha.amit.entity.Product;

public class Mapper {

    public static Product getProduct(ProductDto productDto){
        return new Product(
                productDto.getProductName(),
                productDto.getProductDescription(),
                productDto.getPrice(),
                productDto.getImagePath(),
                productDto.getCategory(),
                productDto.getUserId(),
                productDto.getUserName());
    }

    public static ProductDto getProductDto(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                product.getImagePath(),
                product.getCategory(),
                product.getUserId(),
                product.getUserName());
    }
}
