package com.example.rus_r.model;

public class Lesson {
    private String id;
    private String subjectId;
    private String title;
    private String description;
    private String content; // Text content
    private String googleDriveFileId; // NEW: Google Drive file ID
    private String googleDriveFileName; // NEW: File name from Drive
    private String googleDriveDownloadUrl; // NEW: Download URL
    private long createdAt;
    private long updatedAt;

    // Constructor
    public Lesson() {
    }

    public Lesson(String id, String subjectId, String title, String description, String content) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoogleDriveFileId() {
        return googleDriveFileId;
    }

    public void setGoogleDriveFileId(String googleDriveFileId) {
        this.googleDriveFileId = googleDriveFileId;
    }

    public String getGoogleDriveFileName() {
        return googleDriveFileName;
    }

    public void setGoogleDriveFileName(String googleDriveFileName) {
        this.googleDriveFileName = googleDriveFileName;
    }

    public String getGoogleDriveDownloadUrl() {
        return googleDriveDownloadUrl;
    }

    public void setGoogleDriveDownloadUrl(String googleDriveDownloadUrl) {
        this.googleDriveDownloadUrl = googleDriveDownloadUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}