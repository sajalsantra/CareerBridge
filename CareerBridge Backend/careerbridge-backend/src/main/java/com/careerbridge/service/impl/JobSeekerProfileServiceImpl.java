package com.careerbridge.service.impl;

import com.careerbridge.dto.jobseeker.JobSeekerProfileResponse;
import com.careerbridge.dto.jobseeker.UpdateJobSeekerProfileRequest;
import com.careerbridge.entity.JobSeekerProfile;
import com.careerbridge.entity.User;
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
            String email) {

        User user = getUser(email);

        JobSeekerProfile profile =
                getProfile(user.getId());

        return mapToResponse(user, profile);
    }

    @Override
    @Transactional
    public JobSeekerProfileResponse updateMyProfile(
            String email,
            UpdateJobSeekerProfileRequest request) {

        User user = getUser(email);

        JobSeekerProfile profile =
                getProfile(user.getId());

        // Update User information
        user.setFullName(
                request.getFullName()
        );

        user.setEmail(
                request.getEmail()
        );

        user.setPhone(
                request.getPhone()
        );

        // Update Job Seeker Profile
        profile.setHeadline(
                request.getHeadline()
        );

        profile.setProfessionalSummary(
                request.getProfessionalSummary()
        );

        profile.setLocation(
                request.getLocation()
        );

        profile.setPreferredLocation(
                request.getPreferredLocation()
        );

        profile.setCurrentJobTitle(
                request.getCurrentJobTitle()
        );

        profile.setCurrentCompany(
                request.getCurrentCompany()
        );

        profile.setTotalExperienceYears(
                request.getTotalExperienceYears()
        );

        profile.setExpectedSalary(
                request.getExpectedSalary()
        );

        profile.setNoticePeriodDays(
                request.getNoticePeriodDays()
        );

        profile.setPreferredJobType(
                request.getPreferredJobType()
        );

        profile.setPreferredWorkMode(
                request.getPreferredWorkMode()
        );

        // Calculate profile completion
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

    private User getUser(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    private JobSeekerProfile getProfile(
            Long userId) {

        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
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