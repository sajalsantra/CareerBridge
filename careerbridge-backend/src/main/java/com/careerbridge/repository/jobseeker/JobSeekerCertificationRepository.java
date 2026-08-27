package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSeekerCertificationRepository
        extends JpaRepository<JobSeekerCertification, Long> {

    List<JobSeekerCertification> findByJobSeekerProfileId(
            Long jobSeekerProfileId
    );

    Optional<JobSeekerCertification> findByIdAndJobSeekerProfileId(
            Long certificationId,
            Long jobSeekerProfileId
    );
}