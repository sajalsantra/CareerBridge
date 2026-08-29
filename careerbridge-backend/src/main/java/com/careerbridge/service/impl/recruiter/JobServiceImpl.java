package com.careerbridge.service.impl.recruiter;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.recruiter.job.CreateJobRequest;
import com.careerbridge.dto.recruiter.job.JobResponse;
import com.careerbridge.dto.recruiter.job.UpdateJobRequest;
import com.careerbridge.entity.recruiter.Job;
import com.careerbridge.entity.recruiter.JobCategory;
import com.careerbridge.entity.User;
import com.careerbridge.entity.company.Company;
import com.careerbridge.entity.recruiter.RecruiterProfile;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.JobRepository;
import com.careerbridge.repository.recruiter.JobCategoryRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.company.CompanyRepository;
import com.careerbridge.repository.recruiter.RecruiterProfileRepository;
import com.careerbridge.service.recruiter.JobService;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl
        implements JobService {

    private final JobRepository jobRepository;

    private final UserRepository userRepository;

    private final RecruiterProfileRepository recruiterProfileRepository;

    private final CompanyRepository companyRepository;

    private final JobCategoryRepository jobCategoryRepository;

    public JobServiceImpl(
            JobRepository jobRepository,
            UserRepository userRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            CompanyRepository companyRepository,
            JobCategoryRepository jobCategoryRepository
    ) {

        this.jobRepository = jobRepository;

        this.userRepository = userRepository;

        this.recruiterProfileRepository = recruiterProfileRepository;

        this.companyRepository = companyRepository;

        this.jobCategoryRepository = jobCategoryRepository;
    }

    // CREATE
    @Override
    @Transactional
    public JobResponse createJob(
            String username,
            CreateJobRequest request
    ) {

        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );

        // Recruiter must have a company
        if (recruiterProfile.getCompany() == null) {

            throw new ResourceNotFoundException(
                    AppConstant.COMPANY_REQUIRED_FOR_JOB_CREATION_MESSAGE
            );
        }

        Company company = recruiterProfile.getCompany();

        // Validate experience range
        validateExperienceRange(
                request.getExperienceMin(),
                request.getExperienceMax()
        );

        // Validate salary range
        validateSalaryRange(
                request.getSalaryMin(),
                request.getSalaryMax()
        );

        // Create Job
        Job job = new Job();

        job.setRecruiterProfile(
                recruiterProfile
        );

        job.setCompany(
                company
        );

        job.setJobTitle(
                request.getJobTitle()
        );

        job.setDescription(
                request.getDescription()
        );

        job.setResponsibilities(
                request.getResponsibilities()
        );

        job.setQualifications(
                request.getQualifications()
        );

        job.setLocation(
                request.getLocation()
        );

        job.setJobType(
                request.getJobType()
        );

        job.setWorkMode(
                request.getWorkMode()
        );

        job.setExperienceMin(
                request.getExperienceMin()
        );

        job.setExperienceMax(
                request.getExperienceMax()
        );

        job.setSalaryMin(
                request.getSalaryMin()
        );

        job.setSalaryMax(
                request.getSalaryMax()
        );

        job.setNumberOfOpenings(
                request.getNumberOfOpenings()
        );

        job.setApplicationDeadline(
                request.getApplicationDeadline()
        );

        job.setStatus(
                "ACTIVE"
        );

        // Category
        if (request.getCategoryId() != null) {
            JobCategory category =
                    jobCategoryRepository
                            .findByIdAndActiveTrue(
                                    request.getCategoryId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            AppConstant.JOB_CATEGORY_NOT_FOUND_OR_INACTIVE_MESSAGE
                                    )
                            );
            job.setCategory(category);
        }


        Job savedJob = jobRepository.save(job);

        return mapToResponse(
                savedJob
        );
    }

    // GET MY JOB
    @Override
    @Transactional
    public JobResponse getMyJob(
            String username,
            Long jobId
    ) {

        User user = getUser(username);


        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );


        Job job =
                jobRepository
                        .findByIdAndRecruiterProfileId(
                                jobId,
                                recruiterProfile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOB_NOT_FOUND_OR_ACCESS_DENIED_MESSAGE
                                )
                        );
        return mapToResponse(job);
    }

    // GET MY JOBS
    @Override
    @Transactional
    public List<JobResponse> getMyJobs(
            String username
    ) {
        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );

        return jobRepository
                .findByRecruiterProfileId(
                        recruiterProfile.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    @Transactional
    public JobResponse updateJob(
            String username,
            Long jobId,
            UpdateJobRequest request
    ) {

        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );

        Job job =
                jobRepository
                        .findByIdAndRecruiterProfileId(
                                jobId,
                                recruiterProfile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                       AppConstant.JOB_NOT_FOUND_OR_ACCESS_DENIED_MESSAGE
                                )
                        );

        // Validate experience
        validateExperienceRange(
                request.getExperienceMin(),
                request.getExperienceMax()
        );

        // Validate salary
        validateSalaryRange(
                request.getSalaryMin(),
                request.getSalaryMax()
        );

        if (request.getJobTitle() != null) {
            job.setJobTitle(
                    request.getJobTitle()
            );
        }

        if (request.getDescription() != null) {
            job.setDescription(
                    request.getDescription()
            );
        }

        if (request.getResponsibilities() != null) {
            job.setResponsibilities(
                    request.getResponsibilities()
            );
        }

        if (request.getQualifications() != null) {
            job.setQualifications(
                    request.getQualifications()
            );
        }

        if (request.getLocation() != null) {
            job.setLocation(
                    request.getLocation()
            );
        }

        if (request.getJobType() != null) {
            job.setJobType(
                    request.getJobType()
            );
        }

        if (request.getWorkMode() != null) {
            job.setWorkMode(
                    request.getWorkMode()
            );
        }

        if (request.getExperienceMin() != null) {
            job.setExperienceMin(
                    request.getExperienceMin()
            );
        }

        if (request.getExperienceMax() != null) {
            job.setExperienceMax(
                    request.getExperienceMax()
            );
        }

        if (request.getSalaryMin() != null) {
            job.setSalaryMin(
                    request.getSalaryMin()
            );
        }

        if (request.getSalaryMax() != null) {
            job.setSalaryMax(
                    request.getSalaryMax()
            );
        }

        if (request.getNumberOfOpenings() != null) {
            job.setNumberOfOpenings(
                    request.getNumberOfOpenings()
            );
        }

        if (request.getApplicationDeadline() != null) {
            job.setApplicationDeadline(
                    request.getApplicationDeadline()
            );
        }

        if (request.getStatus() != null) {
            validateStatus(
                    request.getStatus()
            );

            job.setStatus(
                    request.getStatus()
            );
        }

        // Update category
        if (request.getCategoryId() != null) {

            JobCategory category =
                    jobCategoryRepository
                            .findByIdAndActiveTrue(
                                    request.getCategoryId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            AppConstant.JOB_CATEGORY_NOT_FOUND_OR_INACTIVE_MESSAGE
                                    )
                            );
            job.setCategory(category);
        }


        Job updatedJob = jobRepository.save(job);

        return mapToResponse(
                updatedJob
        );
    }

    // DELETE
    @Override
    @Transactional
    public void deleteJob(
            String username,
            Long jobId
    ) {

        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );

        Job job =
                jobRepository
                        .findByIdAndRecruiterProfileId(
                                jobId,
                                recruiterProfile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                       AppConstant.JOB_NOT_FOUND_OR_ACCESS_DENIED_MESSAGE
                                )
                        );

        jobRepository.delete(job);
    }

    // GET USER
    private User getUser(
            String username
    ) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                               AppConstant.USER_NOT_FOUND
                        )
                );
    }

    // GET RECRUITER PROFILE
    private RecruiterProfile getRecruiterProfile(
            Long userId
    ) {
        return recruiterProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.RECRUITER_PROFILE_NOT_FOUND
                        )
                );
    }

    // VALIDATE EXPERIENCE
    private void validateExperienceRange(
            java.math.BigDecimal min,
            java.math.BigDecimal max
    ) {
        if (min != null
                && max != null
                && min.compareTo(max) > 0) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_EXPERIENCE_RANGE
            );
        }
    }

    // VALIDATE SALARY
    private void validateSalaryRange(
            java.math.BigDecimal min,
            java.math.BigDecimal max
    ) {
        if (min != null
                && max != null
                && min.compareTo(max) > 0) {

            throw new IllegalArgumentException(
                   AppConstant.INVALID_SALARY_RANGE
            );
        }
    }

    // VALIDATE STATUS
    private void validateStatus(
            String status
    ) {
        if (!"ACTIVE".equalsIgnoreCase(status)
                && !"CLOSED".equalsIgnoreCase(status)
                && !"EXPIRED".equalsIgnoreCase(status)
                && !"DRAFT".equalsIgnoreCase(status)) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_JOB_STATUS
            );
        }
    }

    // MAP RESPONSE
    private JobResponse mapToResponse(
            Job job
    ) {
        JobResponse response =
                new JobResponse();
        response.setId(
                job.getId()
        );

        // Recruiter
        if (job.getRecruiterProfile() != null) {

            response.setRecruiterProfileId(
                    job.getRecruiterProfile().getId()
            );

            if (job.getRecruiterProfile().getUser() != null) {

                response.setRecruiterName(
                        job.getRecruiterProfile()
                                .getUser()
                                .getFullName()
                );
            }
        }

        // Company
        if (job.getCompany() != null) {

            response.setCompanyId(
                    job.getCompany().getId()
            );

            response.setCompanyName(
                    job.getCompany().getCompanyName()
            );

            response.setCompanyLogoUrl(
                    job.getCompany().getLogoUrl()
            );
        }

        // Job
        response.setJobTitle(
                job.getJobTitle()
        );

        response.setDescription(
                job.getDescription()
        );

        response.setResponsibilities(
                job.getResponsibilities()
        );

        response.setQualifications(
                job.getQualifications()
        );

        response.setLocation(
                job.getLocation()
        );

        response.setJobType(
                job.getJobType()
        );

        response.setWorkMode(
                job.getWorkMode()
        );

        response.setExperienceMin(
                job.getExperienceMin()
        );

        response.setExperienceMax(
                job.getExperienceMax()
        );

        response.setSalaryMin(
                job.getSalaryMin()
        );

        response.setSalaryMax(
                job.getSalaryMax()
        );

        response.setNumberOfOpenings(
                job.getNumberOfOpenings()
        );

        // Category
        if (job.getCategory() != null) {

            response.setCategoryId(
                    job.getCategory().getId()
            );

            response.setCategoryName(
                    job.getCategory().getName()
            );
        }

        response.setPostedDate(
                job.getPostedDate()
        );

        response.setApplicationDeadline(
                job.getApplicationDeadline()
        );

        response.setStatus(
                job.getStatus()
        );

        response.setCreatedAt(
                job.getCreatedAt()
        );

        response.setUpdatedAt(
                job.getUpdatedAt()
        );

        return response;
    }
}