package com.careerbridge.controller.recruiter;


import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.company.CompanyResponse;
import com.careerbridge.dto.company.CreateCompanyRequest;
import com.careerbridge.dto.company.UpdateCompanyRequest;
import com.careerbridge.service.company.RecruiterCompanyService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.RECRUITER_COMPANY)
public class RecruiterCompanyController {
    
    private final RecruiterCompanyService recruiterCompanyService;

    public RecruiterCompanyController(
            RecruiterCompanyService recruiterCompanyService
    ){
        this.recruiterCompanyService =
                recruiterCompanyService;
    }

    // CREATE COMPANY
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid
            @RequestBody
            CreateCompanyRequest request,
            Authentication authentication
    ){
        String username =
                authentication.getName();

        CompanyResponse response =
                recruiterCompanyService.createCompany(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    // GET MY COMPANY
    @GetMapping
    public ResponseEntity<CompanyResponse> getMyCompany(
            Authentication authentication
    ){
        String username = authentication.getName();

        return ResponseEntity.ok(
                recruiterCompanyService.getMyCompany(
                        username
                )
        );
    }

    // UPDATE COMPANY
    @PutMapping
    public ResponseEntity<CompanyResponse> updateCompany(
            @Valid
            @RequestBody
            UpdateCompanyRequest request,
            Authentication authentication
    ){
        String username = authentication.getName();

        return ResponseEntity.ok(
                recruiterCompanyService.updateCompany(
                        username,
                        request
                )
        );
    }

    // UPDATE COMPANY USING ID
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> selectCompany(

            @PathVariable Long companyId,
            Authentication authentication
    ) {
        String username = authentication.getName();

        return ResponseEntity.ok(
                recruiterCompanyService.selectCompany(
                        username,
                        companyId
                )
        );
    }
}