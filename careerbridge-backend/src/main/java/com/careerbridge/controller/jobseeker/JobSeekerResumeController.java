package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.response.ApiResponse;
import com.careerbridge.dto.resume.ResumeResponse;
import com.careerbridge.dto.resume.UpdateResumeRequest;
import com.careerbridge.dto.resume.UploadResumeRequest;
import com.careerbridge.service.jobseeker.JobSeekerResumeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        AppConstant.JOB_SEEKER_RESUME_BASE
)
public class JobSeekerResumeController {

    private final JobSeekerResumeService resumeService;


    public JobSeekerResumeController(
            JobSeekerResumeService resumeService) {

        this.resumeService =
                resumeService;
    }

    // 1. UPLOAD
    @PostMapping
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestBody UploadResumeRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        ResumeResponse response =
                resumeService.uploadResume(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. GET ALL
    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getMyResumes(
            Authentication authentication) {

        String username = authentication.getName();

        List<ResumeResponse> response =
                resumeService.getMyResumes(
                        username
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 3. GET BY ID
    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeResponse> getResume(
            @PathVariable Long resumeId,
            Authentication authentication) {

        String username = authentication.getName();

        ResumeResponse response =
                resumeService.getResume(
                        username,
                        resumeId
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 4. UPDATE
    @PutMapping("/{resumeId}")
    public ResponseEntity<ResumeResponse> updateResume(
            @PathVariable Long resumeId,
            @RequestBody UpdateResumeRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        ResumeResponse response =
                resumeService.updateResume(
                        username,
                        resumeId,
                        request
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 5. DELETE
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<ApiResponse<Void>> deleteResume(
            @PathVariable Long resumeId,
            Authentication authentication) {

        String username = authentication.getName();

        resumeService.deleteResume(username, resumeId);

        return ResponseEntity.ok(
                ApiResponse.success(AppConstant.RESUME_DELETE_SUCCEEDED)
        );
    }

}