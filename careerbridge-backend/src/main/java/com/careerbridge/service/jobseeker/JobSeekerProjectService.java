package com.careerbridge.service.jobseeker;

import com.careerbridge.dto.jobseeker.project.AddProjectRequest;
import com.careerbridge.dto.jobseeker.project.ProjectResponse;
import com.careerbridge.dto.jobseeker.project.UpdateProjectRequest;

import java.util.List;

public interface JobSeekerProjectService {

    ProjectResponse addProject(
            String username,
            AddProjectRequest request
    );

    List<ProjectResponse> getMyProjects(
            String username
    );

    ProjectResponse getProject(
            String username,
            Long projectId
    );

    ProjectResponse updateProject(
            String username,
            Long projectId,
            UpdateProjectRequest request
    );

    void deleteProject(
            String username,
            Long projectId
    );
}