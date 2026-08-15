package com.careerbridge.dto.jobseeker.profile;

import java.math.BigDecimal;

public class JobSeekerProfileResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String phone;

    private String headline;

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

    private Integer profileCompletionPercentage;

    public JobSeekerProfileResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getProfileCompletionPercentage() {
        return profileCompletionPercentage;
    }

    public void setProfileCompletionPercentage(
            Integer profileCompletionPercentage) {

        this.profileCompletionPercentage =
                profileCompletionPercentage;
    }
}