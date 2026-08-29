package com.careerbridge.controller.recruiter;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.recruiter.job.CreateJobRequest;
import com.careerbridge.dto.recruiter.job.JobResponse;
import com.careerbridge.dto.recruiter.job.UpdateJobRequest;
import com.careerbridge.service.recruiter.JobService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.RECRUITER_JOB_BASE)
public class JobController {

    private final JobService jobService;

    public JobController(
            JobService jobService
    ) {
        this.jobService = jobService;
    }

    // CREATE JOB
    @PostMapping
    public ResponseEntity<JobResponse> createJob(

            @Valid
            @RequestBody
            CreateJobRequest request,

            Authentication authentication
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                jobService.createJob(
                        username,
                        request
                )
        );
    }

    // GET ALL MY JOBS
    @GetMapping
    public ResponseEntity<List<JobResponse>> getMyJobs(

            Authentication authentication
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                jobService.getMyJobs(
                        username
                )
        );
    }

    // GET MY JOB BY ID
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getMyJob(

            @PathVariable
            Long jobId,

            Authentication authentication
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                jobService.getMyJob(
                        username,
                        jobId
                )
        );
    }

    // UPDATE JOB
    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(

            @PathVariable
            Long jobId,

            @Valid
            @RequestBody
            UpdateJobRequest request,

            Authentication authentication
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                jobService.updateJob(
                        username,
                        jobId,
                        request
                )
        );
    }

    // DELETE JOB
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(

            @PathVariable
            Long jobId,

            Authentication authentication
    ) {

        String username = authentication.getName();

        jobService.deleteJob(
                username,
                jobId
        );

        return ResponseEntity.ok(
                AppConstant.JOB_DELETED_SUCCESSFULLY
        );
    }
}