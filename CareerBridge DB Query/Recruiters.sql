-- 1. COMPANY
CREATE TABLE companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(200) NOT NULL,
    description TEXT,
    website VARCHAR(500),
    industry VARCHAR(150),
    company_size VARCHAR(100),
    location VARCHAR(255),
    logo_url VARCHAR(500),
    founded_year INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_company_name
        UNIQUE (company_name)
);


-- 2. RECRUITER PROFILES
CREATE TABLE recruiter_profiles(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    company_id BIGINT NULL,
    designation VARCHAR(150),
    bio TEXT,
    location VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_recruiter_profile_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_recruiter_profile_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id)
        ON DELETE SET NULL
);

-- 14. JOBS
CREATE TABLE jobs(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recruiter_profile_id BIGINT NOT NULL,
    company_id BIGINT NOT NULL,
    job_title VARCHAR(200) NOT NULL,
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
    number_of_openings INT NOT NULL DEFAULT 1,
    category_id BIGINT,
    posted_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    application_deadline DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_job_recruiter
        FOREIGN KEY (recruiter_profile_id)
        REFERENCES recruiter_profiles(id)
       ON DELETE CASCADE,

    CONSTRAINT fk_job_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_job_category
        FOREIGN KEY (category_id)
        REFERENCES job_categories(id)
        ON DELETE SET NULL
);



-- INDEXES
CREATE INDEX idx_recruiter_profiles_company
    ON recruiter_profiles(company_id);

CREATE INDEX idx_recruiter_profiles_designation
    ON recruiter_profiles(designation);

CREATE INDEX idx_recruiter_profiles_location
    ON recruiter_profiles(location);

CREATE INDEX idx_companies_industry
    ON companies(industry);

CREATE INDEX idx_companies_location
    ON companies(location);

CREATE INDEX idx_jobs_recruiter
    ON jobs(recruiter_profile_id);

CREATE INDEX idx_jobs_company
    ON jobs(company_id);

CREATE INDEX idx_jobs_category
    ON jobs(category_id);

CREATE INDEX idx_jobs_status
    ON jobs(status);

CREATE INDEX idx_jobs_location
    ON jobs(location);

CREATE INDEX idx_jobs_status_posted
    ON jobs(status, posted_date);

