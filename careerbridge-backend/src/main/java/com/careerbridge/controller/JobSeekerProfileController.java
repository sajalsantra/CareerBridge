package com.careerbridge.controller;

import com.careerbridge.dto.jobseeker.JobSeekerProfileResponse;
import com.careerbridge.dto.jobseeker.UpdateJobSeekerProfileRequest;
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
                        "Profile fetched successfully",
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
                        "Profile updated successfully",
                        response
                )
        );
    }
}