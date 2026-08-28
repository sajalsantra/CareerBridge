package com.careerbridge.service.jobseeker;

import com.careerbridge.dto.jobseeker.skills.AddSkillRequest;
import com.careerbridge.dto.jobseeker.skills.SkillResponse;
import com.careerbridge.dto.jobseeker.skills.UpdateSkillRequest;

import java.util.List;

public interface JobSeekerSkillService {

    // Add a new skill to the logged-in Job Seeker
    SkillResponse addSkill(
            String username,
            AddSkillRequest request
    );

    // Get all skills of the logged-in Job Seeker
    List<SkillResponse> getMySkills(
            String username
    );

    // Update an existing skill
    SkillResponse updateSkill(
            String username,
            Long skillId,
            UpdateSkillRequest request
    );

    // Delete an existing skill
    void deleteSkill(
            String username,
            Long skillId
    );
}