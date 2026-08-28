package com.careerbridge.service.jobseeker;

import com.careerbridge.dto.resume.ResumeResponse;
import com.careerbridge.dto.resume.UpdateResumeRequest;
import com.careerbridge.dto.resume.UploadResumeRequest;

import java.util.List;

public interface JobSeekerResumeService {

    ResumeResponse uploadResume(
            String username,
            UploadResumeRequest request
    );

    List<ResumeResponse> getMyResumes(
            String username
    );

    ResumeResponse getResume(
            String username,
            Long resumeId
    );

    ResumeResponse updateResume(
            String username,
            Long resumeId,
            UpdateResumeRequest request
    );

    void deleteResume(
            String username,
            Long resumeId
    );
}