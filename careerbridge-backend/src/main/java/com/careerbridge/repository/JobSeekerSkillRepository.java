package com.careerbridge.repository;

import com.careerbridge.entity.JobSeekerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobSeekerSkillRepository
        extends JpaRepository<JobSeekerSkill, Long> {

    // Get all skills of a particular Job Seeker Profile
    List<JobSeekerSkill> findByJobSeekerProfileId(Long jobSeekerProfileId);

    // Find a particular skill belonging to a particular profile
    Optional<JobSeekerSkill> findByIdAndJobSeekerProfileId(
            Long id,
            Long jobSeekerProfileId
    );

    // Check whether the profile already has this skill
    boolean existsByJobSeekerProfileIdAndSkillId(
            Long jobSeekerProfileId,
            Long skillId
    );

    // Find a specific skill for a specific profile
    Optional<JobSeekerSkill> findByJobSeekerProfileIdAndSkillId(
            Long jobSeekerProfileId,
            Long skillId
    );

    // Delete a skill from a particular profile
    void deleteByIdAndJobSeekerProfileId(
            Long id,
            Long jobSeekerProfileId
    );
}