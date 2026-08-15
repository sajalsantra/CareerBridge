package com.careerbridge.dto.jobseeker.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UpdateJobSeekerProfileRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

//    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phone;

    @Size(max = 100, message = "Headline cannot exceed 100 characters")
    private String headline;

    @Size(max = 2000, message = "Summary cannot exceed 2000 characters")
    private String professionalSummary;

    private String location;

    private String preferredLocation;

    private String currentJobTitle;

    private String currentCompany;

    private BigDecimal totalExperienceYears;

    private BigDecimal expectedSalary;

    private Integer noticePeriodDays;

    private String preferredJobType;

    private String preferredWorkMode;

    public UpdateJobSeekerProfileRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(String professionalSummary) {
        this.professionalSummary = professionalSummary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getCurrentJobTitle() {
        return currentJobTitle;
    }

    public void setCurrentJobTitle(String currentJobTitle) {
        this.currentJobTitle = currentJobTitle;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public BigDecimal getTotalExperienceYears() {
        return totalExperienceYears;
    }

    public void setTotalExperienceYears(BigDecimal totalExperienceYears) {
        this.totalExperienceYears = totalExperienceYears;
    }

    public BigDecimal getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(BigDecimal expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public Integer getNoticePeriodDays() {
        return noticePeriodDays;
    }

    public void setNoticePeriodDays(Integer noticePeriodDays) {
        this.noticePeriodDays = noticePeriodDays;
    }

    public String getPreferredJobType() {
        return preferredJobType;
    }

    public void setPreferredJobType(String preferredJobType) {
        this.preferredJobType = preferredJobType;
    }

    public String getPreferredWorkMode() {
        return preferredWorkMode;
    }

    public void setPreferredWorkMode(String preferredWorkMode) {
        this.preferredWorkMode = preferredWorkMode;
    }
}