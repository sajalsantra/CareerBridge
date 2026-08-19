package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSeekerExperienceRepository
        extends JpaRepository<JobSeekerExperience, Long> {

    List<JobSeekerExperience> findByJobSeekerProfileId(
            Long profileId
    );

    Optional<JobSeekerExperience> findByIdAndJobSeekerProfileId(
            Long id,
            Long profileId
    );
}