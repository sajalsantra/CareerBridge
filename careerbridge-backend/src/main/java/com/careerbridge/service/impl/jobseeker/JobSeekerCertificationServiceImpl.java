package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.certification.AddCertificationRequest;
import com.careerbridge.dto.jobseeker.certification.CertificationResponse;
import com.careerbridge.dto.jobseeker.certification.UpdateCertificationRequest;
import com.careerbridge.entity.jobseeker.JobSeekerCertification;
import com.careerbridge.entity.User;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerCertificationRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.service.jobseeker.JobSeekerCertificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobSeekerCertificationServiceImpl
        implements JobSeekerCertificationService {

    private final UserRepository userRepository;

    private final JobSeekerProfileRepository profileRepository;

    private final JobSeekerCertificationRepository certificationRepository;


    public JobSeekerCertificationServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository,
            JobSeekerCertificationRepository certificationRepository) {

        this.userRepository = userRepository;

        this.profileRepository =
                profileRepository;

        this.certificationRepository =
                certificationRepository;
    }

    // 1. ADD CERTIFICATION
    @Override
    @Transactional
    public CertificationResponse addCertification(
            String username,
            AddCertificationRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        validateDates(
                request.getIssueDate(),
                request.getExpiryDate()
        );

        validateCredentialUrl(
                request.getCredentialUrl()
        );


        JobSeekerCertification certification = new JobSeekerCertification();

        certification.setJobSeekerProfile(
                profile
        );

        certification.setCertificationName(
                request.getCertificationName().trim()
        );

        certification.setIssuingOrganization(
                trimValue(
                        request.getIssuingOrganization()
                )
        );

        certification.setIssueDate(
                request.getIssueDate()
        );

        certification.setExpiryDate(
                request.getExpiryDate()
        );

        certification.setCredentialId(
                trimValue(
                        request.getCredentialId()
                )
        );

        certification.setCredentialUrl(
                trimValue(
                        request.getCredentialUrl()
                )
        );


        JobSeekerCertification saved =
                certificationRepository.save(
                        certification
                );

        return mapToResponse(
                saved
        );
    }

    // 2. GET ALL CERTIFICATIONS
    @Override
    @Transactional(readOnly = true)
    public List<CertificationResponse> getMyCertifications(
            String username) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        return certificationRepository
                .findByJobSeekerProfileId(
                        profile.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. GET CERTIFICATION BY ID
    @Override
    @Transactional(readOnly = true)
    public CertificationResponse getCertification(
            String username,
            Long certificationId) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerCertification certification =
                certificationRepository
                        .findByIdAndJobSeekerProfileId(
                                certificationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.CERTIFICATE_NOT_FOUND
                                )
                        );

        return mapToResponse(
                certification
        );
    }

    // 4. UPDATE CERTIFICATION
    @Override
    @Transactional
    public CertificationResponse updateCertification(
            String username,
            Long certificationId,
            UpdateCertificationRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerCertification certification =
                certificationRepository
                        .findByIdAndJobSeekerProfileId(
                                certificationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.CERTIFICATE_NOT_FOUND
                                )
                        );


        /*
         * Validate the resulting dates.
         *
         * If only one date is supplied, use the existing
         * value for the other date.
         */

        LocalDate issueDate =
                request.getIssueDate() != null
                        ? request.getIssueDate()
                        : certification.getIssueDate();

        LocalDate expiryDate =
                request.getExpiryDate() != null
                        ? request.getExpiryDate()
                        : certification.getExpiryDate();


        validateDates(
                issueDate,
                expiryDate
        );

        validateCredentialUrl(
                request.getCredentialUrl()
        );


        // Certification name
        if (request.getCertificationName() != null
                && !request.getCertificationName().isBlank()) {

            certification.setCertificationName(
                    request.getCertificationName().trim()
            );
        }


        // Issuing organization
        if (request.getIssuingOrganization() != null) {

            certification.setIssuingOrganization(
                    trimValue(
                            request.getIssuingOrganization()
                    )
            );
        }


        // Issue date
        if (request.getIssueDate() != null) {

            certification.setIssueDate(
                    request.getIssueDate()
            );
        }


        // Expiry date
        if (request.getExpiryDate() != null) {

            certification.setExpiryDate(
                    request.getExpiryDate()
            );
        }


        // Credential ID
        if (request.getCredentialId() != null) {

            certification.setCredentialId(
                    trimValue(
                            request.getCredentialId()
                    )
            );
        }


        // Credential URL
        if (request.getCredentialUrl() != null) {

            certification.setCredentialUrl(
                    trimValue(
                            request.getCredentialUrl()
                    )
            );
        }


        JobSeekerCertification updated =
                certificationRepository.save(
                        certification
                );

        return mapToResponse(
                updated
        );
    }

    // 5. DELETE CERTIFICATION
    @Override
    @Transactional
    public void deleteCertification(
            String username,
            Long certificationId) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerCertification certification =
                certificationRepository
                        .findByIdAndJobSeekerProfileId(
                                certificationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.CERTIFICATE_NOT_FOUND
                                )
                        );

        certificationRepository.delete(
                certification
        );
    }

    // GET USER
    private User getUser(
            String username) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );
    }

    // GET JOB SEEKER PROFILE
    private JobSeekerProfile getProfile(
            Long userId) {

        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                        )
                );
    }

    // DATE VALIDATION
    private void validateDates(
            LocalDate issueDate,
            LocalDate expiryDate) {

        if (issueDate != null
                && expiryDate != null
                && expiryDate.isBefore(issueDate)) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_EXPIRY_DATE
            );
        }
    }

    // URL VALIDATION
    private void validateCredentialUrl(
            String credentialUrl) {

        if (credentialUrl == null
                || credentialUrl.isBlank()) {

            return;
        }

        String url =
                credentialUrl.trim();

        if (!url.startsWith("http://")
                && !url.startsWith("https://")) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_CERTIFICATE_URL
            );
        }
    }

    // TRIM VALUE
    private String trimValue(
            String value) {

        if (value == null) {
            return null;
        }

        String trimmed =
                value.trim();

        return trimmed.isEmpty()
                ? null
                : trimmed;
    }

    // ENTITY → RESPONSE
    private CertificationResponse mapToResponse(
            JobSeekerCertification certification) {

        CertificationResponse response =
                new CertificationResponse();

        response.setId(
                certification.getId()
        );

        response.setCertificationName(
                certification.getCertificationName()
        );

        response.setIssuingOrganization(
                certification.getIssuingOrganization()
        );

        response.setIssueDate(
                certification.getIssueDate()
        );

        response.setExpiryDate(
                certification.getExpiryDate()
        );

        response.setCredentialId(
                certification.getCredentialId()
        );

        response.setCredentialUrl(
                certification.getCredentialUrl()
        );

        response.setCreatedAt(
                certification.getCreatedAt()
        );

        response.setUpdatedAt(
                certification.getUpdatedAt()
        );

        return response;
    }
}