package com.saha.amit.repository;

import com.saha.amit.entity.UserDetailsPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsPreferenceRepository  {

    List<UserDetailsPreference> getUserFromPreference(Boolean preference);
}
