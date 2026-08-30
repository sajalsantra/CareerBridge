package com.careerbridge.dto.jobseeker.applications;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private Long jobSeekerProfileId;

    private Long resumeId;

    private String resumeName;

    private String coverLetter;

    private String status;

    private String recruiterRemarks;

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    private LocalDate applicationDeadline;


    // ==============================
    // Getters and Setters
    // ==============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(
            String companyName) {

        this.companyName = companyName;
    }


    public Long getJobSeekerProfileId() {
        return jobSeekerProfileId;
    }

    public void setJobSeekerProfileId(
            Long jobSeekerProfileId) {

        this.jobSeekerProfileId =
                jobSeekerProfileId;
    }


    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }


    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(
            String resumeName) {

        this.resumeName = resumeName;
    }


    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(
            String coverLetter) {

        this.coverLetter = coverLetter;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getRecruiterRemarks() {
        return recruiterRemarks;
    }

    public void setRecruiterRemarks(
            String recruiterRemarks) {

        this.recruiterRemarks =
                recruiterRemarks;
    }


    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(
            LocalDateTime appliedAt) {

        this.appliedAt = appliedAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }


    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(
            LocalDate applicationDeadline) {

        this.applicationDeadline =
                applicationDeadline;
    }
}