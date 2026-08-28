package com.careerbridge.service.recruiter;

import com.careerbridge.dto.recruiter.profile.RecruiterProfileResponse;
import com.careerbridge.dto.recruiter.profile.UpdateRecruiterProfileRequest;

public interface RecruiterProfileService {

    RecruiterProfileResponse getMyProfile(
            String username
    );

    RecruiterProfileResponse updateMyProfile(
            String username,
            UpdateRecruiterProfileRequest request
    );
}