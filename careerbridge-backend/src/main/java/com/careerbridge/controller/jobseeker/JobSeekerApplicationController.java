package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.applications.ApplicationResponse;
import com.careerbridge.dto.jobseeker.applications.CreateApplicationRequest;
import com.careerbridge.service.jobseeker.JobSeekerApplicationService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.APPLICATION_BASE)
public class JobSeekerApplicationController {


    private final JobSeekerApplicationService
            applicationService;


    public JobSeekerApplicationController(
            JobSeekerApplicationService applicationService
    ) {

        this.applicationService =
                applicationService;
    }

    // APPLY FOR JOB
    @PostMapping
    public ResponseEntity<ApplicationResponse>
    applyForJob(

            @Valid
            @RequestBody
            CreateApplicationRequest request,

            Authentication authentication
    ) {

        String username = authentication.getName();


        ApplicationResponse response =
                applicationService.applyForJob(
                        username,
                        request
                );


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET MY APPLICATIONS
    @GetMapping
    public ResponseEntity<Page<ApplicationResponse>>
    getMyApplications(

            Authentication authentication,

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                applicationService
                        .getMyApplications(
                                username,
                                page,
                                size
                        )
        );
    }

    // GET MY APPLICATIONS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ApplicationResponse>>
    getMyApplicationsByStatus(

            @PathVariable
            String status,

            Authentication authentication,

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size
    ) {

        String username = authentication.getName();


        return ResponseEntity.ok(
                applicationService
                        .getMyApplicationsByStatus(
                                username,
                                status,
                                page,
                                size
                        )
        );
    }

    // GET MY APPLICATION
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponse>
    getMyApplication(

            @PathVariable
            Long applicationId,

            Authentication authentication
    ) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                applicationService
                        .getMyApplication(
                                username,
                                applicationId
                        )
        );
    }

    // WITHDRAW APPLICATION
    @PatchMapping("/{applicationId}/withdraw")
    public ResponseEntity<ApplicationResponse>
    withdrawApplication(

            @PathVariable
            Long applicationId,

            Authentication authentication
    ) {

        String username = authentication.getName();


        return ResponseEntity.ok(
                applicationService
                        .withdrawApplication(
                                username,
                                applicationId
                        )
        );
    }
}