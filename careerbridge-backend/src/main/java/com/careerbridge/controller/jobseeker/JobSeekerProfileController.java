package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.profile.JobSeekerProfileResponse;
import com.careerbridge.dto.jobseeker.profile.UpdateJobSeekerProfileRequest;
import com.careerbridge.dto.response.ApiResponse;
import com.careerbridge.service.JobSeekerProfileService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-seeker")
public class JobSeekerProfileController {

    private final JobSeekerProfileService profileService;

    public JobSeekerProfileController(
            JobSeekerProfileService profileService) {

        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<
            ApiResponse<JobSeekerProfileResponse>
            > getMyProfile(
            Authentication authentication) {

        String username = authentication.getName();

        JobSeekerProfileResponse response =
                profileService.getMyProfile(username);

        return ResponseEntity.ok(
                ApiResponse.success(
                        AppConstant.PROFILE_FETCHED_SUCCEEDED,
                        response
                )
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<
            ApiResponse<JobSeekerProfileResponse>
            > updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody
            UpdateJobSeekerProfileRequest request) {

        String username = authentication.getName();

        JobSeekerProfileResponse response =
                profileService.updateMyProfile(
                        username,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        AppConstant.PROFILE_UPDATED_SUCCEEDED,
                        response
                )
        );
    }
}