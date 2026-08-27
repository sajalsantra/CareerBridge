package com.careerbridge.dto.jobseeker.certification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AddCertificationRequest {

    @NotBlank(message = "Certification name is required.")
    @Size(
            max = 200,
            message = "Certification name must not exceed 200 characters."
    )
    private String certificationName;

    @Size(
            max = 200,
            message = "Issuing organization must not exceed 200 characters."
    )
    private String issuingOrganization;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    @Size(
            max = 150,
            message = "Credential ID must not exceed 150 characters."
    )
    private String credentialId;

    @Size(
            max = 500,
            message = "Credential URL must not exceed 500 characters."
    )
    private String credentialUrl;


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
}