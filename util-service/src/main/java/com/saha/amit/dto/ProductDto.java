package com.saha.amit.dto;

public record ProductDto(int productId, String productName, String productDescription, int price, String imagePath, String category, int userId,  String userName) {
}
