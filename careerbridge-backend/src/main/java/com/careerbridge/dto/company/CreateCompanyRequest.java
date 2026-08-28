package com.careerbridge.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCompanyRequest {

    @NotBlank(
            message = "Company name is required."
    )
    @Size(
            max = 200,
            message = "Company name cannot exceed 200 characters."
    )
    private String companyName;

    @Size(
            max = 500,
            message = "Website cannot exceed 500 characters."
    )
    private String website;
    private String description;

    @Size(
            max = 150,
            message = "Industry cannot exceed 150 characters."
    )
    private String industry;

    @Size(
            max = 100,
            message = "Company size cannot exceed 100 characters."
    )
    private String companySize;

    private String location;

    private String logoUrl;

    private Integer foundedYear;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }
}