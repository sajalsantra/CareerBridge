-- ============================================================
-- JOB SEARCH PORTAL
-- JOB SEEKER MODULE DATABASE
-- Database: CareerBridge
-- ============================================================

-- 1. CREATE DATABASE
CREATE DATABASE IF NOT EXISTS CareerBridge
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE CareerBridge;


-- ============================================================
-- 2. ROLES
-- ============================================================

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);


-- ============================================================
-- 3. USERS
-- ============================================================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    location VARCHAR(150),
    profile_image_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);


-- ============================================================
-- 4. USER ROLES
-- ============================================================

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);


-- ============================================================
-- 5. JOB SEEKER PROFILE
-- ============================================================

CREATE TABLE job_seeker_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    current_job_title VARCHAR(150),
    current_company VARCHAR(150),
    total_experience_years DECIMAL(4,2) DEFAULT 0,
    current_salary DECIMAL(12,2),
    expected_salary DECIMAL(12,2),
    notice_period_days INT,
    preferred_locations VARCHAR(500),
    preferred_job_type VARCHAR(50),
    preferred_work_mode VARCHAR(50),
    career_summary TEXT,
    profile_completion_percentage INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_job_seeker_profile_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_profile_completion
        CHECK (profile_completion_percentage BETWEEN 0 AND 100)
);


-- ============================================================
-- 6. SKILLS
-- ============================================================

CREATE TABLE skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- 7. JOB SEEKER SKILLS
-- ============================================================

CREATE TABLE job_seeker_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_seeker_profile_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    proficiency_level VARCHAR(50),
    years_of_experience DECIMAL(4,2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_seeker_skill_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_job_seeker_skill
        FOREIGN KEY (skill_id)
        REFERENCES skills(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_job_seeker_skill
        UNIQUE (job_seeker_profile_id, skill_id)
);


-- ============================================================
-- 8. EDUCATION
-- ============================================================
CREATE TABLE job_seeker_education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_seeker_profile_id BIGINT NOT NULL,
    degree VARCHAR(150) NOT NULL,
    field_of_study VARCHAR(150),
    institution_name VARCHAR(200) NOT NULL,
    location VARCHAR(200),
    start_date DATE,
    end_date DATE,
    is_current BOOLEAN NOT NULL DEFAULT FALSE,
    grade VARCHAR(50),
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,
           CONSTRAINT fk_education_profile
           FOREIGN KEY (job_seeker_profile_id)
           REFERENCES job_seeker_profiles(id)
       ON DELETE CASCADE
);
-- ============================================================
-- 9. WORK EXPERIENCE
-- ============================================================

CREATE TABLE experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_seeker_profile_id BIGINT NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    job_title VARCHAR(150) NOT NULL,
    employment_type VARCHAR(50),
    location VARCHAR(150),
    start_date DATE NOT NULL,
    end_date DATE,
    currently_working BOOLEAN NOT NULL DEFAULT FALSE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_experience_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE
);

-- ============================================================
-- 10. PROJECTS
-- ============================================================

CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_seeker_profile_id BIGINT NOT NULL,
    project_name VARCHAR(200) NOT NULL,
    description TEXT,
    technologies VARCHAR(500),
    project_url VARCHAR(500),
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_project_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE
);


-- ============================================================
-- 11. CERTIFICATIONS
-- ============================================================

CREATE TABLE certifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_seeker_profile_id BIGINT NOT NULL,
    certification_name VARCHAR(200) NOT NULL,
    issuing_organization VARCHAR(200),
    issue_date DATE,
    expiry_date DATE,
    credential_id VARCHAR(150),
    credential_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_certification_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE
);


-- ============================================================
-- 12. RESUMES
-- ============================================================

CREATE TABLE resumes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_seeker_profile_id BIGINT NOT NULL,
    resume_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255),
    file_path VARCHAR(500) NOT NULL,
    file_type VARCHAR(100),
    file_size BIGINT,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_resume_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE
);


-- ============================================================
-- 13. JOB CATEGORIES
-- ============================================================

CREATE TABLE job_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- 14. JOBS
-- ============================================================

CREATE TABLE jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_title VARCHAR(200) NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    company_logo_url VARCHAR(500),
    description TEXT NOT NULL,
    responsibilities TEXT,
    qualifications TEXT,
    location VARCHAR(150),
    job_type VARCHAR(50),
    work_mode VARCHAR(50),
    experience_min DECIMAL(4,2),
    experience_max DECIMAL(4,2),
    salary_min DECIMAL(12,2),
    salary_max DECIMAL(12,2),
    number_of_openings INT DEFAULT 1,
    category_id BIGINT,
    posted_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    application_deadline DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_job_category
        FOREIGN KEY (category_id)
        REFERENCES job_categories(id)
        ON DELETE SET NULL
);


-- ============================================================
-- 15. JOB SKILLS
-- ============================================================

CREATE TABLE job_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_job_skill_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_job_skill_skill
        FOREIGN KEY (skill_id)
        REFERENCES skills(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_job_skill
        UNIQUE (job_id, skill_id)
);


-- ============================================================
-- 16. APPLICATIONS
-- ============================================================

CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    job_seeker_profile_id BIGINT NOT NULL,
    resume_id BIGINT,
    cover_letter TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'APPLIED',
    recruiter_remarks TEXT,
    applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_application_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_application_resume
        FOREIGN KEY (resume_id)
        REFERENCES resumes(id)
        ON DELETE SET NULL,

    CONSTRAINT uk_job_seeker_application
        UNIQUE (job_id, job_seeker_profile_id)
);


-- ============================================================
-- 17. SAVED JOBS
-- ============================================================

CREATE TABLE saved_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    job_seeker_profile_id BIGINT NOT NULL,
    saved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_saved_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_saved_job_profile
        FOREIGN KEY (job_seeker_profile_id)
        REFERENCES job_seeker_profiles(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_saved_job
        UNIQUE (job_id, job_seeker_profile_id)
);


-- ============================================================
-- 18. NOTIFICATIONS
-- ============================================================

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    notification_type VARCHAR(100),
    reference_id BIGINT,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- ============================================================
-- 19. JOB REPORTS
-- ============================================================

CREATE TABLE job_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    reported_by BIGINT NOT NULL,
    reason VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,

    CONSTRAINT fk_report_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_report_user
        FOREIGN KEY (reported_by)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- ============================================================
-- 20. INDEXES
-- ============================================================

CREATE INDEX idx_users_email
    ON users(email);

CREATE INDEX idx_jobs_title
    ON jobs(job_title);

CREATE INDEX idx_jobs_location
    ON jobs(location);

CREATE INDEX idx_jobs_status
    ON jobs(status);

CREATE INDEX idx_jobs_type
    ON jobs(job_type);

CREATE INDEX idx_jobs_work_mode
    ON jobs(work_mode);

CREATE INDEX idx_jobs_category
    ON jobs(category_id);

CREATE INDEX idx_jobs_posted_date
    ON jobs(posted_date);

CREATE INDEX idx_jobs_deadline
    ON jobs(application_deadline);

CREATE INDEX idx_applications_job
    ON applications(job_id);

CREATE INDEX idx_applications_profile
    ON applications(job_seeker_profile_id);

CREATE INDEX idx_applications_status
    ON applications(status);

CREATE INDEX idx_saved_jobs_profile
    ON saved_jobs(job_seeker_profile_id);

CREATE INDEX idx_notifications_user
    ON notifications(user_id);

CREATE INDEX idx_notifications_read
    ON notifications(is_read);

CREATE INDEX idx_reports_status
    ON job_reports(status);


-- ============================================================
-- 21. INITIAL ROLES
-- ============================================================
INSERT INTO roles (name)
VALUES
    ('JOB_SEEKER'),
    ('RECRUITER'),
    ('ADMIN');


-- ============================================================
-- 22. INITIAL JOB CATEGORIES
-- ============================================================
INSERT INTO job_categories (name, description)
VALUES
    ('Software Development', 'Software and application development jobs'),
    ('Data Science', 'Data science, analytics and machine learning jobs'),
    ('DevOps', 'DevOps and infrastructure jobs'),
    ('Cloud', 'Cloud computing and cloud engineering jobs'),
    ('Cyber Security', 'Cyber security and information security jobs'),
    ('Testing', 'Software testing and quality assurance jobs'),
    ('UI/UX', 'UI and UX design jobs'),
    ('Product Management', 'Product management jobs'),
    ('Business Analysis', 'Business analyst jobs'),
    ('Finance', 'Finance and accounting jobs'),
    ('Human Resources', 'Human resources jobs'),
    ('Marketing', 'Marketing and digital marketing jobs');


-- ============================================================
-- 23. INITIAL SKILLS
-- ============================================================

INSERT INTO skills (name)
VALUES
    ('Java'),
    ('Spring Boot'),
    ('Spring MVC'),
    ('Spring Security'),
    ('Hibernate'),
    ('JPA'),
    ('Angular'),
    ('TypeScript'),
    ('JavaScript'),
    ('HTML'),
    ('CSS'),
    ('SQL'),
    ('MySQL'),
    ('Oracle'),
    ('PL/SQL'),
    ('REST API'),
    ('Microservices'),
    ('Git'),
    ('GitHub'),
    ('Docker'),
    ('Kubernetes'),
    ('AWS'),
    ('Azure'),
    ('Python'),
    ('React'),
    ('Node.js'),
    ('C'),
    ('C++'),
    ('Data Structures'),
    ('Algorithms');


-- ============================================================
-- 24. VERIFY TABLES
-- ============================================================
SHOW TABLES;