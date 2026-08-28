package com.careerbridge.service.impl.recruiter;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.recruiter.profile.RecruiterProfileResponse;
import com.careerbridge.dto.recruiter.profile.UpdateRecruiterProfileRequest;
import com.careerbridge.entity.recruiter.RecruiterProfile;
import com.careerbridge.entity.User;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.recruiter.RecruiterProfileRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.service.recruiter.RecruiterProfileService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruiterProfileServiceImpl
        implements RecruiterProfileService {

    private final UserRepository userRepository;

    private final RecruiterProfileRepository recruiterProfileRepository;


    public RecruiterProfileServiceImpl(
            UserRepository userRepository,
            RecruiterProfileRepository recruiterProfileRepository) {

        this.userRepository = userRepository;

        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    // GET MY PROFILE
    @Override
    @Transactional(readOnly = true)
    public RecruiterProfileResponse getMyProfile(
            String username) {

        User user = getUser(username);

        RecruiterProfile profile = getRecruiterProfile(user.getId());

        return mapToResponse(
                user,
                profile
        );
    }

    // UPDATE MY PROFILE
    @Override
    @Transactional
    public RecruiterProfileResponse updateMyProfile(
            String username,
            UpdateRecruiterProfileRequest request) {

        User user = getUser(username);

        RecruiterProfile profile = getRecruiterProfile(user.getId());


        // Designation
        if (request.getDesignation() != null) {

            String designation = request.getDesignation().trim();

            if (!designation.isEmpty()) {

                profile.setDesignation(
                        designation
                );
            }
        }


        // Bio
        if (request.getBio() != null) {

            String bio = request.getBio().trim();

            profile.setBio(
                    bio.isEmpty()
                            ? null
                            : bio
            );
        }


        // Location
        if (request.getLocation() != null) {

            String location = request.getLocation().trim();

            if (!location.isEmpty()) {

                profile.setLocation(
                        location
                );
            }
        }


        RecruiterProfile updatedProfile =
                recruiterProfileRepository.save(
                        profile
                );

        return mapToResponse(
                user,
                updatedProfile
        );
    }

    // GET USER
    private User getUser(
            String username) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );
    }

    // GET RECRUITER PROFILE
    private RecruiterProfile getRecruiterProfile(
            Long userId) {

        return recruiterProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.RECRUITER_PROFILE_NOT_FOUND
                        )
                );
    }

    // ENTITY → RESPONSE
    private RecruiterProfileResponse mapToResponse(
            User user,
            RecruiterProfile profile) {

        RecruiterProfileResponse response =
                new RecruiterProfileResponse();

        response.setId(
                profile.getId()
        );

        response.setUsername(
                user.getUsername()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setFullName(
                user.getFullName()
        );

        response.setPhone(
                user.getPhone()
        );

        response.setDesignation(
                profile.getDesignation()
        );

        response.setBio(
                profile.getBio()
        );

        response.setLocation(
                profile.getLocation()
        );


        if (profile.getCompany() != null) {

            response.setCompanyId(
                    profile.getCompany().getId()
            );

            response.setCompanyName(
                    profile.getCompany().getCompanyName()
            );
        }


        response.setCreatedAt(
                profile.getCreatedAt()
        );

        response.setUpdatedAt(
                profile.getUpdatedAt()
        );

        return response;
    }
}