package com.careerbridge.service.impl.company;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.company.CompanyResponse;
import com.careerbridge.dto.company.CreateCompanyRequest;
import com.careerbridge.dto.company.UpdateCompanyRequest;
import com.careerbridge.entity.User;
import com.careerbridge.entity.recruiter.RecruiterProfile;
import com.careerbridge.entity.company.Company;
import com.careerbridge.exception.DuplicateResourceException;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.recruiter.RecruiterProfileRepository;
import com.careerbridge.repository.company.CompanyRepository;
import com.careerbridge.service.company.RecruiterCompanyService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RecruiterCompanyServiceImpl
        implements RecruiterCompanyService {

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterCompanyServiceImpl(
            UserRepository userRepository,
            CompanyRepository companyRepository,
            RecruiterProfileRepository recruiterProfileRepository
    ) {

        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.recruiterProfileRepository =
                recruiterProfileRepository;
    }

    // CREATE COMPANY
    @Override
    @Transactional
    public CompanyResponse createCompany(
            String username,
            CreateCompanyRequest request
    ) {

        // 1. Get logged-in user
        User user = getUser(username);

        // 2. Get recruiter profile
        RecruiterProfile recruiterProfile = getRecruiterProfile(user.getId());


        // 3. Check whether recruiter already has a company
        if (recruiterProfile.getCompany() != null) {
            throw new DuplicateResourceException(
                    AppConstant.ALREADY_ASSOCIATED_WITH_COMPANY
            );
        }

        // 4. Check whether company already exists
        Optional<Company> existingCompany =
                companyRepository
                        .findByCompanyNameIgnoreCase(
                                request.getCompanyName()
                        );

        // COMPANY ALREADY EXISTS
        if (existingCompany.isPresent()) {

            Company company = existingCompany.get();

            // Map existing company to recruiter profile
            recruiterProfile.setCompany(company);

            recruiterProfileRepository.save(
                    recruiterProfile
            );

            return mapToResponse(company);
        }

        // COMPANY DOES NOT EXIST
        Company company = new Company();

        company.setCompanyName(
                request.getCompanyName()
        );

        company.setDescription(
                request.getDescription()
        );

        company.setWebsite(
                request.getWebsite()
        );

        company.setIndustry(
                request.getIndustry()
        );

        company.setCompanySize(
                request.getCompanySize()
        );

        company.setLocation(
                request.getLocation()
        );

        company.setLogoUrl(
                request.getLogoUrl()
        );

        company.setFoundedYear(
                request.getFoundedYear()
        );

        // Save new company
        Company savedCompany = companyRepository.save(company);


        // Map company to recruiter
        recruiterProfile.setCompany(
                savedCompany
        );

        recruiterProfileRepository.save(
                recruiterProfile
        );

        return mapToResponse(
                savedCompany
        );
    }

    // GET MY COMPANY
    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getMyCompany(
            String username
    ) {

        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );

        if(recruiterProfile.getCompany()==null){

            throw new ResourceNotFoundException(
                    AppConstant.COMPANY_NOT_FOUND
            );
        }

        return mapToResponse(
                recruiterProfile.getCompany()
        );
    }

    // UPDATE COMPANY
    @Override
    @Transactional
    public CompanyResponse updateCompany(
            String username,
            UpdateCompanyRequest request
    ) {
        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(
                        user.getId()
                );

        Company company = recruiterProfile.getCompany();

        if(company == null){
            throw new ResourceNotFoundException(
                    AppConstant.COMPANY_NOT_FOUND
            );
        }

        if (request.getCompanyName() != null) {

            String newCompanyName = request.getCompanyName().trim();

            if (!newCompanyName.equalsIgnoreCase(
                    company.getCompanyName()
            )) {

                Optional<Company> existingCompany =
                        companyRepository
                                .findByCompanyNameIgnoreCase(
                                        newCompanyName
                                );


                if (existingCompany.isPresent()) {
                    throw new DuplicateResourceException(
                            AppConstant.DUPLICATE_COMPANY_FOUND
                    );
                }

                company.setCompanyName(
                        newCompanyName
                );
            }
        }

        if(request.getDescription()!=null){

            company.setDescription(
                    request.getDescription()
            );
        }

        if(request.getWebsite()!=null){

            company.setWebsite(
                    request.getWebsite()
            );
        }

        if(request.getIndustry()!=null){

            company.setIndustry(
                    request.getIndustry()
            );
        }

        if(request.getCompanySize()!=null){

            company.setCompanySize(
                    request.getCompanySize()
            );
        }

        if(request.getLocation()!=null){

            company.setLocation(
                    request.getLocation()
            );
        }

        if(request.getLogoUrl()!=null){

            company.setLogoUrl(
                    request.getLogoUrl()
            );
        }

        if(request.getFoundedYear()!=null){

            company.setFoundedYear(
                    request.getFoundedYear()
            );
        }

        Company updatedCompany =
                companyRepository.save(
                        company
                );

        return mapToResponse(
                updatedCompany
        );
    }
    // SELECT COMPANY
    @Override
    @Transactional
    public CompanyResponse selectCompany(
            String username,
            Long companyId
    ) {

        User user = getUser(username);

        RecruiterProfile recruiterProfile =
                getRecruiterProfile(user.getId());

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.COMPANY_NOT_FOUND
                                )
                        );

        recruiterProfile.setCompany(company);

        recruiterProfileRepository.save(
                recruiterProfile
        );

        return mapToResponse(company);
    }

    // FIND USER
    private User getUser(
            String username
    ){
        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );
    }

    // FIND RECRUITER PROFILE
    private RecruiterProfile getRecruiterProfile(
            Long userId
    ){
        return recruiterProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.RECRUITER_PROFILE_NOT_FOUND
                        )
                );
    }

    // ENTITY TO RESPONSE
    private CompanyResponse mapToResponse(
            Company company
    ){
        CompanyResponse response = new CompanyResponse();

        response.setId(
                company.getId()
        );

        response.setCompanyName(
                company.getCompanyName()
        );

        response.setDescription(
                company.getDescription()
        );

        response.setWebsite(
                company.getWebsite()
        );

        response.setIndustry(
                company.getIndustry()
        );

        response.setCompanySize(
                company.getCompanySize()
        );

        response.setLocation(
                company.getLocation()
        );

        response.setLogoUrl(
                company.getLogoUrl()
        );

        response.setFoundedYear(
                company.getFoundedYear()
        );

        response.setCreatedAt(
                company.getCreatedAt()
        );

        response.setUpdatedAt(
                company.getUpdatedAt()
        );

        return response;
    }

}