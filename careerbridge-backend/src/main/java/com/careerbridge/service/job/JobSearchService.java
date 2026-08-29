package com.careerbridge.service.job;

import com.careerbridge.dto.job.JobSearchRequest;
import com.careerbridge.dto.job.JobSearchResponse;
import com.careerbridge.dto.recruiter.job.JobResponse;
import org.springframework.data.domain.Page;

public interface JobSearchService {

    Page<JobSearchResponse> searchJobs(
            JobSearchRequest request
    );


    JobResponse getPublicJob(
            Long jobId
    );
}