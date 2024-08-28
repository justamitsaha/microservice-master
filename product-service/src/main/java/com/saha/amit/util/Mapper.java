package com.saha.amit.util;

import com.saha.amit.dto.ProductDto;
import com.saha.amit.entity.Product;
import com.saha.amit.entity.ProductCategory;

public class Mapper {

    public static Product getProduct(ProductDto productDto){
        return new Product(
                productDto.getProductName(),
                productDto.getProductDescription(),
                productDto.getPrice(),
                productDto.getImagePath(),
                productDto.getCategoryId(),
                productDto.getUserId(),
                productDto.getUserName()
        );
    }

    public static ProductDto getProductDto(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                product.getImagePath(),
                product.getCategoryId(),
                product.getUserId(),
                product.getUserName()
        );
    }

    public static ProductDto getProductCategoryDto(ProductCategory productCategory){
        return new ProductDto(
                productCategory.getProductId(),
                productCategory.getProductName(),
                productCategory.getProductDescription(),
                productCategory.getPrice(),
                productCategory.getImagePath(),
                productCategory.getCategoryId(),
                productCategory.getUserId(),
                productCategory.getUserName(),
                productCategory.getCategoryName()
        );
    }


}
