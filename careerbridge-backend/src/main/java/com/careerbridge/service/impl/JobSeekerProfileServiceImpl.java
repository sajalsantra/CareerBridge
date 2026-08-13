package com.careerbridge.service.impl;

import com.careerbridge.dto.jobseeker.JobSeekerProfileResponse;
import com.careerbridge.dto.jobseeker.UpdateJobSeekerProfileRequest;
import com.careerbridge.entity.JobSeekerProfile;
import com.careerbridge.entity.User;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.JobSeekerProfileRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.service.JobSeekerProfileService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobSeekerProfileServiceImpl
        implements JobSeekerProfileService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository profileRepository;

    public JobSeekerProfileServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public JobSeekerProfileResponse getMyProfile(
            String username) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        return mapToResponse(user, profile);
    }

    @Override
    @Transactional
    public JobSeekerProfileResponse updateMyProfile(
            String username,
            UpdateJobSeekerProfileRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        // ==========================================
        // Update User Information
        // ==========================================

        if (request.getFullName() != null) {
            user.setFullName(
                    request.getFullName()
            );
        }

        if (request.getEmail() != null) {
            user.setEmail(
                    request.getEmail()
            );
        }

        if (request.getPhone() != null) {
            user.setPhone(
                    request.getPhone()
            );
        }

        if (request.getHeadline() != null) {
            profile.setHeadline(
                    request.getHeadline()
            );
        }

        if (request.getProfessionalSummary() != null) {
            profile.setProfessionalSummary(
                    request.getProfessionalSummary()
            );
        }

        if (request.getLocation() != null) {
            profile.setLocation(
                    request.getLocation()
            );
        }

        if (request.getPreferredLocation() != null) {
            profile.setPreferredLocation(
                    request.getPreferredLocation()
            );
        }

        if (request.getCurrentJobTitle() != null) {
            profile.setCurrentJobTitle(
                    request.getCurrentJobTitle()
            );
        }

        if (request.getCurrentCompany() != null) {
            profile.setCurrentCompany(
                    request.getCurrentCompany()
            );
        }

        if (request.getTotalExperienceYears() != null) {
            profile.setTotalExperienceYears(
                    request.getTotalExperienceYears()
            );
        }

        if (request.getExpectedSalary() != null) {
            profile.setExpectedSalary(
                    request.getExpectedSalary()
            );
        }

        if (request.getNoticePeriodDays() != null) {
            profile.setNoticePeriodDays(
                    request.getNoticePeriodDays()
            );
        }

        if (request.getPreferredJobType() != null) {
            profile.setPreferredJobType(
                    request.getPreferredJobType()
            );
        }

        if (request.getPreferredWorkMode() != null) {
            profile.setPreferredWorkMode(
                    request.getPreferredWorkMode()
            );
        }

        int completion =
                calculateProfileCompletion(
                        user,
                        profile
                );

        profile.setProfileCompletionPercentage(
                completion
        );

        userRepository.save(user);
        profileRepository.save(profile);

        return mapToResponse(user, profile);
    }

    private User getUser(String username) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }

    private JobSeekerProfile getProfile(
            Long userId) {

        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job seeker profile not found"
                        )
                );
    }

    private JobSeekerProfileResponse mapToResponse(
            User user,
            JobSeekerProfile profile) {

        JobSeekerProfileResponse response =
                new JobSeekerProfileResponse();

        response.setId(profile.getId());
        response.setUserId(user.getId());

        response.setFullName(
                user.getFullName()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setPhone(
                user.getPhone()
        );

        response.setHeadline(
                profile.getHeadline()
        );

        response.setProfessionalSummary(
                profile.getProfessionalSummary()
        );

        response.setLocation(
                profile.getLocation()
        );

        response.setPreferredLocation(
                profile.getPreferredLocation()
        );

        response.setCurrentJobTitle(
                profile.getCurrentJobTitle()
        );

        response.setCurrentCompany(
                profile.getCurrentCompany()
        );

        response.setTotalExperienceYears(
                profile.getTotalExperienceYears()
        );

        response.setExpectedSalary(
                profile.getExpectedSalary()
        );

        response.setNoticePeriodDays(
                profile.getNoticePeriodDays()
        );

        response.setPreferredJobType(
                profile.getPreferredJobType()
        );

        response.setPreferredWorkMode(
                profile.getPreferredWorkMode()
        );

        response.setProfileCompletionPercentage(
                profile.getProfileCompletionPercentage()
        );

        return response;
    }

    private int calculateProfileCompletion(
            User user,
            JobSeekerProfile profile) {

        int totalFields = 12;
        int completedFields = 0;

        if (isNotBlank(user.getFullName())) {
            completedFields++;
        }

        if (isNotBlank(user.getEmail())) {
            completedFields++;
        }

        if (isNotBlank(user.getPhone())) {
            completedFields++;
        }

        if (isNotBlank(profile.getHeadline())) {
            completedFields++;
        }

        if (isNotBlank(profile.getProfessionalSummary())) {
            completedFields++;
        }

        if (isNotBlank(profile.getLocation())) {
            completedFields++;
        }

        if (isNotBlank(profile.getPreferredLocation())) {
            completedFields++;
        }

        if (isNotBlank(profile.getCurrentJobTitle())) {
            completedFields++;
        }

        if (isNotBlank(profile.getCurrentCompany())) {
            completedFields++;
        }

        if (profile.getTotalExperienceYears() != null) {
            completedFields++;
        }

        if (profile.getExpectedSalary() != null) {
            completedFields++;
        }

        if (profile.getPreferredJobType() != null
                && !profile.getPreferredJobType().isBlank()) {

            completedFields++;
        }

        return (completedFields * 100) / totalFields;
    }

    private boolean isNotBlank(String value) {

        return value != null &&
                !value.trim().isEmpty();
    }
}