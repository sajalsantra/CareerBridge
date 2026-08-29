package com.careerbridge.dto.job;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class JobSearchRequest {

    @Size(
            max = 200,
            message = "Keyword cannot exceed 200 characters."
    )
    private String keyword;


    @Size(
            max = 150,
            message = "Location cannot exceed 150 characters."
    )
    private String location;


    private Long categoryId;


    @Size(
            max = 50,
            message = "Job type cannot exceed 50 characters."
    )
    private String jobType;


    @Size(
            max = 50,
            message = "Work mode cannot exceed 50 characters."
    )
    private String workMode;


    @DecimalMin(
            value = "0.0",
            message = "Minimum experience cannot be negative."
    )
    private BigDecimal minExperience;


    @DecimalMin(
            value = "0.0",
            message = "Maximum experience cannot be negative."
    )
    private BigDecimal maxExperience;


    @DecimalMin(
            value = "0.0",
            message = "Minimum salary cannot be negative."
    )
    private BigDecimal minSalary;


    @DecimalMin(
            value = "0.0",
            message = "Maximum salary cannot be negative."
    )
    private BigDecimal maxSalary;


    @Min(
            value = 0,
            message = "Page must be 0 or greater."
    )
    private Integer page = 0;


    @Min(
            value = 1,
            message = "Page size must be at least 1."
    )
    @Max(
            value = 100,
            message = "Page size cannot exceed 100."
    )
    private Integer size = 10;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }


    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }


    public BigDecimal getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(
            BigDecimal minExperience) {
        this.minExperience = minExperience;
    }


    public BigDecimal getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(
            BigDecimal maxExperience) {
        this.maxExperience = maxExperience;
    }


    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(
            BigDecimal minSalary) {
        this.minSalary = minSalary;
    }


    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(
            BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}