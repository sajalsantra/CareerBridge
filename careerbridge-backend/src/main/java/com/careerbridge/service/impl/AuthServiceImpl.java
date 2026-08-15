package com.careerbridge.service.impl;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.dto.auth.LoginRequest;
import com.careerbridge.dto.auth.RegisterRequest;
import com.careerbridge.dto.response.AuthResponse;
import com.careerbridge.entity.jobseeker.JobSeekerProfile;
import com.careerbridge.entity.Role;
import com.careerbridge.entity.User;
import com.careerbridge.entity.UserRole;
import com.careerbridge.entity.UserRoleId;
import com.careerbridge.exception.DuplicateResourceException;
import com.careerbridge.exception.ResourceNotFoundException;
import com.careerbridge.repository.jobseeker.JobSeekerProfileRepository;
import com.careerbridge.repository.RoleRepository;
import com.careerbridge.repository.UserRepository;
import com.careerbridge.repository.UserRoleRepository;
import com.careerbridge.security.JwtService;
import com.careerbridge.service.AuthService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        // 1. Check username
        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new DuplicateResourceException(
                    "Username already registered"
            );
        }

        // 2. Check email
        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already registered"
            );
        }

        // 3. Find JOB_SEEKER role
        Role jobSeekerRole = roleRepository
                .findByName("JOB_SEEKER")
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "JOB_SEEKER role not found"
                        )
                );

        // 4. Create User
        User user = new User();

        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // 5. Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setActive(true);
        user.setEmailVerified(false);

        // 6. Save User
        User savedUser =
                userRepository.save(user);

        // 7. Create UserRole
        UserRole userRole = new UserRole();

        userRole.setUser(savedUser);
        userRole.setRole(jobSeekerRole);

        userRole.setId(
                new UserRoleId(
                        savedUser.getId(),
                        jobSeekerRole.getId()
                )
        );

        userRoleRepository.save(userRole);

        // 8. Create Job Seeker Profile
        JobSeekerProfile profile =
                new JobSeekerProfile();

        profile.setUser(savedUser);
        profile.setProfileCompletionPercentage(0);

        jobSeekerProfileRepository.save(profile);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsernameOrEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        User user = userRepository
                .findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );

        List<String> roles =
                user.getUserRoles()
                        .stream()
                        .map(userRole ->
                                userRole.getRole().getName()
                        )
                        .toList();

        return new AuthResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                roles
        );
    }
}