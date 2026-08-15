package com.careerbridge.entity.jobseeker;

import com.careerbridge.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "job_seeker_profiles")
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String headline;

    @Column(length = 2000)
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

    private Integer profileCompletionPercentage = 0;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setProfileCompletionPercentage(Integer profileCompletionPercentage) {
        this.profileCompletionPercentage = profileCompletionPercentage;
    }
}