package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.certification.AddCertificationRequest;
import com.careerbridge.dto.jobseeker.certification.CertificationResponse;
import com.careerbridge.dto.jobseeker.certification.UpdateCertificationRequest;
import com.careerbridge.dto.response.ApiResponse;
import com.careerbridge.service.JobSeekerCertificationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.JOB_SEEKER_CERTIFICATION_BASE)
public class JobSeekerCertificationController {

    private final JobSeekerCertificationService certificationService;


    public JobSeekerCertificationController(
            JobSeekerCertificationService certificationService) {

        this.certificationService =
                certificationService;
    }

    // 1. ADD CERTIFICATION
    @PostMapping
    public ResponseEntity<CertificationResponse> addCertification(
            @Valid
            @RequestBody
            AddCertificationRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        CertificationResponse response =
                certificationService.addCertification(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. GET ALL CERTIFICATIONS
    @GetMapping
    public ResponseEntity<List<CertificationResponse>>
    getMyCertifications(
            Authentication authentication) {

        String username = authentication.getName();

        List<CertificationResponse> response =
                certificationService.getMyCertifications(
                        username
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 3. GET CERTIFICATION BY ID
    @GetMapping("/{certificationId}")
    public ResponseEntity<CertificationResponse> getCertification(
            @PathVariable Long certificationId,
            Authentication authentication) {

        String username = authentication.getName();

        CertificationResponse response =
                certificationService.getCertification(
                        username,
                        certificationId
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 4. UPDATE CERTIFICATION
    @PutMapping("/{certificationId}")
    public ResponseEntity<CertificationResponse> updateCertification(
            @PathVariable Long certificationId,

            @Valid
            @RequestBody
            UpdateCertificationRequest request,

            Authentication authentication) {

        String username = authentication.getName();

        CertificationResponse response =
                certificationService.updateCertification(
                        username,
                        certificationId,
                        request
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 5. DELETE CERTIFICATION
    @DeleteMapping("/{certificationId}")
    public ResponseEntity<ApiResponse> deleteCertification(
            @PathVariable Long certificationId,
            Authentication authentication) {

        String username = authentication.getName();

        certificationService.deleteCertification(
                username,
                certificationId
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        AppConstant.CERTIFICATE_DELETED
                )
        );
    }
}