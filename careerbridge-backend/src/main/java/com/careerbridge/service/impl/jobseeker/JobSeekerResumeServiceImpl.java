package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.resume.ResumeResponse;
import com.careerbridge.dto.resume.UpdateResumeRequest;
import com.careerbridge.dto.resume.UploadResumeRequest;
import com.careerbridge.entity.jobseeker.JobSeekerResume;
import com.careerbridge.entity.User;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerResumeRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.service.jobseeker.JobSeekerResumeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
public class JobSeekerResumeServiceImpl
        implements JobSeekerResumeService {

    private final UserRepository userRepository;

    private final JobSeekerProfileRepository profileRepository;

    private final JobSeekerResumeRepository resumeRepository;


    public JobSeekerResumeServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository,
            JobSeekerResumeRepository resumeRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.resumeRepository = resumeRepository;
    }

    // 1. UPLOAD RESUME
    @Override
    @Transactional
    public ResumeResponse uploadResume(
            String username,
            UploadResumeRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        validateUploadRequest(request);

        String cleanBase64 = cleanBase64Data(request.getFileData());

        validateBase64(cleanBase64);

        validateFileSize(cleanBase64);

        validateFileType(request.getFileType());


        if (Boolean.TRUE.equals(request.getPrimary())) {
            removePrimaryResume(
                    profile.getId()
            );
        }


        JobSeekerResume resume = new JobSeekerResume();

        resume.setJobSeekerProfile(profile);

        resume.setResumeName(request.getResumeName());

        resume.setOriginalFileName(request.getFileName());

        resume.setFileType(request.getFileType());

        resume.setFileData(cleanBase64);

        resume.setFileSize(
                calculateFileSize(
                        cleanBase64
                )
        );

        resume.setPrimary(
                Boolean.TRUE.equals(
                        request.getPrimary()
                )
        );


        JobSeekerResume savedResume =
                resumeRepository.save(
                        resume
                );

        return mapToResponse(
                savedResume
        );
    }

    // 2. GET ALL RESUMES
    @Override
    @Transactional(readOnly = true)
    public List<ResumeResponse> getMyResumes(
            String username) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        return resumeRepository
                .findByJobSeekerProfileId(
                        profile.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. GET RESUME BY ID
    @Override
    @Transactional(readOnly = true)
    public ResumeResponse getResume(
            String username,
            Long resumeId) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerResume resume =
                resumeRepository
                        .findByIdAndJobSeekerProfileId(
                                resumeId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.RESUME_NOT_FOUND
                                )
                        );

        return mapToResponse(
                resume
        );
    }

    // 4. UPDATE RESUME
    @Override
    @Transactional
    public ResumeResponse updateResume(
            String username,
            Long resumeId,
            UpdateResumeRequest request) {

        User user =
                getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerResume resume =
                resumeRepository
                        .findByIdAndJobSeekerProfileId(
                                resumeId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.RESUME_NOT_FOUND
                                )
                        );


        // Update resume name
        if (request.getResumeName() != null
                && !request.getResumeName().isBlank()) {

            resume.setResumeName(
                    request.getResumeName()
            );
        }


        // Update primary
        if (request.getPrimary() != null) {

            if (request.getPrimary()) {

                removePrimaryResume(
                        profile.getId()
                );

                resume.setPrimary(true);

            } else {

                resume.setPrimary(false);
            }
        }


        // Replace Base64 file
        if (request.getFileData() != null
                && !request.getFileData().isBlank()) {

            String cleanBase64 =
                    cleanBase64Data(
                            request.getFileData()
                    );

            validateBase64(cleanBase64);

            validateFileSize(cleanBase64);


            if (request.getFileType() != null
                    && !request.getFileType().isBlank()) {

                validateFileType(
                        request.getFileType()
                );

                resume.setFileType(
                        request.getFileType()
                );
            }


            if (request.getFileName() != null
                    && !request.getFileName().isBlank()) {

                resume.setOriginalFileName(
                        request.getFileName()
                );
            }


            resume.setFileData(
                    cleanBase64
            );

            resume.setFileSize(
                    calculateFileSize(
                            cleanBase64
                    )
            );
        }


        JobSeekerResume updatedResume =
                resumeRepository.save(
                        resume
                );

        return mapToResponse(
                updatedResume
        );
    }

    // 5. DELETE RESUME
    @Override
    @Transactional
    public void deleteResume(
            String username,
            Long resumeId) {

        User user =
                getUser(username);

        JobSeekerProfile profile =
                getProfile(user.getId());

        JobSeekerResume resume =
                resumeRepository
                        .findByIdAndJobSeekerProfileId(
                                resumeId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.RESUME_NOT_FOUND
                                )
                        );

        resumeRepository.delete(
                resume
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

    // GET PROFILE
    private JobSeekerProfile getProfile(
            Long userId) {

        return profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                        )
                );
    }

    // VALIDATE UPLOAD REQUEST
    private void validateUploadRequest(
            UploadResumeRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    AppConstant.RESUME_REQUEST_REQUIRED
            );
        }


        if (request.getResumeName() == null
                || request.getResumeName().isBlank()) {

            throw new IllegalArgumentException(
                    AppConstant.RESUME_NAME_REQUIRED
            );
        }


        if (request.getFileName() == null
                || request.getFileName().isBlank()) {

            throw new IllegalArgumentException(
                    AppConstant.FILE_NAME_REQUIRED
            );
        }


        if (request.getFileType() == null
                || request.getFileType().isBlank()) {

            throw new IllegalArgumentException(
                    AppConstant.FILE_TYPE_REQUIRED
            );
        }


        if (request.getFileData() == null
                || request.getFileData().isBlank()) {

            throw new IllegalArgumentException(
                    AppConstant.RESUME_FILE_DATA_REQUIRED
            );
        }
    }

    // FILE TYPE VALIDATION
    private void validateFileType(
            String fileType) {

        if (!fileType.equalsIgnoreCase(
                "application/pdf")
                && !fileType.equalsIgnoreCase(
                "application/msword")
                && !fileType.equalsIgnoreCase(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {

            throw new IllegalArgumentException(
                    AppConstant.SUPPORT_FILE_TYPE
            );
        }
    }

    // CLEAN BASE64
    private String cleanBase64Data(
            String fileData) {

        if (fileData == null) {
            return null;
        }

        if (fileData.contains(",")) {
            return fileData.substring(
                    fileData.indexOf(",") + 1
            );
        }
        return fileData;
    }

    // VALIDATE BASE64
    private void validateBase64(
            String fileData) {

        try {

            Base64.getDecoder()
                    .decode(fileData);

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_BASE64_DATA
            );
        }
    }

    // FILE SIZE
    private void validateFileSize(
            String base64Data) {

        long maxSize =
                5L * 1024 * 1024;

        long estimatedSize =
                calculateFileSize(
                        base64Data
                );

        if (estimatedSize > maxSize) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_FILE_SIZE
            );
        }
    }


    private long calculateFileSize(
            String base64Data) {

        int padding = 0;

        if (base64Data.endsWith("==")) {

            padding = 2;

        } else if (base64Data.endsWith("=")) {

            padding = 1;
        }

        return (base64Data.length() * 3L / 4L)
                - padding;
    }

    // PRIMARY RESUME
    private void removePrimaryResume(
            Long profileId) {

        resumeRepository
                .findByJobSeekerProfileId(
                        profileId
                )
                .forEach(resume -> {

                    if (Boolean.TRUE.equals(
                            resume.getPrimary())) {

                        resume.setPrimary(false);

                        resumeRepository.save(
                                resume
                        );
                    }
                });
    }

    // ENTITY → RESPONSE
    private ResumeResponse mapToResponse(
            JobSeekerResume resume) {

        ResumeResponse response =
                new ResumeResponse();

        response.setId(
                resume.getId()
        );

        response.setResumeName(
                resume.getResumeName()
        );

        response.setOriginalFileName(
                resume.getOriginalFileName()
        );

        response.setFileType(
                resume.getFileType()
        );

        response.setFileSize(
                resume.getFileSize()
        );

        response.setFileData(
                resume.getFileData()
        );

        response.setPrimary(
                resume.getPrimary()
        );

        response.setUploadedAt(
                resume.getUploadedAt()
        );

        response.setUpdatedAt(
                resume.getUpdatedAt()
        );

        return response;
    }
}