package com.careerbridge.controller.job;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.job.JobSearchRequest;
import com.careerbridge.dto.job.JobSearchResponse;
import com.careerbridge.dto.recruiter.job.JobResponse;
import com.careerbridge.service.job.JobSearchService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.JOBS_API_BASE)
public class JobSearchController {

    private final JobSearchService jobSearchService;

    public JobSearchController(
            JobSearchService jobSearchService
    ) {
        this.jobSearchService =
                jobSearchService;
    }

    // SEARCH JOBS
    @GetMapping("/search")
    public ResponseEntity<Page<JobSearchResponse>>
    searchJobs(
            @Valid
            @ModelAttribute
            JobSearchRequest request
    ) {
        return ResponseEntity.ok(
                jobSearchService.searchJobs(
                        request
                )
        );
    }

    // GET ALL ACTIVE JOBS
    @GetMapping
    public ResponseEntity<Page<JobSearchResponse>>
    getJobs(
            @Valid
            @ModelAttribute
            JobSearchRequest request
    ) {
        return ResponseEntity.ok(
                jobSearchService.searchJobs(
                        request
                )
        );
    }

    // GET JOB BY ID
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse>
    getJob(
            @PathVariable
            Long jobId
    ) {
        return ResponseEntity.ok(
                jobSearchService.getPublicJob(
                        jobId
                )
        );
    }
}