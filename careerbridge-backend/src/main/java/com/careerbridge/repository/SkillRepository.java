package com.careerbridge.repository;

import com.careerbridge.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository
        extends JpaRepository<Skill, Long> {

    // Find skill by exact name
    Optional<Skill> findByName(String name);

    // Check whether a skill already exists
    boolean existsByName(String name);
}