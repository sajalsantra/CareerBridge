package com.careerbridge.controller.recruiter;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.recruiter.profile.RecruiterProfileResponse;
import com.careerbridge.dto.recruiter.profile.UpdateRecruiterProfileRequest;
import com.careerbridge.service.recruiter.RecruiterProfileService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.RECRUITER_BASE)
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;


    public RecruiterProfileController(
            RecruiterProfileService recruiterProfileService) {

        this.recruiterProfileService = recruiterProfileService;
    }

    // GET MY PROFILE
    @GetMapping("/profile")
    public ResponseEntity<RecruiterProfileResponse>
    getMyProfile(
            Authentication authentication) {

        String username = authentication.getName();

        RecruiterProfileResponse response =
                recruiterProfileService.getMyProfile(
                        username
                );

        return ResponseEntity.ok(
                response
        );
    }

    // UPDATE MY PROFILE
    @PutMapping("/profile")
    public ResponseEntity<RecruiterProfileResponse>
    updateMyProfile(

            @Valid
            @RequestBody
            UpdateRecruiterProfileRequest request,

            Authentication authentication) {

        String username = authentication.getName();

        RecruiterProfileResponse response =
                recruiterProfileService.updateMyProfile(
                        username,
                        request
                );

        return ResponseEntity.ok(
                response
        );
    }
}