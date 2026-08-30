package com.careerbridge.dto.jobseeker.applications;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateApplicationRequest {

    @NotNull(
            message = "Job ID is required."
    )
    private Long jobId;


    @NotNull(
            message = "Resume ID is required."
    )
    private Long resumeId;


    @Size(
            max = 5000,
            message = "Cover letter cannot exceed 5000 characters."
    )
    private String coverLetter;


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }


    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }


    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(
            String coverLetter) {

        this.coverLetter = coverLetter;
    }
}