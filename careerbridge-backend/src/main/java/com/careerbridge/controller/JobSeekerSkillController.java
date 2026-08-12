package com.careerbridge.controller;

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

    // ADD SKILL
    // POST /api/job-seeker/skills

    @PostMapping
    public ResponseEntity<SkillResponse> addSkill(
            @Valid @RequestBody AddSkillRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        SkillResponse response =
                jobSeekerSkillService.addSkill(
                        email,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET MY SKILLS
    // GET /api/job-seeker/skills

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getMySkills(
            Authentication authentication) {

        String email = authentication.getName();

        List<SkillResponse> skills =
                jobSeekerSkillService.getMySkills(
                        email
                );

        return ResponseEntity.ok(skills);
    }

    // UPDATE SKILL
    // PUT /api/job-seeker/skills/{skillId}

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long skillId,
            @Valid @RequestBody UpdateSkillRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        SkillResponse response =
                jobSeekerSkillService.updateSkill(
                        email,
                        skillId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    // DELETE SKILL
    // DELETE /api/job-seeker/skills/{skillId}

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long skillId,
            Authentication authentication) {

        String email = authentication.getName();

        jobSeekerSkillService.deleteSkill(
                email,
                skillId
        );

        return ResponseEntity.noContent().build();
    }
}