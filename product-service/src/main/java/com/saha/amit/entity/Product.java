package com.saha.amit.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Product")
public class Product {

    @Id
    private int productId;
    private String productName;
    private String productDescription;
    private int price;
    private String imagePath;
    private int userId;
    private String userName;
    private int categoryId;
    public Product() {
    }

    public Product(String productName, String productDescription, int price, String imagePath, int categoryId, int userId, String userName) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
