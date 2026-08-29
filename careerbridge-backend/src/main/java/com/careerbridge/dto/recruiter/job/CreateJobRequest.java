package com.careerbridge.dto.recruiter.job;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateJobRequest {

    @NotBlank(message = "Job title is required.")
    @Size(
            max = 200,
            message = "Job title cannot exceed 200 characters."
    )
    private String jobTitle;

    @NotBlank(message = "Job description is required.")
    private String description;

    private String responsibilities;

    private String qualifications;

    @Size(
            max = 150,
            message = "Location cannot exceed 150 characters."
    )
    private String location;

    @Size(
            max = 50,
            message = "Job type cannot exceed 50 characters."
    )
    private String jobType;

    @Size(
            max = 50,
            message = "Work mode cannot exceed 50 characters."
    )
    private String workMode;

    @DecimalMin(
            value = "0.0",
            message = "Minimum experience cannot be negative."
    )
    private BigDecimal experienceMin;

    @DecimalMin(
            value = "0.0",
            message = "Maximum experience cannot be negative."
    )
    private BigDecimal experienceMax;

    @DecimalMin(
            value = "0.0",
            message = "Minimum salary cannot be negative."
    )
    private BigDecimal salaryMin;

    @DecimalMin(
            value = "0.0",
            message = "Maximum salary cannot be negative."
    )
    private BigDecimal salaryMax;

    @NotNull(message = "Number of openings is required.")
    @Min(
            value = 1,
            message = "Number of openings must be at least 1."
    )
    private Integer numberOfOpenings = 1;

    private Long categoryId;

    @FutureOrPresent(
            message = "Application deadline cannot be in the past."
    )
    private LocalDate applicationDeadline;


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }


    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }


    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }


    public BigDecimal getExperienceMin() {
        return experienceMin;
    }

    public void setExperienceMin(BigDecimal experienceMin) {
        this.experienceMin = experienceMin;
    }


    public BigDecimal getExperienceMax() {
        return experienceMax;
    }

    public void setExperienceMax(BigDecimal experienceMax) {
        this.experienceMax = experienceMax;
    }


    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }


    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }


    public Integer getNumberOfOpenings() {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings(Integer numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(
            LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }
}