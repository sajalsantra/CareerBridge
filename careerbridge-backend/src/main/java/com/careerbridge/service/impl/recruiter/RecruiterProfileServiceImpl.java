package com.careerbridge.service.impl.recruiter;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.recruiter.profile.RecruiterProfileResponse;
import com.careerbridge.dto.recruiter.profile.UpdateRecruiterProfileRequest;
import com.careerbridge.entity.company.Company;
import com.careerbridge.entity.recruiter.RecruiterProfile;
import com.careerbridge.entity.User;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.company.CompanyRepository;
import com.careerbridge.repository.recruiter.RecruiterProfileRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.service.recruiter.RecruiterProfileService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruiterProfileServiceImpl
        implements RecruiterProfileService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final RecruiterProfileRepository recruiterProfileRepository;


    public RecruiterProfileServiceImpl(
            UserRepository userRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            CompanyRepository companyRepository) {

        this.userRepository = userRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.companyRepository = companyRepository;
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

        // Update recruiter profile
        if (request.getDesignation() != null) {
            String designation = request.getDesignation().trim();
            if (!designation.isEmpty()) {
                profile.setDesignation(
                        designation
                );
            }
        }

        if (request.getBio() != null) {
            String bio = request.getBio().trim();
            profile.setBio(
                    bio.isEmpty()
                            ? null
                            : bio
            );
        }

        if (request.getLocation() != null) {
            String location = request.getLocation().trim();
            if (!location.isEmpty()) {
                profile.setLocation(
                        location
                );
            }
        }

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(
                                    request.getCompanyId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            AppConstant.COMPANY_NOT_FOUND
                                    )
                            );
            profile.setCompany(company);
        }

        if (request.getCompanyName() != null) {
            Company company = companyRepository.findByCompanyNameIgnoreCase(
                            request.getCompanyName()
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    AppConstant.COMPANY_NOT_FOUND
                            )
                    );
            profile.setCompany(company);
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