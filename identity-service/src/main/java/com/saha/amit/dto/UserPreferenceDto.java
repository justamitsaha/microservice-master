package com.saha.amit.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceDto {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private boolean okToPush;
    private boolean okToMail;
    private boolean okToSms;
    private Instant timeStamp;
}
