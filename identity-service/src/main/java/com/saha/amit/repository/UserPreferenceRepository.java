package com.saha.amit.repository;

import com.saha.amit.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Integer> {


    @Modifying
    @Transactional
    @Query("UPDATE UserPreference up SET up.okToMail = :okToMail, up.okToPush = :okToPush, up.okToSms = :okToSms, up.timeStamp = :timeStamp WHERE up.userMaster.id = :userId")
    int updateUserPreferences(@Param("userId") int userId,
                              @Param("okToMail") boolean okToMail,
                              @Param("okToPush") boolean okToPush,
                              @Param("okToSms") boolean okToSms,
                              @Param("timeStamp") Instant timeStamp);
}
