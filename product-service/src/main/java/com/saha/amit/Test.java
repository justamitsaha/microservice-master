package com.saha.amit;

import com.github.javafaker.Faker;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Faker faker = new Faker();
            String category = faker.commerce().department();
            category = category.replace(" ", "_");
        }
    }
}
