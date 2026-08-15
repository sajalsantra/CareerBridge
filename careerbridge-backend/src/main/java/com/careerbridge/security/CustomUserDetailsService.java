package com.careerbridge.security;

import com.careerbridge.constant.AppConstant;
import com.careerbridge.entity.User;
import com.careerbridge.repository.UserRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull UserDetails loadUserByUsername(@NonNull String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameOrEmailWithRoles(
                        usernameOrEmail,
                        usernameOrEmail
                )
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                AppConstant.USER_NOT_FOUND
                        )
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        user.getUserRoles()
                                .stream()
                                .map(userRole ->
                                        new SimpleGrantedAuthority(
                                                "ROLE_" +
                                                        userRole
                                                                .getRole()
                                                                .getName()
                                        )
                                )
                                .collect(Collectors.toList())
                )
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.getActive())
                .build();
    }

}