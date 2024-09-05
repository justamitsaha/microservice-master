package com.saha.amit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean okToPush;
    private boolean okToMail;
    private boolean okToSms;
    private Instant timeStamp;
    //The Table which has FK is the "OWNING SIDE". and it needs below mapping to create a foreign key
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)    // To create custom FK name in User otherwise default FK name will be USER_PREFERENCE_ID
    private User userMaster;

    public UserPreference(boolean okToPush, boolean okToMail, boolean okToSms, Instant timeStamp) {
        this.okToPush = okToPush;
        this.okToMail = okToMail;
        this.okToSms = okToSms;
        this.timeStamp = timeStamp;
    }

}
