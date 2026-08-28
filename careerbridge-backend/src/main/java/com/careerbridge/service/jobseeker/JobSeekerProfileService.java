package com.careerbridge.service.jobseeker;

import com.careerbridge.dto.jobseeker.profile.JobSeekerProfileResponse;
import com.careerbridge.dto.jobseeker.profile.UpdateJobSeekerProfileRequest;

public interface JobSeekerProfileService {

    JobSeekerProfileResponse getMyProfile(String username);

    JobSeekerProfileResponse updateMyProfile(
            String username,
            UpdateJobSeekerProfileRequest request
    );
}