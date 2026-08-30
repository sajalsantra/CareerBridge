package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.entity.jobseeker.JobSeekerApplication;
import com.careerbridge.dto.jobseeker.applications.ApplicationResponse;
import com.careerbridge.dto.jobseeker.applications.CreateApplicationRequest;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.entity.jobseeker.JobSeekerResume;
import com.careerbridge.entity.recruiter.Job;
import com.careerbridge.exception.DuplicateResourceException;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerApplicationRepository;
import com.careerbridge.repository.JobRepository;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.repository.jobseeker.JobSeekerResumeRepository;
import com.careerbridge.service.jobseeker.JobSeekerApplicationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
public class JobSeekerApplicationServiceImpl
        implements JobSeekerApplicationService {


    private final JobSeekerApplicationRepository applicationRepository;

    private final JobRepository jobRepository;

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    private final JobSeekerResumeRepository resumeRepository;


    public JobSeekerApplicationServiceImpl(
            JobSeekerApplicationRepository applicationRepository,
            JobRepository jobRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            JobSeekerResumeRepository resumeRepository
    ) {

        this.applicationRepository =
                applicationRepository;

        this.jobRepository =
                jobRepository;

        this.jobSeekerProfileRepository =
                jobSeekerProfileRepository;

        this.resumeRepository =
                resumeRepository;
    }

    // APPLY FOR JOB
    @Override
    @Transactional
    public ApplicationResponse applyForJob(
            String username,
            CreateApplicationRequest request
    ) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    AppConstant.USERNAME_REQUIRED
            );
        }


        if (request == null) {
            throw new IllegalArgumentException(
                    AppConstant.APPLICATION_REQUEST_REQUIRED
            );
        }

        if (request.getJobId() == null || request.getJobId() <= 0) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_JOB_ID
            );
        }


        if (request.getResumeId() == null || request.getResumeId() <= 0) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_RESUME_ID
            );
        }

        // 1. Find Job Seeker Profile
        JobSeekerProfile profile =
                jobSeekerProfileRepository
                        .findByUserUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                                )
                        );

        // 2. Find Job
        Job job =
                jobRepository
                        .findById(request.getJobId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOB_NOT_FOUND
                                )
                        );

        // 3. Check Job Status
        if (!"ACTIVE".equalsIgnoreCase(
                job.getStatus())) {

            throw new IllegalArgumentException(
                    AppConstant.JOB_NOT_ACCEPTING
            );
        }

        // 4. Check Application Deadline
        LocalDate deadline =
                job.getApplicationDeadline();

        if (deadline != null
                && deadline.isBefore(
                LocalDate.now()
        )) {

            throw new IllegalArgumentException(
                    AppConstant.APPLICATION_DEADLINE_PASSED
            );
        }

        // 5. Check Duplicate Application
        if (applicationRepository
                .existsByJobIdAndJobSeekerProfileId(
                        job.getId(),
                        profile.getId()
                )) {

            throw new DuplicateResourceException(
                   AppConstant.ALREADY_APPLIED
            );
        }

        // 6. Find Resume
        JobSeekerResume resume =
                resumeRepository
                        .findById(
                                request.getResumeId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.RESUME_NOT_FOUND
                                )
                        );

        // 7. Verify Resume Ownership
        if (resume.getJobSeekerProfile() == null
                || !resume
                .getJobSeekerProfile()
                .getId()
                .equals(profile.getId())) {

            throw new IllegalArgumentException(
                    AppConstant.RESUME_NOT_YOURS
            );
        }

        // 8. Create Application
        JobSeekerApplication application = new JobSeekerApplication();

        application.setJob(job);

        application.setJobSeekerProfile(
                profile
        );

        application.setResume(
                resume
        );

        application.setCoverLetter(
                cleanCoverLetter(
                        request.getCoverLetter()
                )
        );

        application.setStatus(
                AppConstant.APPLIED
        );


        // IMPORTANT:
        // Recruiter remarks must remain null.
        application.setRecruiterRemarks(
                null
        );

        JobSeekerApplication savedApplication =
                applicationRepository.save(
                        application
                );

        return mapToResponse(
                savedApplication
        );
    }

    // GET MY APPLICATIONS
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponse>
    getMyApplications(
            String username,
            int page,
            int size
    ) {

        validatePagination(
                page,
                size
        );

        JobSeekerProfile profile =
                getProfile(username);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                Sort.Direction.DESC,
                                "appliedAt"
                        )
                );

        return applicationRepository
                .findByJobSeekerProfileId(
                        profile.getId(),
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }

    // GET MY APPLICATIONS BY STATUS
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponse>
    getMyApplicationsByStatus(
            String username,
            String status,
            int page,
            int size
    ) {

        validatePagination(
                page,
                size
        );


        if (status == null || status.isBlank()) {

            throw new IllegalArgumentException(
                    AppConstant.APPLICATION_STATUS_REQUIRED
            );
        }

        String normalizedStatus = status.trim().toUpperCase();

        validateStatus(
                normalizedStatus
        );

        JobSeekerProfile profile =
                getProfile(username);


        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                Sort.Direction.DESC,
                                "appliedAt"
                        )
                );


        return applicationRepository
                .findByJobSeekerProfileIdAndStatus(
                        profile.getId(),
                        normalizedStatus,
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }

    // GET MY APPLICATION
    @Override
    @Transactional(readOnly = true)
    public ApplicationResponse
    getMyApplication(
            String username,
            Long applicationId
    ) {

        if (applicationId == null || applicationId <= 0) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_APPLICATION_ID
            );
        }


        JobSeekerProfile profile = getProfile(username);


        JobSeekerApplication application =
                applicationRepository
                        .findByIdAndJobSeekerProfileId(
                                applicationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.APPLICATION_NOT_FOUND
                                )
                        );


        return mapToResponse(
                application
        );
    }

    // WITHDRAW APPLICATION
    @Override
    @Transactional
    public ApplicationResponse
    withdrawApplication(
            String username,
            Long applicationId
    ) {

        if (applicationId == null || applicationId <= 0) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_APPLICATION_ID
            );
        }


        JobSeekerProfile profile = getProfile(username);

        JobSeekerApplication application =
                applicationRepository
                        .findByIdAndJobSeekerProfileId(
                                applicationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.APPLICATION_NOT_FOUND
                                )
                        );

        String currentStatus = application.getStatus();

        if (AppConstant.NON_WITHDRAWABLE_STATUSES
                .contains(
                        currentStatus
                )) {

            throw new IllegalArgumentException(
                    AppConstant.APPLICATION_CANNOT_WITHDRAW
            );
        }


        if (AppConstant.WITHDRAWN.equalsIgnoreCase(
                currentStatus
        )) {

            throw new IllegalArgumentException(
                    AppConstant.APPLICATION_ALREADY_WITHDRAWN
            );
        }

        application.setStatus(
                AppConstant.WITHDRAWN
        );

        return mapToResponse(
                applicationRepository.save(
                        application
                )
        );
    }

    // FIND PROFILE
    private JobSeekerProfile getProfile(
            String username
    ) {

        if (username == null || username.isBlank()) {

            throw new IllegalArgumentException(
                    AppConstant.USERNAME_REQUIRED
            );
        }


        return jobSeekerProfileRepository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                        )
                );
    }

    // VALIDATE STATUS
    private void validateStatus(
            String status
    ) {

        Set<String> validStatuses =
                Set.of(
                        "APPLIED",
                        "SHORTLISTED",
                        "INTERVIEW",
                        "SELECTED",
                        "REJECTED",
                        "WITHDRAWN"
                );


        if (!validStatuses.contains(
                status
        )) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_APPLICATION_STATUS
            );
        }
    }

    // VALIDATE PAGINATION
    private void validatePagination(
            int page,
            int size
    ) {
        if (page < 0) {
            throw new IllegalArgumentException(
                    AppConstant.INVALID_PAGE
            );
        }


        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    AppConstant.INVALID_PAGE_SIZE
            );
        }
    }

    // CLEAN COVER LETTER
    private String cleanCoverLetter(
            String coverLetter
    ) {

        if (coverLetter == null) {
            return null;
        }


        String cleaned =
                coverLetter.trim();


        return cleaned.isEmpty()
                ? null
                : cleaned;
    }

    // MAP RESPONSE
    private ApplicationResponse
    mapToResponse(
            JobSeekerApplication application
    ) {

        ApplicationResponse response =
                new ApplicationResponse();


        response.setId(
                application.getId()
        );


        Job job =
                application.getJob();


        if (job != null) {

            response.setJobId(
                    job.getId()
            );

            response.setJobTitle(
                    job.getJobTitle()
            );


            if (job.getCompany() != null) {

                response.setCompanyName(
                        job.getCompany()
                                .getCompanyName()
                );
            }


            response.setApplicationDeadline(
                    job.getApplicationDeadline()
            );
        }


        if (application
                .getJobSeekerProfile() != null) {

            response.setJobSeekerProfileId(
                    application
                            .getJobSeekerProfile()
                            .getId()
            );
        }


        if (application.getResume() != null) {

            response.setResumeId(
                    application
                            .getResume()
                            .getId()
            );

            response.setResumeName(
                    application
                            .getResume()
                            .getResumeName()
            );
        }


        response.setCoverLetter(
                application.getCoverLetter()
        );

        response.setStatus(
                application.getStatus()
        );

        response.setRecruiterRemarks(
                application.getRecruiterRemarks()
        );

        response.setAppliedAt(
                application.getAppliedAt()
        );

        response.setUpdatedAt(
                application.getUpdatedAt()
        );


        return response;
    }
}