package com.careerbridge.entity.jobseeker;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "certifications")
public class JobSeekerCertification {

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
            name = "certification_name",
            nullable = false,
            length = 200
    )
    private String certificationName;

    @Column(
            name = "issuing_organization",
            length = 200
    )
    private String issuingOrganization;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(
            name = "credential_id",
            length = 150
    )
    private String credentialId;

    @Column(
            name = "credential_url",
            length = 500
    )
    private String credentialUrl;

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


    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(
            String certificationName) {

        this.certificationName =
                certificationName;
    }


    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(
            String issuingOrganization) {

        this.issuingOrganization =
                issuingOrganization;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(
            LocalDate issueDate) {

        this.issueDate = issueDate;
    }


    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(
            LocalDate expiryDate) {

        this.expiryDate = expiryDate;
    }


    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(
            String credentialId) {

        this.credentialId = credentialId;
    }


    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(
            String credentialUrl) {

        this.credentialUrl = credentialUrl;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}