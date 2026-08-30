package com.careerbridge.service.jobseeker;

import com.careerbridge.dto.jobseeker.applications.ApplicationResponse;
import com.careerbridge.dto.jobseeker.applications.CreateApplicationRequest;
import org.springframework.data.domain.Page;

public interface JobSeekerApplicationService {


    ApplicationResponse applyForJob(
            String username,
            CreateApplicationRequest request
    );


    Page<ApplicationResponse> getMyApplications(
            String username,
            int page,
            int size
    );


    Page<ApplicationResponse> getMyApplicationsByStatus(
            String username,
            String status,
            int page,
            int size
    );


    ApplicationResponse getMyApplication(
            String username,
            Long applicationId
    );


    ApplicationResponse withdrawApplication(
            String username,
            Long applicationId
    );
}