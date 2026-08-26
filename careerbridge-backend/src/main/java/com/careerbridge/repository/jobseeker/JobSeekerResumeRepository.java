package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSeekerResumeRepository
        extends JpaRepository<JobSeekerResume, Long> {

    List<JobSeekerResume> findByJobSeekerProfileId(
            Long jobSeekerProfileId
    );

    Optional<JobSeekerResume> findByIdAndJobSeekerProfileId(
            Long resumeId,
            Long jobSeekerProfileId
    );
}