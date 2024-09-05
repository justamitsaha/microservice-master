package com.saha.amit.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceDto {
    private int id;
    private boolean okToPush;
    private boolean okToMail;
    private boolean okToSms;
    private Instant timeStamp;

    public UserPreferenceDto(boolean okToPush, boolean okToMail, boolean okToSms, Instant timeStamp) {
        this.okToPush = okToPush;
        this.okToMail = okToMail;
        this.okToSms = okToSms;
        this.timeStamp = timeStamp;
    }
}
