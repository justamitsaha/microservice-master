package com.saha.amit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int productId;
    private String productName;
    private String productDescription;
    private int price;
    private String imagePath;
    private String category;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int userId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ProductDto() {
    }

    public ProductDto(String productName, String productDescription, int price, String imagePath, String category, int userId, String userName) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
        this.userId = userId;
        this.userName = userName;
    }

    public ProductDto(int productId, String productName, String productDescription, int price, String imagePath, String category, int userId, String userName) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", category='" + category + '\'' +
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
