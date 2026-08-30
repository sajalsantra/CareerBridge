package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerApplicationRepository
        extends JpaRepository<JobSeekerApplication, Long> {


    boolean existsByJobIdAndJobSeekerProfileId(
            Long jobId,
            Long jobSeekerProfileId
    );


    Optional<JobSeekerApplication>
    findByIdAndJobSeekerProfileId(
            Long applicationId,
            Long jobSeekerProfileId
    );


    Page<JobSeekerApplication>
    findByJobSeekerProfileId(
            Long jobSeekerProfileId,
            Pageable pageable
    );


    Page<JobSeekerApplication>
    findByJobSeekerProfileIdAndStatus(
            Long jobSeekerProfileId,
            String status,
            Pageable pageable
    );


    long countByJobSeekerProfileId(
            Long jobSeekerProfileId
    );
}