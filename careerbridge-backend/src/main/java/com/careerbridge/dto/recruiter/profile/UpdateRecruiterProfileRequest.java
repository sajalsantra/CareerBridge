package com.careerbridge.dto.recruiter.profile;

import jakarta.validation.constraints.Size;

public class UpdateRecruiterProfileRequest {

    @Size(
            max = 150,
            message = "Designation must not exceed 150 characters."
    )
    private String designation;

    private String bio;

    @Size(
            max = 255,
            message = "Location must not exceed 255 characters."
    )
    private String location;


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}