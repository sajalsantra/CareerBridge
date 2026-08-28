package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.project.AddProjectRequest;
import com.careerbridge.dto.jobseeker.project.ProjectResponse;
import com.careerbridge.dto.jobseeker.project.UpdateProjectRequest;
import com.careerbridge.dto.response.ApiResponse;
import com.careerbridge.service.jobseeker.JobSeekerProjectService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.JOB_SEEKER_PROJECT_BASE)
public class JobSeekerProjectController {

    private final JobSeekerProjectService projectService;


    public JobSeekerProjectController(
            JobSeekerProjectService projectService) {

        this.projectService = projectService;
    }

    // 1. ADD PROJECT
    @PostMapping
    public ResponseEntity<ProjectResponse> addProject(
            @Valid
            @RequestBody
            AddProjectRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        ProjectResponse response =
                projectService.addProject(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. GET ALL PROJECTS
    @GetMapping
    public ResponseEntity<List<ProjectResponse>>
    getMyProjects(
            Authentication authentication) {

        String username = authentication.getName();

        List<ProjectResponse> response =
                projectService.getMyProjects(
                        username
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 3. GET PROJECT BY ID
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            @PathVariable Long projectId,
            Authentication authentication) {

        String username = authentication.getName();

        ProjectResponse response =
                projectService.getProject(
                        username,
                        projectId
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 4. UPDATE PROJECT
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long projectId,

            @Valid
            @RequestBody
            UpdateProjectRequest request,

            Authentication authentication) {

        String username = authentication.getName();

        ProjectResponse response =
                projectService.updateProject(
                        username,
                        projectId,
                        request
                );

        return ResponseEntity.ok(
                response
        );
    }

    // 5. DELETE PROJECT
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable Long projectId,
            Authentication authentication) {

        String username =
                authentication.getName();

        projectService.deleteProject(
                username,
                projectId
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        AppConstant.PROjECT_DELETE_SUCCEEDED
                )
        );
    }
}