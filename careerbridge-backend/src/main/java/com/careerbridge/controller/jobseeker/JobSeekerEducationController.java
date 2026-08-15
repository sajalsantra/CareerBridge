package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.error.ApiErrorResponse;
import com.careerbridge.dto.jobseeker.education.AddEducationRequest;
import com.careerbridge.dto.jobseeker.education.EducationResponse;
import com.careerbridge.dto.jobseeker.education.UpdateEducationRequest;
import com.careerbridge.service.JobSeekerEducationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-seeker/education")
public class JobSeekerEducationController {

    private final JobSeekerEducationService educationService;

    public JobSeekerEducationController(
            JobSeekerEducationService educationService) {

        this.educationService = educationService;
    }

    // 1. ADD EDUCATION
    @PostMapping
    public ResponseEntity<EducationResponse> addEducation(
            @RequestBody AddEducationRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        EducationResponse response =
                educationService.addEducation(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // 2. GET ALL EDUCATION
    @GetMapping
    public ResponseEntity<List<EducationResponse>> getMyEducation(
            Authentication authentication) {

        String username = authentication.getName();

        List<EducationResponse> response =
                educationService.getMyEducation(
                        username
                );

        return ResponseEntity.ok(response);
    }

    // 3. GET SINGLE EDUCATION
    @GetMapping("/{educationId}")
    public ResponseEntity<EducationResponse> getEducation(
            @PathVariable Long educationId,
            Authentication authentication) {

        String username = authentication.getName();

        EducationResponse response =
                educationService.getEducation(
                        username,
                        educationId
                );

        return ResponseEntity.ok(response);
    }

    // 4. UPDATE EDUCATION
    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable Long educationId,
            @RequestBody UpdateEducationRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        EducationResponse response =
                educationService.updateEducation(
                        username,
                        educationId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    // 5. DELETE EDUCATION
    @DeleteMapping("/{educationId}")
    public ResponseEntity<ApiErrorResponse> deleteEducation(
            @PathVariable Long educationId,
            Authentication authentication) {

        String username = authentication.getName();

        educationService.deleteEducation(
                username,
                educationId
        );

        return ResponseEntity.ok(
                new ApiErrorResponse(
                        AppConstant.EDUCATION_DELETE_SUCCEEDED
                )
        );
    }
}