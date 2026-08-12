package com.careerbridge.service;

import com.careerbridge.dto.jobseeker.AddSkillRequest;
import com.careerbridge.dto.jobseeker.SkillResponse;
import com.careerbridge.dto.jobseeker.UpdateSkillRequest;

import java.util.List;

public interface JobSeekerSkillService {

    // Add a new skill to the logged-in Job Seeker
    SkillResponse addSkill(
            String email,
            AddSkillRequest request
    );

    // Get all skills of the logged-in Job Seeker
    List<SkillResponse> getMySkills(
            String email
    );

    // Update an existing skill
    SkillResponse updateSkill(
            String email,
            Long skillId,
            UpdateSkillRequest request
    );

    // Delete an existing skill
    void deleteSkill(
            String email,
            Long skillId
    );
}