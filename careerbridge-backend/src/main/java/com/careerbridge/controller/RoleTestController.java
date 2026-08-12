package com.careerbridge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/job-seeker/test")
    public String jobSeekerTest() {
        return "JOB_SEEKER access granted!";
    }

    @GetMapping("/recruiter/test")
    public String recruiterTest() {
        return "RECRUITER access granted!";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "ADMIN access granted!";
    }
}