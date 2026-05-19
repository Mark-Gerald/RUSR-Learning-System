package com.example.rus_r.model;

public class Lesson {
    private String id;
    private String subjectId;
    private String title;
    private String description;
    private String fileUrl;
    private String fileType; // "pdf", "ppt", "image", "notes", "document"
    private long createdAt;
    private long updatedAt;
    private long fileSize;

    // Constructor
    public Lesson() {
    }

    public Lesson(String id, String subjectId, String title, String description,
                  String fileUrl, String fileType) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}