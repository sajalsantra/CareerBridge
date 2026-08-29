package com.careerbridge.repository.recruiter;

import com.careerbridge.entity.recruiter.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobCategoryRepository
        extends JpaRepository<JobCategory, Long> {

    Optional<JobCategory> findByNameIgnoreCase(
            String name
    );

    List<JobCategory> findByActiveTrue();

    Optional<JobCategory> findByIdAndActiveTrue(
            Long id
    );

    boolean existsByNameIgnoreCase(
            String name
    );
}