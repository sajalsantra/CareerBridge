package com.careerbridge.service.jobseeker;


import com.careerbridge.dto.jobseeker.experience.AddExperienceRequest;
import com.careerbridge.dto.jobseeker.experience.ExperienceResponse;
import com.careerbridge.dto.jobseeker.experience.UpdateExperienceRequest;

import java.util.List;

public interface JobSeekerExperienceService {

    ExperienceResponse addExperience(
            String username,
            AddExperienceRequest request
    );

    List<ExperienceResponse> getMyExperiences(
            String username
    );

    ExperienceResponse getExperience(
            String username,
            Long experienceId
    );

    ExperienceResponse updateExperience(
            String username,
            Long experienceId,
            UpdateExperienceRequest request
    );

    void deleteExperience(
            String username,
            Long experienceId
    );
}