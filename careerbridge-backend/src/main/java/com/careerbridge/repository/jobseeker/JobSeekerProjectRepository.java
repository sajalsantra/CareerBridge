package com.careerbridge.repository.jobseeker;

import com.careerbridge.entity.jobseeker.JobSeekerProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSeekerProjectRepository
        extends JpaRepository<JobSeekerProject, Long> {

    List<JobSeekerProject> findByJobSeekerProfileId(
            Long jobSeekerProfileId
    );

    Optional<JobSeekerProject> findByIdAndJobSeekerProfileId(
            Long projectId,
            Long jobSeekerProfileId
    );
}