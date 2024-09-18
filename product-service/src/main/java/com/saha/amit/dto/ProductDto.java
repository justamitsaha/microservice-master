package com.saha.amit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int productId;
    @NotEmpty
    private String productName;
    @NotEmpty
    private String productDescription;
    @NotEmpty
    @Min(10) private int price;
    private String imagePath;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int userId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userName;
    private int categoryId;

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ProductDto() {
    }

    public ProductDto(String productName, String productDescription, int price,
                      String imagePath, int categoryId, int userId, String userName) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
    }

    public ProductDto(int productId, String productName, String productDescription, int price,
                      String imagePath, int categoryId, int userId, String userName) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
    }

    public ProductDto(int productId, String productName, String productDescription, int price,
                      String imagePath, int categoryId, int userId, String userName, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", userId=" + userId +
                '}';
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
