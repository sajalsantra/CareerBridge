package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerProfileRepository
        extends JpaRepository<JobSeekerProfile, Long> {

    Optional<JobSeekerProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}