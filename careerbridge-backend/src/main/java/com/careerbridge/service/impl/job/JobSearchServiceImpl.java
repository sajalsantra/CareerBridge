package com.careerbridge.service.impl.job;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.job.JobSearchRequest;
import com.careerbridge.dto.job.JobSearchResponse;
import com.careerbridge.dto.recruiter.job.JobResponse;
import com.careerbridge.entity.recruiter.Job;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.JobRepository;
import com.careerbridge.service.job.JobSearchService;
import com.careerbridge.specification.JobSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobSearchServiceImpl
        implements JobSearchService {

    private final JobRepository jobRepository;


    public JobSearchServiceImpl(
            JobRepository jobRepository
    ) {

        this.jobRepository = jobRepository;
    }

    // SEARCH JOBS
    @Override
    @Transactional(readOnly = true)
    public Page<JobSearchResponse> searchJobs(
            JobSearchRequest request
    ) {

        validateSearchRequest(request);

        Pageable pageable =
                PageRequest.of(
                        request.getPage(),
                        request.getSize(),
                        Sort.by(
                                Sort.Direction.DESC,
                                "postedDate"
                        )
                );

        Specification<Job> specification = JobSpecification.activeJobs();

        if (request.getKeyword() != null
                && !request.getKeyword().isBlank()) {
            specification =
                    specification.and(
                            JobSpecification.keyword(
                                    request.getKeyword()
                            )
                    );
        }

        if (request.getLocation() != null
                && !request.getLocation().isBlank()) {
            specification =
                    specification.and(
                            JobSpecification.location(
                                    request.getLocation()
                            )
                    );
        }

        if (request.getCategoryId() != null) {
            specification =
                    specification.and(
                            JobSpecification.category(
                                    request.getCategoryId()
                            )
                    );
        }

        if (request.getJobType() != null
                && !request.getJobType().isBlank()) {

            specification =
                    specification.and(
                            JobSpecification.jobType(
                                    request.getJobType()
                            )
                    );
        }

        if (request.getWorkMode() != null
                && !request.getWorkMode().isBlank()) {

            specification =
                    specification.and(
                            JobSpecification.workMode(
                                    request.getWorkMode()
                            )
                    );
        }

        if (request.getMinExperience() != null
                || request.getMaxExperience() != null) {

            specification =
                    specification.and(
                            JobSpecification.experience(
                                    request.getMinExperience(),
                                    request.getMaxExperience()
                            )
                    );
        }

        if (request.getMinSalary() != null
                || request.getMaxSalary() != null) {

            specification =
                    specification.and(
                            JobSpecification.salary(
                                    request.getMinSalary(),
                                    request.getMaxSalary()
                            )
                    );
        }

        return jobRepository
                .findAll(
                        specification,
                        pageable
                )
                .map(
                        this::mapToSearchResponse
                );
    }

    // GET PUBLIC JOB
    @Override
    @Transactional(readOnly = true)
    public JobResponse getPublicJob(
            Long jobId
    ) {

        if (jobId == null || jobId <= 0) {
            throw new IllegalArgumentException(
                    AppConstant.INVALID_JOB_ID
            );
        }

        Job job =
                jobRepository
                        .findById(jobId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOB_NOT_FOUND
                                )
                        );

        if (!"ACTIVE".equalsIgnoreCase(
                job.getStatus())) {

            throw new ResourceNotFoundException(
                    AppConstant.JOB_NOT_FOUND
            );
        }

        return mapToJobResponse(job);
    }

    // VALIDATION
    private void validateSearchRequest(JobSearchRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    AppConstant.SEARCH_REQUEST_NULL
            );
        }

        if (request.getPage() < 0) {
            throw new IllegalArgumentException(
                    AppConstant.PAGE_NUMBER_NEGATIVE
            );
        }

        if (request.getSize() <= 0) {
            throw new IllegalArgumentException(
                    AppConstant.PAGE_SIZE_INVALID
            );
        }

        if (request.getMinExperience() != null
                && request.getMaxExperience() != null
                && request.getMinExperience()
                .compareTo(
                        request.getMaxExperience()
                ) > 0) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_EXPERIENCE_RANGE
            );
        }

        if (request.getMinSalary() != null
                && request.getMaxSalary() != null
                && request.getMinSalary()
                .compareTo(
                        request.getMaxSalary()
                ) > 0) {

            throw new IllegalArgumentException(
                   AppConstant.INVALID_SALARY_RANGE
            );
        }
    }

    // SEARCH RESPONSE
    private JobSearchResponse mapToSearchResponse(
            Job job
    ) {

        JobSearchResponse response =
                new JobSearchResponse();


        response.setId(
                job.getId()
        );

        response.setJobTitle(
                job.getJobTitle()
        );


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

        return response;
    }

    // FULL JOB RESPONSE
    private JobResponse mapToJobResponse(
            Job job
    ) {

        JobResponse response = new JobResponse();

        response.setId(
                job.getId()
        );

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