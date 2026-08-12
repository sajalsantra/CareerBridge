package com.careerbridge.service.impl;

import com.careerbridge.dto.auth.LoginRequest;
import com.careerbridge.dto.auth.RegisterRequest;
import com.careerbridge.dto.response.AuthResponse;
import com.careerbridge.entity.JobSeekerProfile;
import com.careerbridge.entity.Role;
import com.careerbridge.entity.User;
import com.careerbridge.entity.UserRole;
import com.careerbridge.entity.UserRoleId;
import com.careerbridge.repository.JobSeekerProfileRepository;
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

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Email already registered"
            );
        }

        Role jobSeekerRole = roleRepository
                .findByName("JOB_SEEKER")
                .orElseThrow(() ->
                        new RuntimeException(
                                "JOB_SEEKER role not found"
                        )
                );

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setActive(true);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

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
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
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
                user.getEmail(),
                roles
        );
    }
}