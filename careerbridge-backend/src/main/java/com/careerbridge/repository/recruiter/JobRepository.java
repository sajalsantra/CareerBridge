package com.careerbridge.repository;

import com.careerbridge.entity.recruiter.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface JobRepository
        extends JpaRepository<Job, Long>,
        JpaSpecificationExecutor<Job> {

    List<Job> findByRecruiterProfileId(
            Long recruiterProfileId
    );

    Optional<Job> findByIdAndRecruiterProfileId(
            Long jobId,
            Long recruiterProfileId
    );

    List<Job> findByCompanyId(
            Long companyId
    );

    List<Job> findByStatus(
            String status
    );

    List<Job> findByCategoryId(
            Long categoryId
    );

    List<Job> findByLocationIgnoreCase(
            String location
    );
}