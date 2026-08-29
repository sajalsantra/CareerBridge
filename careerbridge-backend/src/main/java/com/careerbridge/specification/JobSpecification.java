package com.careerbridge.specification;

import com.careerbridge.entity.recruiter.Job;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class JobSpecification {

    private JobSpecification() {
    }

    public static Specification<Job> activeJobs() {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.upper(
                                root.get("status")
                        ),
                        "ACTIVE"
                );
    }


    public static Specification<Job> keyword(
            String keyword) {

        return (root, query, criteriaBuilder) -> {

            if (keyword == null
                    || keyword.trim().isEmpty()) {

                return criteriaBuilder.conjunction();
            }

            String value =
                    "%" + keyword.trim().toLowerCase() + "%";

            return criteriaBuilder.or(

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("jobTitle")
                            ),
                            value
                    ),

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("description")
                            ),
                            value
                    ),

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("qualifications")
                            ),
                            value
                    ),

                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("responsibilities")
                            ),
                            value
                    )
            );
        };
    }


    public static Specification<Job> location(
            String location) {

        return (root, query, criteriaBuilder) -> {

            if (location == null
                    || location.trim().isEmpty()) {

                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(

                    criteriaBuilder.lower(
                            root.get("location")
                    ),

                    "%" + location.trim().toLowerCase() + "%"
            );
        };
    }


    public static Specification<Job> category(
            Long categoryId) {

        return (root, query, criteriaBuilder) -> {

            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("category").get("id"),
                    categoryId
            );
        };
    }

    public static Specification<Job> jobType(
            String jobType) {

        return (root, query, criteriaBuilder) -> {

            if (jobType == null
                    || jobType.trim().isEmpty()) {

                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(

                    criteriaBuilder.upper(
                            root.get("jobType")
                    ),

                    jobType.trim().toUpperCase()
            );
        };
    }


    public static Specification<Job> workMode(
            String workMode) {

        return (root, query, criteriaBuilder) -> {

            if (workMode == null
                    || workMode.trim().isEmpty()) {

                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(

                    criteriaBuilder.upper(
                            root.get("workMode")
                    ),

                    workMode.trim().toUpperCase()
            );
        };
    }

    public static Specification<Job> experience(
            BigDecimal minExperience,
            BigDecimal maxExperience) {

        return (root, query, criteriaBuilder) -> {

            Specification<Job> specification =
                    (r, q, cb) ->
                            cb.conjunction();

            if (minExperience != null) {

                specification =
                        specification.and(
                                (r, q, cb) ->
                                        cb.greaterThanOrEqualTo(
                                                r.get("experienceMax"),
                                                minExperience
                                        )
                        );
            }

            if (maxExperience != null) {

                specification =
                        specification.and(
                                (r, q, cb) ->
                                        cb.lessThanOrEqualTo(
                                                r.get("experienceMin"),
                                                maxExperience
                                        )
                        );
            }

            return specification.toPredicate(
                    root,
                    query,
                    criteriaBuilder
            );
        };
    }


    public static Specification<Job> salary(
            BigDecimal minSalary,
            BigDecimal maxSalary) {

        return (root, query, criteriaBuilder) -> {

            Specification<Job> specification =
                    (r, q, cb) ->
                            cb.conjunction();

            if (minSalary != null) {

                specification =
                        specification.and(
                                (r, q, cb) ->
                                        cb.greaterThanOrEqualTo(
                                                r.get("salaryMax"),
                                                minSalary
                                        )
                        );
            }

            if (maxSalary != null) {

                specification =
                        specification.and(
                                (r, q, cb) ->
                                        cb.lessThanOrEqualTo(
                                                r.get("salaryMin"),
                                                maxSalary
                                        )
                        );
            }

            return specification.toPredicate(
                    root,
                    query,
                    criteriaBuilder
            );
        };
    }
}