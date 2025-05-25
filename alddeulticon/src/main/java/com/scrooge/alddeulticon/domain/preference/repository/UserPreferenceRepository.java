package com.scrooge.alddeulticon.domain.preference.repository;

import com.scrooge.alddeulticon.domain.preference.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    Optional<UserPreference> findByUserId(Long userId);
    Optional<UserPreference> findByUser_Id(Long userId);
}