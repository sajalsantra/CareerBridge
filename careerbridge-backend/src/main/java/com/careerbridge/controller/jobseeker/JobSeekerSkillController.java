package com.careerbridge.controller.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.error.ApiErrorResponse;
import com.careerbridge.dto.jobseeker.skills.AddSkillRequest;
import com.careerbridge.dto.jobseeker.skills.SkillResponse;
import com.careerbridge.dto.jobseeker.skills.UpdateSkillRequest;
import com.careerbridge.service.JobSeekerSkillService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.JOB_SEEKER_SKILL_BASE)
public class JobSeekerSkillController {

    private final JobSeekerSkillService jobSeekerSkillService;

    public JobSeekerSkillController(
            JobSeekerSkillService jobSeekerSkillService) {

        this.jobSeekerSkillService = jobSeekerSkillService;
    }

    @PostMapping
    public ResponseEntity<SkillResponse> addSkill(
            @Valid @RequestBody AddSkillRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        SkillResponse response =
                jobSeekerSkillService.addSkill(
                        username,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getMySkills(
            Authentication authentication) {

        String username = authentication.getName();

        List<SkillResponse> skills =
                jobSeekerSkillService.getMySkills(
                        username
                );

        return ResponseEntity.ok(skills);
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long skillId,
            @Valid @RequestBody UpdateSkillRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        SkillResponse response =
                jobSeekerSkillService.updateSkill(
                        username,
                        skillId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<ApiErrorResponse> deleteSkill(
            @PathVariable Long skillId,
            Authentication authentication) {

        String username = authentication.getName();

        jobSeekerSkillService.deleteSkill(
                username,
                skillId
        );

        return ResponseEntity.ok(
                new ApiErrorResponse(
                        AppConstant.SKILL_DELETE_SUCCEEDED
                )
        );
    }
}