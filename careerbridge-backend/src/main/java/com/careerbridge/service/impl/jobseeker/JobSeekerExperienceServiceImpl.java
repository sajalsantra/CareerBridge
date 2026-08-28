package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.experience.AddExperienceRequest;
import com.careerbridge.dto.jobseeker.experience.ExperienceResponse;
import com.careerbridge.dto.jobseeker.experience.UpdateExperienceRequest;
import com.careerbridge.entity.User;
import com.careerbridge.entity.jobseeker.JobSeekerExperience;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.exception.ResourceNotFoundException;

import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.jobseeker.JobSeekerExperienceRepository;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.service.jobseeker.JobSeekerExperienceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobSeekerExperienceServiceImpl
        implements JobSeekerExperienceService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository profileRepository;
    private final JobSeekerExperienceRepository experienceRepository;

    public JobSeekerExperienceServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository,
            JobSeekerExperienceRepository experienceRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.experienceRepository = experienceRepository;
    }


    // 1. ADD EXPERIENCE
    @Override
    @Transactional
    public ExperienceResponse addExperience(
            String username,
            AddExperienceRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerExperience experience =
                new JobSeekerExperience();

        experience.setJobSeekerProfile(profile);

        experience.setJobTitle(
                request.getJobTitle()
        );

        experience.setCompanyName(
                request.getCompanyName()
        );

        experience.setEmploymentType(
                request.getEmploymentType()
        );

        experience.setLocation(
                request.getLocation()
        );

        experience.setStartDate(
                request.getStartDate()
        );

        experience.setEndDate(
                request.getEndDate()
        );

        experience.setCurrent(
                request.getCurrent() != null
                        ? request.getCurrent()
                        : false
        );

        experience.setDescription(
                request.getDescription()
        );

        JobSeekerExperience savedExperience =
                experienceRepository.save(experience);

        return mapToResponse(savedExperience);
    }

    // 2. GET ALL EXPERIENCES
    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getMyExperiences(
            String username) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        return experienceRepository
                .findByJobSeekerProfileId(profile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // 3. GET SINGLE EXPERIENCE
    @Override
    @Transactional(readOnly = true)
    public ExperienceResponse getExperience(
            String username,
            Long experienceId) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerExperience experience =
                experienceRepository
                        .findByIdAndJobSeekerProfileId(
                                experienceId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.EXPERIENCE_NOT_FOUND
                                )
                        );

        return mapToResponse(experience);
    }

    // 4. UPDATE EXPERIENCE
    @Override
    @Transactional
    public ExperienceResponse updateExperience(
            String username,
            Long experienceId,
            UpdateExperienceRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerExperience experience =
                experienceRepository
                        .findByIdAndJobSeekerProfileId(
                                experienceId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.EXPERIENCE_NOT_FOUND
                                )
                        );

        // Update only provided fields
        if (request.getJobTitle() != null) {
            experience.setJobTitle(
                    request.getJobTitle()
            );
        }

        if (request.getCompanyName() != null) {
            experience.setCompanyName(
                    request.getCompanyName()
            );
        }

        if (request.getEmploymentType() != null) {
            experience.setEmploymentType(
                    request.getEmploymentType()
            );
        }

        if (request.getLocation() != null) {
            experience.setLocation(
                    request.getLocation()
            );
        }

        if (request.getStartDate() != null) {
            experience.setStartDate(
                    request.getStartDate()
            );
        }

        if (request.getEndDate() != null) {
            experience.setEndDate(
                    request.getEndDate()
            );
        }

        if (request.getCurrent() != null) {
            experience.setCurrent(
                    request.getCurrent()
            );
        }

        if (request.getDescription() != null) {
            experience.setDescription(
                    request.getDescription()
            );
        }

        JobSeekerExperience updatedExperience =
                experienceRepository.save(experience);

        return mapToResponse(updatedExperience);
    }


    // 5. DELETE EXPERIENCE
    @Override
    @Transactional
    public void deleteExperience(
            String username,
            Long experienceId) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerExperience experience =
                experienceRepository
                        .findByIdAndJobSeekerProfileId(
                                experienceId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.EXPERIENCE_NOT_FOUND
                                )
                        );

        experienceRepository.delete(experience);
    }


    // GET USER
    private User getUser(String username) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );
    }


    // GET PROFILE
    private JobSeekerProfile getProfile(Long userId) {

        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                        )
                );
    }


    // ENTITY → RESPONSE


    private ExperienceResponse mapToResponse(
            JobSeekerExperience experience) {

        ExperienceResponse response =
                new ExperienceResponse();

        response.setId(
                experience.getId()
        );

        response.setJobTitle(
                experience.getJobTitle()
        );

        response.setCompanyName(
                experience.getCompanyName()
        );

        response.setEmploymentType(
                experience.getEmploymentType()
        );

        response.setLocation(
                experience.getLocation()
        );

        response.setStartDate(
                experience.getStartDate()
        );

        response.setEndDate(
                experience.getEndDate()
        );

        response.setCurrent(
                experience.getCurrent()
        );

        response.setDescription(
                experience.getDescription()
        );

        return response;
    }
}