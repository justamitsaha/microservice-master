package com.saha.amit.repository;

import com.saha.amit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {

    public List<User> findByPhoneNumberContaining(String phoneNumber);

    public List<User> findByEmailContaining(String email);
}
