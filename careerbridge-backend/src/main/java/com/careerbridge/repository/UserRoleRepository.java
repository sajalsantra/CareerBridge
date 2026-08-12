package com.careerbridge.repository;

import com.careerbridge.entity.UserRole;
import com.careerbridge.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}