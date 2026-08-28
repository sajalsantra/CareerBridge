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


