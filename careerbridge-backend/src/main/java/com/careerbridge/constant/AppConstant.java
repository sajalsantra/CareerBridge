package com.careerbridge.constant;

public class AppConstant {

    public final static String APP_NAME = "CareerBridge";
    public final static String APP_VERSION = "1.0";
    public final static String APP_DESCRIPTION = "Connecting the right people with the right opportunities.";

    // Database
    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://localhost:3306/CareerBridge?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    public final static String DB_USER = "root";
    public final static String DB_PASSWORD = "Sajal@123#123";

    // JWT
    public final static String JWT_SECRET = "CareerBridgeSuperSecretKeyForJwtAuthentication2026VeryLongSecretKey";
    public final static String JWT_TOKEN_TYPE = "Bearer ";
    public final static String JWT_TOKEN_EXPIRES_IN = "86400000";
    public final static String JWT_TOKEN_REFRESH_TOKEN = "refreshToken";
    public final static String JWT_TOKEN_ISSUER = "CareerBridge";
    public final static String JWT_TOKEN_VALIDATION_FAILED = "JWT validation failed: ";

    // API Endpoints
    public static final String AUTH_BASE = "/api/auth";
    public static final String JOB_SEEKER_PROFILE_BASE = "/api/job-seeker";
    public static final String JOB_SEEKER_SKILL_BASE = "/api/job-seeker/skills";
    public static final String JOB_SEEKER_EDUCATION_BASE = "/api/job-seeker/education";
    public static final String JOB_SEEKER_EXPERIENCE_BASE = "/api/job-seeker/experience";
    public static final String JOB_SEEKER_RESUME_BASE = "/api/job-seeker/resume";
    public static final String JOB_SEEKER_CERTIFICATION_BASE = "/api/job-seeker/certification";

    // Massage
    public final static String REGISTRATION_SUCCEEDED = "Registration successful";
    public final static String REGISTRATION_FAILED = "Registration failed";
    public final static String LOGIN_SUCCEEDED = "Login successful";
    public final static String LOGIN_FAILED = "Login failed";
    public final static String UNAUTHORIZED = "Bad credentials. Please check your username/email and password.";;
    public final static String EDUCATION_DELETE_SUCCEEDED = "Education deleted successfully.";
    public final static String PROFILE_FETCHED_SUCCEEDED = "Profile fetched successfully";
    public final static String PROFILE_UPDATED_SUCCEEDED = "Profile updated successfully";
    public final static String SKILL_DELETE_SUCCEEDED = "Skill deleted successfully.";
    public final static String SKILL_UPDATED_SUCCEEDED = "Skill updated successfully";
    public final static String INTERNAL_SERVER_ERROR = "Something went wrong. Please try again later.";
    public final static String USER_NOT_FOUND = "User not found";
    public final static String USER_ALREADY_EXISTS = "User already exists";
    public final static String USER_ALREADY_REGISTERED = "User already registered";
    public final static String EDUCATION_NOT_FOUND = "Education record not found.";
    public final static String EDUCATION_ALREADY_EXISTS = "Education record already exists";
    public final static String JOBSEEKER_PROFILE_NOT_FOUND =  "Job Seeker profile not found.";
    public final static String SKILL_NOT_FOUND = "Skill not found in your profile";
    public final static String SKILL_ALREADY_EXISTS = "Skill already added to your profile";
    public final static String EXPERIENCE_NOT_FOUND = "Experience record not found.";
    public final static String EXPERIENCE_ALREADY_EXISTS = "Experience already exists";
    public final static String EXPERIENCE_UPDATED = "Experience updated successfully.";
    public final static String EXPERIENCE_DELETED = "Experience deleted successfully.";
    public final static String RESUME_DELETE_SUCCEEDED = "Resume deleted successfully.";
    public final static String RESUME_NOT_FOUND = "Resume not found.";
    public final static String RESUME_REQUEST_REQUIRED = "Resume request is required.";
    public final static String RESUME_NAME_REQUIRED = "Resume name is required.";
    public final static String RESUME_FILE_DATA_REQUIRED = "Resume file data is required.";
    public final static String FILE_NAME_REQUIRED = "File name is required.";
    public final static String FILE_TYPE_REQUIRED = "File type is required.";
    public final static String SUPPORT_FILE_TYPE = "Only PDF, DOC and DOCX resume files are allowed.";
    public final static String INVALID_BASE64_DATA = "Invalid Base64 resume data.";
    public final static String INVALID_FILE_SIZE = "Resume file size must not exceed 5 MB.";
    public final static String CERTIFICATE_NOT_FOUND = "Certification not found.";
    public final static String INVALID_EXPIRY_DATE = "Expiry date cannot be before issue date.";
    public final static String INVALID_CERTIFICATE_URL = "Credential URL must start with http:// or https://.";
    public final static String CERTIFICATE_DELETED = "Certification deleted successfully.";


}
