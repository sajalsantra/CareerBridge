package com.careerbridge.dto.jobseeker.project;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateProjectRequest {

    @Size(
            max = 200,
            message = "Project name must not exceed 200 characters."
    )
    private String projectName;

    private String description;

    @Size(
            max = 500,
            message = "Technologies must not exceed 500 characters."
    )
    private String technologies;

    @Size(
            max = 500,
            message = "Project URL must not exceed 500 characters."
    )
    private String projectUrl;

    private LocalDate startDate;

    private LocalDate endDate;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }


    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}