package com.saha.amit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToOne(mappedBy = "userMaster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPreference userPreferenceReference;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                (userPreferenceReference != null ? ", userPreferenceReference=" + userPreferenceReference : "") +
                '}';
    }
}
