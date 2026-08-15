package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.education.AddEducationRequest;
import com.careerbridge.dto.jobseeker.education.EducationResponse;
import com.careerbridge.dto.jobseeker.education.UpdateEducationRequest;
import com.careerbridge.entity.jobseeker.JobSeekerEducation;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.entity.User;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerEducationRepository;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.service.JobSeekerEducationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobSeekerEducationServiceImpl
        implements JobSeekerEducationService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository profileRepository;
    private final JobSeekerEducationRepository educationRepository;

    public JobSeekerEducationServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository,
            JobSeekerEducationRepository educationRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.educationRepository = educationRepository;
    }

    // 1. ADD EDUCATION
    @Override
    @Transactional
    public EducationResponse addEducation(
            String username,
            AddEducationRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerEducation education =
                new JobSeekerEducation();

        education.setJobSeekerProfile(profile);

        education.setDegree(
                request.getDegree()
        );

        education.setFieldOfStudy(
                request.getFieldOfStudy()
        );

        education.setInstitutionName(
                request.getInstitutionName()
        );

        education.setLocation(
                request.getLocation()
        );

        education.setStartDate(
                request.getStartDate()
        );

        education.setEndDate(
                request.getEndDate()
        );

        education.setCurrent(
                request.getCurrent() != null
                        ? request.getCurrent()
                        : false
        );

        education.setGrade(
                request.getGrade()
        );

        education.setDescription(
                request.getDescription()
        );

        JobSeekerEducation savedEducation =
                educationRepository.save(education);

        return mapToResponse(savedEducation);
    }

    // 2. GET ALL EDUCATION
    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getMyEducation(
            String username) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        return educationRepository
                .findByJobSeekerProfileId(profile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. GET SINGLE EDUCATION
    @Override
    @Transactional(readOnly = true)
    public EducationResponse getEducation(
            String username,
            Long educationId) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerEducation education =
                educationRepository
                        .findByIdAndJobSeekerProfileId(
                                educationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.EDUCATION_NOT_FOUND
                                )
                        );

        return mapToResponse(education);
    }

    // 4. UPDATE EDUCATION
    @Override
    @Transactional
    public EducationResponse updateEducation(
            String username,
            Long educationId,
            UpdateEducationRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerEducation education =
                educationRepository
                        .findByIdAndJobSeekerProfileId(
                                educationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.EDUCATION_NOT_FOUND
                                )
                        );

        // Update only fields provided in request

        if (request.getDegree() != null) {
            education.setDegree(
                    request.getDegree()
            );
        }

        if (request.getFieldOfStudy() != null) {
            education.setFieldOfStudy(
                    request.getFieldOfStudy()
            );
        }

        if (request.getInstitutionName() != null) {
            education.setInstitutionName(
                    request.getInstitutionName()
            );
        }

        if (request.getLocation() != null) {
            education.setLocation(
                    request.getLocation()
            );
        }

        if (request.getStartDate() != null) {
            education.setStartDate(
                    request.getStartDate()
            );
        }

        if (request.getEndDate() != null) {
            education.setEndDate(
                    request.getEndDate()
            );
        }

        if (request.getCurrent() != null) {
            education.setCurrent(
                    request.getCurrent()
            );
        }

        if (request.getGrade() != null) {
            education.setGrade(
                    request.getGrade()
            );
        }

        if (request.getDescription() != null) {
            education.setDescription(
                    request.getDescription()
            );
        }

        JobSeekerEducation updatedEducation =
                educationRepository.save(education);

        return mapToResponse(updatedEducation);
    }

    // 5. DELETE EDUCATION
    @Override
    @Transactional
    public void deleteEducation(
            String username,
            Long educationId) {

        User user = getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerEducation education =
                educationRepository
                        .findByIdAndJobSeekerProfileId(
                                educationId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.EDUCATION_NOT_FOUND
                                )
                        );

        educationRepository.delete(education);
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
    private EducationResponse mapToResponse(
            JobSeekerEducation education) {

        EducationResponse response =
                new EducationResponse();

        response.setId(
                education.getId()
        );

        response.setDegree(
                education.getDegree()
        );

        response.setFieldOfStudy(
                education.getFieldOfStudy()
        );

        response.setInstitutionName(
                education.getInstitutionName()
        );

        response.setLocation(
                education.getLocation()
        );

        response.setStartDate(
                education.getStartDate()
        );

        response.setEndDate(
                education.getEndDate()
        );

        response.setCurrent(
                education.getCurrent()
        );

        response.setGrade(
                education.getGrade()
        );

        response.setDescription(
                education.getDescription()
        );

        return response;
    }
}