package com.careerbridge.service.recruiter;

import com.careerbridge.dto.recruiter.job.CreateJobRequest;
import com.careerbridge.dto.recruiter.job.JobResponse;
import com.careerbridge.dto.recruiter.job.UpdateJobRequest;

import java.util.List;

public interface JobService {

    JobResponse createJob(
            String username,
            CreateJobRequest request
    );


    JobResponse getMyJob(
            String username,
            Long jobId
    );


    List<JobResponse> getMyJobs(
            String username
    );


    JobResponse updateJob(
            String username,
            Long jobId,
            UpdateJobRequest request
    );


    void deleteJob(
            String username,
            Long jobId
    );
}