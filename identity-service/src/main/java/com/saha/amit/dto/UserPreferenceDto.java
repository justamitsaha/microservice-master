package com.saha.amit.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPreferenceDto {
    private int id;
    private boolean okToPush;
    private boolean okToMail;
    private boolean okToSms;
    private Instant timeStamp;
}
