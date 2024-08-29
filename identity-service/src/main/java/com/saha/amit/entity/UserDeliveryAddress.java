package com.saha.amit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int addressId;
    private String address;
    private String country;
    private String state;
    private int userId;

}
