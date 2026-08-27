package com.careerbridge.service;

import com.careerbridge.dto.jobseeker.certification.AddCertificationRequest;
import com.careerbridge.dto.jobseeker.certification.CertificationResponse;
import com.careerbridge.dto.jobseeker.certification.UpdateCertificationRequest;

import java.util.List;

public interface JobSeekerCertificationService {

    CertificationResponse addCertification(
            String username,
            AddCertificationRequest request
    );

    List<CertificationResponse> getMyCertifications(
            String username
    );

    CertificationResponse getCertification(
            String username,
            Long certificationId
    );

    CertificationResponse updateCertification(
            String username,
            Long certificationId,
            UpdateCertificationRequest request
    );

    void deleteCertification(
            String username,
            Long certificationId
    );
}