package com.careerbridge.service;

import com.careerbridge.dto.jobseeker.JobSeekerProfileResponse;
import com.careerbridge.dto.jobseeker.UpdateJobSeekerProfileRequest;

public interface JobSeekerProfileService {

    JobSeekerProfileResponse getMyProfile(String email);

    JobSeekerProfileResponse updateMyProfile(
            String email,
            UpdateJobSeekerProfileRequest request
    );
}