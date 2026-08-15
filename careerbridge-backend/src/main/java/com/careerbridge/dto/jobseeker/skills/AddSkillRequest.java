package com.careerbridge.dto.jobseeker.skills;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AddSkillRequest {

    @NotNull(message = "Skill ID is required")
    private Long skillId;

    @NotBlank(message = "Proficiency level is required")
    @Size(max = 50, message = "Proficiency level cannot exceed 50 characters")
    private String proficiencyLevel;

    @DecimalMin(
            value = "0.0",
            message = "Years of experience cannot be negative"
    )
    @DecimalMax(
            value = "99.99",
            message = "Years of experience cannot exceed 99.99"
    )
    private BigDecimal yearsOfExperience;

    public AddSkillRequest() {
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public BigDecimal getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(BigDecimal yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}