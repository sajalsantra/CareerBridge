package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.project.AddProjectRequest;
import com.careerbridge.dto.jobseeker.project.ProjectResponse;
import com.careerbridge.dto.jobseeker.project.UpdateProjectRequest;
import com.careerbridge.entity.jobseeker.JobSeekerProject;
import com.careerbridge.entity.User;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerProjectRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.service.jobseeker.JobSeekerProjectService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobSeekerProjectServiceImpl
        implements JobSeekerProjectService {

    private final UserRepository userRepository;

    private final JobSeekerProfileRepository profileRepository;

    private final JobSeekerProjectRepository projectRepository;


    public JobSeekerProjectServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository,
            JobSeekerProjectRepository projectRepository) {

        this.userRepository = userRepository;

        this.profileRepository = profileRepository;

        this.projectRepository = projectRepository;
    }

    // 1. ADD PROJECT
    @Override
    @Transactional
    public ProjectResponse addProject(
            String username,
            AddProjectRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        validateProjectUrl(
                request.getProjectUrl()
        );


        JobSeekerProject project =
                new JobSeekerProject();

        project.setJobSeekerProfile(
                profile
        );

        project.setProjectName(
                request.getProjectName().trim()
        );

        project.setDescription(
                trimValue(
                        request.getDescription()
                )
        );

        project.setTechnologies(
                trimValue(
                        request.getTechnologies()
                )
        );

        project.setProjectUrl(
                trimValue(
                        request.getProjectUrl()
                )
        );

        project.setStartDate(
                request.getStartDate()
        );

        project.setEndDate(
                request.getEndDate()
        );


        JobSeekerProject savedProject =
                projectRepository.save(
                        project
                );

        return mapToResponse(
                savedProject
        );
    }

    // 2. GET ALL PROJECTS
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getMyProjects(
            String username) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        return projectRepository
                .findByJobSeekerProfileId(
                        profile.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. GET PROJECT BY ID
    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProject(
            String username,
            Long projectId) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerProject project =
                projectRepository
                        .findByIdAndJobSeekerProfileId(
                                projectId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.PROJECT_NOT_FOUND
                                )
                        );

        return mapToResponse(
                project
        );
    }

    // 4. UPDATE PROJECT
    @Override
    @Transactional
    public ProjectResponse updateProject(
            String username,
            Long projectId,
            UpdateProjectRequest request) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerProject project =
                projectRepository
                        .findByIdAndJobSeekerProfileId(
                                projectId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.PROJECT_NOT_FOUND
                                )
                        );


        /*
         * Use the existing values when a field is
         * not provided in the update request.
         */

        LocalDate startDate =
                request.getStartDate() != null
                        ? request.getStartDate()
                        : project.getStartDate();

        LocalDate endDate =
                request.getEndDate() != null
                        ? request.getEndDate()
                        : project.getEndDate();


        validateDates(
                startDate,
                endDate
        );


        /*
         * Only validate URL when the user is actually
         * providing a new URL.
         */

        if (request.getProjectUrl() != null) {

            validateProjectUrl(
                    request.getProjectUrl()
            );
        }


        // Project name
        if (request.getProjectName() != null
                && !request.getProjectName().isBlank()) {

            project.setProjectName(
                    request.getProjectName().trim()
            );
        }


        // Description
        if (request.getDescription() != null) {

            project.setDescription(
                    trimValue(
                            request.getDescription()
                    )
            );
        }


        // Technologies
        if (request.getTechnologies() != null) {

            project.setTechnologies(
                    trimValue(
                            request.getTechnologies()
                    )
            );
        }


        // Project URL
        if (request.getProjectUrl() != null) {

            project.setProjectUrl(
                    trimValue(
                            request.getProjectUrl()
                    )
            );
        }


        // Start date
        if (request.getStartDate() != null) {

            project.setStartDate(
                    request.getStartDate()
            );
        }


        // End date
        if (request.getEndDate() != null) {

            project.setEndDate(
                    request.getEndDate()
            );
        }


        JobSeekerProject updatedProject =
                projectRepository.save(
                        project
                );

        return mapToResponse(
                updatedProject
        );
    }

    // 5. DELETE PROJECT
    @Override
    @Transactional
    public void deleteProject(
            String username,
            Long projectId) {

        User user = getUser(username);

        JobSeekerProfile profile = getProfile(user.getId());

        JobSeekerProject project =
                projectRepository
                        .findByIdAndJobSeekerProfileId(
                                projectId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.PROJECT_NOT_FOUND
                                )
                        );

        projectRepository.delete(
                project
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

    // DATE VALIDATION
    private void validateDates(
            LocalDate startDate,
            LocalDate endDate) {

        if (startDate != null
                && endDate != null
                && endDate.isBefore(startDate)) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_PROJECT_END_DATE
            );
        }
    }

    // URL VALIDATION
    private void validateProjectUrl(
            String projectUrl) {

        if (projectUrl == null || projectUrl.isBlank()) {

            return;
        }

        String url = projectUrl.trim();

        if (!url.startsWith("http://")
                && !url.startsWith("https://")) {

            throw new IllegalArgumentException(
                    AppConstant.INVALID_PROJECT_URL
            );
        }
    }

    // TRIM VALUE
    private String trimValue(
            String value) {

        if (value == null) {
            return null;
        }

        String trimmed = value.trim();

        return trimmed.isEmpty()
                ? null
                : trimmed;
    }

    // ENTITY → RESPONSE
    private ProjectResponse mapToResponse(
            JobSeekerProject project) {

        ProjectResponse response =
                new ProjectResponse();

        response.setId(
                project.getId()
        );

        response.setProjectName(
                project.getProjectName()
        );

        response.setDescription(
                project.getDescription()
        );

        response.setTechnologies(
                project.getTechnologies()
        );

        response.setProjectUrl(
                project.getProjectUrl()
        );

        response.setStartDate(
                project.getStartDate()
        );

        response.setEndDate(
                project.getEndDate()
        );

        response.setCreatedAt(
                project.getCreatedAt()
        );

        response.setUpdatedAt(
                project.getUpdatedAt()
        );

        return response;
    }
}