package com.careerbridge.service.company;

import com.careerbridge.dto.company.CompanyResponse;
import com.careerbridge.dto.company.CreateCompanyRequest;
import com.careerbridge.dto.company.UpdateCompanyRequest;

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
            UpdateCompanyRequest request
    );

    CompanyResponse selectCompany(
            String username,
            Long companyId
    );
}