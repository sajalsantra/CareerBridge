package com.careerbridge.service.impl.jobseeker;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.jobseeker.skills.AddSkillRequest;
import com.careerbridge.dto.jobseeker.skills.SkillResponse;
import com.careerbridge.dto.jobseeker.skills.UpdateSkillRequest;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.entity.jobseeker.JobSeekerSkill;
import com.careerbridge.entity.Skill;
import com.careerbridge.entity.User;
import com.careerbridge.exception.DuplicateResourceException;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.repository.jobseeker.JobSeekerSkillRepository;
import com.careerbridge.repository.SkillRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.service.jobseeker.JobSeekerSkillService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobSeekerSkillServiceImpl
        implements JobSeekerSkillService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final JobSeekerSkillRepository jobSeekerSkillRepository;
    private final SkillRepository skillRepository;

    public JobSeekerSkillServiceImpl(
            UserRepository userRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            JobSeekerSkillRepository jobSeekerSkillRepository,
            SkillRepository skillRepository) {

        this.userRepository = userRepository;
        this.jobSeekerProfileRepository =
                jobSeekerProfileRepository;
        this.jobSeekerSkillRepository =
                jobSeekerSkillRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillResponse addSkill(
            String username,
            AddSkillRequest request) {

        // 1. Find logged-in user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );

        // 2. Find Job Seeker Profile
        JobSeekerProfile profile =
                jobSeekerProfileRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                                )
                        );

        // 3. Find Skill
        Skill skill = skillRepository
                .findById(request.getSkillId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.SKILL_NOT_FOUND
                        )
                );

        // 4. Check duplicate skill
        if (jobSeekerSkillRepository
                .existsByJobSeekerProfileIdAndSkillId(
                        profile.getId(),
                        skill.getId())) {

            throw new DuplicateResourceException(
                    AppConstant.SKILL_ALREADY_EXISTS
            );
        }

        // 5. Create JobSeekerSkill
        JobSeekerSkill jobSeekerSkill =
                new JobSeekerSkill();

        jobSeekerSkill.setJobSeekerProfile(profile);
        jobSeekerSkill.setSkill(skill);
        jobSeekerSkill.setProficiencyLevel(
                request.getProficiencyLevel()
        );
        jobSeekerSkill.setYearsOfExperience(
                request.getYearsOfExperience()
        );

        // 6. Save
        JobSeekerSkill saved =
                jobSeekerSkillRepository.save(
                        jobSeekerSkill
                );

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getMySkills(
            String username) {

        // 1. Find logged-in user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );

        // 2. Find profile
        JobSeekerProfile profile =
                jobSeekerProfileRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                                )
                        );

        // 3. Get skills
        List<JobSeekerSkill> skills =
                jobSeekerSkillRepository
                        .findByJobSeekerProfileId(
                                profile.getId()
                        );

        // 4. Convert Entity → Response DTO
        return skills.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SkillResponse updateSkill(
            String username,
            Long skillId,
            UpdateSkillRequest request) {

        // 1. Find logged-in user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );

        // 2. Find Job Seeker Profile
        JobSeekerProfile profile =
                jobSeekerProfileRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                                )
                        );

        // 3. Find skill belonging to this profile
        JobSeekerSkill jobSeekerSkill =
                jobSeekerSkillRepository
                        .findByIdAndJobSeekerProfileId(
                                skillId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.SKILL_NOT_FOUND
                                )
                        );

        // 4. Update
        jobSeekerSkill.setProficiencyLevel(
                request.getProficiencyLevel()
        );

        if (request.getYearsOfExperience() != null) {
            jobSeekerSkill.setYearsOfExperience(
                    request.getYearsOfExperience()
            );
        }

        // 5. Save
        JobSeekerSkill updated =
                jobSeekerSkillRepository.save(
                        jobSeekerSkill
                );

        return mapToResponse(updated);
    }

    @Override
    public void deleteSkill(
            String username,
            Long skillId) {

        // 1. Find logged-in user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );

        // 2. Find Job Seeker Profile
        JobSeekerProfile profile =
                jobSeekerProfileRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.JOBSEEKER_PROFILE_NOT_FOUND
                                )
                        );

        // 3. Find skill belonging to this profile
        JobSeekerSkill jobSeekerSkill =
                jobSeekerSkillRepository
                        .findByIdAndJobSeekerProfileId(
                                skillId,
                                profile.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        AppConstant.SKILL_NOT_FOUND
                                )
                        );

        // 4. Delete
        jobSeekerSkillRepository.delete(
                jobSeekerSkill
        );
    }

    // Entity → Response DTO
    private SkillResponse mapToResponse(
            JobSeekerSkill entity) {

        SkillResponse response =
                new SkillResponse();

        response.setId(entity.getId());

        response.setSkillId(
                entity.getSkill().getId()
        );

        response.setSkillName(
                entity.getSkill().getName()
        );

        response.setProficiencyLevel(
                entity.getProficiencyLevel()
        );

        response.setYearsOfExperience(
                entity.getYearsOfExperience()
        );

        response.setCreatedAt(
                entity.getCreatedAt()
        );

        return response;
    }
}