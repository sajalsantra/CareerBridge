package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.experience.AddExperienceRequest;
import com.careerbridge.dto.jobseeker.experience.ExperienceResponse;
import com.careerbridge.dto.jobseeker.experience.UpdateExperienceRequest;
import com.careerbridge.dto.response.ApiResponse;
import com.careerbridge.service.JobSeekerExperienceService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.JOB_SEEKER_EXPERIENCE_BASE)
public class JobSeekerExperienceController {

    private final JobSeekerExperienceService experienceService;

    public JobSeekerExperienceController(
            JobSeekerExperienceService experienceService) {

        this.experienceService = experienceService;
    }

    // 1. ADD EXPERIENCE
    @PostMapping
    public ResponseEntity<ExperienceResponse> addExperience(
            @RequestBody AddExperienceRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        ExperienceResponse response =
                experienceService.addExperience(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. GET ALL EXPERIENCES
    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getMyExperiences(
            Authentication authentication) {

        String username = authentication.getName();

        List<ExperienceResponse> response =
                experienceService.getMyExperiences(
                        username
                );

        return ResponseEntity.ok(response);
    }

    // 3. GET SINGLE EXPERIENCE
    @GetMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> getExperience(
            @PathVariable Long experienceId,
            Authentication authentication) {

        String username = authentication.getName();

        ExperienceResponse response =
                experienceService.getExperience(
                        username,
                        experienceId
                );

        return ResponseEntity.ok(response);
    }

    // 4. UPDATE EXPERIENCE
    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long experienceId,
            @RequestBody UpdateExperienceRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        ExperienceResponse response =
                experienceService.updateExperience(
                        username,
                        experienceId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    // 5. DELETE EXPERIENCE
    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ApiResponse> deleteExperience(
            @PathVariable Long experienceId,
            Authentication authentication) {

        String username = authentication.getName();

        experienceService.deleteExperience(
                username,
                experienceId
        );

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        AppConstant.EXPERIENCE_DELETED,
                        null
                )
        );
    }
}