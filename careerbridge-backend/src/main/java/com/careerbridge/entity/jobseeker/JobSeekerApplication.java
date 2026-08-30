package com.careerbridge.entity.jobseeker;

import com.careerbridge.entity.recruiter.Job;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_job_seeker_application",
                        columnNames = {
                                "job_id",
                                "job_seeker_profile_id"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_applications_job",
                        columnList = "job_id"
                ),
                @Index(
                        name = "idx_applications_job_seeker",
                        columnList = "job_seeker_profile_id"
                ),
                @Index(
                        name = "idx_applications_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_applications_job_status",
                        columnList = "job_id,status"
                ),
                @Index(
                        name = "idx_applications_applied_at",
                        columnList = "applied_at"
                )
        }
)
public class JobSeekerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "job_id",
            nullable = false
    )
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "job_seeker_profile_id",
            nullable = false
    )
    private JobSeekerProfile jobSeekerProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "resume_id"
    )
    private JobSeekerResume resume;

    @Column(
            name = "cover_letter",
            columnDefinition = "TEXT"
    )
    private String coverLetter;

    @Column(
            name = "status",
            nullable = false,
            length = 50
    )
    private String status = "APPLIED";

    @Column(
            name = "recruiter_remarks",
            columnDefinition = "TEXT"
    )
    private String recruiterRemarks;

    @Column(
            name = "applied_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime appliedAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        if (appliedAt == null) {
            appliedAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (status == null
                || status.isBlank()) {

            status = "APPLIED";
        }
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


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }


    public JobSeekerProfile getJobSeekerProfile() {
        return jobSeekerProfile;
    }

    public void setJobSeekerProfile(
            JobSeekerProfile jobSeekerProfile) {

        this.jobSeekerProfile =
                jobSeekerProfile;
    }


    public JobSeekerResume getResume() {
        return resume;
    }

    public void setResume(JobSeekerResume resume) {
        this.resume = resume;
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
}