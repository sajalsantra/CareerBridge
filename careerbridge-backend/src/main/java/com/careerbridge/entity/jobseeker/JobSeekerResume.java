package com.careerbridge.entity.jobseeker;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_seeker_resumes")
public class JobSeekerResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "job_seeker_profile_id",
            nullable = false
    )
    private JobSeekerProfile jobSeekerProfile;

    @Column(
            name = "resume_name",
            nullable = false,
            length = 255
    )
    private String resumeName;

    @Column(
            name = "original_file_name",
            length = 255
    )
    private String originalFileName;

    @Column(
            name = "file_type",
            length = 100
    )
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Lob
    @Column(
            name = "file_data",
            nullable = false,
            columnDefinition = "LONGTEXT"
    )
    private String fileData;

    @Column(
            name = "is_primary",
            nullable = false
    )
    private Boolean primary = false;

    @Column(
            name = "uploaded_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime uploadedAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        uploadedAt = now;
        updatedAt = now;
    }


    @PreUpdate
    protected void onUpdate() {

        updatedAt =
                LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public JobSeekerProfile getJobSeekerProfile() {
        return jobSeekerProfile;
    }

    public void setJobSeekerProfile(
            JobSeekerProfile jobSeekerProfile) {

        this.jobSeekerProfile =
                jobSeekerProfile;
    }


    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }


    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(
            String originalFileName) {

        this.originalFileName =
                originalFileName;
    }


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }


    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }


    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }


    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}