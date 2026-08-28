package com.careerbridge.service.jobseeker;

import com.careerbridge.dto.jobseeker.education.AddEducationRequest;
import com.careerbridge.dto.jobseeker.education.EducationResponse;
import com.careerbridge.dto.jobseeker.education.UpdateEducationRequest;

import java.util.List;

public interface JobSeekerEducationService {

    EducationResponse addEducation(
            String username,
            AddEducationRequest request
    );

    List<EducationResponse> getMyEducation(
            String username
    );

    EducationResponse getEducation(
            String username,
            Long educationId
    );

    EducationResponse updateEducation(
            String username,
            Long educationId,
            UpdateEducationRequest request
    );

    void deleteEducation(
            String username,
            Long educationId
    );
}