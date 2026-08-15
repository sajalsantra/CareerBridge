package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSeekerEducationRepository
        extends JpaRepository<JobSeekerEducation, Long> {

    List<JobSeekerEducation> findByJobSeekerProfileId(Long profileId);

    Optional<JobSeekerEducation> findByIdAndJobSeekerProfileId(Long id, Long profileId);
}