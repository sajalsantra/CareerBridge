package com.careerbridge.repository.recruiter;

import com.careerbridge.entity.recruiter.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterProfileRepository
        extends JpaRepository<RecruiterProfile, Long> {

    Optional<RecruiterProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}