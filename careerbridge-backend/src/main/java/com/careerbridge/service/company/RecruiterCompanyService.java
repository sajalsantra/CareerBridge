package com.careerbridge.service.company;

import com.careerbridge.dto.company.CompanyResponse;
import com.careerbridge.dto.company.CreateCompanyRequest;

public interface RecruiterCompanyService {

    CompanyResponse createCompany(
            String username,
            CreateCompanyRequest request
    );

    CompanyResponse getMyCompany(
            String username
    );

    CompanyResponse updateCompany(
            String username,
            CreateCompanyRequest request
    );

}