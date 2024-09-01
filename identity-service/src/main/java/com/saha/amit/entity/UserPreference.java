package com.saha.amit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
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

    public UserPreference() { }

    public UserPreference(int id, boolean okToPush, boolean okToMail, boolean okToSms, Instant timeStamp, User userMaster) {
        this.id = id;
        this.okToPush = okToPush;
        this.okToMail = okToMail;
        this.okToSms = okToSms;
        this.timeStamp = timeStamp;
        this.userMaster = userMaster;
    }

    @Override
    public String toString() {
        return "UserPreference{" +
                "id=" + id +
                ", okToPush=" + okToPush +
                ", okToMail=" + okToMail +
                ", okToSms=" + okToSms +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOkToPush() {
        return okToPush;
    }

    public void setOkToPush(boolean okToPush) {
        this.okToPush = okToPush;
    }

    public boolean isOkToMail() {
        return okToMail;
    }

    public void setOkToMail(boolean okToMail) {
        this.okToMail = okToMail;
    }

    public boolean isOkToSms() {
        return okToSms;
    }

    public void setOkToSms(boolean okToSms) {
        this.okToSms = okToSms;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(User userMaster) {
        this.userMaster = userMaster;
    }
}
