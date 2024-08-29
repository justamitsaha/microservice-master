package com.saha.amit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String role;
    private String password;

    @OneToOne(mappedBy = "userMaster", cascade = CascadeType.ALL)
    private UserPreference userPreferenceReference;

}
