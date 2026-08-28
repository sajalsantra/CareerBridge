package com.careerbridge.entity.jobseeker;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_seeker_projects")
public class JobSeekerProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "job_seeker_profile_id",
            nullable = false
    )
    private JobSeekerProfile jobSeekerProfile;

    @Column(
            name = "project_name",
            nullable = false,
            length = 200
    )
    private String projectName;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "technologies",
            length = 500
    )
    private String technologies;

    @Column(
            name = "project_url",
            length = 500
    )
    private String projectUrl;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt =
                LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public JobSeekerProfile getJobSeekerProfile() {
        return jobSeekerProfile;
    }

    public void setJobSeekerProfile(
            JobSeekerProfile jobSeekerProfile) {

        this.jobSeekerProfile =
                jobSeekerProfile;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(
            String projectName) {

        this.projectName =
                projectName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description =
                description;
    }


    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(
            String technologies) {

        this.technologies =
                technologies;
    }


    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(
            String projectUrl) {

        this.projectUrl =
                projectUrl;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(
            LocalDate startDate) {

        this.startDate =
                startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(
            LocalDate endDate) {

        this.endDate =
                endDate;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt =
                createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt =
                updatedAt;
    }
}