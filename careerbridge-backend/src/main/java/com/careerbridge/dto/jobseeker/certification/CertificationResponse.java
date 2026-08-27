package com.careerbridge.dto.jobseeker.certification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CertificationResponse {

    private Long id;

    private String certificationName;

    private String issuingOrganization;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String credentialId;

    private String credentialUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }
}