package com.saha.amit.repository;

import com.saha.amit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByPhoneNumberContaining(String phoneNumber);

    public List<User> findByEmailContaining(String email);

    @Query("SELECT u FROM User u JOIN u.userPreferenceReference up WHERE up.okToMail = :okToMail")
    List<User> findUsersWithMailPreference(Boolean okToMail);


    @Query(value = "SELECT u.* , up.OK_TO_MAIL , up.OK_TO_PUSH , up.OK_TO_SMS , up.TIME_STAMP  FROM user_table u " +
            "JOIN user_preference up ON u.id = up.user_id WHERE up.ok_to_mail = :okToMail", nativeQuery = true)
    List<User> findUsersWithMailPreferenceNative(Boolean okToMail);
}
