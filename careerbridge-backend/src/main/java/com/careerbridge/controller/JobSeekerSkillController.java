package com.careerbridge.controller;

import com.careerbridge.dto.error.ApiErrorResponse;
import com.careerbridge.dto.jobseeker.AddSkillRequest;
import com.careerbridge.dto.jobseeker.SkillResponse;
import com.careerbridge.dto.jobseeker.UpdateSkillRequest;
import com.careerbridge.service.JobSeekerSkillService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-seeker/skills")
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
                        "Skill deleted successfully."
                )
        );
    }
}