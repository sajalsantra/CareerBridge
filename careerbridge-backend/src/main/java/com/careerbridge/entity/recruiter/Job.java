package com.careerbridge.entity.recruiter;

import com.careerbridge.entity.company.Company;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "jobs",
        indexes = {
                @Index(name = "idx_jobs_recruiter", columnList = "recruiter_profile_id"),
                @Index(name = "idx_jobs_company", columnList = "company_id"),
                @Index(name = "idx_jobs_category", columnList = "category_id"),
                @Index(name = "idx_jobs_status", columnList = "status"),
                @Index(name = "idx_jobs_location", columnList = "location")
        }
)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "recruiter_profile_id",
            nullable = false
    )
    private RecruiterProfile recruiterProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    private Company company;

    @Column(
            name = "job_title",
            nullable = false,
            length = 200
    )
    private String jobTitle;


    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;


    @Column(
            name = "responsibilities",
            columnDefinition = "TEXT"
    )
    private String responsibilities;


    @Column(
            name = "qualifications",
            columnDefinition = "TEXT"
    )
    private String qualifications;


    @Column(
            name = "location",
            length = 150
    )
    private String location;


    @Column(
            name = "job_type",
            length = 50
    )
    private String jobType;


    @Column(
            name = "work_mode",
            length = 50
    )
    private String workMode;

    @Column(
            name = "experience_min",
            precision = 4,
            scale = 2
    )
    private BigDecimal experienceMin;


    @Column(
            name = "experience_max",
            precision = 4,
            scale = 2
    )
    private BigDecimal experienceMax;

    @Column(
            name = "salary_min",
            precision = 12,
            scale = 2
    )
    private BigDecimal salaryMin;


    @Column(
            name = "salary_max",
            precision = 12,
            scale = 2
    )
    private BigDecimal salaryMax;

    @Column(
            name = "number_of_openings",
            nullable = false
    )
    private Integer numberOfOpenings = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id"
    )
    private JobCategory category;

    @Column(
            name = "posted_date",
            nullable = false
    )
    private LocalDateTime postedDate;


    @Column(
            name = "application_deadline"
    )
    private LocalDate applicationDeadline;

    @Column(
            name = "status",
            nullable = false,
            length = 50
    )
    private String status = "ACTIVE";

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        if (postedDate == null) {
            postedDate = now;
        }

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (status == null) {
            status = "ACTIVE";
        }

        if (numberOfOpenings == null) {
            numberOfOpenings = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(
            RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }


    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }


    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }


    public BigDecimal getExperienceMin() {
        return experienceMin;
    }

    public void setExperienceMin(BigDecimal experienceMin) {
        this.experienceMin = experienceMin;
    }


    public BigDecimal getExperienceMax() {
        return experienceMax;
    }

    public void setExperienceMax(BigDecimal experienceMax) {
        this.experienceMax = experienceMax;
    }


    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }


    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }


    public Integer getNumberOfOpenings() {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings(Integer numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }


    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }


    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }


    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(
            LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}